package entity;

public class Room {
    int roomId;
    int roomNumber;
    String roomType;
    double price;
    boolean available;
    int hotelId;

    public Room(){}
    public Room(int roomId, int roomNumber, String roomType, double price, boolean available) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.available = available;
    }
    public Room(int roomNumber, String roomType, double price, boolean available, int hotelId) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.available = available;
        this.hotelId = hotelId;
    }
    void displayDetails() {
        System.out.println("Room Id: " + roomId);
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Room Type: " + roomType);
    }
    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                "roomNumber=" + roomNumber +
                ", roomType='" + roomType + '\'' +
                ", price=" + price +
                ", available=" + available +
                '}';
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
}
