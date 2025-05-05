import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;

public class FileManager {
    private static final String USERS_FILE      = "users.txt";
    private static final String FLIGHTS_FILE    = "flights.txt";
    private static final String PASSENGERS_FILE = "passengers.txt";
    private static final String BOOKINGS_FILE   = "bookings.txt";
    private static final String PAYMENTS_FILE   = "payments.txt";

    //
    // --- Users ---
    //
    public static void saveUsers(List<User> users) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User u : users) {
                String role = u instanceof Customer      ? "Customer"
                            : u instanceof Agent         ? "Agent"
                            : u instanceof Administrator ? "Administrator"
                            : "Unknown";
                bw.write(String.join(",",
                            u.getUserID(),
                            u.getUsername(),
                            u.getPassword(),
                            u.getEmail(),
                            role));
                bw.newLine();
            }
        }
    }

    public static List<User> loadUsers() throws IOException {
        List<User> users = new ArrayList<>();
        File f = new File(USERS_FILE);
        if (!f.exists()) return users;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank() || line.startsWith("//")) continue;
                String[] p = line.split(",");
                if (p.length != 5) {
                    throw new IllegalArgumentException("Expected 5 fields " + line);
                }
                String id = p[0].trim(),
                    un = p[1].trim(),
                    pw = p[2].trim(),
                    em = p[3].trim(),
                    rl = p[4].trim().toUpperCase();
                switch (rl) {
                    case "CUSTOMER":
                        users.add(new Customer(id,un,pw,em));
                        break;
                    case "AGENT":
                        users.add(new Agent(id,un,pw,em,"DefaultAgency"));
                        break;
                    case "ADMIN":
                    case "ADMINISTRATOR":
                        users.add(new Administrator(id,un,pw,em));
                        break;
                    default:
                        System.err.println("Unknown role, skipping: " + rl);
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
                String type = f instanceof DomesticFlight      ? "DOMESTIC"
                            : f instanceof InternationalFlight ? "INTERNATIONAL"
                            : "UNKNOWN";
                String prices = f.classBasePrices.entrySet().stream()
                    .map(e -> e.getKey() + "=" + e.getValue())
                    .collect(Collectors.joining(";"));
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
        if (!f.exists()) return flights;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
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
                    flight = new DomesticFlight(num,air,orig,dest,dep,arr,prices);
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
    public static void savePassengers(List<Passenger> pax) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PASSENGERS_FILE))) {
            for (Passenger p : pax) {
                bw.write(String.join(",",
                            p.getPassengerID(),
                            p.getName(),
                            p.getPassportNumber(),
                            p.getDateOfBirth().toString()));
                bw.newLine();
            }
        }
    }

    public static List<Passenger> loadPassengers() throws IOException {
        List<Passenger> pax = new ArrayList<>();
        File f = new File(PASSENGERS_FILE);
        if (!f.exists()) return pax;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] p = line.split(",");
                if (p.length != 4) {
                    throw new IllegalArgumentException("Expected 4 fields " + line);
                }
                pax.add(new Passenger(
                    p[0].trim(),
                    p[1].trim(),
                    p[2].trim(),
                    LocalDate.parse(p[3].trim())
                ));
            }
        }
        return pax;
    }

    //
    // --- Bookings ---
    //
    public static void saveBookings(List<Booking> bookings) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKINGS_FILE))) {
            for (Booking b : bookings) {
                String paxIds = b.getPassengers().stream()
                                .map(Passenger::getPassengerID)
                                .collect(Collectors.joining("|"));
                bw.write(String.join(",",
                            b.getBookingReference(),
                            b.getCustomer().getUserID(),
                            b.getFlight().getFlightNumber(),
                            paxIds,
                            String.valueOf(b.isPaymentConfirmed())));
                bw.newLine();
            }
        }
    }

    public static List<Booking> loadBookings(
            List<User> users, List<Flight> flights, List<Passenger> pax) throws IOException {
        // Build maps for quick lookup
        Map<String,Customer> custMap = users.stream()
            .filter(u->u instanceof Customer)
            .map(u->(Customer)u)
            .collect(Collectors.toMap(Customer::getUserID, c->c));
        Map<String,Flight> flightMap = flights.stream()
            .collect(Collectors.toMap(Flight::getFlightNumber, f->f));
        Map<String,Passenger> paxMap = pax.stream()
            .collect(Collectors.toMap(Passenger::getPassengerID, p->p));

        List<Booking> bookings = new ArrayList<>();
        File f = new File(BOOKINGS_FILE);
        if (!f.exists()) return bookings;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] p = line.split(",", 5);
                if (p.length != 5) {
                    throw new IllegalArgumentException("Expected 5 fields " + line);
                }
                String ref    = p[0].trim();
                Customer c    = custMap.get(p[1].trim());
                Flight fl     = flightMap.get(p[2].trim());
                List<Passenger> list = Arrays.stream(p[3].split("\\|"))
                                            .map(id -> paxMap.get(id.trim()))
                                            .collect(Collectors.toList());
                boolean paid  = Boolean.parseBoolean(p[4].trim());

                Booking b = new Booking(ref, c, fl, list);
                if (paid) b.confirmPayment();
                bookings.add(b);
                c.addBooking(b);
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
                            p.getPaymentID(),
                            p.getBookingReference(),
                            String.valueOf(p.getAmount()),
                            p.getMethod(),
                            p.getTimestamp().toString(),
                            p.getStatus()));
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
                if (line.isBlank()) continue;
                String[] p = line.split(",", 6);
                if (p.length != 6) {
                    throw new IllegalArgumentException("Expected 6 fields " + line);
                }
                Payment pay = new Payment(
                    p[0].trim(),
                    p[1].trim(),
                    Double.parseDouble(p[2].trim()),
                    p[3].trim(),
                    LocalDateTime.parse(p[4].trim()),
                    p[5].trim()
                );
                payments.add(pay);
            }
        }
        return payments;
    }
}
