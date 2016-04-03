
public class Lawyer extends User {
	
	private int no_of_views;
	
	
	public Lawyer(String u, String p){
		super(u, p, 'L');
	}
	public void increaseCount(){
		no_of_views++;
		lblNoOfViews.setText("" + no_of_views);
	}
	public void initPanel(JISS p){
		super.initPanel(p);
		
		lblNoOfViews.setText("" + no_of_views);
		
	}

}