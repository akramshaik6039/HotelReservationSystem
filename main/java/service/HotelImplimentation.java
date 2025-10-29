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
import java.util.ArrayList;
import java.util.List;

public class HotelImplimentation implements Manage {
    private static final Logger log = LoggerFactory.getLogger(HotelImplimentation.class);
    Connection con= UtilDb.getConnection();
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
    public void deleteHotel(Hotel hotel) {
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Hotel getHotel(Hotel hotel) {
        return null;
    }

    @Override
    public List<Hotel> getHotels() {
        try{
            List <Hotel> hotelList=new ArrayList<>();
            PreparedStatement pst=con.prepareStatement("select * from hotel");
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                int hotelId=rs.getInt("hotelId");
               String hotelName= rs.getString("hotelName");
               String location= rs.getString("location");
               long contactNumber= rs.getLong("contactNumber");
               String address= rs.getString("address");
               String email= rs.getString("hotelEmail");
               int id= rs.getInt("createdBy");
                hotelList.add(new Hotel(hotelId,hotelName,location,contactNumber,address,email,id));
            }
            return hotelList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
