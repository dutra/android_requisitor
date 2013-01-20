package com.dds.requisitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class FetchClassesActivity extends Activity {

	String LOG_TAG = "FetchClassesActivity";
	Integer GET_LENGTH = 128000;
	
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

		
	public static String getHttpResponse(URI uri) throws IOException {
		//Log.d(APP_TAG, "Going to make a get request");
		StringBuilder response = new StringBuilder();
		InputStream is = null;
		try {
			HttpGet get = new HttpGet();
			get.setURI(uri);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(get);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				

				HttpEntity messageEntity = httpResponse.getEntity();
				is = messageEntity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String line;
				while ((line = br.readLine()) != null) {
					response.append(line);
				}
				Log.d("demo", "HTTP Get succeeded");

				
			}
		} catch (Exception e) {
			Log.e("demo", e.getMessage());
		} finally {
			if (is!=null)
				is.close();
		}
		
		Log.d("demo", "Done with HTTP getting");
		return response.toString();
	}
	
	// Reads an InputStream and converts it to a String.
	public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
	    Reader reader = null;
	    reader = new InputStreamReader(stream, "UTF-8");        
	    char[] buffer = new char[len];
	    reader.read(buffer);
	    return new String(buffer);
	}
	
	private final class ProgressBarAsyncTask extends AsyncTask<Void, Integer, Boolean> { //<Params, Progress, Result>
		private ProgressDialog pd;
		private final String MESSAGE[] = { //Available Messages
				"Initializing connection...",
				"Connecting to mit.edu...",
				"Downloading m8a",
				"Parsing",
				"Error download",
				"Done!"
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
			ClassesParser cp = new ClassesParser();
			try {
				URI uri = new URI("http://coursews.mit.edu/coursews/?courses=8");
				publishProgress(2);
				Thread.sleep(200);
				
				//http = new String(downloadUrl("http://student.mit.edu/catalog/m8a.html"));
				//http = getHttpResponse(uri);
				if((http = downloadJSON("http://coursews.mit.edu/coursews/?term=2013SP&courses=8"))== null) {
					publishProgress(4);
					Thread.sleep(200);
					return false;
				}
				//Log.d("HTTP", http);
				publishProgress(3);
				cp.parseClasses(http);
				
				Thread.sleep(200);
				publishProgress(5);
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
