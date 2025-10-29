package entity;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

public class Hotel {

    private int hotelId;
    private String hotelName;
    private String location;
    private long contactNo;
    private String address;
    private String hotelEmail;
    private int createId;

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId=" + hotelId +
                ", hotelName='" + hotelName + '\'' +
                ", location='" + location + '\'' +
                ", contactNo=" + contactNo +
                ", address='" + address + '\'' +
                ", hotelEmail='" + hotelEmail + '\'' +
                ", createId=" + createId +
                '}';
    }

    public Hotel(int hotelId, String hotelName, String location, long contactNo, String address, String hotelEmail, int createId) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.location = location;
        this.contactNo = contactNo;
        this.address = address;
        this.hotelEmail = hotelEmail;
        this.createId = createId;
    }

    public Hotel(String hotelName, String location, long contactNo, String address, String hotelEmail, int createId) {

        this.hotelName = hotelName;
        this.location = location;
        this.contactNo = contactNo;
        this.address = address;
        this.hotelEmail = hotelEmail;
        this.createId = createId;
    }

    public Hotel() {
    }


    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getContactNo() {
        return contactNo;
    }

    public void setContactNo(long contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHotelEmail() {
        return hotelEmail;
    }

    public void setHotelEmail(String hotelEmail) {
        this.hotelEmail = hotelEmail;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }
}
