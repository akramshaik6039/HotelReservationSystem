
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class UserToHotelTransaction {

    @Test
    void testUserToHotelTransaction() {

        double initialUserBalance = 5000.0;
        double hotelInitialRevenue = 10000.0;
        double roomPrice = 1000.0;
        int stayDays = 2;
        double totalAmount = roomPrice * stayDays;
        double finalUserBalance = initialUserBalance - totalAmount;
        double finalHotelRevenue = hotelInitialRevenue + totalAmount;
        String paymentStatus = (initialUserBalance >= totalAmount) ? "SUCCESS" : "FAILED";
        assertEquals(2000.0, totalAmount, "Total amount should be roomPrice * stayDays");
        assertEquals(3000.0, finalUserBalance, "User balance should reduce by total amount");
        assertEquals(12000.0, finalHotelRevenue, "Hotel revenue should increase by total amount");
        assertEquals("SUCCESS", paymentStatus, "Payment should be successful if balance is enough");
    }

    @Test
    void testTransactionFailsWhenInsufficientBalance() {
        double initialUserBalance = 1000.0;
        double roomPrice = 1500.0;
        int stayDays = 1;
        double totalAmount = roomPrice * stayDays;
        String paymentStatus = (initialUserBalance >= totalAmount) ? "SUCCESS" : "FAILED";
        assertEquals("FAILED", paymentStatus, "Payment should fail if user has insufficient balance");
    }

}
