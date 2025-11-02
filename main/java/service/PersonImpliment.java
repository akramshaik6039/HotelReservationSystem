package service;

import dao.ManagePerson;
import entity.Person;
import exceptions.ProfileDelete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util_res.UtilDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class PersonImpliment implements ManagePerson {

    private static final Logger log = LoggerFactory.getLogger(PersonImpliment.class);
    Connection con= UtilDb.getConnection();
Scanner sc=new Scanner(System.in);
    @Override
    public boolean viewDetails(String email,String role) {
        boolean flag=true;
        int userId=-1;
        try{
            PreparedStatement s=con.prepareStatement("select * from persons where email=?");
            s.setString(1,email);
            ResultSet rs=s.executeQuery();
            if(role.equalsIgnoreCase("admin")){
                while(rs.next()){
                    System.out.println("***** Profile *****"
                            +"\n"+"Name :"+rs.getString("name")
                            +"\n"+"Phone Number :"+rs.getLong("phoneNumber")
                            +"\n"+"Email :"+rs.getString("email")
                            +"\n"+"Role :"+rs.getString("admin_role")
                            +"\n"+"Address :"+rs.getString("address")
                           );
                }
            }
            else {

                while(rs.next()){
                    userId=rs.getInt("id");
                    System.out.println("***** Profile *****"
                            +"\n"+"Name :"+rs.getString("name")
                            +"\n"+"Phone Number :"+rs.getLong("phoneNumber")
                            +"\n"+"Email :"+rs.getString("email")
                            +"\n"+"Address :"+rs.getString("address")
                            +"\n"+"Balance :"+rs.getString("balance"));
                }
            }
            boolean start=true;
            while(start){
               if(role.equalsIgnoreCase("user")){
                   System.out.println("***** Profile Settings *****\n"
                           +"1)Edit Profile Details \n"+
                           "2)Delete Profile \n"+
                           "3)Add Balance \n"+
                           "4)View Your Bookings\n"+
                           "5)Exit");
               }
               else{
                   System.out.println("***** Profile Settings *****\n"
                           +"1)Edit Profile Details \n"+
                           "2)Delete Profile \n"+
                           "3)Exit");
               }
                int choose= sc.nextInt();
                switch (choose){
                    case 1:if(role.equalsIgnoreCase("admin")){
                        System.out.println("Enter Name");
                        sc.nextLine();
                        String name=sc.nextLine();
                        System.out.println("Enter Phone Number");
                        long phoneNumber=sc.nextLong();
                        System.out.println("Enter Address");
                        String address=sc.next();
                        System.out.println("Enter Your Role");
                        String admin_role=sc.next();
                        updateDetails(email,role,name,address,phoneNumber,admin_role);
                    }
                    else{
                        System.out.println("Enter Name");
                        String name=sc.next();
                        System.out.println("Enter Phone Number");
                        long phoneNumber=sc.nextLong();
                        System.out.println("Enter Address");
                        String address=sc.next();
                        updateDetails(email,role,name,address,phoneNumber,null);
                    }
                        break;
                        case 2:System.out.println("Are you sure you want to delete this profile? (yes->1)");
                        int confirm=sc.nextInt();
                        if(confirm==1){
                            boolean res=deletePerson(email);
                            if (res){
                                start=false;
                                flag=true;
                            }
                        }else start=false;
                            break;
                            case 3:if(role.equalsIgnoreCase("user")){
                                System.out.println("How Much Amount You Want To Add");
                                double amount=sc.nextDouble();
                                addBalance(email,amount);
                            }
                            else{
                                start=false;
                            }
                                break;
                                case 4:if(role.equalsIgnoreCase("admin")){
                                    System.out.println("Invalid Choice");
                                }
                                else{

                                        viewUserBookings(userId);
                                }
                                    break;
                                case 5:if(role.equalsIgnoreCase("user")){
                                    start=false;
                                }else System.out.println("Invalid Choice");
                                    default:System.out.println("invalid Choice!");
                }
            }
        }catch(Exception e){
            e.printStackTrace();

        }
        return flag;
    }

    @Override
    public void updateDetails(String email,String role,String name,String address,long phoneNumber,String admin_role) {
        try{
           if(role.equalsIgnoreCase("admin")){
               PreparedStatement pre=con.prepareStatement("update persons set name=?,phoneNumber=?,address=?,admin_role=? where email=?");
               pre.setString(1,name);
               pre.setLong(2,phoneNumber);
               pre.setString(3,address);
               pre.setString(4,admin_role);
               pre.setString(5,email);
               int res=pre.executeUpdate();
               if(res>0){
                   System.out.println("Profile Updated Successfully");
               }
               else {
                   System.out.println("Profile Updated Failed");
               }
           }
           else{
               try{
                   PreparedStatement pre =con.prepareStatement("update persons set name=?,phoneNumber=?,address=? where email=?");
                   pre.setString(1,name);
                   pre.setLong(2,phoneNumber);
                   pre.setString(3,address);
                   pre.setString(4,email);
                   int res=pre.executeUpdate();
                   if(res>0){
                       System.out.println("Profile Updated Successfully");
                   }else System.out.println("Profile Updated Failed");
               }catch(Exception e){
                   e.printStackTrace();
               }
           }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean deletePerson(String email) {
        try{
            PreparedStatement pre=con.prepareStatement("delete from persons where email=?");
            pre.setString(1,email);
            int res=pre.executeUpdate();
            if(res>0){
                PersonImpliment.log.error("***** Profile Deleted Successfully *****");
                return true;
            }else{ System.out.println("Profile Deleted Failed");
            return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void addBalance(String email,double amount) {
        try{
            PreparedStatement pre=con.prepareStatement("update persons set balance=? where email=?");
            pre.setDouble(1,amount);
            pre.setString(2,email);
            int res=pre.executeUpdate();
            if(res>0) System.out.println("Amount Added Successfully");
            else {
                System.out.println("Amount Added Failed");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void viewUserBookings(int Userid) {
        System.out.println(Userid);
        try{

            PreparedStatement pst = con.prepareStatement(
                    "SELECT r.reservationId,r.transactionId, r.checkIn, r.checkOut, r.totalAmount, r.status, " +
                            "rm.roomNumber, rm.roomType, h.hotelName, h.location " +
                            "FROM reservation r " +
                            "JOIN room rm ON r.roomId = rm.roomId " +
                            "JOIN hotel h ON rm.hotelId = h.hotelId " +
                            "WHERE r.id = ?");
            pst.setInt(1, Userid);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                System.out.println("--------------*****---------------");
                System.out.println("Reservation ID: " + rs.getInt("reservationId"));
                System.out.println("Transaction ID: " + rs.getString("transactionId"));
                System.out.println("Hotel: " + rs.getString("hotelName"));
                System.out.println("Room Type: " + rs.getString("roomType"));
                System.out.println("Check-In: " + rs.getDate("checkIn"));
                System.out.println("Check-Out: " + rs.getDate("checkOut"));
                System.out.println("Amount: " + rs.getDouble("totalAmount"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("---------------*****--------------");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
