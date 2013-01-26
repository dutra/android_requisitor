package com.dds.requisitor;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	UserPreferences up = new UserPreferences(MainActivity.this);
	ArrayList<String> mSelectedItems;
	ArrayList<String> smList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		int i;
		i=db.getCount();
		Log.d("I", "aaaa"+Integer.toString(i));

		if(i==0) {

			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setMessage("The courses' database hasn't been initialized yet. Would you like to do that now?")
			.setTitle("Database Initialization");

			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Intent i = new Intent(MainActivity.this, FetchClassesActivity.class);
					startActivity(i);
				}
			});
			builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
				}
			});

			AlertDialog dialog = builder.create();
			dialog.show();

		}

		
		if(up.load()==1) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setMessage("You haven't set your informations yet. Would you like to do that now?")
			.setTitle("User Preferences Initialization");

			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Intent i = new Intent(MainActivity.this, UserPreferencesActivity.class);
					startActivity(i);
				}
			});
			builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
					Toast.makeText(MainActivity.this, "You can manage your preferences at any time by acessing the menu", Toast.LENGTH_LONG).show();
				}
			});

			AlertDialog dialog = builder.create();
			dialog.show();
			
		}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);



	}

	
	public void onClickFetch(View v) {
		Intent i = new Intent(v.getContext(), FetchClassesActivity.class);
		startActivity(i);
	}
	public void onClickList(View v) {
		mSelectedItems = new ArrayList<String>();
		ArrayList<String> semesterList = new ArrayList<String>();
		smList = new ArrayList<String>();
		semesterList.add("Spring 2012");
		smList.add("2012SP");
		semesterList.add("Fall 2012");
		smList.add("2012FA");
		semesterList.add("Spring 2013");
		smList.add("2013SP");

		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("List Courses");

		builder.setMultiChoiceItems(semesterList.toArray(new String[semesterList.size()]), null,
				new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				//        	 String buffer = new String();
				if (isChecked) {
					//buffer = semesterList.get(which);
					// If the user checked the item, add it to the selected items
					mSelectedItems.add(smList.get(which).toString());
				} else if (mSelectedItems.contains(smList.get(which).toString())) {
					// Else, if the item is already in the array, remove it 
					mSelectedItems.remove(smList.get(which).toString());
				}
			}
		});

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				if(mSelectedItems.size()==0) {
					Toast.makeText(MainActivity.this, "You should choose at least one semester", Toast.LENGTH_SHORT).show();
				} else {

					Intent i = new Intent(MainActivity.this, ListClassesActivity.class);
					i.putStringArrayListExtra("TERMS", mSelectedItems);
					startActivity(i);
				}


			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();

	}

	public void onClickSearch(View v) {
		Intent i = new Intent(v.getContext(), SearchClassActivity.class);
		startActivity(i);
	}
	public void onClickEraseUP(View v) {
		up.eraseAll();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        /*case R.id.about:
        startActivity(new Intent(this, About.class));
        return true;
        case R.id.help:
        startActivity(new Intent(this, Help.class));
        return true;*/
        default:
        return super.onOptionsItemSelected(item);
        }
    }
	
}
