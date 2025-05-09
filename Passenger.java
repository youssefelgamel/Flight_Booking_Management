

public class Passenger {
    private String passengerID;
    private String name;
    private String passportNumber;

    public Passenger(String passengerID, String name, String passportNumber) {
        this.passengerID = passengerID;
        this.name = name;
        this.passportNumber = passportNumber;
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

    public void getPassengerDetails(){
        System.out.println("Passenger Details:");
        System.out.println("ID: " + passengerID);
        System.out.println("Name: " + name);
        System.out.println("Passport Number: " + passportNumber);
    }
}
