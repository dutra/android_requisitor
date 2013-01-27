package com.dds.requisitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class FetchClassesActivity extends Activity {
	UserPreferences up = new UserPreferences(FetchClassesActivity.this);
	ClassesParser cp = new ClassesParser();
	DatabaseHandler db = new DatabaseHandler(FetchClassesActivity.this);
	String LOG_TAG = "FetchClassesActivity";
	//ArrayList<Class> _classes = new ArrayList<Class>();

	private String downloadJSON(String s){
		URL url;
		StringBuffer jsonstring = null;
		HttpURLConnection connection;       
		try {
			url = new URL(s);

			//Log.i("System out", "url:" + url);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(1000 * 1); // Timeout is in seconds
			InputStreamReader is = new InputStreamReader(connection
					.getInputStream());
			BufferedReader buff = new BufferedReader(is);
			jsonstring = new StringBuffer();
			String line = "";
			do {
				line = buff.readLine();
				if (line != null)
					jsonstring.append(line);
			} while (line != null);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return null;

		}
		return jsonstring.toString().trim();
	}

	private final class ProgressBarAsyncTask extends AsyncTask<Void, Integer, Boolean> { //<Params, Progress, Result>
		private ProgressDialog pd;
		private ArrayList<String> MESSAGES = new ArrayList<String>(); 


		private String http;

		/**
		 * This method will be called before the execution of the task. Here we 
		 * are activating the visibility of the progress controls of the main
		 * activity.
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			MESSAGES.add("Initializing connection..."); //0
			MESSAGES.add("Connecting to mit.edu..."); //1
			MESSAGES.add("Download classes from course "); //2
			MESSAGES.add("Parsing..."); //3
			MESSAGES.add("Saving to Database... (This may take a couple of minutes)"); //4
			MESSAGES.add("Error downloading. Are you connected to the internet?"); //5
			MESSAGES.add("Done!");//6


			pd = ProgressDialog.show(FetchClassesActivity.this, "Please wait", MESSAGES.get(0), true);
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
			//Log.d(LOG_TAG, "Post-Execute: " + result);
			super.onPostExecute(result);
			try {
		        pd.dismiss();
		        pd = null;
		        finish();
		    } catch (Exception e) {
		        // nothing
		    }
			
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
			if(progress.length>1) {
				pd.setMessage(MESSAGES.get(progress[0])+up.getcourseNall().get(progress[1])+"...");
			}
			else {
				pd.setMessage(MESSAGES.get(progress[0]));
			}

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
				db.eraseAll();
				

				for(int i=0; i<up.getcourseNall().size(); i++) {
					publishProgress(2,i);
					//Thread.sleep(100);
					if((http = downloadJSON("http://coursews.mit.edu/coursews/?term=2013SP&courses="+up.getcourseNall().get(i)))== null) {
						publishProgress(5);
						Thread.sleep(3000);
						return false;
					}
					publishProgress(3); //parsing
				//	Log.d("CP", "parsingClasses");
					cp.parseClasses(http);
					//Log.d("CP", "gettingClasses");
					//Thread.sleep(100);
				}
				publishProgress(4); //saving to db
				Log.d("DB", "Saving to db");
				db.addClasses(cp.getClasses());
				//Thread.sleep(200);

				publishProgress(6);
				Thread.sleep(300);



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
