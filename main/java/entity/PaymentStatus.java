package entity;

public enum PaymentStatus {
    PENDING,SUCCESS,FAILED;

    public PaymentStatus getPaymentStatus(int paymentId) {
            return PaymentStatus.SUCCESS;
    }
}
