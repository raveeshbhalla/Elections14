package co.raveesh.elections14;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import co.raveesh.elections14.object.Party;
import co.raveesh.elections14.object.Preferences;
import co.raveesh.elections14.object.State;
import co.raveesh.elections14.object.SyncListener;

import android.content.Context;
import android.os.AsyncTask;

public class SyncDataTask extends AsyncTask<Object, Integer, Object> {

	private String TAG = "GetNodeTask";
	private Context mContext;
	private SyncListener mSyncListener;

	public SyncDataTask(Context context) {
		mContext = context;
	}

	public SyncDataTask(Context context, SyncListener listener) {
		mContext = context;
		mSyncListener = listener;
	}

	protected void onPreExecute() {
		if (Constants.MAIN_ACTIVITY_LISTENER != null)
			Constants.MAIN_ACTIVITY_LISTENER.onSyncStarted();

		if (Constants.SERVICE_LISTENER != null)
			Constants.SERVICE_LISTENER.onSyncStarted();
	}

	@Override
	protected Object doInBackground(Object... params) {
		try {
			storeResultsInDatabase(Constants
					.getURLNode(Constants.RESULTS_PARTYWISE));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void storeResultsInDatabase(TagNode node) {
		try {
			Party nda = new Party(Constants.NDA, 0, 0);
			Party upa = new Party(Constants.UPA, 0, 0);
			Party aap = new Party(Constants.AAP, 0, 0);
			Party others = new Party(Constants.OTHERS, 0, 0);

			Object[] info_nodes = node.evaluateXPath("//div[@id='div1']");
			if (info_nodes.length > 0) {
				TagNode firstNode = (TagNode) info_nodes[0];
				Object[] states = firstNode.evaluateXPath("/table");

				Constants.Log(TAG, "States:" + states.length);

				/*
				 * Iterating through results of each state. 1 TagNode additional
				 * was being returned initially for the scraping done above,
				 * hence the -1
				 */
				for (int i = 0; i < states.length - 1; i++) {
					State state = new State((TagNode) states[i]);
					Constants.Log(TAG, state.toString());

					ArrayList<Party> stateParties = state.getParties();
					/*
					 * Scanning data on state parties, storing them to the list
					 * which will later be stored in database
					 */
					for (int j = 0; j < stateParties.size(); j++) {
						boolean found = false;

						/*
						 * Checking if state party has previously been stored in
						 * ArrayList If it has, it's data for leading, won and
						 * total are updated appropriately
						 */
						List<String> list = Arrays.asList(NDA);
						int index = list.indexOf(stateParties.get(j).NAME);
						if (index != -1) {
							nda.addParty(stateParties.get(j));
							continue;
						}

						list = Arrays.asList(UPA);
						index = list.indexOf(stateParties.get(j).NAME);
						if (index != -1) {
							upa.addParty(stateParties.get(j));
							continue;
						}

						if (stateParties.get(j).NAME
								.equalsIgnoreCase("Aam Aadmi Party")) {
							aap.addParty(stateParties.get(j));
							continue;
						}

						else {
							others.addParty(stateParties.get(j));
						}
					}
				}

				/*
				 * Storing results in database
				 */
				Constants.Log(TAG, nda.toString());
				Constants.Log(TAG, upa.toString());
				Constants.Log(TAG, aap.toString());
				Constants.Log(TAG, others.toString());

				Preferences prefs = new Preferences(mContext);
				prefs.storeParty(nda);
				prefs.storeParty(aap);
				prefs.storeParty(upa);
				prefs.storeParty(others);
			}
		} catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onPostExecute(Object object) {
		if (Constants.MAIN_ACTIVITY_LISTENER != null)
			Constants.MAIN_ACTIVITY_LISTENER.onSyncComplete();

		if (Constants.SERVICE_LISTENER != null)
			Constants.SERVICE_LISTENER.onSyncComplete();

		if (mSyncListener != null) {
			mSyncListener.onSyncComplete();
		}
	}

	String[] NDA = { "Bharatiya Janata Party",
			"Desiya Murpokku Dravida Kazhagam", "Pattali Makkal Katchi",
			"Marumalarchi Dravida Munnetra Kazhagam",
			"Kongunadu Makkal Desia Katchi", "Indhiya Jananayaga Katchi",
			"New Justice Party", "Telugu Desam Party", "Jana Sena Party",
			"Shiv Sena", "Swabhimani Paksha",
			"Republican Party of India (Athvale)", "Rashtriya Samaj Paksha",
			"Shiromani Akali Dal", "Lok Janshakti Party",
			"Rashtriya Lok Samata Party", "Haryana Janhit Congress",
			"Apna Dal", "Kerala Congress (Nationalist)",
			"Revolutionary Socialist Party (Bolshevik)",
			"All India N.R. Congress", "National People's Party (India)",
			"Naga People's Front", "United Democratic Front",
			"Manipur Peoples Party", "North-East Regional Political Front",
			"Gorkha Janmukti Morcha", "Maharashtrawadi Gomantak Party",
			"Goa Vikas Party" };

	String[] UPA = { "Indian National Congress", "Nationalist Congress Party",
			"Rashtriya Lok Dal", "Rashtriya Janata Dal",
			"Jammu & Kashmir National Conference",
			"Indian Union Muslim League", "Kerala Congress (Mani)",
			"Mahan Dal", "Jharkhand Mukti Morcha",
			"Socialist Janata (Democratic)",
			"Revolutionary Socialist Party (Rebel)*", "Peace Party of India" };
}
