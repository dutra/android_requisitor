package com.dds.requisitor;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;

public class AboutUsActivity extends BaseMenuActivity {
//	UserPreferences up = new UserPreferences(AboutUsActivity.this);
//	ArrayList<String> mSelectedItems;
//	ArrayList<String> smList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

	}
	
	public void OnClickContactUs(View v) {
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND); 
		String aEmailList[] = { "svasserm@mit.edu","dutra@mit.edu" };  
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);  
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Requisitor Feedback");  
		emailIntent.setType("plain/text");
//		startActivity(Intent.createChooser(emailIntent, "Send your email in:"));
		startActivity(emailIntent);

	}
	


}
