public abstract  class Flight { // This superclass is gonna be extended to Domestic(local) and International subclasses.
    protected int flightNumber;
    protected String airline;
    protected String origin;
    protected String distination;
    protected String depatureTime; // The time listed on ticket or boarding pass.
    protected String arrivalTime;
    protected int availableSeats;

    public Flight(){}

    public Flight(int flightNumber,String airline, String origin, String distination, String depatureTime, String arrivalTime, int availableSeats){
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.origin = origin;
        this.distination = distination;
        this.depatureTime = depatureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
    }

    public void checkAvailability(){}

    public void updateSchedule(){}

    public void calculatePrice(){}

    public void reserveSeat(){}
}


class Domestic extends Flight {

}


class International extends Flight{

}
