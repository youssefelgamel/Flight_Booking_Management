public class BankTransferPayment extends Payment{
    private String bankAccountNumber;


    public BankTransferPayment(){}
    public BankTransferPayment(String bookingReference, double amount, String bankAccountNumber) {
        super(bookingReference, amount); 
        this.bankAccountNumber = bankAccountNumber;
    }

    @Override
    public boolean processPayment(Payment payment){
        if (!(payment instanceof BankTransferPayment)){
            throw new IllegalArgumentException("Invalid payment type");
        }
        BankTransferPayment bankTransferPayment = (BankTransferPayment) payment;
        System.out.println("Processing bank transfer payment for booking: " + bankTransferPayment.getBookingReference());
        return true;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }
}
