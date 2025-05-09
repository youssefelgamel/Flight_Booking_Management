import javax.swing.*;
import java.awt.*; 
public class RegisterFormGUI extends JFrame {

	public RegisterFormGUI() {
		super("Flight Booking Management Register");
		addGuiComponents();
	}
	
	private void addGuiComponents() {
		JLabel titleLabel = new JLabel("Register");
		titleLabel.setBounds(0, 25, 520, 100);
		getContentPane().add(titleLabel);
		titleLabel.setFont(titleLabel.getFont().deriveFont(24.0f)); // set font size
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // center the title
		titleLabel.setVerticalAlignment(SwingConstants.TOP); // align to top
		titleLabel.setForeground(CommonConstants.TEXT_COLOR); // set color to white

		JLabel usernameLabel = new JLabel("Enter a Username:");
		usernameLabel.setBounds(50, 70, 100, 30);
		getContentPane().add(usernameLabel);

		JTextField usernameField = new JTextField();
		usernameField.setBounds(150, 70, 200, 30);
		getContentPane().add(usernameField);
		usernameField.setBackground(CommonConstants.TEXT_BOX_COLOR);
		usernameField.setForeground(CommonConstants.TEXT_COLOR);
		
		JLabel passwordLabel = new JLabel("Enter a Password:");
		passwordLabel.setBounds(30, 255, 400, 25);
		getContentPane().add(passwordLabel);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(30, 285, 450, 55);
		getContentPane().add(passwordField);
		passwordField.setBackground(CommonConstants.TEXT_BOX_COLOR);
		passwordField.setForeground(CommonConstants.TEXT_COLOR);
		JLabel repasswordLabel = new JLabel("Re-enter a Password:");
		repasswordLabel.setBounds(30, 35, 400, 25);
		getContentPane().add(repasswordLabel);

		JPasswordField repasswordField = new JPasswordField();
		repasswordField.setBounds(30, 395, 400, 35);
		getContentPane().add(repasswordField);
		repasswordField.setBackground(CommonConstants.TEXT_BOX_COLOR);
		repasswordField.setForeground(CommonConstants.TEXT_COLOR);
        add(repassswordLabel);
        add(repassswordField);

        JButton RegisterButton = new JButton("Register");
		RegisterButton.setFont(new Font("Dialog",Font.BOLD,18));
		RegisterButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		RegisterButton.setBackground(CommonConstants.BUTTON_COLOR);
		RegisterButton.setBounds(125, 520, 250,  50);
		add(RegisterButton);
        Jlabel loginLabel=new JLabel(Hava an accunt?? Login now);
		loginLabel.setHorizontalAlignment(Swing.Constants.CENTER);
		loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginLabel.setForeground(CommonConstants.TEXT_COLOR);
        LoginLabel.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicker(MouseEvent e){
				RegisterFormGUI.this.dispose();
				new LoginFormGUI().setVisible(true); 
			}
		});
        loginLabel.setBounds(125,600,250,30);
		add(loginLabel);
	 }
}