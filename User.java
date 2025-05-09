
public abstract class User implements Interfaces.Aunthenticatable{ // This superclass is gonna be extended to Customer, Agent and Administrator subclasses.
    protected String userID;
    protected String username;
    protected String password; // Passwords must be at least 6 characters with letters and numbers
    protected String email;

    public User(String userID, String username, String password, String email){
        this.userID = userID;
        this.username = username;
        this.setPassword(password); // to check validation
        this.email = email;
    }

    // --------------------
    // Password handling
    // --------------------
public void setPassword(String password) {
    if (password.length() >= 6 && password.matches(".*[a-zA-Z].*") && password.matches(".*\\d.*")) {
        this.password = password;  // Store password if it meets the criteria
    } else {
        throw new IllegalArgumentException("Password must be at least 6 characters with letters and numbers!");
    }
}

    public String getPassword() {
        return password; 
    }

    public String getUserID(){
        return userID;
    }

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    public void updateProfile(String userID, String username, String password, String email){
        this.userID = userID;
        this.username = username;
        this.setPassword(password); // to check validation
        this.email = email;
    }


    @Override
    public abstract boolean login(String username, String password); // This method is implemented in the subclasses from the Interfaces.Aunthenticatable interface


    @Override
    public abstract void logout();

}


//--------------------- Agent ---------------------
class Agent extends User{ 
    private String department;

    public Agent(String userID, String username, String password, String email, String department){ 
    super(userID,username,password,email);
    this.department = department;
    }

    @Override
    public boolean login(String username, String password) { // This method is implemented in the subclasses from the User class
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public void logout() {
        // Logic for user logout
    }

    public String getDepartment() {
        return department;
    }
}



//--------------------- Administrator ---------------------
class Administrator extends User{

    public Administrator(String userID, String username, String password, String email){
        super(userID,username,password,email);
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    
    @Override
    public void logout() {
        // Logic for user logout
    }

    public void addFlight(Flight flight) {
        // Logic to add flight to the system
    }

    public void removeFlight(Flight flight) {
        // Logic to remove flight from the system
    }

}