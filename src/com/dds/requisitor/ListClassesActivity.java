package com.dds.requisitor;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ListClassesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_classes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_list_classes, menu);
		return true;
	}

}
