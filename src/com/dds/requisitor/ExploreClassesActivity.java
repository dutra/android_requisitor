package com.dds.requisitor;

import java.util.ArrayList;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ExploreClassesActivity extends BaseMenuActivity {
	LinearLayoutTerms llTerms;
	UserPreferences up = new UserPreferences(ExploreClassesActivity.this);
	UserDatabaseHandler dbU = new UserDatabaseHandler(ExploreClassesActivity.this);
	int semesterPos;
	int replacePos = -1;
	int lastSelectedSemester=-1;
	int lastSelectedPos=-1;
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
			//Log.e("", "bad menuInfo", e);
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
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.reset:
			llTerms.reset();
			return true;
		case R.id.clear:
			llTerms.clearAll();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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
		private ArrayList<Class> takenClasses;

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
			this.takenClasses = new ArrayList<Class>();
			takenClasses.addAll(dbU.getClassesBySemesters(up.getTermsS()));
			sCourses.add(new ArrayList<String>());
			sClasses.add(new ArrayList<Class>());
			this.sCourses = new ArrayList<ArrayList<String>>();
			this.sClasses = new ArrayList<ArrayList<Class>>();
		}
		
		public void reset() {
			llMain.removeAllViewsInLayout();
			clearAll();
			this.tvTerms = new ArrayList<TextView>();
			this.lists = new ArrayList<ListView>();
			this.vChilds = new ArrayList<View>();
			this.ecAdapters = new ArrayList<ExploreClassesArrayAdapter>();
			this.sCourses = new ArrayList<ArrayList<String>>();
			this.sClasses = new ArrayList<ArrayList<Class>>();
			inflate();
		}
		
		public void clearAll() {
			
			
			
			this.sTerms = new ArrayList<String>(); 
			this.takenClasses = new ArrayList<Class>();
			
			
			takenClasses.clear();
			
			
			takenClasses.addAll(dbU.getClassesBySemesters(up.getTermsS()));
			
			Log.d("adaptorsize",ecAdapters.size()+"");			
			for(int i=0; i<ecAdapters.size(); i++){
				sCourses.get(i).clear();
				sClasses.get(i).clear();
				
				for(int j=0;j<6;j++) {
					sClasses.get(i).add(new Class());
					sCourses.get(i).add("");
				}
				
				ecAdapters.get(i).notifyDataSetChanged();
				
				
			}
			
						
		}

		public void inflate() {

			for(int i = 0; i<up.getTermsS().size(); i++) {
				String termS = up.getTermsS().get(i);
				String termL = up.getTermsL().get(i);

				sCourses.add(new ArrayList<String>());
				sClasses.add(new ArrayList<Class>());

				for (Class c : takenClasses) {
					if(c.getTakenIn().equals(termS)) {
						sClasses.get(i).add(c);
						sCourses.get(i).add(c.getMajorN()+"."+c.getClassN()+" "+c.getTitle());
					}
					Log.d("HERE?", "BUGBUG");
				}
				for(int j=0;j<6-sCourses.get(i).size();j++) {
					sClasses.get(i).add(new Class());
					sCourses.get(i).add("");
				}

				sTerms.add(termL);

				vChilds.add(View.inflate(context, R.layout.linear_explore_classes, null));
				lists.add((ListView)vChilds.get(i).findViewById(R.id.list));
				lists.get(i).setId(i);
				tvTerms.add((TextView) vChilds.get(i).findViewById(R.id.tvTerm));
				tvTerms.get(i).setText(sTerms.get(i));
			//	Log.d("sTerm",sTerms.get(i));
				ecAdapters.add(new ExploreClassesArrayAdapter(vChilds.get(i).getContext(), sCourses.get(i)));
				Log.d("CONTEXT", lists.get(i).toString());
				lists.get(i).setAdapter(ecAdapters.get(i));

				Log.d("CONTEXT", "HERE?");
				llMain.addView(vChilds.get(i));

				registerForContextMenu(lists.get(i));


				lists.get(i).setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						int semester = parent.getId();
						//int semesterSelectedPos = i;
						//if(lastSelected!=-1) {
						//TextView tvLastSelected = (TextView) view.findViewById(R.id.label);




						if(sClasses.get(semester).get(position)!=null) {
							//tvSelected = (TextView) lists.get(semester).getChildAt(position).findViewById(R.id.label);
							//tvSelected.setText(""+position+" "+parent.getId());
							TextView tvSelected = (TextView) view.findViewById(R.id.label);


							if(semester==lastSelectedSemester&&position==lastSelectedPos) {
								//tvSelected.setTextAppearance(getApplicationContext(), R.style.normalText);
								highlightClearAll();
								lastSelectedSemester=lastSelectedPos=-1;

							}
							else {
								
								
								
								//tvSelected.setTextAppearance(tvSelected.getContext(), android.R.style.)
								highlightPreReqsFrom(sClasses.get(semester).get(position));
								tvSelected.setTextAppearance(context, R.style.List_item_prereq_selected);
								
								lastSelectedPos=position;
								lastSelectedSemester=semester;
							}
							//Log.d("title:", tvSelected.getText());
							//	Log.d("child",lists.get(semester).getChildAt(position).findViewById(R.id.label));
						}
						//
						//tvSelected.setBackgroundColor(getResources().getColor(android.R.color.background_light));
						//}
						//Log.d("view", view.toString());
					}
				});
			}
		}
		public void highlightPreReqsFrom(Class c) {

			ArrayList<Class> prereqs = new ArrayList<Class>();
			prereqs.addAll(db.getAllPrereqs(c.getID()));
			highlightClearAll();

			int prereqsmissing = prereqs.size();

			for(Class p : prereqs) {
				if(p.getTitle()==null) { prereqsmissing--; continue; }
				Log.d("P",p.getMajorN()+"."+p.getClassN()+" "+p.getTitle());
				for(int semester=0; semester<sTerms.size(); semester++) {

					for(int pos=0; pos<sClasses.get(semester).size(); pos++) {
						Class ac = sClasses.get(semester).get(pos);
						if(ac.getID()==p.getID()&&ac.getTitle()!=null) {
							prereqsmissing--;
							TextView tv = (TextView) lists.get(semester).getChildAt(pos).findViewById(R.id.label);
							tv.setTextAppearance(context, R.style.List_item_prereq);
						}
					}


				}
			}
			Log.d("prereqsmissing",""+prereqsmissing);

		}
		public void highlightClearAll() {
			for(int semester=0; semester<sTerms.size(); semester++) {
				for(int pos=0; pos<sClasses.get(semester).size(); pos++) {
					TextView tv = (TextView) lists.get(semester).getChildAt(pos).findViewById(R.id.label);
					tv.setTextAppearance(context, R.style.List_item);
					
				}
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
