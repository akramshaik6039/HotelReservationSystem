package entity;

public class Reservation {
    private int reservationId;
    private int roomId;
    private int customerId;
   private String checkIn;
   private String checkOut;
   private double price;
   private  ReservationStatus reservationStatus;

   public Reservation() {}

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public Reservation(int roomId, int customerId, String checkIn, String checkOut, double price, ReservationStatus reservationStatus ) {
       this.roomId = roomId;
       this.customerId = customerId;
       this.checkIn = checkIn;
       this.checkOut = checkOut;
       this.price = price;
       this.reservationStatus = reservationStatus;
    }

}
