
import java.util.List;

public class Booking implements Interfaces.Ticketable{
    private String bookingReference;
    private Customer customer;          // “has-a” relationship "composition"
    private Flight flight;              // “has-a” relationship "composition"
    private List<Passenger> passengers; // “has-many” relationship "aggregation"
    private boolean paymentConfirmed;

    public Booking(String bookingReference,Customer customer, Flight flight, List<Passenger> passengers) {
        this.bookingReference = bookingReference;
        this.customer = customer;
        this.flight = flight;
        this.passengers = passengers;
        this.paymentConfirmed = false; // Default value
    }

    public String getBookingReference() {
        return bookingReference;
    }


    // Generate a Wonderful ticket!
    @Override
    public String generateTicket(){
        StringBuilder sb = new StringBuilder();
        sb.append("Ticket for ").append(customer.getUsername()).append("\n")
        .append(" Ref:").append(bookingReference).append("\n")
        .append(" Flight: ").append(flight.getFlightNumber()).append("\n")
        .append(" Airline: ").append(flight.getAirline()).append("\n")
        .append(" Origin: ").append(flight.getOrigin()).append("\n")
        .append(" Destination: ").append(flight.getDestination()).append("\n")
        .append(" Passengers: \n").append("ID\tName\tPassport Number\tSpecial Requests\n");
        for (Passenger passenger : passengers) {
            sb.append(passenger.getPassengerID()).append("\t")
            .append(passenger.getName()).append("\t")
            .append(passenger.getPassportNumber()).append("\t")
            .append(passenger.getDateOfBirth()).append("\t");
        }

        return sb.toString();
    }

    public double calculateTotalPrice() {
        return flight.calculatePrice() * passengers.size(); // Example price calculation based on flight price and number of passengers.
    }

    public void confirmPayment() {
        this.paymentConfirmed = true;
    }

    public boolean isPaymentConfirmed() {
        return paymentConfirmed;
    }

    public Customer getCustomer() {
        return customer;
    }
    
    public Flight getFlight() {
        return flight;
    }
    
    public List<Passenger> getPassengers() {
        return passengers;
    }
}
