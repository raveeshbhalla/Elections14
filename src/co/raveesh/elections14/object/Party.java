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
		//TOTAL = 2*(LEADING + WON)/3;

		TOTAL = LEADING+WON;
	}
	
	public boolean isSameParty(Party party){
		return this.NAME.equalsIgnoreCase(party.NAME);
	}
	
	public void addParty(Party party){
		if (party.NAME.equalsIgnoreCase("Total")){
			return;
		}
		this.LEADING += party.LEADING;
		this.WON += party.WON;
		/*
		 * TODO: remove 2/3
		 */
		//TOTAL = (2/3)*(LEADING + WON);
		TOTAL = LEADING+WON;
	}
	
	@Override
	public String toString(){
		return new Formatter().format("%s %d %d %f", NAME,LEADING,WON,TOTAL).toString();
	}
}
