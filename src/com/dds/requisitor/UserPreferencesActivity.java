package com.dds.requisitor;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
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
		if (load == 1) {
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

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.activity_user_preferences, menu); return
	 * true; }
	 */

}
