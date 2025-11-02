package entity;

public enum ReservationStatus {
    BOOKED ,PAID, CANCELLED, COMPLETED;

    public ReservationStatus getStatus(String status) {
       switch(status) {
           case "BOOKED":return BOOKED;
           case "PAID":return PAID;
           case "CANCELLED":return CANCELLED;
           case "COMPLETED":return COMPLETED;
           default:return null;
       }
    }
}

