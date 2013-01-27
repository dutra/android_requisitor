package com.dds.requisitor;

import java.util.ArrayList;

import com.dds.requisitor.R.id;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ExploreClassesActivity extends BaseMenuActivity {
	LinearLayoutTerms llTerms;
	UserPreferences up = new UserPreferences(ExploreClassesActivity.this);
	int semesterPos;
	int replacePos = -1;
	DatabaseHandler db = new DatabaseHandler(ExploreClassesActivity.this);

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
	public void onCreateContextMenu(ContextMenu menu,View v,ContextMenuInfo info)
	{
	/*	ListView lv;
		if(v.getId()>llTerms.getTermsTitle().size()) {
			lv = (ListView) v.findViewById(R.id.list);
		}
		else { lv = (ListView) v; }
		menu.setHeaderTitle(llTerms.getTermsTitle().get(lv.getId()));*/
		//if(v.getId()==R.id.llMain) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.activity_explore_classes_context_menu, menu);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info;
		LinearLayout main = null;
		ListView l = null;
		Intent i = new Intent();
		try {
			
			info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			l = (ListView) info.targetView.getParent();
			
		} catch (ClassCastException e) {
			Log.e("", "bad menuInfo", e);
			return false;
		}
		switch (item.getItemId()) {
		 
		 case R.id.add : 
			 semesterPos = l.getId();
			 i = new Intent(ExploreClassesActivity.this, SearchClassActivity.class);
				i.putExtra("FIND", 1);
				startActivityForResult(i,1);
			 return true;
		 
		 case R.id.replace :
			 semesterPos = l.getId();
			 replacePos = (int) info.position;
			 i = new Intent(ExploreClassesActivity.this, SearchClassActivity.class);
				i.putExtra("FIND", 1);
				startActivityForResult(i,1);
			 return true;
			 
		 case R.id.delete : 
			 llTerms.rmClass(l.getId(), (int) info.position);
			 return true;
		           
		 }
		
//		long id = l.getAdapter().getItemId(info.position);
		//Log.d("", "id = " + id);
	//	Toast.makeText(this, "id = " +l.getId()+info.id, Toast.LENGTH_SHORT).show();
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_explore_classes, menu);
		return true;
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

		     if(resultCode == RESULT_OK){

		      int id = data.getIntExtra("ID",0);
		      if (replacePos>-1) {
		    	  llTerms.setClass(db.getClass(id), semesterPos, replacePos);
		    	  replacePos=-1;
		      }
		      else {
		      llTerms.addClass(db.getClass(id), semesterPos);
		      }
		      Log.d("ID returned", ""+id);
		      
		}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
			TextView textView = (TextView) rowView.findViewById(R.id.label);
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
		private ArrayList<TextView> tvTerms;
		private ArrayList<ArrayList<String>> sCourses;
		private ArrayList<ArrayList<Class>> sClasses;
		private ArrayList<String> sTerms;

		public LinearLayoutTerms(LinearLayout llMain, Context context) {
			this.llMain = llMain;
			this.context = context;
			this.vChilds = new ArrayList<View>();
			this.lists = new ArrayList<ListView>();
			this.tvTerms = new ArrayList<TextView>();
			this.ecAdapters = new ArrayList<ExploreClassesArrayAdapter>();
			this.sCourses = new ArrayList<ArrayList<String>>();
			this.sClasses = new ArrayList<ArrayList<Class>>();
			this.sTerms = new ArrayList<String>(); 
		}

		public void inflate() {
			for(int i = 0; i<10; i++) {

				sCourses.add(new ArrayList<String>());
				sClasses.add(new ArrayList<Class>());
				for(int j=0;j<5;j++) {
					sClasses.get(i).add(new Class());
					sCourses.get(i).add("");
				}
				
				sTerms.add("SPRING");

				vChilds.add(View.inflate(context, R.layout.linear_explore_classes, null));
				lists.add((ListView)vChilds.get(i).findViewById(R.id.list));
				lists.get(i).setId(i);
				tvTerms.add((TextView) vChilds.get(i).findViewById(R.id.tvTerm));
				tvTerms.get(i).setText(sTerms.get(i));
				ecAdapters.add(new ExploreClassesArrayAdapter(vChilds.get(i).getContext(), sCourses.get(i)));
				Log.d("CONTEXT", lists.get(i).toString());
				lists.get(i).setAdapter(ecAdapters.get(i));
				Log.d("CONTEXT", "HERE?");
				llMain.addView(vChilds.get(i));
				
				
				LinearLayout llclickable = (LinearLayout) vChilds.get(i).findViewById(id.llclickable);
				//registerForContextMenu(llclickable);
				//LinearLayout tmp = (LinearLayout) vChilds.get(i);
				
//				registerForContextMenu(llMain);
				//registerForContextMenu(tmp);
				registerForContextMenu(lists.get(i));
			}
		}
		public void setTermTitle(int i, String s) {
			try {
				sTerms.set(i, s);
				tvTerms.get(i).setText(s);

			} catch (IndexOutOfBoundsException e) {
				// TODO: handle exception
			}
		}
		public void setTermsTitle(ArrayList<String> ss) {
			for(int i=0; i<ss.size(); i++)
				setTermTitle(i, ss.get(i));
		}
		public void setClass(Class c, int semester, int pos) {
			sClasses.get(semester).set(pos, c);
			sCourses.get(semester).set(pos, c.getTitle());
			ecAdapters.get(semester).notifyDataSetChanged();
		}
		public void setClasses(ArrayList<Class> classes, int i) {
			sCourses.get(i).clear();
			sClasses.get(i).clear();
			sClasses.get(i).addAll(classes);
			for(Class c : classes) {
				sCourses.get(i).add(c.getTitle());	
			}
			
			ecAdapters.get(i).notifyDataSetChanged();
		}
		public void addClass(Class c, int i) {
			sClasses.get(i).add(c);
			sCourses.get(i).add(c.getTitle());
			ecAdapters.get(i).notifyDataSetChanged();
		}
		public void rmClass(int semester, int pos) {
			sClasses.get(semester).remove(pos);
			sCourses.get(semester).remove(pos);
			ecAdapters.get(semester).notifyDataSetChanged();
		}
		public ArrayList<String> getTermsTitle() {
			return sTerms;
		}
	}
	
}
