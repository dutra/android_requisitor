package com.dds.requisitor;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListClassesActivity extends ListActivity {
	DatabaseHandler db = new DatabaseHandler(ListClassesActivity.this);
	UserDatabaseHandler dbU = new UserDatabaseHandler(ListClassesActivity.this);
	private class ListClassesAdapter extends ArrayAdapter<Class> {
		private final Context context;
		private final ArrayList<Class> classes;
		
		
		public ListClassesAdapter(Context context, ArrayList<Class> c) {

			super(context, R.layout.list_classes_adapter_row_layout, c);
			this.context = context;
			this.classes = c;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.list_classes_adapter_row_layout, parent, false);
			TextView textView = (TextView) rowView.findViewById(R.id.course);
		    textView.setText(classes.get(position).getTitle());
		    
			return rowView;
		}
	} 

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ArrayList<String> terms = new ArrayList<String>();
		ArrayList<Class> c = new ArrayList<Class>();
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			terms = extras.getStringArrayList("TERMS");
		}
		if (terms.isEmpty()) {
			finish();
		}
		//Toast.makeText(ListClassesActivity.this, terms.toString(), Toast.LENGTH_LONG).show();
		Log.d("TERMS", terms.toString()+c.size());
		
		c = dbU.getClassesBySemesters(terms);
		
		if(c.size()==0) {

			AlertDialog.Builder builder = new AlertDialog.Builder(ListClassesActivity.this);
			builder.setTitle("Error").setMessage("Could not find any classes that matched the selected semesters");

			builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					finish();
					//
				}
			});
			AlertDialog dialog = builder.create();
			dialog.show();
			
		}
		setContentView(R.layout.activity_list_classes);
		setListAdapter(new ListClassesAdapter(this, c));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_list_classes, menu);
		return true;
	}

}
