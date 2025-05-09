import java.time.LocalDateTime;
import java.util.Map;
// -----------------------------------------------
// Domestic flights
public class DomesticFlight extends Flight {
    private static final double DOMESTIC_TAX_RATE = 0.05;

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
        double base = classBasePrices.getOrDefault("Economy", 0.0);
        return base * (1 + DOMESTIC_TAX_RATE);
    }

    // /** Optionally, calculate a different class: */
    // public double calculatePrice(String seatClass) {
    //     double base = classBasePrices.getOrDefault(seatClass, 0.0);
    //     return base * (1 + DOMESTIC_TAX_RATE);
    // }
}