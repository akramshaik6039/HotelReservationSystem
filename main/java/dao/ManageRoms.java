package dao;

import entity.Room;

public interface ManageRoms {
    void addRoom(int hotelId);
    void deleteRoom(int roomId);
    void updateRoom(int roomId,int roomNumber);
    void getAllRooms(int hotelId);
    void getHotelById(int hotelId);
}
