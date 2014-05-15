package co.raveesh.elections14;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import co.haptik.library.Haptik;
import co.raveesh.elections14.object.Party;
import co.raveesh.elections14.object.Preferences;
import co.raveesh.elections14.object.ResultRow;
import co.raveesh.elections14.object.SyncListener;

public class MainActivity extends Activity {

	private final String TAG = "MainActivity";
	private Party mNDA, mUPA, mAAP, mOthers;
	Preferences mPrefs;
	Context mContext;
	ImageButton mRefresh;

	private OnClickListener refreshClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			syncResults();
		}
	};
	
	private OnClickListener shareClickListener = new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_SEND);
			intent.setType("text/plain");
			String text = "Live Elections 2014 results:\n";
			if (mNDA != null){
				text=text+mNDA.NAME+": "+Math.round(mNDA.TOTAL)+"\n";
			}
			if (mUPA != null){
				text=text+mUPA.NAME+": "+Math.round(mUPA.TOTAL)+"\n";
			}
			if (mAAP != null){
				text=text+mAAP.NAME+": "+Math.round(mAAP.TOTAL)+"\n";
			}
			if (mOthers != null){
				text=text+mOthers.NAME+": "+Math.round(mOthers.TOTAL)+"\n";
			}
			text=text+"via http://goo.gl/p9B4lW";
			intent.putExtra(Intent.EXTRA_TEXT, text);
			mContext.startActivity(Intent.createChooser(intent, "Share with..."));
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPrefs = new Preferences(this);
		mContext = this;

		LinearLayout layout = (LinearLayout) findViewById(R.id.buttonHaptik);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Haptik.launch(mContext);
			}

		});

		mRefresh = (ImageButton) findViewById(R.id.refresh);
		ImageButton share = (ImageButton)findViewById(R.id.share);
		share.setOnClickListener(shareClickListener);
	}

	private void refreshData() {
		Party nda = mPrefs.getParty(Constants.NDA);
		Party upa = mPrefs.getParty(Constants.UPA);
		Party aap = mPrefs.getParty(Constants.AAP);
		Party others = mPrefs.getParty(Constants.OTHERS);
		/*
		 * @TODO: remove this part
		 */
		others.TOTAL = 543-(nda.TOTAL + upa.TOTAL + aap.TOTAL);
		Constants.Log(TAG, nda.toString());
		Constants.Log(TAG, upa.toString());
		Constants.Log(TAG, aap.toString());
		Constants.Log(TAG, others.toString());

		int max = 500;

		ProgressBar pbNDA = (ProgressBar) findViewById(R.id.bjpScale);
		ProgressBar pbUPA = (ProgressBar) findViewById(R.id.upaScale);
		ProgressBar pbAAP = (ProgressBar) findViewById(R.id.aapScale);
		ProgressBar pbOthers = (ProgressBar) findViewById(R.id.othersScale);
		TextView tvNDA = (TextView) findViewById(R.id.countBjp);
		TextView tvUPA = (TextView) findViewById(R.id.countUpa);
		TextView tvAAP = (TextView) findViewById(R.id.countAap);
		TextView tvOthers = (TextView) findViewById(R.id.countOthers);

		pbNDA.setMax(max);
		pbUPA.setMax(max);
		pbAAP.setMax(max);
		pbOthers.setMax(max);

		ResultRow rowNDA = new ResultRow(pbNDA, tvNDA, mNDA, nda);
		ResultRow rowUPA = new ResultRow(pbUPA, tvUPA, mUPA, upa);
		ResultRow rowAAP = new ResultRow(pbAAP, tvAAP, mAAP, aap);
		ResultRow rowOthers = new ResultRow(pbOthers, tvOthers, mOthers, others);

		new ProgressUpdateTask(300, 30).execute(new ResultRow[] { rowNDA,
				rowUPA, rowAAP, rowOthers });

		mNDA = nda;
		mUPA = upa;
		mAAP = aap;
		mOthers = others;

	}

	@Override
	public void onResume() {
		super.onResume();
		mNDA = mPrefs.getParty(Constants.NDA);
		mUPA = mPrefs.getParty(Constants.UPA);
		mAAP = mPrefs.getParty(Constants.AAP);
		mOthers = mPrefs.getParty(Constants.OTHERS);
		refreshData();
		syncResults();
	}

	@Override
	public void onPause() {
		super.onPause();
		Constants.MAIN_ACTIVITY_LISTENER = null;
	}

	private void syncResults() {
		Constants.MAIN_ACTIVITY_LISTENER = mListener;
		new SyncDataTask(this).execute(new Object());
	}

	private void startRefreshAnimation() {
		Animation rotation = AnimationUtils.loadAnimation(mContext,
				R.anim.rotate);
		rotation.setRepeatCount(Animation.INFINITE);
		mRefresh.startAnimation(rotation);
		mRefresh.setOnClickListener(null);
	}

	private void stopRefreshAnimation() {
		mRefresh.clearAnimation();
		mRefresh.setOnClickListener(refreshClickListener);
	}

	private SyncListener mListener = new SyncListener() {
		@Override
		public void onSyncComplete() {
			refreshData();
			stopRefreshAnimation();
		}

		@Override
		public void onSyncStarted() {
			startRefreshAnimation();
		}

		@Override
		public void onSyncFailed() {
			stopRefreshAnimation();
		}
	};

}
