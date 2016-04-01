import java.awt.CardLayout;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JTable;

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
					publicPros;
	private int CIN;
	private boolean status; //true for SCHEDULED, false for CLOSED
	
	//private database  adjs, hearings;
	
	private JPanel panel;
	private JTextField txtDateHearing;
	private JTextField txtReasonAdj;
	private JTextField txtDefName;
	private JTextField txtDefAddr;
	private JTextField txtTypeCrime;
	private JTextField txtLocation;
	private JTextField txtDateCrime;
	private JTextField txtArrestingOffcr;
	private JTextField txtPresJudge;
	private JTextField txtPublicPros;
	private JTextField txtDateStarting;
	private JTextField txtDateComp;
	private JTable tblHears;
	private JTable tblAdjs;
	
	private JPanel hearingAssignPanel;
	/**
	 * @wbp.parser.entryPoint
	 */
	
	public Case(int c, String dn, String da, Date dc, String t, String l, String na, Date dar){
		CIN = c;
		defName = dn;
		defAddr = da;
		type = t;
		dateCrime = dc;
		location = l;
		arrestingOfficer = na;
		dateArrest = dar;
		
		
	}
	public void initPanel(){
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
		btnGetVacantSlots.setBounds(75, 161, 164, 25);
		hearingAssignPanel.add(btnGetVacantSlots);
		
		JComboBox<String> cmbSlots = new JComboBox<String>();
		cmbSlots.setBounds(214, 218, 121, 25);
		hearingAssignPanel.add(cmbSlots);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(214, 328, 117, 25);
		hearingAssignPanel.add(btnOk);
		
		JLabel lblCin = new JLabel("CIN:");
		lblCin.setBounds(75, 52, 70, 15);
		hearingAssignPanel.add(lblCin);
		
		JLabel lblCin_ = new JLabel("New label");
		lblCin_.setBounds(227, 52, 134, 15);
		hearingAssignPanel.add(lblCin_);
		
		JPanel adjournPanel = new JPanel();
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
		btnAssignNewDate.setBounds(195, 244, 243, 25);
		adjournPanel.add(btnAssignNewDate);
		
		JPanel updatePanel = new JPanel();
		panel.add(updatePanel, "name_231762007164");
		updatePanel.setLayout(null);
		
		JLabel lblSummaryOfProceedings = new JLabel("Summary of proceedings:");
		lblSummaryOfProceedings.setBounds(39, 100, 186, 15);
		updatePanel.add(lblSummaryOfProceedings);
		
		JTextArea txtSummaryProc = new JTextArea();
		txtSummaryProc.setBounds(243, 100, 205, 99);
		updatePanel.add(txtSummaryProc);
		
		JButton btnAssignDateOf = new JButton("Assign date of next hearing");
		btnAssignDateOf.setBounds(200, 314, 248, 25);
		updatePanel.add(btnAssignDateOf);
		
		JButton btnCloseCase = new JButton("Close case");
		btnCloseCase.setBounds(39, 314, 147, 25);
		updatePanel.add(btnCloseCase);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(331, 352, 117, 25);
		updatePanel.add(btnCancel);
		
		JPanel closePanel = new JPanel();
		panel.add(closePanel, "name_486218151453");
		closePanel.setLayout(null);
		
		JLabel lblJudgementSummary = new JLabel("Judgement summary:");
		lblJudgementSummary.setBounds(53, 112, 167, 15);
		closePanel.add(lblJudgementSummary);
		
		JTextArea txtJudgeSum = new JTextArea();
		txtJudgeSum.setBounds(250, 112, 200, 104);
		closePanel.add(txtJudgeSum);
		
		JButton btnOk_1 = new JButton("OK");
		btnOk_1.setBounds(319, 298, 117, 25);
		closePanel.add(btnOk_1);
		
		JButton btnCancel_1 = new JButton("Cancel");
		btnCancel_1.setBounds(180, 298, 117, 25);
		closePanel.add(btnCancel_1);
		
		JPanel viewPanel = new JPanel();
		panel.add(viewPanel, "name_1078370647751");
		viewPanel.setLayout(null);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(66, 448, 68, 25);
		viewPanel.add(btnSave);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(269, 448, 86, 25);
		viewPanel.add(btnUpdate);
		
		JButton btnAdjourn = new JButton("Adjourn");
		btnAdjourn.setBounds(367, 448, 89, 25);
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
		lblNameOfArresting.setBounds(66, 189, 191, 15);
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
		
		txtDateCrime = new JTextField();
		txtDateCrime.setColumns(10);
		txtDateCrime.setBounds(255, 148, 114, 19);
		viewPanel.add(txtDateCrime);
		
		txtArrestingOffcr = new JTextField();
		txtArrestingOffcr.setColumns(10);
		txtArrestingOffcr.setBounds(255, 187, 174, 19);
		viewPanel.add(txtArrestingOffcr);
		
		txtPresJudge = new JTextField();
		txtPresJudge.setColumns(10);
		txtPresJudge.setBounds(255, 226, 174, 19);
		viewPanel.add(txtPresJudge);
		
		txtPublicPros = new JTextField();
		txtPublicPros.setColumns(10);
		txtPublicPros.setBounds(255, 253, 174, 19);
		viewPanel.add(txtPublicPros);
		
		txtDateStarting = new JTextField();
		txtDateStarting.setColumns(10);
		txtDateStarting.setBounds(255, 280, 114, 19);
		viewPanel.add(txtDateStarting);
		
		txtDateComp = new JTextField();
		txtDateComp.setColumns(10);
		txtDateComp.setBounds(255, 307, 114, 19);
		viewPanel.add(txtDateComp);
		
		JLabel lblDateHearing = new JLabel("New label");
		lblDateHearing.setBounds(255, 336, 114, 15);
		viewPanel.add(lblDateHearing);
		
		JLabel lblStatus_ = new JLabel("New label");
		lblStatus_.setBounds(255, 390, 114, 15);
		viewPanel.add(lblStatus_);
		
		JLabel lblCin_1 = new JLabel("CIN:");
		lblCin_1.setBounds(64, -1, 70, 15);
		viewPanel.add(lblCin_1);
		
		JLabel lblCIN_ = new JLabel("New label");
		lblCIN_.setBounds(255, 0, 174, 15);
		viewPanel.add(lblCIN_);
		
		JButton btnCaseHistory = new JButton("Case history");
		btnCaseHistory.setBounds(269, 417, 191, 25);
		viewPanel.add(btnCaseHistory);
		
		JPanel historyPanel = new JPanel();
		panel.add(historyPanel, "name_3221067023128");
		historyPanel.setLayout(null);
		
		JLabel lblHearingHistory = new JLabel("Hearing history:");
		lblHearingHistory.setBounds(55, 62, 126, 15);
		historyPanel.add(lblHearingHistory);
		
		JLabel lblAdjournmentHistory = new JLabel("Adjournment history:");
		lblAdjournmentHistory.setBounds(55, 274, 150, 15);
		historyPanel.add(lblAdjournmentHistory);
		
		tblHears = new JTable();
		tblHears.setBounds(49, 89, 365, 154);
		historyPanel.add(tblHears);
		
		tblAdjs = new JTable();
		tblAdjs.setBounds(49, 295, 365, 154);
		historyPanel.add(tblAdjs);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(55, 463, 117, 25);
		historyPanel.add(btnBack);
	}
	
	public JPanel getPanel(){
		return panel;
	}
	
	public JPanel getHearingPanel(){
		return hearingAssignPanel;
	}
	public void adjourn(){
		
	}
	
	public void update(){
		
	}
	
	public void close(){
		
	}
}