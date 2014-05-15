package co.raveesh.elections14.object;

import java.util.Formatter;

public class Party {

	public String NAME;
	public int LEADING;
	public int WON;
	public float TOTAL;
	
	public Party(String name, int won, int leading){
		NAME = name;
		LEADING = leading;
		WON = won;
		/*
		 * @TODO: remove 2/3
		 */
		TOTAL = 2*(LEADING + WON)/3;
	}
	
	public boolean isSameParty(Party party){
		return this.NAME.equalsIgnoreCase(party.NAME);
	}
	
	public void addParty(Party party){
		this.LEADING += party.LEADING;
		this.WON += party.WON;
		/*
		 * TODO: remove 2/3
		 */
		TOTAL = (2/3)*(LEADING + WON);
	}
	
	@Override
	public String toString(){
		return new Formatter().format("Name:%s, Won:%d, Leading:%d, Total:%f",NAME,WON,LEADING,TOTAL).toString();
	}
}
