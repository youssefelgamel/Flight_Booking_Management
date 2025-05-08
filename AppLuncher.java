import javax.swing.*;
public class AppLuncher {
    public static void main(String[]args){
    SwingUtitles.invokeLater(new Runnable() {
        public void run(){
            //make our ui visible
            new  LoginFormGUI().setVisible(true);           
        }
    }); 
    }    
}
