import java.util.Date;

public class Case{
	
	private String def_name,
				   def_addr, //defendant name and address
				   type; //type of crime
	private Date date_crime,
				 date_hearing,
				 date_start,
				 date_expctd_cmpl;
	private String location_crime,
					arresting_officer,
					presiding_judge,
					public_pros;
	private int CIN;
	private boolean status; //true for PENDING, false for CLOSED
	
	//private database  adjs, hearings;
	public void adjourn(){
		
	}
	
	public void update(){
		
	}
	
	public void close(){
		
	}
}