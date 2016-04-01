import java.awt.EventQueue;

import java.sql.*;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Login {

	private JFrame frame;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JPanel createRegistrarPanel, loginPanel;
	private Lawyer l1, l2;
	private JTextField txtRegUsername;
	private JPasswordField pwdReg_1;
	private JPasswordField pwdReg_2;
	
	public static DBConnect db;
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
		
		db = new DBConnect();
		
		if(! db.checkDB("jiss")){ //if database does not exist
			
			//create the databases and all tables
			
			db.update("create database jiss");
			db.query("use jiss");
			db.update("create table users (username varchar(20), password varchar(20), type char(1))");
			db.update("create table cases (CIN int(8))");
			db.update("create table hearings (CIN int(8), scheduled_date date, slot char(1), summary varchar(20))");
			db.update("create table adjs (CIN int(8), scheduled_date date, slot char(1), reason varchar(20)) ");
			
			db.update("create table lawyers (username varchar(20), l_id int(8), no_of_views int(8))");
			db.update("create table judges(username varchar(20), j_id int(8))");
		}
		else
			db.query("use jiss");
		
	
		//db.update("drop database jiss");
		
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
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		createRegistrarPanel = new JPanel();
		frame.getContentPane().add(createRegistrarPanel, "name_48817473655117");
		createRegistrarPanel.setLayout(null);
		
		JButton btnCreateRegistrar = new JButton("Create Registrar");
		btnCreateRegistrar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
				if(! pwdReg_1.getText().equals(pwdReg_2.getText())){
					JOptionPane.showMessageDialog(frame, pwdReg_1.getText() + " : " + pwdReg_2.getText());
					pwdReg_1.setText("");
					pwdReg_2.setText("");
					return;
				}
				
				db.update("insert into users values (\"" + txtRegUsername.getText() + "\",\""  + pwdReg_1.getText() + "\", \"R\")");
				createRegistrarPanel.setVisible(false);
				loginPanel.setVisible(true);
				
			}
		});
		btnCreateRegistrar.setBounds(73, 226, 199, 25);
		createRegistrarPanel.add(btnCreateRegistrar);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(73, 44, 115, 15);
		createRegistrarPanel.add(lblUsername);
		
		JLabel lblPassword_1 = new JLabel("Password:");
		lblPassword_1.setBounds(73, 71, 102, 15);
		createRegistrarPanel.add(lblPassword_1);
		
		JLabel lblConfirmPassword = new JLabel("Confirm password:");
		lblConfirmPassword.setBounds(73, 98, 133, 15);
		createRegistrarPanel.add(lblConfirmPassword);
		
		txtRegUsername = new JTextField();
		txtRegUsername.setBounds(226, 42, 182, 19);
		createRegistrarPanel.add(txtRegUsername);
		txtRegUsername.setColumns(10);
		
		pwdReg_1 = new JPasswordField();
		pwdReg_1.setBounds(226, 69, 183, 19);
		createRegistrarPanel.add(pwdReg_1);
		
		pwdReg_2 = new JPasswordField();
		pwdReg_2.setBounds(225, 96, 183, 19);
		createRegistrarPanel.add(pwdReg_2);
		
		JLabel lblRegistrarAccountCreation = new JLabel("Registrar Account Creation:");
		lblRegistrarAccountCreation.setBounds(73, 12, 219, 15);
		createRegistrarPanel.add(lblRegistrarAccountCreation);
		
		loginPanel = new JPanel();
		frame.getContentPane().add(loginPanel, "name_48894252615869");
		loginPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username : ");
		lblNewLabel.setBounds(33, 94, 90, 26);
		loginPanel.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password : ");
		lblPassword.setBounds(33, 132, 102, 26);
		loginPanel.add(lblPassword);
		
		JComboBox<String> cmbTypeUser = new JComboBox<String>();
		cmbTypeUser.addItem("Registrar");
		cmbTypeUser.addItem("Lawyer");
		cmbTypeUser.addItem("Judge");
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
		
		JButton btnLogin = new JButton("Log in");
		btnLogin.setBounds(33, 210, 117, 25);
		loginPanel.add(btnLogin);
		
		
	}
}
