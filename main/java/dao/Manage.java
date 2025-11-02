package dao;

import entity.Hotel;

import java.util.List;
import java.util.Map;

public interface Manage {
    void addHotel(Hotel hotel);
    void updateHotel(Hotel hotel);
    void deleteHotel(int  hotelId,int id);
    void getHotel(int  hotelId,int id);
    List<Hotel> getHotels(int id);
    void getHotelsForUser();
    void getAllHotelNames();
    void getHotelsForUserByLocation(String cityName);
}
