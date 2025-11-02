package service;

import dao.Booking;
import util_res.UtilDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class BookingImplimentation implements Booking {
    Connection conn= UtilDb.getConnection();
    Scanner sc=new Scanner(System.in);
    @Override
    public void addBooking(int roomId,int personId) {
        try{

            LocalDate checkIn = null;
            LocalDate checkOut = null;
            LocalDate today = LocalDate.now();

            while (true) {
                System.out.print("Enter Check-In Date (YYYY-MM-DD): ");
                String checkInStr = sc.next();
                checkIn = LocalDate.parse(checkInStr);

                if (checkIn.isBefore(today)) {
                    System.out.println(" Check-in date cannot be in the past. Please enter a valid date.");
                } else {
                    break;
                }
            }
            while (true) {
                System.out.print("Enter Check-Out Date (YYYY-MM-DD): ");
                String checkOutStr = sc.next();
                checkOut = LocalDate.parse(checkOutStr);

                if (!checkOut.isAfter(checkIn)) {
                    System.out.println(" Check-out must be after check-in. Please enter a valid date.");
                } else {
                    break;
                }
            }
            long days = ChronoUnit.DAYS.between(checkIn, checkOut);
            if (days == 0) {
                System.out.println("Minimum stay must be at least 1 day.");
            } else {
                System.out.println("Stay duration: " + days + " nights");
            }
            double roomPrice=0;
            PreparedStatement checkRooms = conn.prepareStatement("select count(*) from reservation where roomId=? AND status in ('BOOKED','PAID') AND (? < checkOut AND ? > checkIn)");
            checkRooms.setInt(1, roomId);
            checkRooms.setLong(2, checkIn.until(LocalDate.now(), ChronoUnit.DAYS));
            checkRooms.setLong(3, checkOut.until(checkIn, ChronoUnit.DAYS));
            ResultSet checkRs = checkRooms.executeQuery();
            if (checkRs.next() && checkRs.getInt(1) > 0){
                System.out.println("Room is Already Booked!");
            }else {
                try{
                    PreparedStatement pst = conn.prepareStatement("SELECT * FROM room WHERE roomId=?");
                    pst.setInt(1, roomId);
                    ResultSet rs = pst.executeQuery();
                    while (rs.next()) {
                        roomPrice = rs.getDouble("price");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                double total=roomPrice*days;
                System.out.println("Total Amount Night :"+total);

                System.out.println("Do you Want to Confirm Reservation? (Y)");
                char confirm = sc.next().charAt(0);

                if (confirm == 'Y' || confirm == 'y') {
                    double wallet=0.00;
                    PreparedStatement pst = conn.prepareStatement("SELECT * FROM persons WHERE id=?");
                    pst.setInt(1, personId);
                    ResultSet rs = pst.executeQuery();
                    while (rs.next()) {
                        wallet = rs.getDouble("balance");
                    }
                    if(wallet<total){
                        System.out.println("Insufficient Balance in Wallet Plz Recharge.....");
                    }
                    else{
                        try {
                            double newBalance=wallet-total;
                            PreparedStatement pst1 = conn.prepareStatement("update persons set balance=? where id=?");
                            pst1.setDouble(1, newBalance);
                            pst1.setInt(2, personId);
                            int res=pst1.executeUpdate();
                            if(res>0){
                                PreparedStatement pre= conn.prepareStatement("INSERT INTO reservation (id, roomId, checkIn, checkOut, totalAmount, status) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                                pre.setInt(1, personId);
                                pre.setInt(2, roomId);
                                pre.setString(3,checkIn.toString());
                                pre.setString(4,checkOut.toString());
                                pre.setDouble(5,total);
                                pre.setString(6,"BOOKED");
                                int res1=pre.executeUpdate();
                                int reservationId=-1;
                                if(res1>0){
                                    ResultSet resultSet = pre.getGeneratedKeys();
                                    if (resultSet.next()) {
                                        reservationId = resultSet.getInt(1);
                                    }

                                }

                                PaymentImpliment pay=new PaymentImpliment();
                                long transactionId=pay.generatePaymentId();
                                PreparedStatement pre1=conn.prepareStatement("insert into payment (transactionId,reservationId,amount,paymentStatus) values (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
                                pre1.setLong(1, transactionId);
                                pre1.setInt(2, reservationId);
                                pre1.setDouble(3, total);
                                pre1.setString(4,"SUCCESS");
                                int res2=pre1.executeUpdate();
                                long paymentId=-1;
                                if(res2>0){
                                    java.sql.ResultSet resultSet1 =pre1.getGeneratedKeys();
                                    while(resultSet1.next()){
                                        paymentId=resultSet1.getInt(1);
                                    }
                                    PreparedStatement pre2=conn.prepareStatement("update hotel as h join room as r on h.hotelId=r.hotelId set h.hotelrevenue=h.hotelrevenue+? where r.roomId=?");
                                    pre2.setDouble(1, total);
                                    pre2.setInt(2, roomId);
                                    int res3= pre2.executeUpdate();
                                    if(res3>0){
                                        PreparedStatement pre3=conn.prepareStatement("update room set availability=? where roomId=?");
                                        pre3.setBoolean(1, false);
                                        pre3.setInt(2, roomId);
                                        int res4= pre3.executeUpdate();
                                        if(res4>0){
                                            PreparedStatement pre7=conn.prepareStatement("update reservation set paymentId=?,status=?,transactionId=? where reservationId=?");
                                            pre7.setLong(1, paymentId);
                                            pre7.setString(2, "PAID");
                                            pre7.setLong(3, transactionId);
                                            pre7.setInt(4, reservationId);
                                            int res5= pre7.executeUpdate();
                                            if(res5>0){
                                                System.out.println("Payment Successfully Paid!. Your Transaction Id is "+transactionId);
                                            }
                                            else System.out.println("Payment Failed!.");

                                        }
                                    }
                                }
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
