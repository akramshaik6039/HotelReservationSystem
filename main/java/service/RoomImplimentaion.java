package service;

import dao.ManageRoms;
import entity.Room;
import exceptions.InvalidHotelId;
import util_res.UtilDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class RoomImplimentaion implements ManageRoms {
    Connection con= UtilDb.getConnection();
    Scanner sc=new Scanner(System.in);
    @Override
    public void addRoom(int  hotelId) {
        try{
                System.out.println("Enter Room Number");
                int roomNumber=sc.nextInt();
                System.out.println("Enter Room Type(Single -1/Double -2/Triple -3/Fourth 4)");
                System.out.println("Enter 1 to 4 for Room Types :");
                int  roomTypeChoose=sc.nextInt();
                String roomType="";
               switch(roomTypeChoose){
                   case 1:roomType="Single";
                   break;
                   case 2:roomType="Double";
                   break;
                   case 3:roomType="Triple";
                   break;
                   case 4:roomType="Fourth";
                   break;
                   default:System.out.println("Invalid Input");
                   roomTypeChoose=-1;
                   while(roomTypeChoose==-1){
                       System.out.println("Enter Room Type(Single/Double/Triple/Fourth)");
                       roomType=sc.next();
                   }
               }
            System.out.println("Enter Room Price Per Day");
            double price=sc.nextDouble();
            PreparedStatement pst=con.prepareStatement("insert into room(roomNumber,roomType,price,hotelId) values(?,?,?,?)");
            pst.setInt(1,roomNumber);
            pst.setString(2,roomType);
            pst.setDouble(3,price);
            pst.setInt(4,hotelId);
            int res=pst.executeUpdate();
            if(res>0){
                System.out.println("Room added successfully");
            }else System.out.println("Room add failed");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRoom(int roomId) {
            try{
                PreparedStatement pre=con.prepareStatement("delete from room where roomId=?");

                pre.setInt(1,roomId);
                int res=pre.executeUpdate();
                if(res>0){
                    System.out.println("Room deleted successfully");
                }
                else System.out.println("Room delete failed");
            }catch (Exception e){
                e.printStackTrace();
            }



    }

    @Override
    public void updateRoom(int roomId,int roomNumber) {
try{
//System.out.println("Enter Room Number");
//int roomNumber1=sc.nextInt();
System.out.println("Enter Room Type(Single -1/Double -2/Triple -3/Fourth -4)");
int roomTypeChoose=sc.nextInt();
String roomType="";
if(roomTypeChoose==-1){
    roomType="Single";
}
else if(roomTypeChoose==2){roomType="Double";}
else if(roomTypeChoose==3){roomType="Triple";}
else if(roomTypeChoose==4){roomType="Fourth";}
else {
    System.out.println("Invalid Input");
    roomTypeChoose=-1;
    while(roomTypeChoose==-1){
        System.out.println("Enter Room Type(Single/Double/Triple/Fourth)");
        roomType=sc.next();
    }
}
System.out.println("Enter Room Price");
double price1=sc.nextDouble();
PreparedStatement pre=con.prepareStatement("update room set roomNumber=?,roomType=?,price=? where  roomId=?");
//pre.setInt(1,roomNumber1);
pre.setInt(1,roomNumber);
pre.setString(2,roomType);
pre.setDouble(3,price1);
pre.setInt(4,roomId);
int res=pre.executeUpdate();
if(res>0){
    System.out.println("Room updated successfully");
}else System.out.println("Room updated failed");
}catch (Exception e){
    e.printStackTrace();
}
    }

    @Override
    public void getAllRooms(int hotelId) {
        try{
            ArrayList<Room> rooms=new ArrayList<>();
            PreparedStatement pst=con.prepareStatement("select * from room where  hotelId=?");
            pst.setInt(1,hotelId);
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                int roomId=rs.getInt("roomId");
                int roomNumber=rs.getInt("roomNumber");
                String roomType=rs.getString("roomType");
                boolean available=rs.getBoolean("availability");
                double price=rs.getDouble("price");
                //int roomId, int roomNumber, String roomType, double price, boolean available
                rooms.add(new Room(roomId,roomNumber,roomType,price,available));
            }
            for(Room room:rooms){
                System.out.println("Room Details: Room Id:"+room.getRoomId()+" Room Number:"+room.getRoomNumber()+" Room Type:"+room.getRoomType()+" Price:"+room.getPrice()+" Availability:"+room.isAvailable());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getHotelById(int hotelId) {
        try{
            ArrayList<Integer> hotelIds=new ArrayList<>();
            ArrayList<String> rooms=new ArrayList<>();
            ArrayList<Integer> roomIds=new ArrayList<>();
            ArrayList<Boolean> roomAvailable=new ArrayList<>();
           PreparedStatement pst=con.prepareStatement("select * from room");
           java.sql.ResultSet res =pst.executeQuery();
           while(res.next()){
               int roomId=res.getInt("roomId");
               int  hotelId1=res.getInt("hotelId");
               boolean available=res.getBoolean("availability");
               hotelIds.add(hotelId1);
               roomIds.add(roomId);
               roomAvailable.add(available);
           }
           if(!roomIds.isEmpty()&&!roomAvailable.isEmpty()){
              if(hotelIds.contains(hotelId)){
                  PreparedStatement pre=con.prepareStatement("select * from room where  hotelId=?");
                  pre.setInt(1,hotelId);
//                  pre.setBoolean(2,true);
                  ResultSet rs=pre.executeQuery();
                  while(rs.next()){
                      int roomId=rs.getInt("roomId");
                      int roomNumber=rs.getInt("roomNumber");
                      String roomType=rs.getString("roomType");
                      double price=rs.getDouble("price");
                      rooms.add("Room Id :"+roomId+", Room Number : "+roomNumber+", Room Type : "+roomType+", Price : "+price);
                  }
                  for(String room:rooms){
                      System.out.println(room);
                  }
              }
              else {
                  while(!roomIds.contains(hotelId)){
                      System.out.println("Enter Valid Hotel ID");
                      hotelId=sc.nextInt();
                      getHotelById(hotelId);
                  }

              }
           }
           else{
               throw new Exception("Rooms are Not Available");
           }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
