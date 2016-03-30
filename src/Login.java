import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class Login {

	private JFrame frame;
	private JTextField txtUsername;
	private JTextField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel createRegistrarPanel = new JPanel();
		frame.getContentPane().add(createRegistrarPanel, "name_48817473655117");
		createRegistrarPanel.setLayout(null);
		
		JButton btnCreateRegistrar = new JButton("Create Registrar");
		btnCreateRegistrar.setBounds(61, 87, 199, 46);
		createRegistrarPanel.add(btnCreateRegistrar);
		
		JPanel loginPanel = new JPanel();
		frame.getContentPane().add(loginPanel, "name_48894252615869");
		loginPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username : ");
		lblNewLabel.setBounds(33, 94, 90, 26);
		loginPanel.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password : ");
		lblPassword.setBounds(33, 132, 102, 26);
		loginPanel.add(lblPassword);
		
		JComboBox cmbTypeUser = new JComboBox();
		cmbTypeUser.setBounds(178, 39, 158, 24);
		loginPanel.add(cmbTypeUser);
		
		JLabel lblTypeOfUser = new JLabel("Type of User : ");
		lblTypeOfUser.setBounds(33, 38, 102, 26);
		loginPanel.add(lblTypeOfUser);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(178, 94, 158, 26);
		loginPanel.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(178, 132, 158, 26);
		loginPanel.add(txtPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(33, 210, 117, 25);
		loginPanel.add(btnLogin);
	}
}
