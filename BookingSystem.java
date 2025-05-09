public class BookingSystem {
    private User users;
    private Flight flights;
    private Booking bookings;
    private Payment payments;

    public BookingSystem(){}

    public BookingSystem(User users, Flight flights, Booking bookings, Payment payments){
        this.users = users;
        this.flights = flights;
        this.bookings = bookings;
        this.payments = payments;
    }

    public void searchFlights(){}

    public void createBooking(){}

    public void processPayment(){}

    public void generateTicket(){}
    
}
