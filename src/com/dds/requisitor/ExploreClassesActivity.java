package com.dds.requisitor;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ExploreClassesActivity extends BaseMenuActivity {
	LinearLayoutTerms llTerms;
	
	private class ExploreClassesArrayAdapter extends ArrayAdapter<String> {
		private final Context context;
		private final ArrayList<String> values;

		public ExploreClassesArrayAdapter(Context context, ArrayList<String> values) {
			super(context, R.layout.list_explore_classes, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View rowView = inflater.inflate(R.layout.list_explore_classes, parent,
					false);
			TextView textView = (TextView) rowView.findViewById(R.id.list);
			textView.setText(values.get(position));

			return rowView;
		}

		@Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
		}
	}
	
	private class LinearLayoutTerms {
		private LinearLayout llMain;
		private Context context;
		private ArrayList<View> vChilds;
		private ArrayList<ExploreClassesArrayAdapter> ecAdapters;
		private ArrayList<ListView> lists;
		private ArrayList<ArrayList<String>> sCourses;

		public LinearLayoutTerms(LinearLayout llMain, Context context) {
			this.llMain = llMain;
			this.context = context;
			this.vChilds = new ArrayList<View>();
			this.lists = new ArrayList<ListView>();
			this.ecAdapters = new ArrayList<ExploreClassesArrayAdapter>();
			this.sCourses = new ArrayList<ArrayList<String>>();
		}

		public void inflate() {
			for(int i = 0; i<2; i++) {
				sCourses.add(new ArrayList<String>());
				sCourses.get(i).add("CCCCCC");
				vChilds.add(View.inflate(context, R.layout.linear_explore_classes, null));
				lists.add((ListView)vChilds.get(i).findViewById(R.id.list));
				
				ecAdapters.add(new ExploreClassesArrayAdapter(vChilds.get(i).getContext(), sCourses.get(i)));
				Log.d("CONTEXT", lists.get(i).toString());
				//lists.get(i).setAdapter(ecAdapters.get(i));
				Log.d("CONTEXT", "HERE?");
				//llMain.addView(vChilds.get(i));
				
			}

		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explore_classes);
		LinearLayout llMain = (LinearLayout) findViewById(R.id.llMain);
		llTerms = new LinearLayoutTerms(llMain, this);
		llTerms.inflate();
		/*View llChild1 = LinearLayout.inflate(this, R.layout.linear_explore_classes, null);

		llMain.addView(llChild1);
		TextView tvLinear = (TextView) llChild1.findViewById(R.id.tvLinear);
		tvLinear.setText("bbbbbbbb");*/
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_explore_classes, menu);
		return true;
	}

}
