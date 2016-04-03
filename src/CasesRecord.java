import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
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
	private JTextField txtCIN;
	private JTextField txtDateHearing;
	private JTable tblQueryResult;
	
	private Case current; //the current Case object
	private JPanel casePanel;
	
	private JScrollPane sp;
	
	private User accessor;
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
		sp.setBounds(76, 42, 335, 336);
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
				System.out.println(ch);
				String cin = (String) tblCases.getValueAt(ch, 0);
				int cin_ = Integer.parseInt(cin);
				System.out.println("cin_ = " + cin_);
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
		btnQueryCase.setBounds(205, 447, 117, 25);
		caseManPanel.add(btnQueryCase);
		
		queryPanel = new JPanel();
		panel.add(queryPanel, "name_1725993655147");
		queryPanel.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(34, 40, 398, 110);
		queryPanel.add(tabbedPane);
		
		JPanel pendingPanel = new JPanel();
		tabbedPane.addTab("Pending", null, pendingPanel, null);
		pendingPanel.setLayout(null);
		
		JLabel lblShowingAllPending = new JLabel("Showing all pending cases:");
		lblShowingAllPending.setBounds(25, 31, 249, 15);
		pendingPanel.add(lblShowingAllPending);
		
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
		btnGo.setBounds(264, 12, 117, 25);
		resolvedPanel.add(btnGo);
		
		JPanel dateofHearingPanel = new JPanel();
		tabbedPane.addTab("Date of hearing", null, dateofHearingPanel, null);
		dateofHearingPanel.setLayout(null);
		
		JLabel lblDateOfHearing = new JLabel("Date of hearing:");
		lblDateOfHearing.setBounds(30, 29, 116, 15);
		dateofHearingPanel.add(lblDateOfHearing);
		
		txtCIN = new JTextField();
		txtCIN.setBounds(150, 27, 114, 19);
		dateofHearingPanel.add(txtCIN);
		txtCIN.setColumns(10);
		
		JButton btnGo_1 = new JButton("Go");
		btnGo_1.setBounds(276, 24, 93, 25);
		dateofHearingPanel.add(btnGo_1);
		
		JPanel CINPanel = new JPanel();
		tabbedPane.addTab("CIN", null, CINPanel, null);
		CINPanel.setLayout(null);
		
		JLabel lblCin = new JLabel("CIN:");
		lblCin.setBounds(32, 31, 70, 15);
		CINPanel.add(lblCin);
		
		txtDateHearing = new JTextField();
		txtDateHearing.setBounds(88, 29, 131, 19);
		CINPanel.add(txtDateHearing);
		txtDateHearing.setColumns(10);
		
		JButton btnGo_2 = new JButton("Go");
		btnGo_2.setBounds(234, 26, 99, 25);
		CINPanel.add(btnGo_2);
		
		tblQueryResult = new JTable();
		tblQueryResult.setBounds(37, 174, 395, 251);
		queryPanel.add(tblQueryResult);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				queryPanel.setVisible(false);
				caseManPanel.setVisible(true);
				
			}
		});
		btnBack.setBounds(34, 439, 117, 25);
		queryPanel.add(btnBack);
		
		JButton btnView = new JButton("View");
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
	public void createCase(){
		
		Date dateCrime, dateArrest;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setLenient(false);
		try{
			dateCrime = df.parse(txtDateCrime.getText());
			dateArrest = df.parse(txtDateArrest.getText());
		
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
			
		
			
		}catch(Exception e){}
		
		current.initPanel(getThis());
		casePanel = current.getPanel();
		current.goToHearing();
		panel.add(casePanel);
		
		
		createPanel.setVisible(false);
		casePanel.setVisible(true);
		
	}
	
	public void reloadTableCases(){
		
		caseManPanel.remove(sp);
		loadTableCases();
		sp = new JScrollPane(tblCases);
		sp.setBounds(79, 74, 341, 300);
		
		caseManPanel.add(sp);
	}

	private void loadTableCases(){
		
		String[] cols = {"CIN", "Defendant name", "Starting date", "Type", "Status"};
		int count = JISS.db.queryCount("select count(*) from cases");
		
		String[][] rows = new String[count][5];
		int i=0;
		
		ResultSet rs = JISS.db.getrs("select CIN,defName,dateStart,type,status from cases");
		try{
			while(rs.next()){
				for(int j=0; j<5; j++){
					rows[i][j] = rs.getString(j+1);
				}
				i++;
			}
		
		tblCases = new JTable(rows, cols);
		}catch(Exception e){}
	}
	
	public void query(){
		
	}
	
	public int generateCIN(){
		
		int c = JISS.db.queryCount("select count(*) from cases");
		return (c+1);
	}
	
	public void backFromCase(){
		caseManPanel.setVisible(true);
		casePanel.setVisible(false);
		panel.remove(casePanel);
		casePanel = null;
	}
	
	public Case getCase(int cin){
		
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
					, da, rs.getString(12), rs.getString(13), dh, ds, dec, s);
					
			return case_;
		}
		catch(Exception e){ e.printStackTrace();}
		
		//System.out.println("returning null");
		return null;
		
	}
	private CasesRecord getThis(){
		return this;
	}
}