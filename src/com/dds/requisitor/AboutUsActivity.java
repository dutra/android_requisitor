package com.dds.requisitor;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AboutUsActivity extends BaseMenuActivity {
	Dialog pd;
	
	
//	UserPreferences up = new UserPreferences(AboutUsActivity.this);
//	ArrayList<String> mSelectedItems;
//	ArrayList<String> smList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.dialog_class_about_us);
		
		pd = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
		pd.setContentView(R.layout.dialog_class_about_us);	
		
		//pd.setTitle("About Us");
		
		Button btClose = (Button) pd.findViewById(R.id.btClose);
		// if button is clicked, close the custom dialog
		btClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pd.dismiss();
			}
		});

		pd.show();

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
