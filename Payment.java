
public class Payment implements Interfaces.PaymentProcessor{
    
    private String bookingReference;
    private double amount;

    public Payment() {}
    public Payment(String bookingReference, double amount){
        this.bookingReference = bookingReference;
        this.amount = amount;
    }
        public String getBookingReference() {
        return bookingReference;
    }
    public double getAmount() {
        return amount;
    }

    @Override
    public boolean processPayment(Payment payment) {
        // For example, you can check if the amount is valid and return true or false
        return payment.getAmount() > 0; // Example: return true if amount is positive
    }
}









