import java.awt.CardLayout;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class Case{
	
	private String defName,
				   defAddr, //defendant name and address
				   type; //type of crime
	private Date dateCrime,
				 dateArrest,
				 dateHearing,
				 dateStart,
				 dateExpctdCmpl;
	private String location,
					arrestingOfficer,
					presidingJudge,
					publicPros,
					judgeSum;
	private int CIN;
	private boolean status; //FALSE for PENDING, TRUE for CLOSED
	

	
	private JPanel panel;
	private JTextField txtDateHearing;
	private JTextField txtReasonAdj;
	private JTextField txtDefName;
	private JTextField txtDefAddr;
	private JTextField txtTypeCrime;
	private JTextField txtLocation;
	private JLabel lblDateCrime;
	private JTextField txtArrestingOffcr;
	private JTextField txtPresJudge;
	private JTextField txtPublicPros;
	private JLabel lblDateStarting;
	private JTextField txtDateComp;
	
	private JTextArea txtSummaryProc, txtJudgeSum;
	private JTable tblHears;
	private JTable tblAdjs;
	
	private JLabel lblCIN_;
	private JLabel lblStatus_, lblDateHearing;
	private JComboBox<String> cmbSlots;
	
	private JPanel hearingAssignPanel;
	private JPanel viewPanel, updatePanel, adjournPanel, closePanel, historyPanel;
	private JLabel lblDateArrest;
	
	private JButton btnAdjourn, btnUpdate;
	private CasesRecord CR;
	private User U;
	private boolean mode, source; //source = true for User, false for CasesRecord
	private JScrollPane sp1, sp2;
	private JTextField txtSummaryView;
	
	public Case(int c, String dn, String da, Date dc, String t, String l, String ao, Date dar){
		CIN = c;
		defName = dn;
		defAddr = da;
		type = t;
		dateCrime = dc;
		location = l;
		arrestingOfficer = ao;
		dateArrest = dar;
		
		status = false;
		
	}
	
	public Case(int c, String dn, String da, Date dc, String t, String l, String ao, Date dar, String pj, String pp,
			Date dh, Date ds, Date dec, boolean s, String j){
		CIN = c;
		defName = dn;
		defAddr = da;
		type = t;
		dateCrime = dc;
		location = l;
		arrestingOfficer = ao;
		dateArrest = dar;
		presidingJudge = pj;
		publicPros = pp;
		dateHearing = dh;
		dateStart = ds;
		dateExpctdCmpl = dec;
		status = s;
		judgeSum = j;
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	
	public void initPanel(User u, boolean mode){
		
		U =u;
		source = true;
		initPanel(mode);
	}
	
	public void initPanel(CasesRecord cr){
		CR = cr;
		source = false;
		initPanel(true);
	}
	
	public void initPanel(boolean mode_){ //mode = true for registrar privileges
		
		mode = mode_;
		panel = new JPanel();
		panel.setSize(500, 500);
		panel.setLayout(new CardLayout(0, 0));
		
		hearingAssignPanel = new JPanel();
		panel.add(hearingAssignPanel, "name_3694238466770");
		hearingAssignPanel.setLayout(null);
		
		JLabel lblDateofHearing = new JLabel("Date of hearing:");
		lblDateofHearing.setBounds(75, 98, 134, 15);
		hearingAssignPanel.add(lblDateofHearing);
		
		JLabel lblChooseSlot = new JLabel("Choose slot:");
		lblChooseSlot.setBounds(75, 223, 121, 15);
		hearingAssignPanel.add(lblChooseSlot);
		
		txtDateHearing = new JTextField();
		txtDateHearing.setBounds(227, 96, 114, 19);
		hearingAssignPanel.add(txtDateHearing);
		txtDateHearing.setColumns(10);
		
		JButton btnGetVacantSlots = new JButton("Get vacant slots");
		btnGetVacantSlots.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date dh = JISS.getDate(txtDateHearing.getText());
				if(dh == null){
					JOptionPane.showMessageDialog(panel, "Please enter valid date (dd/mm/yyyy)");
					return;
				}
				
				if(dateHearing == null){
					if(dh.compareTo(dateArrest) < 0){
						JOptionPane.showMessageDialog(panel, "Date of hearing cannot be earlier than date of arrest!");
						return;
					}
				}else{
					if(dh.compareTo(dateHearing) <= 0){
						JOptionPane.showMessageDialog(panel, "Date of hearing cannot be earlier than/equal to date of previous hearing!");
						return;
					}
				}
				int[] slots = new int[3];
				for(int i=0; i<3; i++)
					slots[i] = 1;
				ResultSet rs = JISS.db.getrs("select slot from hearings where scheduled_date = \"" + txtDateHearing.getText() + "\"");
				try{
					while(rs.next()){
						if(rs.getString(1).equals("A")){
							slots[0] = 0;
						}else if(rs.getString(1).equals("B")){
							slots[1] = 0;
						}else{
							slots[2] = 0;
						}
					}
					
					if(slots[0] == 1)
						cmbSlots.addItem("A");
					if(slots[1] == 1)
						cmbSlots.addItem("B");
					if(slots[2] == 1)
						cmbSlots.addItem("C");
				}catch(Exception ex){}
			}
		});
		btnGetVacantSlots.setBounds(75, 161, 164, 25);
		hearingAssignPanel.add(btnGetVacantSlots);
		
		cmbSlots = new JComboBox<String>();
		cmbSlots.setBounds(214, 218, 121, 25);
		hearingAssignPanel.add(cmbSlots);
		
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sl = (String)cmbSlots.getSelectedItem();
				if(sl == null){
					JOptionPane.showMessageDialog(panel, "No slot selected!");
					return;
				}
				JISS.db.update("insert into hearings values (" + CIN + ", \"" + txtDateHearing.getText() + "\", \"" + sl + "\", \"-\")");
				
				JISS.db.update("update cases set dateHearing=\"" + txtDateHearing.getText() + "\" where CIN = " + CIN);
				JISS.db.update("update cases set dateStart=dateHearing where dateStart is null and CIN=" + CIN);
				
				dateHearing = JISS.getDate(txtDateHearing.getText());
				if(dateStart == null)
					dateStart = dateHearing;
				txtDateHearing.setText("");
				cmbSlots.removeAllItems();
				hearingAssignPanel.setVisible(false);
				viewPanel.setVisible(true);
				loadViewPanel();
			}
		});
		btnOk.setBounds(214, 328, 117, 25);
		hearingAssignPanel.add(btnOk);
		
		JLabel lblCin = new JLabel("CIN:");
		lblCin.setBounds(75, 52, 70, 15);
		hearingAssignPanel.add(lblCin);
		
		JLabel lblCin_ = new JLabel("" + CIN);
		lblCin_.setBounds(227, 52, 134, 15);
		hearingAssignPanel.add(lblCin_);
		
		adjournPanel = new JPanel();
		panel.add(adjournPanel, "name_4142862498009");
		adjournPanel.setLayout(null);
		
		JLabel lblReasonForAdjournment = new JLabel("Reason for adjournment:");
		lblReasonForAdjournment.setBounds(54, 103, 192, 15);
		adjournPanel.add(lblReasonForAdjournment);
		
		txtReasonAdj = new JTextField();
		txtReasonAdj.setBounds(246, 101, 192, 19);
		adjournPanel.add(txtReasonAdj);
		txtReasonAdj.setColumns(10);
		
		JButton btnAssignNewDate = new JButton("Assign new date of hearing");
		btnAssignNewDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ResultSet rs = JISS.db.getrs("select slot from hearings where CIN = " + CIN + " and scheduled_date = \"" + JISS.DtoS(dateHearing) + "\"");
				String sl = "";
				try{
					rs.next();
					sl = rs.getString(1);
				}catch(Exception ex){
				}
				JISS.db.update("delete from hearings where CIN = " + CIN + " and scheduled_date = \"" + JISS.DtoS(dateHearing) + "\"");
				JISS.db.update("insert into adjs (CIN, scheduled_date, slot, reason) values (" + CIN + ", \"" + JISS.DtoS(dateHearing) + "\", \"" + sl + "\", \"" +
				txtReasonAdj.getText() + "\")");
				
				adjournPanel.setVisible(false);
				hearingAssignPanel.setVisible(true);
				txtReasonAdj.setText("");
			}
		});
		btnAssignNewDate.setBounds(195, 244, 243, 25);
		adjournPanel.add(btnAssignNewDate);
		
		JButton btnCancel_2 = new JButton("Cancel");
		btnCancel_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adjournPanel.setVisible(false);
				viewPanel.setVisible(true);
				txtReasonAdj.setText("");
				
			}
		});
		btnCancel_2.setBounds(321, 289, 117, 25);
		adjournPanel.add(btnCancel_2);
		
		updatePanel = new JPanel();
		panel.add(updatePanel, "name_231762007164");
		updatePanel.setLayout(null);
		
		JLabel lblSummaryOfProceedings = new JLabel("Summary of proceedings:");
		lblSummaryOfProceedings.setBounds(39, 100, 186, 15);
		updatePanel.add(lblSummaryOfProceedings);
		
		txtSummaryProc = new JTextArea();
		txtSummaryProc.setBounds(243, 100, 205, 99);
		updatePanel.add(txtSummaryProc);
		
		JButton btnAssignDateOf = new JButton("Assign date of next hearing");
		btnAssignDateOf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JISS.db.update("update hearings set summary=\"" + txtSummaryProc.getText() + "\" where CIN = "+ CIN + " and scheduled_date = \"" 
			+ JISS.DtoS(dateHearing) + "\"");
				hearingAssignPanel.setVisible(true);
				updatePanel.setVisible(false);
				txtSummaryProc.setText("");
			}
		});
		btnAssignDateOf.setBounds(200, 314, 248, 25);
		updatePanel.add(btnAssignDateOf);
		
		JButton btnCloseCase = new JButton("Close case");
		btnCloseCase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closePanel.setVisible(true);
				updatePanel.setVisible(false);
				
			}
		});
		btnCloseCase.setBounds(39, 314, 147, 25);
		updatePanel.add(btnCloseCase);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePanel.setVisible(false);
				viewPanel.setVisible(true);
				txtSummaryProc.setText("");
			}
		});
		btnCancel.setBounds(331, 352, 117, 25);
		updatePanel.add(btnCancel);
		
		closePanel = new JPanel();
		panel.add(closePanel, "name_486218151453");
		closePanel.setLayout(null);
		
		JLabel lblJudgementSummary = new JLabel("Judgement summary:");
		lblJudgementSummary.setBounds(53, 112, 167, 15);
		closePanel.add(lblJudgementSummary);
		
		txtJudgeSum = new JTextArea();
		txtJudgeSum.setBounds(250, 112, 200, 104);
		closePanel.add(txtJudgeSum);
		
		JButton btnOk_1 = new JButton("OK");
		btnOk_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JISS.db.update("update hearings set summary=\"" + txtSummaryProc.getText() + "\" where CIN = "+ CIN + " and scheduled_date = \"" 
						+ JISS.DtoS(dateHearing) + "\"");
				
				
				JISS.db.update("update cases set status=1, jsum=\"" + txtJudgeSum.getText() + "\" where CIN ="+ CIN);
				closePanel.setVisible(false);
				viewPanel.setVisible(true);
				
				status = true;
				judgeSum = txtJudgeSum.getText();
				loadViewPanel();
				txtSummaryProc.setText("");
				txtJudgeSum.setText("");
			}
		});
		btnOk_1.setBounds(319, 298, 117, 25);
		closePanel.add(btnOk_1);
		
		JButton btnCancel_1 = new JButton("Cancel");
		btnCancel_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closePanel.setVisible(false);
				updatePanel.setVisible(true);
				txtJudgeSum.setText("");
			}
		});
		btnCancel_1.setBounds(180, 298, 117, 25);
		closePanel.add(btnCancel_1);
		
		viewPanel = new JPanel();
		panel.add(viewPanel, "name_1078370647751");
		viewPanel.setLayout(null);
		
		JButton btnSave = new JButton("Save & Back");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(txtDefName.getText().isEmpty() || txtTypeCrime.getText().isEmpty()){
					JOptionPane.showMessageDialog(panel, "Required fields: defendant name, type of crime");
					return;
				}
				
				Date dc = JISS.getDate(txtDateComp.getText());
				if(!txtDateComp.getText().equals("") && dc == null && !status){
					JOptionPane.showMessageDialog(panel, "Please enter valid date of completion(dd/mm/yyyy)!");
					return;
				}
				if(dc != null && dc.compareTo(dateHearing) < 0){
					JOptionPane.showMessageDialog(panel, "Expected date of completion cannot be earlier than date of next hearing!");
					return;
				}
				
				
				if(!status && mode)
					JISS.db.update("update cases set "+
					"defName=\"" + txtDefName.getText() + "\", " +
					"defAddr=\"" + txtDefAddr.getText() + "\", " +
					"type=\"" + txtTypeCrime.getText() + "\", "+
				//	"dateCrime=\"" + txtDateCrime.getText() + "\", "+
					"location=\"" + txtLocation.getText() + "\", "+
				//	"dateArrest=\"" + txtDateArrest.getText() + "\", "+
					"dateComp=\"" + txtDateComp.getText() + "\", "+
					"ao=\"" + txtArrestingOffcr.getText() + "\", "+
					"pj=\"" + txtPresJudge.getText() + "\", " +
					"pp=\"" + txtPublicPros.getText() + "\" where CIN =" + CIN	);
				if(source)
					U.backFromCase();
				else
					CR.backFromCase();
			}
			
		});
		btnSave.setBounds(66, 417, 198, 25);
		viewPanel.add(btnSave);
		
		btnUpdate = new JButton("Update hearing details");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePanel.setVisible(true);
				viewPanel.setVisible(false);
			}
		});
		btnUpdate.setBounds(66, 448, 198, 25);
		viewPanel.add(btnUpdate);
		
		btnAdjourn = new JButton("Adjourn hearing");
		btnAdjourn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adjournPanel.setVisible(true);
				viewPanel.setVisible(false);
			}
		});
		btnAdjourn.setBounds(269, 448, 193, 25);
		viewPanel.add(btnAdjourn);
		
		JLabel lblDefendantName = new JLabel("Defendant name:");
		lblDefendantName.setBounds(66, 26, 123, 15);
		viewPanel.add(lblDefendantName);
		
		txtDefName = new JTextField();
		txtDefName.setBounds(255, 24, 174, 19);
		viewPanel.add(txtDefName);
		txtDefName.setColumns(10);
		
		JLabel lblDefendantAddress = new JLabel("Defendant address:");
		lblDefendantAddress.setBounds(66, 59, 174, 15);
		viewPanel.add(lblDefendantAddress);
		
		JLabel lblTypeOfCrime = new JLabel("Type of crime:");
		lblTypeOfCrime.setBounds(66, 96, 174, 15);
		viewPanel.add(lblTypeOfCrime);
		
		JLabel lblLocationOfCrime = new JLabel("Location of crime:");
		lblLocationOfCrime.setBounds(66, 123, 174, 15);
		viewPanel.add(lblLocationOfCrime);
		
		JLabel lblDateOfCrime = new JLabel("Date of crime:");
		lblDateOfCrime.setBounds(66, 150, 174, 15);
		viewPanel.add(lblDateOfCrime);
		
		JLabel lblNameOfArresting = new JLabel("Name of arresting officer:");
		lblNameOfArresting.setBounds(66, 204, 191, 15);
		viewPanel.add(lblNameOfArresting);
		
		JLabel lblNameOfPresiding = new JLabel("Name of presiding judge:");
		lblNameOfPresiding.setBounds(66, 228, 183, 15);
		viewPanel.add(lblNameOfPresiding);
		
		JLabel label_3 = new JLabel("Name of public prosecutor:");
		label_3.setBounds(66, 255, 191, 15);
		viewPanel.add(label_3);
		
		JLabel label = new JLabel("Status:");
		label.setBounds(66, 390, 174, 15);
		viewPanel.add(label);
		
		JLabel lblDateOfStarting = new JLabel("Date of starting:");
		lblDateOfStarting.setBounds(66, 282, 174, 15);
		viewPanel.add(lblDateOfStarting);
		
		JLabel lblDateOfNext = new JLabel("Date of next hearing:");
		lblDateOfNext.setBounds(66, 336, 157, 15);
		viewPanel.add(lblDateOfNext);
		
		JLabel lblExpectedDateOf = new JLabel("Expctd date of completion:");
		lblExpectedDateOf.setBounds(66, 309, 191, 15);
		viewPanel.add(lblExpectedDateOf);
		
		txtDefAddr = new JTextField();
		txtDefAddr.setBounds(255, 57, 174, 19);
		viewPanel.add(txtDefAddr);
		txtDefAddr.setColumns(10);
		
		txtTypeCrime = new JTextField();
		txtTypeCrime.setBounds(255, 94, 174, 19);
		viewPanel.add(txtTypeCrime);
		txtTypeCrime.setColumns(10);
		
		txtLocation = new JTextField();
		txtLocation.setColumns(10);
		txtLocation.setBounds(255, 121, 174, 19);
		viewPanel.add(txtLocation);
		
		lblDateCrime = new JLabel();
		lblDateCrime.setBounds(255, 148, 114, 19);
		viewPanel.add(lblDateCrime);
		
		txtArrestingOffcr = new JTextField();
		txtArrestingOffcr.setColumns(10);
		txtArrestingOffcr.setBounds(255, 202, 174, 19);
		viewPanel.add(txtArrestingOffcr);
		
		txtPresJudge = new JTextField();
		txtPresJudge.setColumns(10);
		txtPresJudge.setBounds(255, 226, 174, 19);
		viewPanel.add(txtPresJudge);
		
		txtPublicPros = new JTextField();
		txtPublicPros.setColumns(10);
		txtPublicPros.setBounds(255, 253, 174, 19);
		viewPanel.add(txtPublicPros);
		
		lblDateStarting = new JLabel("");
		lblDateStarting.setBounds(255, 280, 114, 19);
		viewPanel.add(lblDateStarting);
		
		txtDateComp = new JTextField();
		txtDateComp.setColumns(10);
		txtDateComp.setBounds(255, 307, 114, 19);
		viewPanel.add(txtDateComp);
		
		lblDateHearing = new JLabel("New label");
		lblDateHearing.setBounds(255, 336, 114, 15);
		viewPanel.add(lblDateHearing);
		
		lblStatus_ = new JLabel("New label");
		lblStatus_.setBounds(255, 390, 114, 15);
		viewPanel.add(lblStatus_);
		
		JLabel lblCin_1 = new JLabel("CIN:");
		lblCin_1.setBounds(64, -1, 70, 15);
		viewPanel.add(lblCin_1);
		
		lblCIN_ = new JLabel("New label");
		lblCIN_.setBounds(255, 0, 174, 15);
		viewPanel.add(lblCIN_);
		
		JButton btnCaseHistory = new JButton("Case history");
		btnCaseHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				historyPanel.setVisible(true);
				reloadHistoryPanel();
				viewPanel.setVisible(false);
				
			}
		});
		btnCaseHistory.setBounds(269, 417, 191, 25);
		viewPanel.add(btnCaseHistory);
		
		JLabel lblDateOfArrest = new JLabel("Date of arrest:");
		lblDateOfArrest.setBounds(66, 177, 132, 15);
		viewPanel.add(lblDateOfArrest);
		
		lblDateArrest = new JLabel();
		lblDateArrest.setBounds(255, 171, 114, 19);
		viewPanel.add(lblDateArrest);
		
		JLabel lblJudgementSummary_1 = new JLabel("Judgement summary:");
		lblJudgementSummary_1.setBounds(64, 363, 166, 15);
		viewPanel.add(lblJudgementSummary_1);
		
		txtSummaryView = new JTextField();
		txtSummaryView.setEditable(false);
		txtSummaryView.setColumns(10);
		txtSummaryView.setBounds(255, 363, 174, 19);
		viewPanel.add(txtSummaryView);
		
		historyPanel = new JPanel();
		panel.add(historyPanel, "name_3221067023128");
		historyPanel.setLayout(null);
		
		JLabel lblHearingHistory = new JLabel("Hearing history:");
		lblHearingHistory.setBounds(55, 37, 126, 15);
		historyPanel.add(lblHearingHistory);
		
		JLabel lblAdjournmentHistory = new JLabel("Adjournment history:");
		lblAdjournmentHistory.setBounds(55, 246, 150, 15);
		historyPanel.add(lblAdjournmentHistory);
		
		
		//tblHears = new JTable();
		loadHistoryPanel();
		sp1 = new JScrollPane(tblHears);
		sp1.setBounds(29, 64, 438, 179);
		historyPanel.add(sp1);
		
		sp2 = new JScrollPane(tblAdjs);
		
		sp2.setBounds(29, 273, 438, 178);
		historyPanel.add(sp2);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				historyPanel.setVisible(false);
				viewPanel.setVisible(true);
			}
		});
		btnBack.setBounds(55, 463, 117, 25);
		historyPanel.add(btnBack);
		
		loadViewPanel();
		loadHistoryPanel();
	}
	
	public JPanel getPanel(){
		
		return panel;
	}
	
	public void goToHearing(){
		hearingAssignPanel.setVisible(true);
		viewPanel.setVisible(false);
	}
	public void goToView(){
		hearingAssignPanel.setVisible(false);
		viewPanel.setVisible(true);
	}
	
