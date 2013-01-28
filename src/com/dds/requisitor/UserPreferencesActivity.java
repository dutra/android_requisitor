package com.dds.requisitor;

import java.util.ArrayList;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class UserPreferencesActivity extends BaseMenuActivity {
	UserPreferences up;
	Spinner spCourse;
	Spinner spYear;
	EditText etName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ArrayList<String> years = new ArrayList<String>();
		years.add("Freshman");
		years.add("Sophomore");
		years.add("Junior");
		years.add("Senior");
		up = new UserPreferences(UserPreferencesActivity.this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_preferences);
		int load = up.load();

		spCourse = (Spinner) findViewById(R.id.spCourse);
		ArrayAdapter<String> dataAdapterCourse = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, up.getcourseSall());
		dataAdapterCourse
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCourse.setAdapter(dataAdapterCourse);

		spYear = (Spinner) findViewById(R.id.spYear);
		ArrayAdapter<String> dataAdapterYear = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, years);
		dataAdapterYear
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spYear.setAdapter(dataAdapterYear);

		etName = (EditText) findViewById(R.id.etName);
		if (load == 0) {
			etName.setText(up.getName());
			spCourse.setSelection(up.getcourseNall().indexOf(up.getCourseN()));
			spCourse.setSelection(years.indexOf(up.getGrade()));
			spYear.setSelection(years.indexOf(up.getGrade()));
		}

	}

	public void OnClickSave(View v) {
		up.setName(etName.getText().toString());
		up.setCourseN(up.getcourseNall()
				.get(spCourse.getSelectedItemPosition()));
		up.setGrade(spYear.getSelectedItem().toString());

		up.save();
		finish();
	}
	
	public void OnClickErase(View v) {
		up.eraseAll();
		etName.setText("");
		spCourse.setSelection(0);
		spYear.setSelection(0);

	}

/*	@Override 
	 public boolean onCreateOptionsMenu(Menu menu) { 
	 getMenuInflater().inflate(R.menu.activity_user_preferences, menu); return
	 true; }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_main:
			startActivity(new Intent(this, MainActivity.class));
			return true;
		case R.id.menu_search:
			startActivity(new Intent(this, SearchClassActivity.class));
			return true;
		case R.id.menu_list:
			startActivity(new Intent(this, ListClassesActivity.class));
		case R.id.menu_settings:
			startActivity(new Intent(this, UserPreferencesActivity.class));
			return true;
		case R.id.menu_refresh:
			startActivity(new Intent(this, FetchClassesActivity.class));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}*/

}
