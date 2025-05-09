import javax.swing.*;

public class Form extends JFrame {

    public Form(String title) {
        super(title); // title
        setSize(520, 680); // size
        setDefaultCloseOperation(EXIT_ON_CLOSE); // when we click (x) the program will stop
        setLayout(null); // Disable default layout
        setLocationRelativeTo(null); // Center the form on the screen
        setResizable(false); // Prevent resizing
        setLayout(null); // set layout to null for absolute positioning
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close operation
		setVisible(true); // make the GUI visible
        // Set the background color of the form to a new color
        getContentPane().setBackground(CommonConstants.PRIMARY_COLOR); // Change to BUTTON_COLOR or any other color
    }
}