import java.time.LocalDateTime;
import java.util.Map;
// -----------------------------------------------
// International flights
public class InternationalFlight extends Flight {
    private static final double INTERNATIONAL_SURCHARGE = 50.0;

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
        double base = classBasePrices.getOrDefault("Economy", 0.0);
        // add a flat surcharge rather than multiplying
        return base + INTERNATIONAL_SURCHARGE;
    }

    public double calculatePrice(String seatClass) {
        double base = classBasePrices.getOrDefault(seatClass, 0.0);
        return base + INTERNATIONAL_SURCHARGE;
    }
}
