import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        BookingSystem system;

        try {
            system = new BookingSystem();
        } catch (Exception e) {
            System.err.println("Failed to load data: " + e.getMessage());
            input.close();
            return;
        }

        // Authentication Phase
        if (!authenticateUser(system, input)) {
            System.out.println("Exiting system. Goodbye!");
            input.close();
            return;
        }

        // Main Program Loop
        User currentUser = system.getCurrentUser();
        boolean running = true;

        while (running) {
            System.out.println("\n--- Main Menu ---");

            if (currentUser instanceof Customer) {
                running = handleCustomerMenu((Customer) currentUser, system, input);
            } else if (currentUser instanceof Administrator) {
                running = handleAdminMenu(system, input);
            } else if (currentUser instanceof Agent) {
                running = handleAgentMenu(system, input);
            } else {
                System.err.println("Unknown user role.");
                running = false;
            }
        }

        input.close();
    }

    // --- Authentication ---

    private static boolean authenticateUser(BookingSystem system, Scanner input) {
        while (true) {
            System.out.println("\nWelcome to The Flight Booking Management System");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("Select option: ");
            String choice = input.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Username: ");
                        String username = input.nextLine().trim();
                        System.out.print("Password: ");
                        String password = input.nextLine().trim();

                        if (system.login(username, password)) {
                            System.out.println("Logged in successfully as " + username);
                            return true;
                        } else {
                            System.err.println("Login failed for " + username);
                        }
                        break;

                    case "2":
                        System.out.print("Username: ");
                        String newUser = input.nextLine().trim();
                        System.out.print("Password: ");
                        String newPassword = input.nextLine().trim();
                        System.out.print("Email: ");
                        String newEmail = input.nextLine().trim();

                        System.out.print("Select role (1- Customer, 2- Administrator, 3- Agent): ");
                        String roleChoice = input.nextLine().trim();
                        String role;
                        switch (roleChoice) {
                            case "1": role = "Customer"; break;
                            case "2": role = "Administrator"; break;
                            case "3": role = "Agent"; break;
                            default:
                                System.err.println("Invalid role choice. Defaulting to Customer.");
                                role = "Customer";
                                break;
                        }

                        system.register(newUser, newPassword, newEmail, role);
                        System.out.println("Registration successful! You can now log in.");
                        break;

                    case "0":
                        return false;

                    default:
                        System.err.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    // --- Role-Based Menus ---

    private static boolean handleCustomerMenu(Customer customer, BookingSystem system, Scanner input) {
        System.out.println("1) Search Flights");
        System.out.println("2) View My Bookings");
        System.out.println("3) Logout and Exit");
        System.out.print("Select option: ");
        String choice = input.nextLine().trim();

        switch (choice) {
            case "1":
                searchAndBookFlight(customer, system, input);
                return true;
            case "2":
                viewBookings(customer);
                return true;
            case "3":
                system.logout();
                System.out.println("Logged out. Thank you for using the system.");
                return false;
            default:
                System.err.println("Invalid choice. Please try again.");
                return true;
        }
    }

    private static boolean handleAdminMenu(BookingSystem system, Scanner input) {
        System.out.println("1) Add Flight");
        System.out.println("2) View All Flights");
        System.out.println("3) Logout and Exit");
        System.out.print("Select option: ");
        String choice = input.nextLine().trim();

        switch (choice) {
            case "1":
                addNewFlight(system, input);
                return true;
            case "2":
                viewAllFlights(system);
                return true;
            case "3":
                system.logout();
                System.out.println("Logged out. Thank you for using the system.");
                return false;
            default:
                System.err.println("Invalid choice. Please try again.");
                return true;
        }
    }

    private static boolean handleAgentMenu(BookingSystem system, Scanner input) {
        System.out.println("1) Search Flights");
        System.out.println("2) Create Booking");
        System.out.println("3) Logout and Exit");
        System.out.print("Select option: ");
        String choice = input.nextLine().trim();

        switch (choice) {
            case "1":
                displayAvailableFlights(system, input);
                return true;
            case "2":
                if (system.getCurrentUser() instanceof Customer) {
                    searchAndBookFlight((Customer) system.getCurrentUser(), system, input);
                } else {
                    System.err.println("Agent-assisted customer booking requires active customer selection.");
                }
                return true;
            case "3":
                system.logout();
                System.out.println("Logged out. Thank you for using the system.");
                return false;
            default:
                System.err.println("Invalid choice. Please try again.");
                return true;
        }
    }

    // --- Helper Operations ---

    private static void searchAndBookFlight(Customer customer, BookingSystem system, Scanner input) {
        List<Flight> flights = displayAvailableFlights(system, input);
        if (flights.isEmpty()) return;

        try {
            System.out.print("Select flight (1-" + flights.size() + "): ");
            int index = Integer.parseInt(input.nextLine().trim()) - 1;
            if (index < 0 || index >= flights.size()) {
                System.err.println("Invalid flight selection.");
                return;
            }

            Flight selectedFlight = flights.get(index);
            System.out.print("Number of passengers: ");
            int numPax = Integer.parseInt(input.nextLine().trim());

            List<Passenger> passengers = new ArrayList<>();
            for (int i = 1; i <= numPax; i++) {
                System.out.printf("--- Passenger %d ---\n", i);
                System.out.print("ID: ");
                String passengerID = input.nextLine().trim();
                System.out.print("Name: ");
                String name = input.nextLine().trim();
                System.out.print("Passport number: ");
                String passport = input.nextLine().trim();
                System.out.print("Date of Birth (YYYY-MM-DD): ");
                LocalDateTime dob = LocalDate.parse(input.nextLine().trim()).atStartOfDay();

                passengers.add(new Passenger(passengerID, name, passport, dob));
            }

            Booking booking = system.createBooking(customer, selectedFlight, passengers);
            System.out.println("\nBooking created! Reference: " + booking.getBookingReference());
            System.out.println("Total price: " + booking.calculateTotalPrice());

        } catch (Exception e) {
            System.err.println("Error booking flight: " + e.getMessage());
        }
    }

    private static List<Flight> displayAvailableFlights(BookingSystem system, Scanner input) {
        System.out.print("Origin: ");
        String origin = input.nextLine().trim();
        System.out.print("Destination: ");
        String destination = input.nextLine().trim();

        List<Flight> flights = system.searchFlights(origin, destination);
        if (flights.isEmpty()) {
            System.out.println("No flights found from " + origin + " to " + destination);
            return flights;
        }

        System.out.println("\nAvailable flights:");
        for (int i = 0; i < flights.size(); i++) {
            Flight f = flights.get(i);
            System.out.printf("%d) %s | %s | %s to %s | Depart: %s\n",
                    i + 1, f.getFlightNumber(), f.getAirline(), f.getOrigin(), f.getDestination(), f.getDepartureTime());
        }
        return flights;
    }

    private static void viewBookings(Customer customer) {
        List<Booking> bookings = customer.getBookings();
        if (bookings.isEmpty()) {
            System.out.println("\nNo bookings found for " + customer.getUsername());
        } else {
            System.out.println("\nYour bookings:");
            for (Booking b : bookings) {
                System.out.println(b.generateTicket());
            }
        }
    }

    private static void addNewFlight(BookingSystem system, Scanner input) {
        try {
            System.out.print("1- Domestic flight\n2- International flight\nSelect option: ");
            String flightType = input.nextLine().trim();

            System.out.print("Flight number: ");
            String flightNumber = input.nextLine().trim();
            System.out.print("Airline: ");
            String airline = input.nextLine().trim();
            System.out.print("Origin: ");
            String origin = input.nextLine().trim();
            System.out.print("Destination: ");
            String destination = input.nextLine().trim();
            System.out.print("Departure time (YYYY-MM-DDTHH:MM): ");
            LocalDateTime departureTime = LocalDateTime.parse(input.nextLine().trim());
            System.out.print("Arrival time (YYYY-MM-DDTHH:MM): ");
            LocalDateTime arrivalTime = LocalDateTime.parse(input.nextLine().trim());

            Map<String, Double> priceMap = new HashMap<>();
            while (true) {
                System.out.print("Enter fare class (or blank to finish): ");
                String fareClass = input.nextLine().trim();
                if (fareClass.isEmpty()) break;

                System.out.print("Enter price for " + fareClass + ": ");
                double price = Double.parseDouble(input.nextLine().trim());
                priceMap.put(fareClass, price);
            }

            Flight flight;
            if ("1".equals(flightType)) {
                flight = new DomesticFlight(flightNumber, airline, origin, destination, departureTime, arrivalTime, priceMap);
            } else {
                flight = new InternationalFlight(flightNumber, airline, origin, destination, departureTime, arrivalTime, priceMap);
            }

            system.addFlight(flight);
            System.out.println("Flight added successfully!");
        } catch (Exception e) {
            System.err.println("Error adding flight: " + e.getMessage());
        }
    }

    private static void viewAllFlights(BookingSystem system) {
        List<Flight> allFlights = system.getFlights();
        if (allFlights.isEmpty()) {
            System.out.println("No flights available.");
        } else {
            System.out.println("\nAll flights:");
            for (Flight f : allFlights) {
                System.out.println(f.getFlightNumber() + " | " + f.getAirline() + " | " + f.getOrigin() + " to " + f.getDestination());
            }
        }
    }
}
