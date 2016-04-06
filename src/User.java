import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.CardLayout;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class User{
	
	private String username, password;
	private char type;
	
	protected JPanel panel;
	protected JPanel mainPanel;


	private JPanel pwdchangePanel;
	private JTextField txtKeywords;
	private JTable tblCases;
	private JPasswordField pwdOld;
	private JPasswordField pwdNew_1;
	private JPasswordField pwdNew_2;
	
	protected JLabel lblNoOfViews;
	
	private JISS parent;
	public JPanel CRPanel, URPanel, casePanel;
	
	private JScrollPane sp;
	
	public User(String u, String p, char t){
		username = u;
		password = p;
		type = t;
	}
	
	private int changePassword(){
		if(! String.valueOf(pwdOld.getPassword()).equals(password))
			return 1;
		else if(! String.valueOf(pwdNew_1.getPassword()).equals(String.valueOf(pwdNew_2.getPassword()))){
			return 2;
		}else if(String.valueOf(pwdNew_1.getPassword()).isEmpty()){
			return 3;
		}else{
			password = String.valueOf(pwdNew_1.getPassword());
			return 0;
		}
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void initPanel(JISS p){
		
		parent = p;
		panel = new JPanel();
		panel.setSize(500,500);
		panel.setLayout(new CardLayout(0, 0));
		
		mainPanel = new JPanel();
		panel.add(mainPanel, "name_1007512518629");
		mainPanel.setLayout(null);
		
		
		JLabel lblEnterKeywords = new JLabel("Enter keyword(s):");
		lblEnterKeywords.setBounds(57, 49, 174, 15);
		mainPanel.add(lblEnterKeywords);
		
		txtKeywords = new JTextField();
		txtKeywords.setBounds(204, 47, 174, 19);
		mainPanel.add(txtKeywords);
		txtKeywords.setColumns(10);
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.backFromUser();
			}
		});
		btnLogOut.setBounds(371, 12, 117, 25);
		mainPanel.add(btnLogOut);
		
		
		tblCases = new JTable();
		sp = new JScrollPane(tblCases);
		sp.setBounds(12, 104, 476, 274);
		mainPanel.add(sp);
		
		pwdchangePanel = new JPanel();
		panel.add(pwdchangePanel, "name_1113697649244");
		pwdchangePanel.setLayout(null);
		
		JButton btnChangePassword = new JButton("Change password");
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				pwdchangePanel.setVisible(true);
			}
		});
		btnChangePassword.setBounds(280, 447, 167, 25);
		mainPanel.add(btnChangePassword);
		
		JLabel lblLoggedInAs = new JLabel("Welcome");
		lblLoggedInAs.setBounds(56, 24, 73, 15);
		mainPanel.add(lblLoggedInAs);
		
		JLabel lblLoggedUsername = new JLabel(username);
		lblLoggedUsername.setBounds(139, 24, 109, 15);
		mainPanel.add(lblLoggedUsername);
		
		JButton btnUserManagement = new JButton("User management");
		btnUserManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JISS.UR.initPanel(getThis());
				URPanel = JISS.UR.getPanel();
				panel.add(URPanel);
				URPanel.setVisible(true);
				mainPanel.setVisible(false);
			}
		});
		btnUserManagement.setBounds(35, 410, 193, 25);
		mainPanel.add(btnUserManagement);
		
		btnUserManagement.setEnabled(type == 'R');
		JButton btnCaseManagement = new JButton("Case management");
		btnCaseManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JISS.CR.initPanel(getThis());
				CRPanel = JISS.CR.getPanel();
				panel.add(CRPanel);
				CRPanel.setVisible(true);
				mainPanel.setVisible(false);
				
				
			}
		});
		btnCaseManagement.setBounds(35, 447, 194, 25);
		mainPanel.add(btnCaseManagement);
		
		btnCaseManagement.setEnabled(type == 'R');
		JButton btnGo = new JButton("Go");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String keywords = txtKeywords.getText();
				if(keywords.equals("")){
					JOptionPane.showMessageDialog(panel, "Keyword cannot be empty!");
					return;
				}
				
				String[] cols = {"CIN", "Defendant name", "Starting date", "Type", "Status"};
				int count = JISS.db.queryCount("select count(*) from cases where concat_ws(defName, defAddr, type, location, ao, pj, pp)"
						+ " like '%" + keywords + "%'");
				
				//System.out.println(count);
				String[][] rows = new String[count][5];
				int i=0;
				
				ResultSet rs = JISS.db.getrs("select CIN,defName,dateStart,type,status from cases where concat_ws(defName, defAddr, type, location, ao, pj, pp)"
						+ " like '%" + keywords + "%'");
				try{
					while(rs.next()){
						for(int j=0; j<4; j++){
							rows[i][j] = rs.getString(j+1);
						}
						if(rs.getString(5).equals("1"))
							rows[i][4] = "Closed";
						else
							rows[i][4] = "Pending";
						i++;
					}
				
				tblCases = new JTable(rows, cols);
				}catch(Exception ex){}
				
				mainPanel.remove(sp);
				sp = new JScrollPane(tblCases);
				sp.setBounds(12, 104, 476, 274);
				mainPanel.add(sp);
			}
			
		});
		btnGo.setBounds(390, 46, 60, 25);
		mainPanel.add(btnGo);
		
		JLabel lblNumberOfCases = new JLabel("Number of cases viewed:");
		lblNumberOfCases.setBounds(57, 76, 199, 15);
		mainPanel.add(lblNumberOfCases);
		lblNumberOfCases.setVisible(type == 'L');
		lblNoOfViews = new JLabel("");
		lblNoOfViews.setBounds(245, 77, 70, 15);
		mainPanel.add(lblNoOfViews);
		
		JButton btnView = new JButton("View");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ch = tblCases.getSelectedRow();
				if(ch==-1){
					JOptionPane.showMessageDialog(panel, "Please select a case!");
					return;
				}
				String cin = (String) tblCases.getValueAt(ch, 0);
				int cin_ = Integer.parseInt(cin);
				Case case_ = JISS.CR.getCase(cin_);
				case_.initPanel(getThis(), type == 'R');
				casePanel = case_.getPanel();
				panel.add(casePanel);
				casePanel.setVisible(true);
				mainPanel.setVisible(false);
				case_.goToView();
				
				if(type == 'L'){
					((Lawyer)getThis()).increaseCount();
					JISS.db.update("update lawyers set no_of_views = no_of_views+1 where username = \""+ username + "\"");
				}
			}
		});
		btnView.setBounds(280, 410, 167, 25);
		mainPanel.add(btnView);
		lblNoOfViews.setVisible(type == 'L');
		
		JLabel lblEnterOldPassword = new JLabel("Enter old password:");
		lblEnterOldPassword.setBounds(35, 80, 186, 15);
		pwdchangePanel.add(lblEnterOldPassword);
		
		JLabel lblEnterNewPassword = new JLabel("Enter new password:");
		lblEnterNewPassword.setBounds(35, 122, 151, 15);
		pwdchangePanel.add(lblEnterNewPassword);
		
		JLabel lblConfirmNewPassword = new JLabel("Confirm new password:");
		lblConfirmNewPassword.setBounds(35, 169, 167, 15);
		pwdchangePanel.add(lblConfirmNewPassword);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pwdchangePanel.setVisible(false);
				mainPanel.setVisible(true);
				pwdOld.setText("");
				pwdNew_1.setText("");
				pwdNew_2.setText("");
			}
		});
		btnCancel.setBounds(55, 233, 117, 25);
		pwdchangePanel.add(btnCancel);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(changePassword() == 0){
					pwdchangePanel.setVisible(false);
					mainPanel.setVisible(true);
					JISS.db.update("update users set password=\"" + password + "\" where username = \"" + username + "\"");
				}
				else if(changePassword() == 1){
					JOptionPane.showMessageDialog(panel, "Wrong old password!");
				}
				else if(changePassword() == 2){
					JOptionPane.showMessageDialog(panel, "Re-typed password does not match!");
				}else if(changePassword() == 3){
					JOptionPane.showMessageDialog(panel, "Password cannot be empty!");
				}
				
				pwdOld.setText("");
				pwdNew_1.setText("");
				pwdNew_2.setText("");
			}
			
		});
		btnOk.setBounds(203, 233, 117, 25);
		pwdchangePanel.add(btnOk);
		
		pwdOld = new JPasswordField();
		pwdOld.setBounds(214, 78, 139, 25);
		pwdchangePanel.add(pwdOld);
		
		pwdNew_1 = new JPasswordField();
		pwdNew_1.setBounds(214, 117, 139, 25);
		pwdchangePanel.add(pwdNew_1);
		
		pwdNew_2 = new JPasswordField();
		pwdNew_2.setBounds(214, 167, 139, 25);
		pwdchangePanel.add(pwdNew_2);
		
		
		
	}
	
	public User getThis(){
		return this;
	}
	
	public void backFromUR(){ //when control goes from UsersRecord object to User
		mainPanel.setVisible(true);
		URPanel.setVisible(false);
		panel.remove(URPanel);
		URPanel = null;
	}
	
	public void backFromCR(){//when control goes from CasesRecord object to User
		mainPanel.setVisible(true);
		CRPanel.setVisible(false);
		panel.remove(CRPanel);
		CRPanel = null;
	}
	
	public void backFromCase(){ //when control goes from Case object to User
		mainPanel.setVisible(true);
		casePanel.setVisible(false);
		panel.remove(casePanel);
		casePanel = null;
	}
	
	public JPanel getPanel(){
		return panel;
	}
}