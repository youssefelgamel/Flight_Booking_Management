import javax.swing.*;
public class form extends JFrame{
    public form(String title){
        super(title); //title
        setSize(520,680); //size
        setDefaultCloseOperation(EXIT_ON_CLOSE); //end the process after we close our GUI
        setLayout(null);         
        setLocationRelativeTo(null);//let the GUI nel centro
        // setResizble(false); //let our GUI has a stable size
    }
}