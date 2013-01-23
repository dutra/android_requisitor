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

public class FetchClassesActivity extends Activity {
 
	ClassesParser cp = new ClassesParser();
	DatabaseHandler db = new DatabaseHandler(FetchClassesActivity.this);
	String LOG_TAG = "FetchClassesActivity";
	ArrayList<Class> _classes = new ArrayList<Class>();
	
	private String downloadJSON(String s){
	    URL url;
	    StringBuffer jsonstring = null;
	    HttpURLConnection connection;       
	    try {
	        url = new URL(s);

	    Log.i("System out", "url:" + url);
	    connection = (HttpURLConnection) url.openConnection();
	    connection.setConnectTimeout(1000 * 5); // Timeout is in seconds
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
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    	e.printStackTrace();
	    	return null;
	        
	    }
	    return jsonstring.toString().trim();
	}

	private final class ProgressBarAsyncTask extends AsyncTask<Void, Integer, Boolean> { //<Params, Progress, Result>
		private ProgressDialog pd;
		private final String MESSAGE[] = { //Available Messages
				"Initializing connection...", //0
				"Connecting to mit.edu...", //1
				"Downloading m8a...", //2
				"Parsing...", //3
				"Saving to DataBase..", //4
				"Error downloading!", //5
				"Done!"//6
		};
		private String http;

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
				URI uri = new URI("http://coursews.mit.edu/coursews/?courses=8");
				publishProgress(2);
				Thread.sleep(200);
				
				//http = new String(downloadUrl("http://student.mit.edu/catalog/m8a.html"));
				//http = getHttpResponse(uri);
				if((http = downloadJSON("http://coursews.mit.edu/coursews/?courses=8"))== null) {
					publishProgress(5);
					Thread.sleep(200);
					return false;
				}
				//Log.d("HTTP", http);
				publishProgress(3); //parsing
				Log.d("CP", "parsingClasses");
				cp.parseClasses(http);
				Log.d("CP", "gettingClasses");
				_classes = cp.getClasses();
				for(Class i : _classes) {
					Log.d("WTF", i.getTitle());
				}
				Thread.sleep(200);
				
				publishProgress(4); //saving to db
				Log.d("DB", "Saving to db");
				db.addClasses(_classes);
				Thread.sleep(200);
				
				publishProgress(6);
				Thread.sleep(200);
				
				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
