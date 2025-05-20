import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class FileManager {  // static and final data field that are no longer can be modified.
    private static final String USERS_FILE      = "users.txt"; 
    private static final String FLIGHTS_FILE    = "flights.txt";
    private static final String PASSENGERS_FILE = "passengers.txt";
    private static final String BOOKINGS_FILE   = "bookings.txt";
    private static final String PAYMENTS_FILE   = "payments.txt";

    //
    // --- Users ---
    // The BufferedWriter class can be used with other writers to write data (in characters) more efficiently.
    public static void saveUsers(List<User> users) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))) { // USERS_FILE  = "users.txt"
            for (User u : users) {
                if (u instanceof Customer){ // 'instanceof keyword' check whether an object is an instance of a specific class or interface.
                    Customer c = (Customer) u; // casting to tell the compiler to treat the object referred to by u as a Cutomer.
                    bw.write(String.join(",",
                            c.getUserID(),
                            c.getUsername(),
                            c.getPassword(),
                            c.getEmail(),
                            c.getPassportNumber(), // Cutomer is the passenger so, both have a passport number.
                            "CUSTOMER"));
                }
                else if (u instanceof Agent){
                    Agent a = (Agent) u;
                    bw.write(String.join(",",
                            a.getUserID(),
                            a.getUsername(),
                            a.getPassword(),
                            a.getEmail(),
                            a.getDepartment(),
                            "AGENT"));
                }
                else if (u instanceof Administrator){
                    Administrator a = (Administrator) u;
                    bw.write(String.join(",",
                            a.getUserID(),
                            a.getUsername(),
                            a.getPassword(),
                            a.getEmail(),
                            a.getAdminSecurityLevel(),
                            "ADMININSTRATOR"));
                }
                else {
                    System.err.println("Unknown user type, skipping: " + u); // print any other user type but in our case there are only 3.
                    continue;
                }
                bw.newLine(); // after all the operations a new line is needed to separate every line and to avoid confusion.
            }
        }
    }
    public static List<User> loadUsers() throws IOException {
        List<User> users = new ArrayList<>(); // define an ArrayList that it's element of type User
        File f = new File(USERS_FILE); // USERS_FILE  = "users.txt"
        if (!f.exists()) return users;  // return empty list if file does not exist.

        try (BufferedReader br = new BufferedReader(new FileReader(f))) { // reads the file line by line.
            String line;
            while ((line = br.readLine()) != null) { // check if a single line is not blank.
                if (line.isBlank() || line.startsWith("//" /* check if it's a comment line */ )) continue; // There must be a condition excited to get the method isBlank(), startWith of the String data type.
                String[] p = line.split(","); // p for parts, regex is a sequece of characters that defined a search pattern.
                /*
                 *  The previos line of code takes a line as a one String and splits it with comma separated parts.
                 *  Example:
                 *  String line = "Alice,alice123,passw0rd,alice@example.com";
                    split on commas
                    String[] p = line.split(",");
                    now:
                    p[0] == "Alice"
                    p[1] == "alice123"
                    p[2] == "passw0rd"
                    p[3] == "alice@example.com"
                 */

                String role = p[p.length - 1].trim().toUpperCase(); // Define a role variable and place it at the end with no spaces and in uppercase.
                switch (role) {

                    case "CUSTOMER":
                        if (p.length != 6) { // 6 fields: ID, username, password, email, passportNumber, role
                            throw new IllegalArgumentException("Expected 6 fields " + line);
                        }
                            String custId   = p[0].trim();
                            String custUser = p[1].trim();
                            String custPass = p[2].trim();
                            String custEmail= p[3].trim();
                            String passport = p[4].trim();
                            users.add(new Customer(custId, custUser, custPass, custEmail, passport)); // constructor takes 5 attributes.
                        break;

                    case "AGENT":
                        if (p.length != 6) {
                            throw new IllegalArgumentException("Expected 6 fields " + line);
                        }
                            String agentDepartment = p[4].trim();
                            users.add(new Agent(p[0],p[1],p[2],p[3],agentDepartment));
                        break;

                    case "ADMININSTRATOR":
                    case "ADMIN":
                        if (p.length != 6) {
                            throw new IllegalArgumentException("Expected 6 fields " + line);
                        }
                            String AdminSecurityLevel = p[4].trim();
                            users.add(new Administrator(p[0],p[1],p[2],p[3],AdminSecurityLevel));
                            break;
                    default:
                        System.out.println("Unknown user type, skipping: " + role);
                }
            }
        }
        return users;
    }

    //
    // --- Flights ---
    //
    public static void saveFlights(List<Flight> flights) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FLIGHTS_FILE))) {
            for (Flight f : flights) {
                String type = f instanceof DomesticFlight      ? "DOMESTIC"  // Shorter way to get the type of the flight.
                            : f instanceof InternationalFlight ? "INTERNATIONAL"
                            : "UNKNOWN";
                String prices = f.classBasePrices.entrySet().stream() // stream the map entries
                    .map(e -> e.getKey() + "=" + e.getValue()) // mapping the two types of flights with a base Price.
                    .collect(Collectors.joining(";")); // key: Economy,First Class || value: the base Price.
                bw.write(String.join(",",
                            type,
                            f.getFlightNumber(),
                            f.getAirline(),
                            f.getOrigin(),
                            f.getDestination(),
                            f.getDepartureTime().toString(),
                            f.getArrivalTime().toString(),
                            prices));
                bw.newLine();
            }
        }
    }

    public static List<Flight> loadFlights() throws IOException {
        List<Flight> flights = new ArrayList<>();
        File f = new File(FLIGHTS_FILE);
        if (!f.exists()) return flights; // returns an empty list if no such file.

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank() || line.startsWith("//")) continue;
                String[] p = line.split(",", 8);
                if (p.length != 8) {
                    throw new IllegalArgumentException("Expected 8 fields " + line);
                }
                String type    = p[0].trim().toUpperCase();
                String num     = p[1].trim();
                String air     = p[2].trim();
                String orig    = p[3].trim();
                String dest    = p[4].trim();
                LocalDateTime dep = LocalDateTime.parse(p[5].trim());
                LocalDateTime arr = LocalDateTime.parse(p[6].trim());
                Map<String,Double> prices = Arrays.stream(p[7].split(";"))
                    .map(s -> s.split("="))
                    .collect(Collectors.toMap(a->a[0], a->Double.valueOf(a[1])));

                Flight flight;
                if (type.equals("DOMESTIC")) {
                    flight = new DomesticFlight(num,air,orig,dest,dep,arr,prices); // the only difference between the two types is the price.
                } else {
                    flight = new InternationalFlight(num,air,orig,dest,dep,arr,prices);
                }
                flights.add(flight);
            }
        }
        return flights;
    }

    //
    // --- Passengers ---
    //
    public static void savePassengers(List<Passenger> passengers) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PASSENGERS_FILE))) {
            for (Passenger p : passengers) {
                bw.write(String.join(",",
                            p.getPassengerID(),
                            p.getName(),
                            p.getPassportNumber()));
                bw.newLine();
            }
        }
    }

    public static List<Passenger> loadPassengers() throws IOException {
        List<Passenger> passengers = new ArrayList<>();
        File f = new File(PASSENGERS_FILE);
        if (!f.exists()) return passengers;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank() || line.startsWith("\\")) continue;
                String[] p = line.split(",");
                if (p.length != 3) {
                    throw new IllegalArgumentException("Expected 3 fields " + line);
                }
                passengers.add(new Passenger(
                    p[0].trim(),
                    p[1].trim(),
                    p[2].trim()
                ));
            }
        }
        return passengers;
    }

    //
    // --- Bookings ---
    //
    public static void saveBookings(List<Booking> bookings) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKINGS_FILE))) {
            for (Booking b : bookings) {
            // null‐safe passengers list
            List<Passenger> pax = b.getPassengers();
            String paxIds = (pax == null || pax.isEmpty())
                ? ""
                : pax.stream()
                    .map(Passenger::getPassengerID)
                    .collect(Collectors.joining("|"));
                bw.write(String.join(",",
                            b.getBookingReference(), // Booking reference, CustomerId, Flight Number, boolean is paymentConfirmed.
                            b.getCustomer().getUserID(),
                            b.getFlight().getFlightNumber(),
                            paxIds,
                            String.valueOf(b.isPaymentConfirmed())));
                bw.newLine();
            }
        }
    }

