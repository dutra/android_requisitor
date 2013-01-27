package com.dds.requisitor;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class BaseMenuActivity extends Activity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_base_menu, menu);
		return true;
	}

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
	}

}
