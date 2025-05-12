import java.time.LocalDateTime;
import java.util.Map;
// -----------------------------------------------
// Domestic flights
public class DomesticFlight extends Flight {
    
    private static final double Domestic_Charge = 0.05;

    public DomesticFlight(String flightNumber,
                        String airline,
                        String origin,
                        String destination,
                        LocalDateTime departureTime,
                        LocalDateTime arrivalTime,
                        Map<String, Double> classBasePrices) {

        super(flightNumber, airline, origin, destination,departureTime ,arrivalTime ,classBasePrices);
    }

    @Override
    public double calculatePrice() {
        double base = 0;
        if (classBasePrices.containsKey("Economy")){

            base = classBasePrices.getOrDefault("Economy", 0.0);
        }else{
            base = classBasePrices.getOrDefault("First Class", 0.0);
        }
        return base * (1 + Domestic_Charge); // calculate the base fare.
    }
}