public class Passenger {
    private int passengerID;
    private String name;
    private int passportNumber;
    private String dateOfBirth;
    private String specialRequests; // specific kind of food or facilities etc....

    public Passenger(){}

    public Passenger(int passengerID, String name, int passportNumber, String dateOfBirth, String specialRequests){
        this.passengerID = passengerID;
        this.name = name;
        this.passportNumber = passportNumber;
        this.dateOfBirth = dateOfBirth;
        this.specialRequests = specialRequests;
    }

    public void updateInfo(){}

    public void getPassengerDetails(){
        System.out.println("Passenger Details:");
        System.out.println("ID: " + passengerID);
        System.out.println("Name: " + name);
        System.out.println("Passport Number: " + passportNumber);
        System.out.println("DOB: " + dateOfBirth);
        System.out.println("Special Requests: " + specialRequests);
    }
}