public static List<Booking> loadBookings (List<User>users, List<Flight>flights, List<Passenger>pax) throws IOException {

    List<Booking> bookings = new ArrayList<>();
    File file = new File(BOOKINGS_FILE);
    if (!file.exists()) return bookings;

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.isBlank() || line.startsWith("//")) continue; 

            // Expect exactly 5 fields: ref, customerId, flightNum, paxIds, paid
            String[] p = line.split(",", 5);
            if (p.length != 5) {
                throw new IllegalArgumentException("Expected 5 fields, got: " + line);
            }

            String ref       = p[0].trim();
            String custId    = p[1].trim();
            String flightNum = p[2].trim();
            String paxBlock  = p[3].trim();
            boolean paid     = Boolean.parseBoolean(p[4].trim()); // parses the String argument as a boolean
            // If not null return true else return false.

            // Find the Customer by ID
            Customer customer = null;
            for (User u : users) {
                if (u instanceof Customer && ((Customer) u).getUserID().equals(custId)) {
                    customer = (Customer) u;
                    break;
                }
            }
            if (customer == null) {
                throw new IllegalArgumentException("Unknown customer ID: " + custId);
            }

            // Find the Flight by flight number
            Flight flight = null;
            for (Flight f : flights) {
                if (f.getFlightNumber().equals(flightNum)) {
                    flight = f;
                    break;
                }
            }
            if (flight == null) {
                throw new IllegalArgumentException("Unknown flight number: " + flightNum);
            }

            // Build the passenger list
            List<Passenger> bookedPax = new ArrayList<>();
            String[] paxIds = paxBlock.split("\\|");
            for (String pid : paxIds) {
                String passengerId = pid.trim();
                boolean found = false;
                for (Passenger passenger : pax) {
                    if (passenger.getPassengerID().equals(passengerId)) {
                        bookedPax.add(passenger);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    throw new IllegalArgumentException("Unknown passenger ID: " + passengerId);
                }
            }

            // Construct and register the booking
            Booking booking = new Booking(ref, customer, flight, bookedPax);
            if (paid) {
                booking.confirmPayment();
            }
            bookings.add(booking);
            customer.addBooking(booking);
        }
    }

    return bookings;
}


    //
    // --- Payments ---
    //
    public static void savePayments(List<Payment> payments) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PAYMENTS_FILE))) {
            for (Payment p : payments) {
                bw.write(String.join(",",
                            p.getBookingReference(),
                            String.valueOf(p.getAmount())
                            ));
                bw.newLine();
            }
        }
    }

    public static List<Payment> loadPayments() throws IOException {
        List<Payment> payments = new ArrayList<>();
        File f = new File(PAYMENTS_FILE);
        if (!f.exists()) return payments;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank() || line.startsWith("//")) continue;
                String[] p = line.split(",", 2);
                if (p.length != 2) {
                    throw new IllegalArgumentException("Expected 2 fields " + line);
                }
                String ref = p[0].trim();
                double amount = Double.parseDouble(p[1].trim());
                Payment pay = new Payment(ref, amount);
                payments.add(pay);
            }
        }
        return payments;
    }
}
