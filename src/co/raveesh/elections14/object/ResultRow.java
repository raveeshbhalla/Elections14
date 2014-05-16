package co.raveesh.elections14.object;

import android.widget.ProgressBar;
import android.widget.TextView;

public class ResultRow {

	public Party old, neu;
	public TextView text;
	public ProgressBar pb;
	
	public ResultRow(ProgressBar pb, TextView text, Party old, Party neu){
		this.old = old;
		this.neu = neu;
		this.text = text;
		this.pb = pb;
	}
	
	@Override
	public String toString(){
		return old.toString() + ";" + neu.toString();
	}
}
