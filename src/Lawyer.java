import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;

public class Lawyer extends User {
	
	private String l_id; //lawyer ID
	private int no_of_views;
	
	
	public Lawyer(String u, String p){
		super(u, p, 'L');
	}
	
	public void initPanel(JISS p){
		super.initPanel(p);
		
		lblNoOfViews.setText("" + no_of_views);
		
	}
	
	
	
}