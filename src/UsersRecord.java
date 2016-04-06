import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

public class UsersRecord{
	
	private JPanel panel;
	
	private JTable tblUsers;
	private JTextField txtUsername;
	private JTextField txtPassword;
	
	private JPanel userManPanel, newUserPanel;
	
	private JComboBox<String> cmbTypeUser;
	
	private JScrollPane sp;
	private User accessor; //the user accessing the UsersRecord
	/**
	 * @wbp.parser.entryPoint
	 */
	public void initPanel(User ac){
		
		accessor = ac;
		
		panel = new JPanel();
		panel.setSize(500, 500);
		panel.setLayout(new CardLayout(0, 0));
		
		
		userManPanel = new JPanel();
		panel.add(userManPanel, "name_1091471022440");
		userManPanel.setLayout(null);
		
		loadTableUsers();
		//tblUsers = new JTable();
		sp = new JScrollPane(tblUsers);
		sp.setBounds(79, 74, 341, 300);
		userManPanel.add(sp);
		
		
		JButton btnNewUser = new JButton("New user");
		btnNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userManPanel.setVisible(false);
				newUserPanel.setVisible(true);
			}
		});
		btnNewUser.setBounds(79, 402, 117, 25);
		userManPanel.add(btnNewUser);
		
		JButton btnDeleteUser = new JButton("Delete user");
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteUser();
			}
		});
		btnDeleteUser.setBounds(210, 402, 117, 25);
		userManPanel.add(btnDeleteUser);
		
		JLabel lblListOfRegistered = new JLabel("List of registered users:");
		lblListOfRegistered.setBounds(79, 32, 215, 15);
		userManPanel.add(lblListOfRegistered);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accessor.backFromUR();
			}
		});
		btnBack.setBounds(79, 439, 117, 25);
		userManPanel.add(btnBack);
		
		newUserPanel = new JPanel();
		panel.add(newUserPanel, "name_3062697305341");
		newUserPanel.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(88, 90, 106, 15);
		newUserPanel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(88, 135, 83, 15);
		newUserPanel.add(lblPassword);
		
		JButton btnCreateUser = new JButton("Create user");
		btnCreateUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createUser();
			}
		});
		btnCreateUser.setBounds(88, 303, 155, 25);
		newUserPanel.add(btnCreateUser);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmbTypeUser.setSelectedIndex(0);
				txtUsername.setText("");
				txtPassword.setText("");
				newUserPanel.setVisible(false);
				userManPanel.setVisible(true);
			}
		});
		btnCancel.setBounds(272, 303, 117, 25);
		newUserPanel.add(btnCancel);
		
		JLabel lblTypeOfUser = new JLabel("Type of user:");
		lblTypeOfUser.setBounds(88, 50, 106, 15);
		newUserPanel.add(lblTypeOfUser);
		
		cmbTypeUser = new JComboBox<String>();
		cmbTypeUser.addItem("Lawyer");
		cmbTypeUser.addItem("Judge");
		cmbTypeUser.setBounds(225, 45, 130, 25);
		newUserPanel.add(cmbTypeUser);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(225, 88, 114, 19);
		newUserPanel.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(225, 133, 114, 19);
		newUserPanel.add(txtPassword);
		txtPassword.setColumns(10);
		
		//loadTableUsers();
	}
	
	public void loadTableUsers(){ //loads the users table from the database
		
		String[] cols = {"Username", "Type"};
		int count = JISS.db.queryCount("select count(*) from users");
		
		String[][] rows = new String[count - 1][2];
		int i=0;
		
		ResultSet rs = JISS.db.getrs("select username,type from users where type != \"R\"");
		try{
			while(rs.next()){
				for(int j=0; j<2; j++){
					rows[i][j] = rs.getString(j+1);
				}
				i++;
			}
		
		tblUsers = new JTable(rows, cols);
		}catch(Exception e){}
		
	}
	
	public void reloadTableUsers(){ //reloads user table to show changes
		
		userManPanel.remove(sp);
		loadTableUsers();
		sp = new JScrollPane(tblUsers);
		sp.setBounds(79, 74, 341, 300);

		userManPanel.add(sp);
	}
	public JPanel getPanel(){
		return panel;
	}
	public void createUser(){
		int ch = cmbTypeUser.getSelectedIndex();
		int c = JISS.db.queryCount("select count(*) from users where username = \"" + txtUsername.getText() + "\"");
		if(c!=0){
			JOptionPane.showMessageDialog(panel, "Username already taken!");
			return;
		}
		
		if(txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()){
			JOptionPane.showMessageDialog(panel, "All fields required!");
			return;
		}
		char t;
		if(ch == 0) t = 'L';
		else t = 'J';
		JISS.db.update("insert into users values (\"" + txtUsername.getText() + "\", \"" + txtPassword.getText() + "\", \"" + t + "\")");
		
		if(t=='L')
			JISS.db.update("insert into lawyers values (\""+ txtUsername.getText()+ "\", 0)");
		
		cmbTypeUser.setSelectedIndex(0);
		txtUsername.setText("");
		txtPassword.setText("");
		newUserPanel.setVisible(false);
		userManPanel.setVisible(true);
		
		reloadTableUsers();
	}
	
	public void deleteUser(){
		int ch = tblUsers.getSelectedRow();
		if(ch==-1){
			JOptionPane.showMessageDialog(panel, "Please select a user!");
			return;
		}
		//System.out.println(ch);
		String uname = (String) tblUsers.getValueAt(ch, 0);
		JISS.db.update("delete from users where username = \"" + uname + "\"");
		
		reloadTableUsers();
	}
}