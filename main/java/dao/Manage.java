package dao;

import entity.Hotel;

import java.util.List;

public interface Manage {
    void addHotel(Hotel hotel);
    void updateHotel(Hotel hotel);
    void deleteHotel(Hotel hotel);
    Hotel getHotel(Hotel hotel);
    List<Hotel> getHotels();

}
