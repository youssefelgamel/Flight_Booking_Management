import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//--------------------- Customer ---------------------
class Customer extends User{


    private String passportNumber;
    private List<Booking> bookings = new ArrayList<>(); // Marked private to enforce encapsulation (no one outside the class can directly manipulate the list).

    public Customer(String userId, String username, String password, String email, String passportNumber) {
        super(userId, username, password, email);
        this.passportNumber = passportNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public void logout() {
        // Logic for user logout
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> getBookings() {
        return Collections.unmodifiableList(bookings); // Return an unmodifiable view of the bookings list
    }
}
