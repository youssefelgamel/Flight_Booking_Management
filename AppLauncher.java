import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
         SwingUtilities.invokeLater(new Runnable() {
             @Override
            public void run(){
            new LoginFormGUI().setVisible(true);
            // new RegisterFormGUI().setVisible(true);
            }
        });
    }
}