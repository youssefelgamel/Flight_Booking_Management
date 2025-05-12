public class Interfaces{

    public interface Validation{ // is acceptable to log in\out.
        boolean login(String username, String password);
        void logout();
    }
    
    public interface CalculatePrice{
        double calculatePrice();
    }
    
    public interface Ticket{
        String generateTicket();
    }
    
    public interface PaymentProcessing{
        boolean processPayment(Payment payment);
    }
    
}


