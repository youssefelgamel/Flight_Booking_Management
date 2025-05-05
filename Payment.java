import java.time.LocalDateTime;

public class Payment {
    private String paymentID;
    private String bookingReference;
    private double amount;
    private String method; // paying method
    private LocalDateTime timestamp; // payment time
    private String status; 


    public Payment(String paymentID, String bookingReference, double amount, String method,LocalDateTime timestamp ,String status){
        this.paymentID = paymentID;
        this.bookingReference = bookingReference;
        this.amount = amount;
        this.method = method;
        this.timestamp = timestamp;
        this.status = status;
    }
    public String getPaymentID() {
        return paymentID;
    }
    public String getBookingReference() {
        return bookingReference;
    }
    public double getAmount() {
        return amount;
    }
    public String getMethod() {
        return method;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public String getStatus() {
        return status;
    }


}