//	public JPanel getHearingPanel(){
//		return hearingAssignPanel;
//	}
	
	public void loadViewPanel(){
			
		try{
			
			
			btnAdjourn.setEnabled(!status && mode);
			btnUpdate.setEnabled(!status && mode);
			
			lblCIN_.setText("" + CIN);
			
			txtDefName.setText(defName);
			txtDefAddr.setText(defAddr);
			txtTypeCrime.setText(type);
			txtLocation.setText(location);
			txtArrestingOffcr.setText(arrestingOfficer);
			lblDateCrime.setText(JISS.DtoS(dateCrime));
			lblDateArrest.setText(JISS.DtoS(dateArrest));
			lblDateStarting.setText(JISS.DtoS(dateStart));
			txtDateComp.setText(JISS.DtoS(dateExpctdCmpl));
			txtDateComp.setEditable(!status && mode);
			txtDefName.setEditable(!status && mode);
			txtDefAddr.setEditable(!status && mode);
			txtTypeCrime.setEditable(!status && mode);
			txtLocation.setEditable(!status && mode);
		//	txtDateCrime.setEditable(!status && mode);
		//	txtDateArrest.setEditable(!status && mode);
			txtArrestingOffcr.setEditable(!status && mode);
			txtPresJudge.setEditable(!status && mode);
			txtPublicPros.setEditable(!status && mode);
			
			if(status){
				lblDateHearing.setText("NA");
				txtDateComp.setText("Already closed");
				
				
			}
			else{
				lblDateHearing.setText(JISS.DtoS(dateHearing));
				txtDateComp.setText(JISS.DtoS(dateExpctdCmpl));
			}
			txtPresJudge.setText(presidingJudge);
			txtPublicPros.setText(publicPros);
			
			
			String stat;
			if(!status) stat = "Pending";
			else stat = "Closed";
			lblStatus_.setText(stat);
			txtSummaryView.setText(judgeSum);
		
		}catch(Exception e){}
	}
	
	private void reloadHistoryPanel(){
		historyPanel.remove(sp1);
		historyPanel.remove(sp2);
		loadHistoryPanel();
		sp1 = new JScrollPane(tblHears);
		sp1.setBounds(29, 64, 438, 179);
		historyPanel.add(sp1);
		
		sp2 = new JScrollPane(tblAdjs);

		sp2.setBounds(29, 273, 438, 178);
		historyPanel.add(sp2);
	}

	
	private void loadHistoryPanel(){
		String[] cols = {"Scheduled date", "Slot", "Reason for adjournment"};
		int count = JISS.db.queryCount("select count(*) from adjs where CIN = "+ CIN);
		
		String[][] rows = new String[count][3];
		int i=0;
		
		ResultSet rs = JISS.db.getrs("select scheduled_date,slot,reason from adjs where CIN = "+ CIN);
		try{
			while(rs.next()){
				for(int j=0; j<3; j++){
					rows[i][j] = rs.getString(j+1);
				}
				i++;
			}
		
		tblAdjs = new JTable(rows, cols);
		
		}catch(Exception e){}
		
		String[] cols2 = {"Scheduled date", "Slot", "Proc. summary"};
		count = JISS.db.queryCount("select count(*) from hearings where CIN = "+ CIN);
		
		rows = new String[count][3];
		i=0;
		
		rs = JISS.db.getrs("select scheduled_date,slot,summary from hearings where CIN = "+ CIN);
		try{
			while(rs.next()){
				for(int j=0; j<3; j++){
					rows[i][j] = rs.getString(j+1);
				}
				i++;
			}
		
		tblHears = new JTable(rows, cols2);
		
		}catch(Exception e){}
		
	}
	
}