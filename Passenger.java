
import java.time.LocalDate;


public class Passenger {
    private String passengerID;
    private String name;
    private String passportNumber;
    private LocalDate dateOfBirth; // optional field

    public Passenger(String passengerID, String name, String passportNumber, LocalDate dateOfBirth) {
        this.passengerID = passengerID;
        this.name = name;
        this.passportNumber = passportNumber;
        this.dateOfBirth = dateOfBirth; // optional field
    }

    public String getPassengerID() {
        return passengerID;
    }

    public String getName() {
        return name;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }


    public void getPassengerDetails(){
        System.out.println("Passenger Details:");
        System.out.println("ID: " + passengerID);
        System.out.println("Name: " + name);
        System.out.println("Passport Number: " + passportNumber);
    }
}
