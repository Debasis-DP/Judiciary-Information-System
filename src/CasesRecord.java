import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class CasesRecord{
	
	private JPanel panel, caseManPanel, queryPanel, createPanel;
	private JTable tblCases;
	private JTextField txtDefName;
	private JTextField txtDefAddr;
	private JTextField txtTypeCrime, txtDateCrime;
	private JTextField txtLocation;
	private JTextField txtArrestingOffcr;
	private JTextField txtDateArrest;
	private JTextField txtFrom;
	private JTextField txtTo;
	private JTextField txtDateHearing;
	private JTextField txtCIN;
	private JTable tblQueryCases;
	
	private Case current; //the current Case object
	private JPanel casePanel;
	
	private JScrollPane sp, sp2;
	
	private User accessor;
	private boolean querying= false;
	/**
	 * @wbp.parser.entryPoint
	 */
	public void initPanel(User ac){
		
		accessor = ac;
		panel = new JPanel();
		panel.setSize(500, 500);
		panel.setLayout(new CardLayout(0, 0));
		
		caseManPanel = new JPanel();
		panel.add(caseManPanel, "name_1122675107797");
		caseManPanel.setLayout(null);
		
		
		loadTableCases();
		//tblCases = new JTable();
		sp = new JScrollPane(tblCases);
		sp.setBounds(12, 42, 476, 336);
		caseManPanel.add(sp);
		
		JLabel lblListOfCases = new JLabel("List of cases:");
		lblListOfCases.setBounds(76, 12, 155, 15);
		caseManPanel.add(lblListOfCases);
		
		JButton btnNewCase = new JButton("New case");
		btnNewCase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createPanel.setVisible(true);
				caseManPanel.setVisible(false);
			}
		});
		btnNewCase.setBounds(76, 400, 117, 25);
		caseManPanel.add(btnNewCase);
		
		JButton btnEditCase = new JButton("Edit case");
		btnEditCase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ch = tblCases.getSelectedRow();
				if(ch==-1){
					JOptionPane.showMessageDialog(panel, "Please select a case!");
					return;
				}
				String cin = (String) tblCases.getValueAt(ch, 0);
				int cin_ = Integer.parseInt(cin);
				Case case_ = getCase(cin_);
				case_.initPanel(getThis());
				casePanel = case_.getPanel();
				panel.add(casePanel);
				casePanel.setVisible(true);
				caseManPanel.setVisible(false);
				case_.goToView();
			}
		});
		btnEditCase.setBounds(205, 400, 117, 25);
		caseManPanel.add(btnEditCase);
		
		JButton btnBack_1 = new JButton("Back");
		btnBack_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accessor.backFromCR();
			}
		});
		btnBack_1.setBounds(76, 447, 117, 25);
		caseManPanel.add(btnBack_1);
		
		JButton btnQueryCase = new JButton("Query");
		btnQueryCase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				queryPanel.setVisible(true);
				caseManPanel.setVisible(false);
			}
		});
		btnQueryCase.setBounds(205, 447, 117, 25);
		caseManPanel.add(btnQueryCase);
		
		queryPanel = new JPanel();
		panel.add(queryPanel, "name_1725993655147");
		queryPanel.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 40, 476, 110);
		queryPanel.add(tabbedPane);
		
		JPanel pendingPanel = new JPanel();
		tabbedPane.addTab("Pending", null, pendingPanel, null);
		pendingPanel.setLayout(null);
		
		JLabel lblShowingAllPending = new JLabel("All pending cases:");
		lblShowingAllPending.setBounds(25, 31, 249, 15);
		pendingPanel.add(lblShowingAllPending);
		
		JButton btnGo_3 = new JButton("Go");
		btnGo_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String[] cols = {"CIN", "Defendant name", "Starting date", "Type"};
				int count = JISS.db.queryCount("select count(*) from cases where status=0");
				
				String[][] rows = new String[count][4];
				int i=0;
				
				ResultSet rs = JISS.db.getrs("select CIN,defName,dateStart,type from cases where status=0");
				try{
					while(rs.next()){
						for(int j=0; j<4; j++){
							rows[i][j] = rs.getString(j+1);
						}
						
						i++;
					}
				
				tblQueryCases = new JTable(rows, cols);
				}catch(Exception ex){}
				
				reloadTableQuery();
				
			}
		});
		btnGo_3.setBounds(171, 26, 117, 25);
		pendingPanel.add(btnGo_3);
		
		JPanel resolvedPanel = new JPanel();
		tabbedPane.addTab("Resolved", null, resolvedPanel, null);
		resolvedPanel.setLayout(null);
		
		JLabel lblFromdate = new JLabel("From (date):");
		lblFromdate.setBounds(23, 12, 101, 15);
		resolvedPanel.add(lblFromdate);
		
		JLabel lblTodate = new JLabel("To (date):");
		lblTodate.setBounds(23, 39, 82, 15);
		resolvedPanel.add(lblTodate);
		
		txtFrom = new JTextField();
		txtFrom.setBounds(138, 10, 114, 19);
		resolvedPanel.add(txtFrom);
		txtFrom.setColumns(10);
		
		txtTo = new JTextField();
		txtTo.setBounds(138, 37, 114, 19);
		resolvedPanel.add(txtTo);
		txtTo.setColumns(10);
		
		JButton btnGo = new JButton("Go");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				if(JISS.getDate(txtFrom.getText()) == null || JISS.getDate(txtTo.getText()) == null){
					JOptionPane.showMessageDialog(panel, "Please enter valid date (dd/mm/yyyy)!");
					return;
				}
				
				String[] cols = {"Starting date", "CIN", "Date of judgement", "Presiding judge", "Judgement summary"};
				int count = JISS.db.queryCount("select count(*) from cases where str_to_date(dateStart, '%d/%m/%Y')"+
				" > str_to_date(\"" + txtFrom.getText() + "\", '%d/%m/%Y') and str_to_date(dateHearing, '%d/%m/%Y')"+
				" < str_to_date(\"" + txtTo.getText() + "\", '%d/%m/%Y') and status=1");
				//System.out.println(count);
				String[][] rows = new String[count][5];
				int i=0;
				
				try{
					ResultSet rs = JISS.db.getrs("select dateStart, CIN, dateHearing, pj, jsum from cases where str_to_date(dateStart, '%d/%m/%Y')"+
				" > str_to_date(\"" + txtFrom.getText() + "\", '%d/%m/%Y') and str_to_date(dateHearing, '%d/%m/%Y')"+
				" < str_to_date(\"" + txtTo.getText() + "\", '%d/%m/%Y') and status=1 order by str_to_date(dateStart, '%d/%m/%Y')");
						while(rs.next()){
							for(int j=0; j<5; j++){
								rows[i][j] = rs.getString(j+1);
							}
						}
					
					tblQueryCases = new JTable(rows, cols);
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
				reloadTableQuery();
			}
		});
		btnGo.setBounds(264, 12, 117, 25);
		resolvedPanel.add(btnGo);
		
		JPanel dateofHearingPanel = new JPanel();
		tabbedPane.addTab("Date of hearing", null, dateofHearingPanel, null);
		dateofHearingPanel.setLayout(null);
		
		JLabel lblDateOfHearing = new JLabel("Date of hearing:");
		lblDateOfHearing.setBounds(30, 29, 116, 15);
		dateofHearingPanel.add(lblDateOfHearing);
		
		txtDateHearing = new JTextField();
		txtDateHearing.setBounds(150, 27, 114, 19);
		dateofHearingPanel.add(txtDateHearing);
		txtDateHearing.setColumns(10);
		
		JButton btnGo_1 = new JButton("Go");
		btnGo_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(JISS.getDate(txtDateHearing.getText()) == null){
					JOptionPane.showMessageDialog(panel, "Please enter valid date (dd/mm/yyyy)!");
					return;
				}
				String[] cols = {"CIN", "Defendant name", "Type", "Slot"};
				int count = JISS.db.queryCount("select count(*) from cases where dateHearing=\"" + txtDateHearing.getText() + "\"");
				//System.out.println(count);
				String[][] rows = new String[count][4];
				int i=0;
				
				ResultSet rs = JISS.db.getrs("select CIN,defName,dateStart from cases where dateHearing=\"" + txtDateHearing.getText() + "\"");
				try{
					while(rs.next()){
						for(int j=0; j<3; j++){
							rows[i][j] = rs.getString(j+1);
						}
				
					}
					for(i=0; i<count; i++){
						rs = JISS.db.getrs("select slot from hearings where CIN=" + rows[i][0] 
								+ " and scheduled_date = \"" + txtDateHearing.getText() + "\"");
						rs.next();
						rows[i][3] = rs.getString(1);
					}
				
				tblQueryCases = new JTable(rows, cols);
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
				reloadTableQuery();
			
			}
		});
		btnGo_1.setBounds(276, 24, 93, 25);
		dateofHearingPanel.add(btnGo_1);
		
		JPanel CINPanel = new JPanel();
		tabbedPane.addTab("CIN", null, CINPanel, null);
		CINPanel.setLayout(null);
		
		JLabel lblCin = new JLabel("CIN:");
		lblCin.setBounds(32, 31, 70, 15);
		CINPanel.add(lblCin);
		
		txtCIN = new JTextField();
		txtCIN.setBounds(88, 29, 131, 19);
		CINPanel.add(txtCIN);
		txtCIN.setColumns(10);
		
		JButton btnGo_2 = new JButton("Go");
		btnGo_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtCIN.getText().isEmpty()){
					JOptionPane.showMessageDialog(panel, "Please enter a CIN!");
					return;
				}
				String[] cols = {"CIN", "Defendant name", "Starting date", "Type", "Status"};
				int count = JISS.db.queryCount("select count(*) from cases where CIN=" + txtCIN.getText());
				
				String[][] rows = new String[count][5];
				int i=0;
				
				ResultSet rs = JISS.db.getrs("select CIN,defName,dateStart,type,status from cases where CIN=" + txtCIN.getText());
				try{
					while(rs.next()){
						for(int j=0; j<4; j++){
							rows[i][j] = rs.getString(j+1);
						}
						if(rs.getString(5).equals("1"))
							rows[i][4] = "Closed";
						else
							rows[i][4] = "Pending";
					}
				
				tblQueryCases = new JTable(rows, cols);
				}catch(Exception ex){}
				
				reloadTableQuery();
			}
		});
		btnGo_2.setBounds(234, 26, 99, 25);
		CINPanel.add(btnGo_2);
		
		tblQueryCases = new JTable();
		sp2 = new JScrollPane(tblQueryCases);
		sp2.setBounds(12, 174, 476, 251);
		queryPanel.add(sp2);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				queryPanel.setVisible(false);
				caseManPanel.setVisible(true);
				querying = false;
				
			}
		});
		btnBack.setBounds(34, 439, 117, 25);
		queryPanel.add(btnBack);
		
		JButton btnView = new JButton("View");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ch = tblQueryCases.getSelectedRow();
				if(ch==-1){
					JOptionPane.showMessageDialog(panel, "Please select a case!");
					return;
				}
				String cin = (String) tblQueryCases.getValueAt(ch, 0);
				int cin_ = Integer.parseInt(cin);
				Case case_ = getCase(cin_);
				case_.initPanel(getThis());
				casePanel = case_.getPanel();
				panel.add(casePanel);
				casePanel.setVisible(true);
				queryPanel.setVisible(false);
				case_.goToView();
				
				querying = true;
			}
		});
		btnView.setBounds(177, 439, 117, 25);
		queryPanel.add(btnView);
		
		createPanel = new JPanel();
		panel.add(createPanel, "name_1914445518684");
		createPanel.setLayout(null);
		
		JLabel lblDefendantName = new JLabel("Defendant name:");
		lblDefendantName.setBounds(51, 52, 177, 15);
		createPanel.add(lblDefendantName);
		
		JLabel lblDefendantAddress = new JLabel("Defendant address:");
		lblDefendantAddress.setBounds(51, 87, 177, 15);
		createPanel.add(lblDefendantAddress);
		
		JLabel lblTypeOfCrime = new JLabel("Type of crime:");
		lblTypeOfCrime.setBounds(51, 129, 177, 15);
		createPanel.add(lblTypeOfCrime);
		
		JLabel lblLocationOfCrime = new JLabel("Location of crime:");
		lblLocationOfCrime.setBounds(51, 170, 177, 15);
		createPanel.add(lblLocationOfCrime);
		
		JLabel lblNameOfArresting = new JLabel("Name of arresting officer:");
		lblNameOfArresting.setBounds(51, 216, 192, 15);
		createPanel.add(lblNameOfArresting);
		
		JLabel lblDateOfArrest = new JLabel("Date of arrest:");
		lblDateOfArrest.setBounds(52, 296, 120, 15);
		createPanel.add(lblDateOfArrest);
		
		txtDefName = new JTextField();
		txtDefName.setBounds(246, 50, 192, 19);
		createPanel.add(txtDefName);
		txtDefName.setColumns(10);
		
		txtDefAddr = new JTextField();
		txtDefAddr.setColumns(10);
		txtDefAddr.setBounds(246, 85, 192, 19);
		createPanel.add(txtDefAddr);
		
		txtTypeCrime = new JTextField();
		txtTypeCrime.setColumns(10);
		txtTypeCrime.setBounds(246, 127, 192, 19);
		createPanel.add(txtTypeCrime);
		
		txtDateCrime = new JTextField();
		txtDateCrime.setColumns(10);
		txtDateCrime.setBounds(246, 253, 192, 19);
		createPanel.add(txtDateCrime);
		
		txtLocation = new JTextField();
		txtLocation.setColumns(10);
		txtLocation.setBounds(246, 168, 192, 19);
		createPanel.add(txtLocation);
		
		txtArrestingOffcr = new JTextField();
		txtArrestingOffcr.setColumns(10);
		txtArrestingOffcr.setBounds(246, 214, 192, 19);
		createPanel.add(txtArrestingOffcr);
		
		txtDateArrest = new JTextField();
		txtDateArrest.setColumns(10);
		txtDateArrest.setBounds(246, 296, 192, 19);
		createPanel.add(txtDateArrest);
		
		JButton btnProceed = new JButton("Proceed");
		btnProceed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createCase();
				
			}
		});
		btnProceed.setBounds(246, 393, 177, 25);
		createPanel.add(btnProceed);
		
		JLabel lblDateOfCrime = new JLabel("Date of crime:");
		lblDateOfCrime.setBounds(51, 255, 99, 15);
		createPanel.add(lblDateOfCrime);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createPanel.setVisible(false);
				caseManPanel.setVisible(true);
			}
		});
		btnCancel.setBounds(74, 393, 117, 25);
		createPanel.add(btnCancel);
		
		
	}
	
	public JPanel getPanel(){
		return panel;
	}
	private void createCase(){
		
		Date dateCrime, dateArrest;
		try{
			dateCrime = JISS.getDate(txtDateCrime.getText());
			dateArrest = JISS.getDate(txtDateArrest.getText());
		
			if(txtDefName.getText().isEmpty() || txtTypeCrime.getText().isEmpty()){
				JOptionPane.showMessageDialog(panel, "Required fields: defendant name, type of crime");
				return;
			}
			if(dateCrime == null || dateArrest == null){
				JOptionPane.showMessageDialog(panel, "Please enter valid date (dd/mm/yyyy)!");
				return;
			}
			else if(dateCrime.compareTo(dateArrest) > 0){
				JOptionPane.showMessageDialog(panel, "Date of arrest cannot be earlier than date of crime!");
				return;
			}
			int c = generateCIN();
			current = new Case(c, txtDefName.getText(), txtDefAddr.getText(),
				dateCrime, txtTypeCrime.getText(),txtLocation.getText(), txtArrestingOffcr.getText(), dateArrest);
			
			JISS.db.update("insert into cases (CIN, defName, defAddr, dateCrime, type, location, ao, dateArrest, status) values "
					+ "(" + c + ", \"" + txtDefName.getText() + "\", \""
					+ txtDefAddr.getText() + "\", \""
					+ txtDateCrime.getText() + "\", \""
					+ txtTypeCrime.getText() + "\", \""
					+ txtLocation.getText() + "\", \""
					+ txtArrestingOffcr.getText() + "\", \""
					+ txtDateArrest.getText() + "\", 0)"
					);
			
		
			txtDefName.setText("");
			txtDefAddr.setText("");
			txtTypeCrime.setText("");
			txtDateCrime.setText("");
			txtLocation.setText("");
			txtArrestingOffcr.setText("");
			txtDateArrest.setText("");
			
			
		}catch(Exception e){}
		
		current.initPanel(getThis());
		casePanel = current.getPanel();
		current.goToHearing();
		panel.add(casePanel);
		
		
		createPanel.setVisible(false);
		casePanel.setVisible(true);
		
	}
	
	private void reloadTableCases(){
		
		caseManPanel.remove(sp);
		loadTableCases();
		sp = new JScrollPane(tblCases);
		sp.setBounds(12, 42, 476, 336);
		
		caseManPanel.add(sp);
	}

	private void loadTableCases(){ //loads cases table from the database
		
		String[] cols = {"CIN", "Defendant name", "Starting date", "Type", "Status"};
		int count = JISS.db.queryCount("select count(*) from cases");
		
		String[][] rows = new String[count][5];
		int i=0;
		
		ResultSet rs = JISS.db.getrs("select CIN,defName,dateStart,type,status from cases");
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
		}catch(Exception e){}
	}
	
	private void reloadTableQuery(){
		queryPanel.remove(sp2);
		sp2 = new JScrollPane(tblQueryCases);
		sp2.setBounds(12, 174, 476, 251);
		queryPanel.add(sp2);
	}
	
	private int generateCIN(){
		
		int c = JISS.db.queryCount("select count(*) from cases");
		return (c+1);
	}
	
	public void backFromCase(){
		
		if(querying)
			queryPanel.setVisible(true);
			
		else
			caseManPanel.setVisible(true);
		casePanel.setVisible(false);
		panel.remove(casePanel);
		casePanel = null;
		reloadTableCases();
		
		
	}
	
	public Case getCase(int cin){ //returns the Case details in a Case object
		
		ResultSet rs = JISS.db.getrs("select * from cases where CIN = " + cin);
		try{
			rs.next();
			
			Date dc, da, ds, dh,dec;
			
			dc = JISS.getDate(rs.getString(7));
			da = JISS.getDate(rs.getString(8));
			dh = JISS.getDate(rs.getString(9));
			ds = JISS.getDate(rs.getString(10));
			dec = JISS.getDate(rs.getString(11));
			boolean s;
			if(rs.getString(14).equals("1")){
				s = true;
			}else s = false;
			Case case_ = new Case(cin, rs.getString(2), rs.getString(3),dc, rs.getString(4), rs.getString(5), rs.getString(6)
					, da, rs.getString(12), rs.getString(13), dh, ds, dec, s, rs.getString(15));
					
			return case_;
		}
		catch(Exception e){ e.printStackTrace();}
		
		return null;
		
	}
	private CasesRecord getThis(){
		return this;
	}
}