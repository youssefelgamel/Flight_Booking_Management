public abstract class User{ // This superclass is gonna be extended to Customer, Agent and Administrator subclasses.
    protected int userID;
    protected String username;
    protected String password;
    protected String name;
    protected String email;
    protected String contactInfo;

    public User(){}

    public User(int userID, String username, String password, String name, String email, String contactInfo){
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.contactInfo = contactInfo;
    }

    public void login(){}

    public void logout(){}

    public void updateProfile(){}


}


class Agent extends User{ 
    private int agentID;
    private String department;
    private double commission; // عمولة

    public Agent(){}

    public Agent(int userID, String username, String password, String name, String email,
                String contactInfo,int agentID, String department, double commission){
    super(userID,username,password,name,email,contactInfo);
    this.agentID = agentID;
    this.department = department;
    this.commission = commission;
    }

    public void manageFlights(){}

    public void createBookingForCustomer(){}

    public void modifyBooking(){}

    public void generateReports(){}

}


class Administrator extends User{
    private int adminID;
    private String securityLevel;

    public Administrator(){}

    public Administrator(int userID, String username, String password, String name, String email,
                        String contactInfo, int adminID, String securityLevel){
        super(userID,username,password,name,email,contactInfo);
        this.adminID = adminID;
        this.securityLevel = securityLevel;
    }

    public void createUser(){}

    public void modifySystemSettings(){}

    public void viewSystemLogs(){}

    public void manageUserAccess(){}

}


class Customer extends User{
    private int customerID;
    private String address;
    private String bookingHistory;
    private String preferences;

    public Customer(){}

    public Customer(int userID, String username, String password, String name, String email,
                    String contactInfo,int customerID,String address,String bookingHistory,String preferences){
        super(userID,username,password,name,email,contactInfo);
        this.customerID = customerID;
        this.address = address;
        this.bookingHistory = bookingHistory;
        this.preferences = preferences;
    }

    public void searchFlights(){}

    public void createBooking(){}

    public void viewBooking(){}

    public void cancelBooking(){}
}