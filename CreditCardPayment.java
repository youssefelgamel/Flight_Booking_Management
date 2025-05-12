public class CreditCardPayment extends Payment{
    private String cardNumber;
    private String cvv;

    public CreditCardPayment(){}

    public CreditCardPayment(String bookingReference, double amount, String cardNumber, String cvv) {
        super(bookingReference, amount); 
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    @Override
    public boolean processPayment(Payment payment){
        if (!(payment instanceof CreditCardPayment)){
            throw new IllegalArgumentException("Invalid payment type");
        }
        CreditCardPayment creditCardPayment = (CreditCardPayment) payment; // casting to Creditcardpayment.
        System.out.println("Processing credit card payment for booking: " + creditCardPayment.getBookingReference());
        return true;
    }

    public String getCardNumber() {
        return cardNumber;
    }
    public String getCvv() {
        return cvv;
    }
}
