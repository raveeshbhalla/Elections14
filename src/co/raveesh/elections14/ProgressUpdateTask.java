package co.raveesh.elections14;

import android.os.AsyncTask;
import co.raveesh.elections14.object.ResultRow;

public class ProgressUpdateTask extends AsyncTask<ResultRow, ResultRow, Void> {

	private String TAG = "ProgressUpdateTask";
	int mDuration, mFps;

	public ProgressUpdateTask(int duration, int fps) {
		mDuration = duration;
		mFps = fps;
	}

	@Override
	protected Void doInBackground(ResultRow... rows) {
		int frames = mFps * 1000 / mDuration;
		int frameDuration = mDuration / frames;
		for (int i = 1; i <= frames; i++) {
			ResultRow row = rows[0];
			float diff = row.neu.TOTAL - row.old.TOTAL;
			float stepSize = diff / frames;
			row.old.TOTAL += stepSize;

			row = rows[1];
			diff = row.neu.TOTAL - row.old.TOTAL;
			stepSize = diff / frames;
			row.old.TOTAL += stepSize;

			row = rows[2];
			diff = row.neu.TOTAL - row.old.TOTAL;
			stepSize = diff / frames;
			row.old.TOTAL += stepSize;

			row = rows[3];
			diff = row.neu.TOTAL - row.old.TOTAL;
			stepSize = diff / frames;
			row.old.TOTAL += stepSize;
			publishProgress(rows);
			try {
				Thread.sleep(frameDuration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	protected void onProgressUpdate(ResultRow... rows) {
		Constants.Log(TAG,rows[0].toString());
		Constants.Log(TAG,rows[1].toString());
		Constants.Log(TAG,rows[2].toString());
		Constants.Log(TAG,rows[3].toString());
		rows[0].pb.setProgress(Math.round(rows[0].old.TOTAL));
		rows[1].pb.setProgress(Math.round(rows[1].old.TOTAL));
		rows[2].pb.setProgress(Math.round(rows[2].old.TOTAL));
		rows[3].pb.setProgress(Math.round(rows[3].old.TOTAL));
		rows[0].text.setText(String.valueOf(Math.round(rows[0].old.TOTAL)));
		rows[1].text.setText(String.valueOf(Math.round(rows[1].old.TOTAL)));
		rows[2].text.setText(String.valueOf(Math.round(rows[2].old.TOTAL)));
		rows[3].text.setText(String.valueOf(Math.round(rows[3].old.TOTAL)));

	}

}
