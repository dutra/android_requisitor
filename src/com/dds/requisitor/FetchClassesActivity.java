package com.dds.requisitor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class FetchClassesActivity extends Activity {

	String LOG_TAG = "FetchClassesActivity";

	private final class ProgressBarAsyncTask extends AsyncTask<Void, Integer, Boolean> { //<Params, Progress, Result>
		private ProgressDialog pd;
		private final String MESSAGE[] = { //Available Messages
				"Initializing connection...",
				"Connecting to mit.edu...",
				"Done!"
		};

		/**
		 * This method will be called before the execution of the task. Here we 
		 * are activating the visibility of the progress controls of the main
		 * activity.
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = ProgressDialog.show(FetchClassesActivity.this, "Please wait", MESSAGE[0], true);
			pd.setCancelable(true);

		}

		/**
		 * This method is called after the execution of the background task. Here
		 * we reset the progress controls and set their visible property to 
		 * invisible again, to hide them.
		 * 
		 * @param result: is the result of the background task
		 */
		@Override
		protected void onPostExecute(Boolean result) {
			Log.d(LOG_TAG, "Post-Execute: " + result);
			super.onPostExecute(result);
			pd.dismiss();
			finish();
		}				

		/**
		 * This method will be called after the invocation of the 
		 * publishProgress( progress) method in the background task. Here is where
		 * we update the progress controls.
		 * 
		 * @param progress: the amount of progress of the background task
		 */

		@Override
		protected void onProgressUpdate(Integer... progress) {
			Log.d(LOG_TAG, "Progress Update: " + progress[0].toString());
			super.onProgressUpdate(progress[0]);

			pd.setMessage(MESSAGE[progress[0]]);

		}

		/**
		 * This method is called for executing the background task in the AsyncTask.
		 * For this tutorial we are only sleeping the thread for the number of 
		 * seconds passed as parameter of the function.
		 * 
		 * @return the result of the background task
		 */
		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
				publishProgress(1);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ProgressBarAsyncTask pbTask = new ProgressBarAsyncTask();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fetch_classes);
		pbTask.execute();

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_fetch_classes, menu);
		return true;
	}

}
