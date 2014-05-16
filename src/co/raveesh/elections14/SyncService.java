package co.raveesh.elections14;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;
import co.raveesh.elections14.object.Party;
import co.raveesh.elections14.object.Preferences;

public class SyncService extends Service {

	private String TAG = "SyncService";
	RemoteViews views;
	Preferences mPrefs;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		buildUpdate();

		return super.onStartCommand(intent, flags, startId);
	}

	private void buildUpdate() {
		Constants.Log(TAG, "Updating Widget");
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		ComponentName thisWidget = new ComponentName(this, WidgetProvider.class);
		int[] ids = manager.getAppWidgetIds(thisWidget);
		for (int i = 0; i < ids.length; i++) {
			if (Build.VERSION.SDK_INT >= 16) {
				Bundle bundle = manager.getAppWidgetOptions(ids[i]);
				int minHeight = bundle
						.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
				int maxHeight = bundle
						.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
				if (minHeight < 192 && maxHeight < 192) {
					views = new RemoteViews(this.getPackageName(),
							R.layout.widget_horizontal);
				} else {
					views = new RemoteViews(this.getPackageName(),
							R.layout.widget);
				}
			}
			else{
				views = new RemoteViews(this.getPackageName(),
						R.layout.widget_horizontal);
			}
			mPrefs = new Preferences(this);
			new SyncDataTask(this).execute(new Object());
			refreshData();
			manager.updateAppWidget(ids[i], views);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void refreshData() {
		Party nda = mPrefs.getParty(Constants.NDA);
		Party upa = mPrefs.getParty(Constants.UPA);
		Party aap = mPrefs.getParty(Constants.AAP);
		Party others = mPrefs.getParty(Constants.OTHERS);
		/*
		 * @TODO: remove this part
		 */
		//others.TOTAL = 543 - (nda.TOTAL + upa.TOTAL + aap.TOTAL);
		Constants.Log(TAG, nda.toString());
		Constants.Log(TAG, upa.toString());
		Constants.Log(TAG, aap.toString());
		Constants.Log(TAG, others.toString());

		views.setTextViewText(R.id.countBjp,
				String.valueOf(Math.round(nda.TOTAL)));
		views.setTextViewText(R.id.countUpa,
				String.valueOf(Math.round(upa.TOTAL)));
		views.setTextViewText(R.id.countAap,
				String.valueOf(Math.round(aap.TOTAL)));
		views.setTextViewText(R.id.countOthers,
				String.valueOf(Math.round(others.TOTAL)));

		views.setProgressBar(R.id.bjpScale, 500, Math.round(nda.TOTAL), false);
		views.setProgressBar(R.id.upaScale, 500, Math.round(upa.TOTAL), false);
		views.setProgressBar(R.id.aapScale, 500, Math.round(aap.TOTAL), false);
		views.setProgressBar(R.id.othersScale, 500, Math.round(others.TOTAL),
				false);

	}

}
