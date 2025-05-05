
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        BookingSystem system;

        try {
            system = new BookingSystem();
        } catch (Exception e) {
            System.err.println("Faild to load data: " + e.getMessage());
            return;
        }

            // Registration or login
        while (true){
            System.out.println("Welcome to The Flight Booking Management System");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            String choice = input.nextLine().trim(); // trim to remove whitespace from both sides of a string
            try {
                if (choice.equals("1")){
                    System.out.print("Username: ");
                    String username = input.nextLine().trim();
                    System.out.print("Password: ");
                    String password = input.nextLine().trim();
                    if ( system.login(username, password)){
                        System.out.println("Logged in as " + username + "\n");
                        break; // Exit the loop if login is successful
                    } else {
                        System.err.println("Login failed for " + username);
                    }
                }else if (choice.equals("2")){
                    System.out.print("Username: ");
                    String newUser = input.nextLine().trim();
                    System.out.print("Password: ");
                    String newPassword = input.nextLine().trim();
                    System.out.print("Email: ");
                    String newEmail = input.nextLine().trim();

                    // Role selection
                    System.out.print("Select role (1- Customer, 2- Administrator, 3- Agent): ");
                    String roleChoice = input.nextLine().trim();
                    String role;
                    switch (roleChoice) {
                        case "1":
                            role = "Customer";
                            break;
                        case "2":
                            role = "Administrator";
                            break;
                        case "3":
                            role = "Agent";
                            break;
                        default:
                            System.err.println("Invalid role selection. Defaulting to Customer.");
                            role = "Customer";
                    }

                    system.register(newUser, newPassword, newEmail,role);
                    System.out.println("Registeration successful! you can now login \n");
                }else if (choice.equals("0")){
                    System.out.println("Exiting the system. Goodbye!");
                    input.close();
                    return; // Exit the program
                }else {
                    System.err.println("Invalid choice. Please try again.");
                }
            }catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
                
        // Main menu loop
        User currentUser = system.getCurrentUser(); // Get the current user after login
        boolean running = true;
        while (running){
            System.out.println("Main Menu:");
            if (currentUser instanceof Customer){ // check if the current user is a customer

            System.out.println("1) Search Flights");
            System.out.println("2) View My Bookings");
            System.out.println("3) Logout and Exit");
            System.out.print("Select option: ");
            String menu = input.nextLine().trim();

            switch (menu){
                case "1":
                    System.out.print("Origin: ");
                    String origin = input.nextLine().trim();
                    System.out.print("Destination: ");
                    String destination = input.nextLine().trim();
                    System.out.print("Date (YYYY-MM-DD): ");
                    String date = input.nextLine().trim();
                    List<Flight> flights = system.searchFlights(origin, destination);
                    if (flights.isEmpty()){
                        System.out.println("No flights found from " + origin + " to " + destination + " on " + date);
                        break; // Exit the loop if no flights are found
                    }
                    System.out.println("Available flights:");
                    for (int i = 0; i < flights.size(); i++){
                        Flight f = flights.get(i);
                        System.out.printf("%d) %s | %s | %s to %s | Depart: %s\n",
                            i+1,
                            f.getFlightNumber(),
                            f.getAirline(),
                            f.getOrigin(),
                            f.getDestination());
                    }
                    System.out.print("Select flight (1-" + flights.size() + "): ");
                    int select = Integer.parseInt(input.nextLine()) - 1;
                    Flight selectedFlight = flights.get(select);
                    System.out.print("Number of passengers: ");
                    int numPax = Integer.parseInt(input.nextLine().trim());
                    List<Passenger> passengers = new ArrayList<>();
                    for (int i = 1; i <= numPax; i++){
                        System.out.printf("Passenger %d ID: ", i);
                        String passengerID = input.nextLine().trim();
                        System.out.printf("Passenger %d name: ", i);
                        String name = input.nextLine().trim();
                        System.out.printf("Passenger %d passport number: ", i);
                        String passport = input.nextLine().trim();
                        System.out.printf("Passenger %d date of birth (YYYY-MM-DD): ", i);
                        LocalDateTime dob = LocalDateTime.parse(input.nextLine().trim());
                        passengers.add(new Passenger(passengerID, name, passport, dob));
                    }
                    Customer customer = (Customer) system.getCurrentUser();
                    Booking booking = system.createBooking(customer, selectedFlight, passengers);
                    System.out.println("\nBooking created! Reference: " + booking.getBookingReference());
                    System.out.println("Total price: " + booking.calculateTotalPrice());
                    break; // Exit the loop after creating a booking
                case "2":
                    // View Bookings
                    Customer currentCustomer = (Customer) system.getCurrentUser();
                    List<Booking> bookings = currentCustomer.getBookings();
                    if (bookings.isEmpty()){
                        System.out.println();
                        System.out.println("No bookings found for " + currentCustomer.getUsername());
                        System.out.println();
                    } else {
                        System.out.println("Your bookings:");
                        for (Booking b : bookings){
                            System.out.println(b.generateTicket());
                        }
                    }
                    break; // Exit the loop after viewing bookings
                case "3":
                    system.logout(); // Logout the user
                    System.out.println("Logged out. Thank you for using the system.");
                    running = false; // Exit the main menu loop
                    break;
                default:
                    System.err.println("Invalid choice. Please try again.");
                    break; // Exit the loop if an invalid choice is made
            }

        }else if (currentUser instanceof Administrator){
            System.out.println("1) Add flight");
            System.out.println("2) View all flights");
            System.out.println("3) Logout and exit");
            System.out.print("Select option: ");
            String menu = input.nextLine().trim();
            switch (menu){
                case "1":
                try{
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
                        System.out.print("Price: ");
                        Map<String, Double> priceMap = new HashMap<>();
                        while (true) { 
                            System.out.print("Enter fare class (or blank to finish):");
                            String fareClass = input.nextLine().trim();
                            if (fareClass.isEmpty()) {
                                break; // Exit the loop if the user enters a blank fare class
                            }
                            System.out.print("Enter price for " + fareClass + ": ");
                            double price = Double.parseDouble(input.nextLine().trim());
                            priceMap.put(fareClass, price); // Add the fare class and price to the map
                        }

                        Flight flight;
                        if (flightType.equals("1")){
                            flight = new DomesticFlight(flightNumber, airline, origin, destination, departureTime, arrivalTime, priceMap); // Create a domestic flight
                        } else {
                            flight = new InternationalFlight(flightNumber, airline, origin, destination, departureTime, arrivalTime, priceMap); // Create an international flight
                        }
                        system.addFlight(flight); // Add the flight to the system
                        System.out.println("Flight added successfully!");
                    }catch (Exception e) {
                        System.err.println("Error adding flight: " + e.getMessage());
                    }
                    break; // Exit the loop after adding a flight
        
                case "2":
                    // View all flights
                    List<Flight> allFlights = system.getFlights(); // Get all flights from the system
                    if (allFlights.isEmpty()){
                        System.out.println("No flights available.");
                    } else {
                        System.out.println("All flights:");
                        for (Flight f : allFlights){
                            System.out.println(f.getFlightNumber() + " | " + f.getAirline() + " | " + f.getOrigin() + " to " + f.getDestination());
                        }
                    }
                    break; // Exit the loop after viewing all flights
                case "3":
                    system.logout(); // Logout the user
                    System.out.println("Logged out. Thank you for using the system.");
                    running = false; // Exit the main menu loop
                    break;
                default:
                    System.err.println("Invalid choice. Please try again.");
                    break;} // Exit the loop if an invalid choice is made
                }else{ // Agent
                    System.out.println("1) Search Flights");
                    System.out.println("2) Create Booking");
                    System.out.println("3) Logout");
                    System.out.print("Select option: ");
                    String menu = input.nextLine().trim();
                    switch (menu){
                        case "1":
                            System.out.print("Origin: ");
                            String origin = input.nextLine().trim();
                            System.out.print("Destination: ");
                            String destination = input.nextLine().trim();
                            List<Flight> flights = system.searchFlights(origin, destination); // Search for flights
                            if (flights.isEmpty()){
                                System.out.println("No flights found from " + origin + " to " + destination);
                                break; // Exit the loop if no flights are found
                            }
                            System.out.println("Available flights:");
                            for (int i = 0; i < flights.size(); i++){
                                Flight f = flights.get(i);
                                System.out.printf("%d) %s | %s | %s to %s | Depart: %s\n",
                                        i+1,
                                        f.getFlightNumber(),
                                        f.getAirline(),
                                        f.getOrigin(),
                                        f.getDestination());
                            }
                            break; // Exit the loop after searching for flights
                        case "2":
                            System.out.print("Origin: ");
                            String origin2 = input.nextLine().trim();
                            System.out.print("Destination: "); 
                            String destination2 = input.nextLine().trim();
                            List<Flight> flights2 = system.searchFlights(origin2, destination2); // Search for flights
                            if (flights2.isEmpty()){
                                System.out.println("No flights found from " + origin2 + " to " + destination2);
                                break; // Exit the loop if no flights are found
                            }
                            for (int i = 0;i<flights2.size(); i++){
                                Flight f = flights2.get(i);
                                System.out.printf("%d) %s | %s | %s to %s | Depart: %s\n",
                                        i+1,
                                        f.getFlightNumber(),
                                        f.getAirline(),
                                        f.getOrigin(),
                                        f.getDestination());
                            }
                            System.out.print("Select flight (1-" + flights2.size() + "): ");
                            int select2 = Integer.parseInt(input.nextLine()) - 1;
                            Flight selectedFlight2 = flights2.get(select2); // Get the selected flight
                            System.out.print("Number of passengers: ");
                            int numPax2 = Integer.parseInt(input.nextLine().trim());
                            List<Passenger> passengers2 = new ArrayList<>();
                            for (int i = 1; i <= numPax2; i++){
                                System.out.printf("Passenger %d ID: ", i);
                                String passengerID = input.nextLine().trim();
                                System.out.printf("Passenger %d name: ", i);
                                String name = input.nextLine().trim();
                                System.out.printf("Passenger %d passport number: ", i);
                                String passport = input.nextLine().trim();
                                System.out.printf("Passenger %d date of birth (YYYY-MM-DD): ", i);
                                LocalDateTime dob = LocalDateTime.parse(input.nextLine().trim());
                                passengers2.add(new Passenger(passengerID, name, passport, dob)); // Add the passenger to the list
                            }
                            Customer customer2 = (Customer) system.getCurrentUser(); // Get the current user
                            Booking booking2 = system.createBooking(customer2, selectedFlight2, passengers2); // Create a booking
                            System.out.println("\nBooking created! Reference: " + booking2.getBookingReference());
                            System.out.println("Total price: " + booking2.calculateTotalPrice()); // Calculate the total price of the booking
                            break; // Exit the loop after creating a booking
                        case "3":
                            system.logout(); // Logout the user
                            System.out.println("Logged out. Thank you for using the system.");
                            running = false; // Exit the main menu loop
                            break;
                        default:
                            System.err.println("Invalid choice. Please try again.");
                    }
                }
                }
        input.close(); // Close the scanner
    }
}
