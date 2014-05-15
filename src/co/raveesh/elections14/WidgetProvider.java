package co.raveesh.elections14;

import java.util.Calendar;
import java.util.Formatter;

import co.raveesh.elections14.object.Party;
import co.raveesh.elections14.object.Preferences;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {
	String TAG = "WidgetProvider";
	
	private PendingIntent service = null;

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		
		final AlarmManager m = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		final Calendar TIME = Calendar.getInstance();
		TIME.set(Calendar.MINUTE, 0);
		TIME.set(Calendar.SECOND, 0);
		TIME.set(Calendar.MILLISECOND, 0);

		final Intent intent = new Intent(context, SyncService.class);

		if (service == null) {
			service = PendingIntent.getService(context, 0, intent,
					PendingIntent.FLAG_CANCEL_CURRENT);
		}

		m.setRepeating(AlarmManager.RTC, TIME.getTime().getTime(), 1000 * 60 * 5,
				service);
	}

	@Override
	public void onDisabled(Context context) {
		final AlarmManager m = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		m.cancel(service);
	}
	
	@Override
	public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle bundle) {
	        int minWidth = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
	        int maxWidth = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
	        int minHeight = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
	        int maxHeight = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
	        RemoteViews rv = null;
	       
	        Constants.Log(TAG, new Formatter().format("Resized %d %d %d %d", minWidth,maxWidth,minHeight,maxHeight).toString());
	        if (minHeight <192 && maxHeight<192){
	        	rv = new RemoteViews(context.getPackageName(), R.layout.widget_horizontal);
	        }
	        else{
	        	rv = new RemoteViews(context.getPackageName(), R.layout.widget);
	        }
	        refreshData(appWidgetManager, appWidgetId, new Preferences(context),rv);
	}
	
	private void refreshData(AppWidgetManager appWidgetManager, int appWidgetId, Preferences mPrefs, RemoteViews views) {
		Party nda = mPrefs.getParty(Constants.NDA);
		Party upa = mPrefs.getParty(Constants.UPA);
		Party aap = mPrefs.getParty(Constants.AAP);
		Party others = mPrefs.getParty(Constants.OTHERS);
		/*
		 * @TODO: remove this part
		 */
		others.TOTAL = 543 - (nda.TOTAL + upa.TOTAL + aap.TOTAL);
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
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}

}
