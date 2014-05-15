package co.raveesh.elections14.object;

import java.util.ArrayList;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import co.raveesh.elections14.Constants;

public class State {
	private final String TAG ="State";
	private ArrayList<Party> mParties;

	public State(TagNode node){
		mParties = new ArrayList<Party>();
		
		try {
			Object[] info_nodes = node.evaluateXPath("//tr");
			Constants.Log(TAG, "Parties:"+(info_nodes.length-3));
			
			for (int i=3;i<info_nodes.length;i++){
				addParty((TagNode)info_nodes[i]);
			}
			
		} catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addParty(TagNode node) throws XPatherException{
		Object[] info_nodes = node.evaluateXPath("/td");
		String name;
		int leading;
		int won;
		name = ((TagNode)info_nodes[0]).getText().toString();
		won = Integer.parseInt(((TagNode)info_nodes[1]).getText().toString());
		leading = Integer.parseInt(((TagNode)info_nodes[2]).getText().toString());
		mParties.add(new Party(name,won,leading));
	}
	
	public ArrayList<Party> getParties(){
		return mParties;
	}
	
	public String toString(){
		ArrayList<String> string = new ArrayList<String>();
		for (int i=0;i<mParties.size();i++){
			string.add(mParties.get(i).toString());
		}
		return string.toString();
	}
}
