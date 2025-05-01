public class Main {
    public static void main(String[] args) {

        Agent a = new Agent(1, "agent007", "TopSecret1", "James Bond",
                        "jbond@mi6.co.uk", "+44...", "Intelligence", 0.15);

        Customer c = new Customer(2, "jane_doe", "Pass1234", "Jane Doe",
                        "jane@ex.com", "+20...", "123 Elm St",
                        "BKG123;BKG456", "window seat");

        Administrator adm = new Administrator(3, "root", "Adm1n123", "Root User",
                        "root@sys.local", "+1...", "superuser");

        String path = "users.csv";
        a.saveToCsv(path);
        c.saveToCsv(path);
        adm.saveToCsv(path);
    }
}

