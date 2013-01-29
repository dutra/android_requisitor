package com.dds.requisitor;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends BaseMenuActivity {
	UserPreferences up = new UserPreferences(MainActivity.this);
	ArrayList<String> mSelectedItems;
	ArrayList<String> smList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		int i;
		i = db.getCount();
		Log.d("I", "aaaa" + Integer.toString(i));

		if (i == 0) {

			AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivity.this);
			builder.setMessage(
					"The courses' database hasn't been initialized yet. Would you like to do that now?")
					.setTitle("Database Initialization");

			builder.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							Intent i = new Intent(MainActivity.this,
									FetchClassesActivity.class);
							startActivity(i);
						}
					});
			builder.setNegativeButton("Not now",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
						}
					});

			AlertDialog dialog = builder.create();
			dialog.show();

		}

		if (up.load() == 1) {

			AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivity.this);
			builder.setMessage(
					"You haven't set your information yet. Would you like to do that now?")
					.setTitle("User Preferences Initialization");

			builder.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							Intent i = new Intent(MainActivity.this,
									UserPreferencesActivity.class);
							startActivity(i);
						}
					});
			builder.setNegativeButton("Not now",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
							Toast.makeText(
									MainActivity.this,
									"You can manage your preferences at any time by acessing the menu",
									Toast.LENGTH_LONG).show();
						}
					});

			AlertDialog dialog = builder.create();
			dialog.show();

		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(this));
		
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// Toast.makeText(MainActivity.this, "" + position,
				// Toast.LENGTH_SHORT).show();
				switch (position) {
				case 0:
					onClickExplore(v);
					break;
				case 1:
					onClickSearch(v);
					break;
				case 2:
					onClickList(v);
					break;
				case 3:
					onClickUP(v);
					break;
				case 4:
					onClickHelp(v);
					break;
				case 5:
					onClickAbout(v);
					break;
				}
			}
		});

	}

	public void onClickFetch(View v) {
		Intent i = new Intent(v.getContext(), FetchClassesActivity.class);
		startActivity(i);
	}

	public void onClickList(View v) {
		Intent i = new Intent(MainActivity.this, ListClassesActivity.class);
		startActivity(i);

	}
	
	public void onClickUP(View v) {
		Intent i = new Intent(v.getContext(), UserPreferencesActivity.class);
		startActivity(i);
	}

	public void onClickSearch(View v) {
		Intent i = new Intent(v.getContext(), SearchClassActivity.class);
		startActivity(i);
	}

	public void onClickEraseUP(View v) {
		up.eraseAll();
	}

	public void onClickExplore(View v) {
		Intent i = new Intent(v.getContext(), ExploreClassesActivity.class);
		startActivity(i);

	}
	public void onClickHelp(View v) {
		Intent i = new Intent(v.getContext(), HelpActivity.class);
		startActivity(i);

	}
	public void onClickAbout(View v) {
		Intent i = new Intent(v.getContext(), AboutUsActivity.class);
		startActivity(i);

	}
	

}
