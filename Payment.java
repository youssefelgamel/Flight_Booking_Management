public class Payment {
    private int paymentID;
    private String bookingReference;
    private double amount;
    private String method; // paying method
    private String status;
    private String transactionDate;

    public Payment(){}

    public Payment(int paymentID, String bookingReference, double amount, String method, String status, String transactionDate){
        this.paymentID = paymentID;
        this.bookingReference = bookingReference;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.transactionDate = transactionDate;
    }

    public void processPayment(){}

    public void validatePaymentDetails(){}

    public void updateStatus(){}
}
