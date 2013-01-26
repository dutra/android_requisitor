package com.dds.requisitor;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class SearchClassActivity extends Activity {
	UserPreferences up = new UserPreferences(SearchClassActivity.this);
	EditText etCourse;
	Spinner spCourse;
	ArrayList<String> courses = new ArrayList<String>();
	ArrayList<Class> classes = new ArrayList<Class>();
	ListView list;
	DatabaseHandler db = new DatabaseHandler(SearchClassActivity.this);
	SearchClassArrayAdapter myadapter;

	private class SearchClassArrayAdapter extends ArrayAdapter<String> {
		private final Context context;
		private final ArrayList<String> values;

		public SearchClassArrayAdapter(Context context, ArrayList<String> values) {
			super(context, R.layout.list_search_class, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View rowView = inflater.inflate(R.layout.list_search_class, parent, false);
			TextView textView = (TextView) rowView.findViewById(R.id.course);
			textView.setText(values.get(position));

			return rowView;
		}
		@Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		myadapter = new SearchClassArrayAdapter(this, courses);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_class);

		list = (ListView) findViewById(R.id.list);

		list.setAdapter(myadapter);

		etCourse = (EditText)findViewById(R.id.etCourse);

		spCourse = (Spinner) findViewById(R.id.spCourse);
		ArrayAdapter<String> dataAdapterCourse = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, up.getcourseNall());
		dataAdapterCourse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spCourse.setAdapter(dataAdapterCourse);
		spCourse.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long id) {
				updateList(up.getcourseNall().get(position).toString()+".");
				etCourse.setText(up.getcourseNall().get(position).toString()+".");

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});


		etCourse.addTextChangedListener(new TextWatcher(){
			public void afterTextChanged(Editable e) {
				//	updateList(e.toString());
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void onTextChanged(CharSequence s, int start, int before, int count){}
		}); 
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
				classes.get(position).getTitle();
				// custom dialog
				final Dialog dialog = new Dialog(SearchClassActivity.this);
				dialog.setContentView(R.layout.dialog_class_info);
				dialog.setTitle(classes.get(position).getMajorN()+"."+classes.get(position).getClassN());

				TextView tvUnits = (TextView) dialog.findViewById(R.id.tvUnits);
				tvUnits.setText(classes.get(position).getUnitsString());
				TextView tvDescription = (TextView) dialog.findViewById(R.id.tvDescription);
				tvDescription.setText(classes.get(position).getDescription());
				
				
				Button btAdd = (Button) dialog.findViewById(R.id.btAdd);
				// if button is clicked, close the custom dialog
				btAdd.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				Button btCancel = (Button) dialog.findViewById(R.id.btCancel);
				// if button is clicked, close the custom dialog
				btCancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				dialog.show();
			}
		});
	}









	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search_class, menu);
		return true;
	}
	private void updateList(String e) {
		String courseN;
		String classN;
		ArrayList<String> strings = new ArrayList<String>();
		if(e.toString().split("\\.",2).length>0) {
			courseN = e.toString().split("\\.", 2)[0];
			if(up.getcourseNall().indexOf(courseN)!=-1) {
				spCourse.setSelection(up.getcourseNall().indexOf(courseN));
			}

			if(e.toString().split("\\.").length==2) {
				Log.d("LENGTH", "2");
				classN = e.toString().split("\\.")[1];
			}
			classes = db.getClassesByInitial(e.toString());

			for(Class c : classes) {
				strings.add(c.getMajorN()+"."+c.getClassN()+" "+c.getTitle());
			}
			Log.d("STRINGS", strings.toString()+strings.size());
			if(strings.size()!=0) {
				courses.clear();
				courses.addAll(strings);
				myadapter.notifyDataSetChanged();
			}
		}
	}

}
