package service;

import dao.Manage;
import entity.Hotel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util_res.UtilDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HotelImplimentation implements Manage {
    private static final Logger log = LoggerFactory.getLogger(HotelImplimentation.class);
    Connection con= UtilDb.getConnection();
    Scanner sc=new Scanner(System.in);
    @Override
    public void addHotel(Hotel hotel) {
        // hotelName,  location,  contactNo, address,  hotelEmail, createId
        try {
            PreparedStatement pst=con.prepareStatement("insert into hotel (hotelName,location,contactNumber,address,hotelEmail,createdBy) values(?,?,?,?,?,?)");
            pst.setString(1,hotel.getHotelName());
            pst.setString(2,hotel.getLocation());
            pst.setLong(3,hotel.getContactNo());
            pst.setString(4,hotel.getAddress());
            pst.setString(5,hotel.getHotelEmail());
            pst.setInt(6,hotel.getCreateId());
            int res=pst.executeUpdate();
            if (res>0){
                log.info("Hotel added successfully!");
            }else {
                log.error("Hotel added failure!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateHotel(Hotel hotel) {
        // hotelName,  location,  contactNo, address,  hotelEmail, createId
try{
PreparedStatement pre=con.prepareStatement("update hotel set hotelName=?,location=?,contactNumber=?,address=?,hotelEmail=? where hotelId=? and createdBy=? ");
pre.setString(1,hotel.getHotelName());
pre.setString(2,hotel.getLocation());
pre.setLong(3,hotel.getContactNo());
pre.setString(4,hotel.getAddress());
pre.setString(5,hotel.getHotelEmail());
pre.setInt(6,hotel.getHotelId());
pre.setInt(7,hotel.getCreateId());
int res=pre.executeUpdate();
if (res>0){
    log.info("Hotel updated successfully!");
}
else {
    log.error("Hotel updated failure!");
}
}catch (Exception e){
    e.printStackTrace();
}
    }

    @Override
    public void deleteHotel(int hotelId,int id) {
        try{
            PreparedStatement preparedStatement=con.prepareStatement("delete from hotel where hotelId=? and createdBy=?");
            preparedStatement.setInt(1,hotelId);
            preparedStatement.setInt(2,id);
            int res=preparedStatement.executeUpdate();
            if (res>0){
                log.info("Hotel deleted successfully!");
            }
            else {
                log.error("Hotel deleted failure!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getHotel(int hotelId,int id) {
        try{

            PreparedStatement pre=con.prepareStatement("select * from hotel where hotelId=? and createdBy=?");
            pre.setInt(1,hotelId);
            pre.setInt(2,id);
            ResultSet rs=pre.executeQuery();
            System.out.println("Hotel Details:");
            if (rs.next()){
                System.out.println("Hotel Id :"+hotelId+" : Hotel Name :"+rs.getString("hotelName"));
            }

        }catch (Exception e){
            e.printStackTrace();

        }

    }

    @Override
    public List<Hotel> getHotels(int id) {
        try{
            List <Hotel> hotelList=new ArrayList<>();
            PreparedStatement pst=con.prepareStatement("select * from hotel where createdBy=? ");
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                int hotelId=rs.getInt("hotelId");
               String hotelName= rs.getString("hotelName");
               String location= rs.getString("location");
               long contactNumber= rs.getLong("contactNumber");
               String address= rs.getString("address");
               String email= rs.getString("hotelEmail");
               int id1= rs.getInt("createdBy");
                hotelList.add(new Hotel(hotelId,hotelName,location,contactNumber,address,email,id1));
            }
            for (Hotel hotel:hotelList){
                System.out.println("Hotel Details: Hotel Id:"+hotel.getHotelId()+", HotelName:"+hotel.getHotelName()    +", Hotel Location :"+hotel.getLocation()+", Contact Number :"+hotel.getContactNo()+", Address :"+hotel.getAddress()+", HotelMail :"+hotel.getHotelEmail());
            }
            return hotelList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void getHotelsForUser() {
        try{
            List <String> hotelList=new ArrayList<>();
            hotelList.add("Id HotelName Location");
            PreparedStatement pst=con.prepareStatement("select * from hotel");
            ResultSet rs=pst.executeQuery();
           if (!hotelList.isEmpty()){
               while(rs.next()){
                   int hotelId=rs.getInt("hotelId");
                   String hotelName=rs.getString("hotelName");
                   String location=rs.getString("location");
                   hotelList.add(hotelId+" "+hotelName+" "+location);
               }
           }else System.out.println("No hotels found!");


            for(String hotelName:hotelList){
                System.out.println(hotelName);
            }
        }catch (Exception e){
            e.printStackTrace();

        }

    }

    @Override
    public void getAllHotelNames() {
        try{

            Map<Integer,String> hotelList=new TreeMap<>();
//            Set<String> hotelList2=new TreeSet<>();
           int id=0;
            hotelList.put(id,"Locations");
//            hotelList2.add("Locations");
            PreparedStatement pst=con.prepareStatement("select * from hotel ");
            ResultSet rs=pst.executeQuery();

               while(rs.next()){
                   int hotelId=rs.getInt("hotelId");
                   String hotelName=rs.getString("location");
                   hotelList.put(++id,hotelName);
//                   hotelList2.add(hotelName);
               }
            if(hotelList.isEmpty()){
                System.out.println("No hotels found!");
            }

            for(Map.Entry<Integer,String> entry:hotelList.entrySet()){
                if(entry.getValue().contains("Locations")){
                    System.out.println(entry.getValue());
                }
                else System.out.println(entry.getKey()+" "+entry.getValue());
            }
//            for(String hotelName:hotelList2){
//                if(hotelList.containsKey("Locations")){
//                    System.out.println(hotelName);
//                }else System.out.println("No hotels found!");
//            }
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    @Override
    public void getHotelsForUserByLocation(String cityName) {
        try{
            ArrayList<String> hotelList=new ArrayList<>();
            PreparedStatement pst=con.prepareStatement("select * from hotel where location=? ");
            pst.setString(1,cityName);
            ResultSet rs=pst.executeQuery();
//
                    while(rs.next()){
                        int  hotelId=rs.getInt("hotelId");
                        String hotelName=rs.getString("hotelName");
                        String location=rs.getString("location");
                        hotelList.add("Hotel Id :"+hotelId+" HotelName :"+hotelName+" Location :"+location);
                    }
                    for(int i=0;i<hotelList.size();i++){
                        System.out.println(hotelList.get(i));
                    }
            if(hotelList.isEmpty()){
                System.out.println("No hotels found Currently in this city!");
                if(!hotelList.contains(cityName)){
                    System.out.println("No hotels found Currently in this city!");
              }

           }

        }catch (Exception e){
            e.printStackTrace();

        }

    }
}
