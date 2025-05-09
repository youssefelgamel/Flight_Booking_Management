import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class BookingSystem implements Interfaces.Aunthenticatable{ // The Baba class 
    private List<User> users = new ArrayList<>(); // List of users
    private List<Flight> flights = new ArrayList<>(); // List of flights
    private List<Booking> bookings = new ArrayList<>(); // List of bookings
    private List<Payment> payments = new ArrayList<>(); // List of payments
    private List<Passenger> passengers = new ArrayList<>(); // List of passengers
    private User currentUser;
    private int userIdCounter = 0; // Counter for user IDs

    public BookingSystem() throws IOException {
        users = FileManager.loadUsers(); // Load users from file
        flights = FileManager.loadFlights();
        passengers = FileManager.loadPassengers(); // Load passengers from file
        bookings = FileManager.loadBookings(users, flights, passengers); 
        payments = FileManager.loadPayments();
    }

    @Override
    public boolean  login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.login(username, password)) {
                currentUser = user; // Set the current user
                return true; // Login successful
            }
        }
        return false; // Login failed
    }

    @Override
    public void logout() {
        if (currentUser != null) {
            currentUser.logout();
        }
        currentUser = null;

    }

public List<Flight> searchFlights(String origin, String destination){
    return flights.stream()
            .filter(flight -> flight.getOrigin().equalsIgnoreCase(origin) && flight.getDestination().equalsIgnoreCase(destination))
            .collect(Collectors.toList()); // Filter flights based on origin and destination
}

public Booking createBooking(Customer customer, Flight flight, List<Passenger> passengers){
    String ref = UUID.randomUUID().toString(); // Generate a unique booking reference
    Booking booking = new Booking(ref, customer, flight, passengers); // Create a new booking
    bookings.add(booking); // Add booking to the list
    customer.addBooking(booking); // Add booking to the customer's list of bookings
    return booking; // Return the created booking
}

public boolean processPayment(Booking booking, Interfaces.PaymentProcessor processor, Payment payment){
    boolean ok = processor.processPayment(payment); // Process the payment using the provided processor
    if (ok) {
        booking.confirmPayment(); // Confirm payment for the booking
        payments.add(payment); // Add payment to the list
    }
    return ok; // Return the payment status
}

public String generateTicket(Booking booking) {
    return booking.generateTicket(); // Generate ticket for the booking
}

public User getCurrentUser() {
    return currentUser; // Return the current user
}

public User register(String username, String password, String email,String role, String passportNumber) throws IOException {
    // Check if the username already exists
    for (User user : users){
        if (user.getUsername().equalsIgnoreCase(username)){
            throw new IllegalArgumentException("Username already exists!"); // Throw exception if username exists
        }
    }
    String userId = String.valueOf(userIdCounter++); // Generate a unique user ID
    User user; // Declare user variable
    switch (role.toUpperCase()){
        case "AGENT":
            user = new Agent(userId, username, password, email,"Default Agency"); // Create a new agent
            break;
        case "ADMIN":
        case "ADMINISTRATOR":
            user = new Administrator(userId, username, password, email); // Create a new admin
            break;
        default:
            user = new Customer(userId, username, password, email, passportNumber); // Create a new customer
    }

    users.add(user); // Add user to the list
    FileManager.saveUsers(users); // Save users to file

    currentUser = user; // Set the current user to the newly registered user
    return user;
    
}

public void addFlight(Flight flight) throws IOException {
    flights.add(flight); // Add flight to the list
    FileManager.saveFlights(flights); // Save flights to file
}

public List<Flight> getFlights(){
    return Collections.unmodifiableList(flights); // Return an unmodifiable view of the flights list
}

public User findUserByUsername(String username) {
    for (User user : users) {
        if (user.getUsername().equals(username)) {
            return user; // Return the user if found
        }
    }
    return null; // Return null if user not found
}


}
