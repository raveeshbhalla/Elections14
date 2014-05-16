package co.raveesh.elections14;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import android.util.Log;
import co.raveesh.elections14.object.SyncListener;

public class Constants {

	private static boolean debug = true;
	public static String RESULTS_PARTYWISE = "http://eciresults.ap.nic.in/PartyWiseResult.htm";
	public static SyncListener MAIN_ACTIVITY_LISTENER;
	public static SyncListener SERVICE_LISTENER;	
	public static SyncListener WIDGET_LISTENER;
	
	public static String NDA = "BJP+";
	public static String UPA = "Cong";
	public static String AAP = "AAP";
	public static String OTHERS = "Others";
	
	/**
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static TagNode getURLNode(String url) throws IOException{
		HtmlCleaner cleaner = new HtmlCleaner();
        URLConnection conn = (new URL(url)).openConnection();
        return cleaner.clean(new InputStreamReader(conn.getInputStream())); 
	}
	
	/**
	 * @param TAG
	 * @param Message
	 */
	public static void Log(String TAG, String Message){
		Log(TAG,Message,false);
	}
	
	/**
	 * @param TAG
	 * @param Message
	 * @param override
	 */
	public static void Log(String TAG, String Message, boolean override){
		if (debug || override){
			Log.d(TAG,Message);
		}
	}
}
