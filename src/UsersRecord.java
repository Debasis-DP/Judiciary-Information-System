import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class UsersRecord{
	
	private JPanel panel;
	
	private JTable tblUsers;
	private JTextField txtUsername;
	private JTextField txtPassword;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void initPanel(){
		
		panel = new JPanel();
		panel.setSize(500, 500);
		panel.setLayout(new CardLayout(0, 0));
		
		
		JPanel userManPanel = new JPanel();
		panel.add(userManPanel, "name_1091471022440");
		userManPanel.setLayout(null);
		
		tblUsers = new JTable();
		tblUsers.setBounds(79, 74, 341, 300);
		userManPanel.add(tblUsers);
		
		JButton btnNewUser = new JButton("New user");
		btnNewUser.setBounds(79, 402, 117, 25);
		userManPanel.add(btnNewUser);
		
		JButton btnDeleteUser = new JButton("Delete user");
		btnDeleteUser.setBounds(210, 402, 117, 25);
		userManPanel.add(btnDeleteUser);
		
		JLabel lblListOfRegistered = new JLabel("List of registered users:");
		lblListOfRegistered.setBounds(79, 32, 215, 15);
		userManPanel.add(lblListOfRegistered);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(79, 439, 117, 25);
		userManPanel.add(btnBack);
		
		JPanel newUserPanel = new JPanel();
		panel.add(newUserPanel, "name_3062697305341");
		newUserPanel.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(88, 90, 106, 15);
		newUserPanel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(88, 135, 83, 15);
		newUserPanel.add(lblPassword);
		
		JButton btnCreateUser = new JButton("Create user");
		btnCreateUser.setBounds(88, 303, 155, 25);
		newUserPanel.add(btnCreateUser);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(272, 303, 117, 25);
		newUserPanel.add(btnCancel);
		
		JLabel lblTypeOfUser = new JLabel("Type of user:");
		lblTypeOfUser.setBounds(88, 50, 106, 15);
		newUserPanel.add(lblTypeOfUser);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(225, 45, 130, 25);
		newUserPanel.add(comboBox);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(225, 88, 114, 19);
		newUserPanel.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(225, 133, 114, 19);
		newUserPanel.add(txtPassword);
		txtPassword.setColumns(10);
		
	}
	
	public JPanel getPanel(){
		return panel;
	}
	public void createUser(){
		
	}
	
	public void deleteUser(){
		
	}
}