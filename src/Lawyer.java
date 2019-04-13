/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Debasis
 */
public class Lawyer extends User {
	
	private int no_of_views;
	
	
	public Lawyer(String u, String p){
		super(u, p, 'L');
	}
	public void increaseCount(int views){
		no_of_views = views;
		lblNoOfViews.setText("" + no_of_views);
	}
	public void initPanel(JISS p){
		super.initPanel(p);
		
		//lblNoOfViews.setText("" + no_of_views);
		
	}

}