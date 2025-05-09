import javax.swing.*; 
public class Form extends JFrame{
    public Form(String title){
        super(title);//title
        setSize(520,680); //size
        setDefaultCloseOperation(EXIT_ON_CLOSE);// whem we click (x) the prog will stop
        setSize(520,680); //size
        setDefaultCloseOperation(EXIT_ON_CLOSE); //end the process after we close our GUI
        setLayout(null);         
        setLocationRelativeTo(null);//let the GUI nel centro
        // setResizble(false); //let our GUI has a stable size
    }
}