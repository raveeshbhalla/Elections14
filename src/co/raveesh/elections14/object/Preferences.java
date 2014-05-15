package co.raveesh.elections14.object;

import co.raveesh.elections14.Constants;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

	private SharedPreferences prefs;
	private String TAG = "Preferences";
	
	public Preferences(Context context){
		prefs = context.getSharedPreferences("Elections",context.MODE_PRIVATE);
	}
	
	public void storeParty(Party party){
		Constants.Log(TAG, party.toString());
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(party.NAME+"WON", party.WON);
		editor.putInt(party.NAME+"LEADING", party.LEADING);
		editor.commit();
	}
	
	public Party getParty(String name){
		int won = prefs.getInt(name+"WON",0)+Constants.getRandom();
		int leading = prefs.getInt(name+"LEADING",0)+Constants.getRandom();
		Party party = new Party(name,won,leading);
		Constants.Log(TAG, party.toString());
		return party;
	}
}
