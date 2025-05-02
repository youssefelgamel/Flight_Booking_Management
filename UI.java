import javax.swing.*;

public class UI {

    public UI() {
        JFrame frame = new JFrame();
        setFrame(frame);
        frame.setVisible(true);
    }
    private void setFrame(JFrame frame) {
        frame.setSize(420, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Flight Booking Management System");
    }
}
