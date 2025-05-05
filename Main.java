//import java.util.Scanner; 
public class Main {

    public static void main(String[] args) {
        // Scanner input=new Scanner(System.in);
        UI ui = new UI();
        Agent a1 = new Agent(1, "agent007", "TopSecret1", "James Bond",
                "jbond@mi6.co.uk", "+44...", "Intelligence", 0.15);

        Customer c1 = new Customer(2, "jane_doe", "Pass1234", "Jane Doe",
                "jane@ex.com", "+20...", "123 Elm St",
                "BKG123;BKG456", "window seat");

        Administrator adm1 = new Administrator(3, "root", "Adm1n123", "Root User",
                "root@sys.local", "+1...", "superuser");

        // Customer c2 = new Customer(2,"Dezllar","0123","Youssef
        // Elgamel","youssefelgamel12@gmail.com","01271446218",
        // "Alexandria","BKG12","2 seats"); // Error in password

        String path = "users.csv";
        a1.saveToCsv(path);
        c1.saveToCsv(path);
        adm1.saveToCsv(path);

        /// 555555555555555555555555555555555555555555555

        /* Booooooombaaaaaaaaaaaaaaaaaa */

        /* Booooooombaaaaaaaaaaaaaaaaaa */

        /* Booooooombaaaaaaaaaaaaaaaaaa */
    }
}
