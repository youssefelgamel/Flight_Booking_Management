
import java.time.LocalDateTime;
import java.util.Map;

// 1) Abstract Flight now takes its base-prices map in the constructor:
public abstract class Flight implements Interfaces.PriceCalculatable {
    protected String flightNumber;
    protected String airline;
    protected String origin;
    protected String destination;
    protected LocalDateTime departureTime; 
    protected LocalDateTime arrivalTime;  
    protected Map<String, Double> classBasePrices; // Map interface to store class base prices
    // The key is the class name (e.g., "Economy", "Business"), and the value is the base price for that class.


    public Flight(String flightNumber,
                String airline,
                String origin,
                String destination,
                LocalDateTime departureTime,
                LocalDateTime arrivalTime,
                Map<String, Double> classBasePrices) {
        this.flightNumber     = flightNumber;
        this.airline          = airline;
        this.origin           = origin;
        this.destination      = destination;
        this.departureTime    = departureTime;
        this.arrivalTime      = arrivalTime;
        this.classBasePrices  = classBasePrices;
    }

    public String getFlightNumber() { return flightNumber; }
    public String getAirline()      { return airline; }
    public String getOrigin()       { return origin; }
    public String getDestination()  { return destination; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public LocalDateTime getArrivalTime()   { return arrivalTime; }

    @Override
    public abstract double calculatePrice();
}



