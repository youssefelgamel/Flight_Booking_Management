import java.io.*; // import necessary libraries for securing password
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public abstract class User{ // This superclass is gonna be extended to Customer, Agent and Administrator subclasses.
    protected int userID;
    protected String username;
    protected String password; // Passwords must be at least 6 characters with letters and numbers
    protected String name;
    protected String email;
    protected String contactInfo;

    public User(){}

    public User(int userID, String username, String password, String name, String email, String contactInfo){
        this.userID = userID;
        this.username = username;
        this.setPassword(password); // to check validation
        this.name = name;
        this.email = email;
        this.contactInfo = contactInfo;
    }

    // --------------------
    // Password handling
    // --------------------
private String hashPassword(String password) { // to make the password more secured and to avoid saving it in plain text.
    try {                                      // using try-catch to handle errors.
        MessageDigest md = MessageDigest.getInstance("SHA-256"); // MessageDigest is a class in Java that allows us to generate cryptographic hashes (e.g., SHA-256)
        byte[] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {   
            sb.append(String.format("%02x", b));  // Convert byte to hex string
        }
        return sb.toString();
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException("Error while hashing password.");
    }
}

public void setPassword(String password) {
    if (password.length() >= 6 && password.matches(".*[a-zA-Z].*") && password.matches(".*\\d.*")) {
        this.password = hashPassword(password);  // Store hashed password
    } else {
        throw new IllegalArgumentException("Password must be at least 6 characters with letters and numbers!");
    }
}

// --- persistence as CSV ---
public void saveToCsv(String filepath){
    File file = new File(filepath);
    boolean writeHeader = !file.exists() || file.length() == 0;

    try(BufferedWriter w = new BufferedWriter(new FileWriter(file,true))){
        if (writeHeader){
            w.write(getCsvHeader());
            w.newLine();
        }
        w.write(toCsvLine());
        w.newLine();
    }catch (IOException e){
        throw new RuntimeException("Faild to save user data", e);
    }
}

private String getCsvHeader(){
    return String.join(",",
    "role",
        "userID",
        "username",
        "password",
        "name",
        "email",
        "contactInfo",
        getExtraFieldNames()
    );
}
    /** One CSV line for this user */
private String toCsvLine() {
    return String.join(",",
        getClass().getSimpleName(),
        String.valueOf(userID),
        username,
        password,
        name,
        email,
        contactInfo,
        getExtraFields()
        );
}
/** Subclasses must supply the comma-separated field names here */
protected abstract String getExtraFieldNames();

/** Subclasses must supply the comma-separated values here */
protected abstract String getExtraFields();

    public String getPassword(){
        return password;
    }

    public int getUserID(){
        return userID;
    }

    public String getUsername(){
        return username;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getContactInfo(){
        return contactInfo;
    }

    public void login(){}

    public void logout(){}

    public void updateProfile(){}
}


class Agent extends User{ 
    private String department;
    private double commission; // عمولة

    public Agent(){}

    public Agent(int userID, String username, String password, String name, String email,
                String contactInfo, String department, double commission){
    super(userID,username,password,name,email,contactInfo);
    this.department = department;
    this.commission = commission;
    }

    @Override
    protected String getExtraFieldNames(){
        return "department,commission";
    }

    @Override
    protected String getExtraFields(){
        return department + "," + commission;
    }

    public void manageFlights(){}

    public void createBookingForCustomer(){}

    public void modifyBooking(){}

    public void generateReports(){}

}


class Administrator extends User{
    private String securityLevel;

    public Administrator(){}

    public Administrator(int userID, String username, String password, String name, String email,
                        String contactInfo, String securityLevel){
        super(userID,username,password,name,email,contactInfo);
        this.securityLevel = securityLevel;
    }

    @Override
    protected String getExtraFieldNames() {
        return "securityLevel";
    }

    @Override
    protected String getExtraFields() {
        return securityLevel;
    }

    public void createUser(){}

    public void modifySystemSettings(){}

    public void viewSystemLogs(){}

    public void manageUserAccess(){}

}


class Customer extends User{
    private String address;
    private String bookingHistory;
    private String preferences;

    public Customer(){}

    public Customer(int userID, String username, String password, String name, String email,
                    String contactInfo, String address,String bookingHistory,String preferences){
        super(userID,username,password,name,email,contactInfo);
        this.address = address;
        this.bookingHistory = bookingHistory;
        this.preferences = preferences;
    }

    @Override
    protected String getExtraFieldNames() {
        return "address,bookingHistory,preferences";
    }

    @Override
    protected String getExtraFields() {
        // wrap fields that may contain commas in quotes
        return String.join(",",
            "\"" + address + "\"",
            "\"" + bookingHistory + "\"",
            "\"" + preferences + "\""
        );
    }

    public void searchFlights(){}

    public void createBooking(){}

    public void viewBooking(){}

    public void cancelBooking(){}
}