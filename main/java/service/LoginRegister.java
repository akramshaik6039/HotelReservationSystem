package service;


import entity.Admin;
import entity.Hotel;
import entity.Person;
import entity.Role;

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
    PersonImpliment implimentsPerson=new PersonImpliment();
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
                    log.info("Register successfully!");
                }
                else{
                    log.info("Register failed!");
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
                    log.info("Register successfully!");
                }else{
                    log.info("Register failed!");
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
                       while(start){
                           log.info("Login successfully! Welcome to Admin DashBoard {}", rs.getString("name"));
                           System.out.println("1)Add Hotel \n2)Delete Hotel \n3)Update Hotel \n4)Get a Hotel By Name \n5)Get All Hotels\n6)Edit Profile \n7)Exit");
                           int choose1=sc.nextInt();
                           // hotelName,  location,  contactNo, address,  hotelEmail, createId
                           switch(choose1){
                               case 1:
                                   log.info("Enter Hotel Name");
                                   String hotelName=sc.next();
                                   log.info("Enter Hotel Location");
                                   String hotelLocation=sc.next();
                                   log.info("Enter Hotel Contact No");
                                   long hotelContactNo=sc.nextLong();
                                   log.info("Enter Hotel Address");
                                   String hotelAddress=sc.next();
                                   log.info("Enter Hotel HotelEmail");
                                   String hotelEmail=sc.next();
                                   hotelImplimentation.addHotel(new Hotel(hotelName,hotelLocation,hotelContactNo,hotelAddress,hotelEmail,rs.getInt("id")));
                                   break;
                               case 2:log.info("Enter Hotel Name");
                               String hotelName1=sc.next();
                               log.info("Enter Hotel Location");
                               String hotelLocation1=sc.next();
                               log.info("Enter Hotel Contact No");
                               long hotelContactNo1=sc.nextLong();
                               log.info("Enter Hotel Address");
                                   hotelImplimentation.deleteHotel(new Hotel());
                                   break;
                               case 3:
                                   log.info(hotelImplimentation.getHotels().toString());
                                    log.info("Enter Hotel Id");
                                    int  hotelId=sc.nextInt();
                                   log.info("Enter Hotel Name");
                                   String hotelName2=sc.next();
                                   log.info("Enter Hotel Location");
                                   String hotelLocation2=sc.next();
                                   log.info("Enter Hotel Contact No");
                                   long hotelContactNo2=sc.nextLong();
                                   log.info("Enter Hotel Address");
                                   String hotelAddress2=sc.next();
                                   log.info("Enter Hotel HotelEmail");
                                   String hotelEmail2=sc.next();
                                   hotelImplimentation.updateHotel(new Hotel(hotelId,hotelName2,hotelLocation2,hotelContactNo2,hotelAddress2,hotelEmail2,rs.getInt("id")));
                                   break;
                               case 4:hotelImplimentation.getHotel(new Hotel());
                                   break;
                               case 5:log.info(hotelImplimentation.getHotels().toString());
                                   break;
                               case 6:implimentsPerson.updatePerson(email,password);
                                   break;
                               case 7:start=false;
                                   break;
                               default:log.info("Invalid choice!");
                           }
                       }
                    }
                    else if(role.equals("user")){
                        log.info("Login successfully! Welcome to User DashBoard {}", rs.getString("name"));
                        System.out.println("1)View All Hotels \n2)View Hotel By City\n3)Edit Profile\n4)Exit");
                    }
                }
                else{
                    log.error("Login failed!,Give Correct Credentials...");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
    }
}
