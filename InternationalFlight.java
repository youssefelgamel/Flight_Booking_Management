import java.time.LocalDateTime;
import java.util.Map;
// -----------------------------------------------
// International flights
public class InternationalFlight extends Flight {
    
    private static final double International_Charge = 50.0;

    public InternationalFlight(String flightNumber,
                            String airline,
                            String origin,
                            String destination,
                            LocalDateTime departureTime,
                            LocalDateTime arrivalTime,
                            Map<String, Double> classBasePrices) {

        super(flightNumber, airline, origin, destination, departureTime, arrivalTime, classBasePrices);
    }

    @Override
    public double calculatePrice() {
        double base;
        if(classBasePrices.containsKey("Economy")){
            base = classBasePrices.getOrDefault("Economy", 0.0);
        }else{
            base = classBasePrices.getOrDefault("First Class", 0.0);
        }

        return base + International_Charge;
    }
}
