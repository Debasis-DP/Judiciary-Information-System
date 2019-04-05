
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Debasis
 */
public class Adjournment extends Case{
    /*private int CIN;
    private String schedule;
    private char slot;
    private String reason;
    */
    public Adjournment (int CIN, Date Schedule, char slot,String summary){
        
        super(CIN, Schedule, slot, summary);
    }
    
}
