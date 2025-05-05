import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//--------------------- Customer ---------------------
class Customer extends User{

    private List<Booking> bookings = new ArrayList<>();

    public Customer(String userId, String username, String password, String email) {
        super(userId, username, password, email);
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
