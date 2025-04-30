public class Booking {
    private String bookingReference;
    private Customer customer; // aggregation
    private Flight flight;     // aggregation
    private int passengers;
    private String seatSelections;
    private String status;
    private String paymentStatus;

    public Booking(){}

    public Booking(String bookingReference, Customer customer, Flight flight, int passengers, String seatSelections, String status, String paymentStatus){
        this.bookingReference = bookingReference;
        this.customer = customer;
        this.flight = flight;
        this.passengers = passengers;
        this.seatSelections = seatSelections;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }

    public void addPassenger(){}

    public void calculateTotalPrice(){}

    public void confirmBooking(){}

    public void cancelBooking(){}
}
