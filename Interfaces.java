public class Interfaces{

    public interface Aunthenticatable{
        boolean login(String username, String password);
        void logout();
    }
    
    public interface PriceCalculatable{
        double calculatePrice();
    }
    
    public interface Ticketable{
        String generateTicket();
    }
    
    public interface PaymentProcessor{
        boolean processPayment(Payment payment);
    }
    
}


