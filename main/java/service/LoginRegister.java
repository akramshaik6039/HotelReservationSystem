package service;


import dao.Booking;
import entity.*;

import exceptions.InvalidHotelId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util_res.UtilDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class LoginRegister {

    private static final Logger log = LoggerFactory.getLogger(LoginRegister.class);
    Connection con = UtilDb.getConnection();
    Scanner sc = new Scanner(System.in);
    HotelImplimentation  hotelImplimentation = new HotelImplimentation();
    BookingImplimentation bookingImplimentation=new BookingImplimentation();
    PersonImpliment implimentsPerson=new PersonImpliment();
   RoomImplimentaion implimentsRoom=new RoomImplimentaion();
    public void register(Person person) {

        try{
            if(person.getRole().toString().equals("admin")){
                Role role=person.getRole();

                //name,phoneNumber,email,role,admin_role,address,password
                PreparedStatement pre=con.prepareStatement("insert into persons (name,phoneNumber,email,role,admin_role,address,password) values(?,?,?,?,?,?,?)");
                pre.setString(1, person.getName());
                pre.setLong(2, person.getPhoneNumber());
                pre.setString(3, person.getEmail());
                pre.setString(4,person.getRole().toString());
                pre.setString(5,person.getAdmin_role());
                pre.setString(6,person.getAddress());
                pre.setString(7,person.getPassword());
               int res=pre.executeUpdate();
                if(res>0){
                    log.info("***** Register successfully! *****");
                }
                else{
                    log.error("----- Register failed! -----");
                }
            }
            else if(person.getRole().toString().equals("user")){
                Role role=person.getRole();
                PreparedStatement pre=con.prepareStatement("insert into persons (name,phoneNumber,email,role,address,password) values(?,?,?,?,?,?)");
                pre.setString(1, person.getName());
                pre.setLong(2, person.getPhoneNumber());
                pre.setString(3, person.getEmail());
                pre.setString(4,person.getRole().toString());
                pre.setString(5,person.getAddress());
                pre.setString(6,person.getPassword());
                int res=pre.executeUpdate();
                if(res>0){
                    log.info("***** Register successfully! *****");
                }else{
                    log.info("----- Register failed! -----");
                }

            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void login(String email,String password) {
            try{
                PreparedStatement pre=con.prepareStatement("select * from persons where email=? and password=?");
                pre.setString(1, email);
                pre.setString(2, password);
                ResultSet rs=pre.executeQuery();
                if(rs.next()){
                    String role=rs.getString("role");
                    if(role.equalsIgnoreCase("admin")){
                        boolean start=true;
                        log.info("***** Login successfully! Welcome to Admin DashBoard "+ rs.getString("name")+" *****");
                       while(start){
                           System.out.println("1)Add Hotel \n2)Delete Hotel \n3)Update Hotel \n4)Get Room Details of a Hotel \n5)Get All Hotels\n6)Edit Profile \n7)Exit");
                           int choose1=sc.nextInt();
                           // hotelName,  location,  contactNo, address,  hotelEmail, createId
                           switch(choose1){
                               case 1:
                                   System.out.println("Enter Hotel Name");
                                   String hotelName=sc.next();
                                   System.out.println("Enter Hotel Location");
                                   String hotelLocation=sc.next().toLowerCase();
                                   System.out.println("Enter Hotel Contact No");
                                   long hotelContactNo=sc.nextLong();
                                   System.out.println("Enter Hotel Address");
                                   String hotelAddress=sc.next();
                                   System.out.println("Enter Hotel HotelEmail");
                                   String hotelEmail=sc.next();
                                   hotelImplimentation.addHotel(new Hotel(hotelName,hotelLocation,hotelContactNo,hotelAddress,hotelEmail,rs.getInt("id")));
                                   break;
                               case 2: System.out.println(hotelImplimentation.getHotels(rs.getInt("id")).toString());
                                   System.out.println("Enter Hotel Id");
                                 int hotelId1=sc.nextInt();
                                   hotelImplimentation.deleteHotel(hotelId1,rs.getInt("id"));
                                   break;
                               case 3:
                                   System.out.println(hotelImplimentation.getHotels(rs.getInt("id")).toString());
                                   System.out.println("Enter Hotel Id");
                                    int  hotelId=sc.nextInt();
                                   System.out.println("Enter Hotel Name");
                                   String hotelName2=sc.next();
                                   System.out.println("Enter Hotel Location");
                                   String hotelLocation2=sc.next().toLowerCase();
                                   System.out.println("Enter Hotel Contact No");
                                   long hotelContactNo2=sc.nextLong();
                                   System.out.println("Enter Hotel Address");
                                   String hotelAddress2=sc.next();
                                   System.out.println("Enter Hotel HotelEmail");
                                   String hotelEmail2=sc.next();
                                   hotelImplimentation.updateHotel(new Hotel(hotelId,hotelName2,hotelLocation2,hotelContactNo2,hotelAddress2,hotelEmail2,rs.getInt("id")));
                                   break;
                               case 4:hotelImplimentation.getHotels(rs.getInt("id"));
                                   System.out.println("Enter Hotel Id");
                               int  hotelId3=sc.nextInt();
                               hotelImplimentation.getHotel(hotelId3,rs.getInt("id"));
                               boolean startRoom=true;
                               while(startRoom){
                                   System.out.println("1)Add Room\n"+
                                           "2)Update Room By RoomId\n"+
                                           "3)Delete Room By RoomId\n"+
                                           "4)View Rooms\n"+
                                           "5)Exit");
                                   int choose2=sc.nextInt();
                                   switch(choose2){
                                       case 1:implimentsRoom.addRoom(hotelId3);
                                       break;
                                       case 2:implimentsRoom.getAllRooms(hotelId3);
                                                System.out.println("Enter Room Id");
                                                int roomId1=sc.nextInt();
                                           System.out.println("Enter Room Number");
                                           int roomNumber1=sc.nextInt();
                                                implimentsRoom.updateRoom(roomId1,roomNumber1);
                                           break;
                                       case 3:implimentsRoom.getAllRooms(hotelId3);
                                       System.out.println("Enter Room Id");
                                       int roomId2=sc.nextInt();

                                           System.out.println("Sure Are You Want To Delete Room Details (y/n)");
                                           char confirm=sc.next().charAt(0);
                                      if(confirm=='y'){
                                          implimentsRoom.deleteRoom(roomId2);
                                      }else System.out.println("Wrong Confirmation");
                                           break;
                                       case 4:implimentsRoom.getAllRooms(hotelId3);
                                           break;
                                           case 5:startRoom=false;break;
                                           default:System.out.println("Invalid choice");break;
                                   }
                               }

                                   break;
                               case 5:hotelImplimentation.getHotels(rs.getInt("id"));
                                   break;
                               case 6:boolean flag=implimentsPerson.viewDetails(email,role);
                               if(flag==true){
                                   start=false;
                               }
                                   break;
                               case 7:start=false;
                                   break;
                               default:log.info("Invalid choice!");
                           }
                       }
                    }
                    else if(role.equalsIgnoreCase("USER")){
                        log.info(" ***** Login successfully! Welcome to User DashBoard "+ rs.getString("name")+" *****");
                        boolean start2=true;
                        while(start2){
                            System.out.println("1)View All Hotels \n2)View Hotel By City\n3)Profile\n4)Exit");
                            int choose2=sc.nextInt();
                            switch(choose2){
                                case 1:hotelImplimentation.getHotelsForUser();

                                        System.out.println("*** Choose a Hotel Id To Book a Hotel ***");
                                        int hotelId=sc.nextInt();
                                           implimentsRoom.getHotelById(hotelId);
                                           System.out.println("Enter Room Id");
                                           int roomId1=sc.nextInt();
                                            bookingImplimentation.addBooking(roomId1,rs.getInt("id"));

                                break;
                                case 2:hotelImplimentation.getAllHotelNames();
                                System.out.println("*** Choose a City Hotels ***");
                                String cityHotelName=sc.next();
                                    hotelImplimentation.getHotelsForUserByLocation(cityHotelName);
                                    System.out.println("*** Choose a Hotel Id To Book a Hotel ***");
                                    int hotelId1=sc.nextInt();
                                    implimentsRoom.getHotelById(hotelId1);
                                    System.out.println("Enter Room Id");
                                    int roomId2=sc.nextInt();
                                    bookingImplimentation.addBooking(roomId2,rs.getInt("id"));



                                    break;
                                    case 3:implimentsPerson.viewDetails(email,role);
                                        break;
                                        case 4:start2=false;
                                        break;
                                        default:log.info("Invalid choice!");


                            }
                        }
                    }
                }
                else{
                    log.error("***** Login failed!,Give Correct Credentials/Register Your Details *****");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
    }
}
