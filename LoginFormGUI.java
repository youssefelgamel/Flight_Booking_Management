import javax.swing.*;
import java.awt.*; 
public class LoginFormGUI extends JFrame {

	public LoginFormGUI() {
		super("Flight Booking Management Login");
		addGuiComponents();
	}
	
	private void addGuiComponents() {
		JLabel titleLabel = new JLabel("Login");
		titleLabel.setBounds(0, 25, 520, 100);
		getContentPane().add(titleLabel);
		titleLabel.setFont(titleLabel.getFont().deriveFont(24.0f)); // set font size
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // center the title
		titleLabel.setVerticalAlignment(SwingConstants.TOP); // align to top
		titleLabel.setForeground(CommonConstants.TEXT_COLOR); // set color to white

		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(50, 70, 100, 30);
		getContentPane().add(usernameLabel);

		JTextField usernameField = new JTextField();
		usernameField.setBounds(150, 70, 200, 30);
		getContentPane().add(usernameField);
		usernameField.setBackground(CommonConstants.TEXT_BOX_COLOR);
		usernameField.setForeground(CommonConstants.TEXT_COLOR);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(50, 120, 100, 30);
		getContentPane().add(passwordLabel);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(150, 120, 200, 30);
		getContentPane().add(passwordField);
		passwordField.setBackground(CommonConstants.TEXT_BOX_COLOR);
		passwordField.setForeground(CommonConstants.TEXT_COLOR);
		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Dialog",Font.BOLD,18));
		loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginButton.setBackground(CommonConstants.BUTTON_COLOR);
		loginButton.setBounds(125, 520, 250,  50);
		add(loginButton);
		Jlabel registerLabel=new JLabel(Not a user?? Register now);
		registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		registerLabel.setForeground(CommonConstants.TEXT_COLOR);
		registerLabel.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicker(MouseEvent e){
				LoginFormGUI.this.dispose();
				new RegisterFormGUI().setVisible(true); 
			}
		});
		registerLabel.setBounds(125,600,250,30);
		add(registerLabel);

	}
}