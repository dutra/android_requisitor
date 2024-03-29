package com.dds.requisitor;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.Toast;

public class ExploreClassesActivity extends BaseMenuActivity {
	LinearLayoutTerms llTerms;
	UserPreferences up = new UserPreferences(ExploreClassesActivity.this);
	UserDatabaseHandler dbU = new UserDatabaseHandler(ExploreClassesActivity.this);
	int semesterPos;
	int replacePos = -1;
	int lastSelectedSemester=-1;
	int lastSelectedPos=-1;
	FlowLayout llmissingprereqs;
	DatabaseHandler db = new DatabaseHandler(ExploreClassesActivity.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explore_classes);
		llmissingprereqs = (FlowLayout) findViewById(R.id.llmissingprereqs);
		LinearLayout llMain = (LinearLayout) findViewById(R.id.llMain);
		llTerms = new LinearLayoutTerms(llMain, this);
		if(llTerms.load()==-1) {
			Log.d("LOAD", "-1");
			llTerms.inflate_dbu();
		}
		else {
			llTerms.inflate();
			Log.d("LOAD", "0");
		}

		/*View llChild1 = LinearLayout.inflate(this, R.layout.linear_explore_classes, null);

		llMain.addView(llChild1);
		TextView tvLinear = (TextView) llChild1.findViewById(R.id.tvLinear);
		tvLinear.setText("bbbbbbbb");*/

	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		llTerms.save();
		llTerms.reset();

	}
	@Override
	protected void onResume() {
		super.onResume();

	};
	@Override
	protected void onPause() {
		super.onPause();
		llTerms.save();

	};
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
			if(llTerms.isEmpty(semesterPos, (int) info.position))
				replacePos = (int) info.position;
			i = new Intent(ExploreClassesActivity.this, SearchClassActivity.class);
			i.putExtra("FIND", 1);
			i.putExtra("SEMESTER", up.getTermsS().get(semesterPos));
			startActivityForResult(i,1);
			return true;

		case R.id.replace :
			semesterPos = l.getId();
			replacePos = (int) info.position;
			i = new Intent(ExploreClassesActivity.this, SearchClassActivity.class);
			i.putExtra("FIND", 1);
			i.putExtra("SEMESTER", up.getTermsS().get(semesterPos));
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
		case R.id.save:
			llTerms.save();
			Toast.makeText(getApplicationContext(), "Your explore classes were saved with success!", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.restore:
			if(llTerms.load()==-1) {
				Toast.makeText(getApplicationContext(), "Your explore classes were never saved! Showing your taken classes.", Toast.LENGTH_SHORT).show();
				llTerms.reset_dbu();
			}
			else {
				llTerms.reset_dbu();
			}
			return true;
		case R.id.reset:
			llTerms.reset_dbu();
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
				semesterPos = up.getTermsS().indexOf(data.getStringExtra("CHOSENSEMESTER"));
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
		boolean missing;
		private LinearLayout llMain;
		private Context context;
		private ArrayList<View> vChilds;
		ArrayList<ArrayList<Class>> savedClasses = new ArrayList<ArrayList<Class>>();
		private ArrayList<ExploreClassesArrayAdapter> ecAdapters;
		private ArrayList<ListView> lists;
		private ArrayList<TextView> tvTerms;
		private ArrayList<ArrayList<String>> sCourses;
		private ArrayList<ArrayList<Class>> sClasses;
		private ArrayList<String> sTerms;
		private ArrayList<Class> takenClasses;
		ArrayList<Class> missingprereqs;

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
			missingprereqs = new ArrayList<Class>();
			
		}

		public boolean isEmpty(int semesterPos, int Pos) {
			return sCourses.get(semesterPos).get(Pos).isEmpty();
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

		public void reset_dbu() {
			llMain.removeAllViewsInLayout();
			clearAll();
			this.tvTerms = new ArrayList<TextView>();
			this.lists = new ArrayList<ListView>();
			this.vChilds = new ArrayList<View>();
			this.ecAdapters = new ArrayList<ExploreClassesArrayAdapter>();
			this.sCourses = new ArrayList<ArrayList<String>>();
			this.sClasses = new ArrayList<ArrayList<Class>>();
			inflate_dbu();
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

				for(int j=0;j<4;j++) {
					sClasses.get(i).add(new Class());
					sCourses.get(i).add("");
				}

				ecAdapters.get(i).notifyDataSetChanged();
			}
		}

		public void save() {
			ArrayList<String> ids = new ArrayList<String>();
			ArrayList<String> semesters = new ArrayList<String>();
			ArrayList<String> tmpids = new ArrayList<String>();
			savedClasses = new ArrayList<ArrayList<Class>>();


			for(int i=0; i<sClasses.size(); i++) {
				tmpids.clear();
				semesters.add(String.valueOf(i));

				savedClasses.add(new ArrayList<Class>());
				for(int j=0; j<sClasses.get(i).size(); j++) {
					savedClasses.get(i).add(sClasses.get(i).get(j));
					tmpids.add(String.valueOf(sClasses.get(i).get(j).getID()));
				}
				ids.add(SerializeArray(tmpids));
			}
			up.setExploreSavedClasses(ids, semesters);
			Log.d("IDS", ids.toString());
			up.save();

		}

		public int load() {
			up.load();
			Log.d("explore crashing", "maybe here?");
			ArrayList<String> ids = new ArrayList<String>();
			ArrayList<String> semesters = new ArrayList<String>();
			ArrayList<String> tmpids = new ArrayList<String>();
			savedClasses = new ArrayList<ArrayList<Class>>();

			semesters.addAll(up.getExploreSavedClassesSemesters());
			if(semesters.size()==0) { return -1;}
			Log.d("explore crashing", "or here?"+semesters.size());

			tmpids.addAll(up.getExploreSavedClassesIDs());

			for(int i = 0; i<semesters.size(); i++) {
				ids.clear();
				ids = ParseArray(tmpids.get(i));
				savedClasses.add(new ArrayList<Class>());
				for(int j=0; j<ids.size();j++) {
					savedClasses.get(i).add(db.getClass(Integer.parseInt(ids.get(j))));
				}

			}
			return 0;

		}

		public void inflate() {

			for(int i = 0; i<up.getTermsS().size(); i++) {
				String termS = up.getTermsS().get(i);
				String termL = up.getTermsL().get(i);

				sCourses.add(new ArrayList<String>());
				sClasses.add(new ArrayList<Class>());


				for(Class c : savedClasses.get(i)) {
					if(c.getMajorN()==null) {continue;}
					sClasses.get(i).add(c);
					sCourses.get(i).add(c.getMajorN()+"."+c.getClassN()+" "+c.getTitle());
				}



				for(int j=0;j<8-sCourses.get(i).size();j++) {
					sClasses.get(i).add(new Class());
					sCourses.get(i).add("");
				}

				sTerms.add(termL);

				vChilds.add(View.inflate(context, R.layout.linear_explore_classes, null));

				if(getResources().getConfiguration().orientation==getResources().getConfiguration().ORIENTATION_LANDSCAPE) { //if landscape
					lists.add((ListView)vChilds.get(i).findViewById(R.id.list));
					tvTerms.add((TextView) vChilds.get(i).findViewById(R.id.tvTerm));
				}
				if(getResources().getConfiguration().orientation==getResources().getConfiguration().ORIENTATION_PORTRAIT) { //if portrait
					lists.add((ListView)vChilds.get(i).findViewById(R.id.fall));
					tvTerms.add((TextView) vChilds.get(i).findViewById(R.id.tvfall));

					lists.add((ListView)vChilds.get(i).findViewById(R.id.spring));					
					tvTerms.add((TextView) vChilds.get(i).findViewById(R.id.tvspring));
				}

				lists.get(i).setId(i);

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
						if(sClasses.get(semester).get(position)!=null) {
							
							missingprereqs.clear();
							
							llmissingprereqs.removeAllViews();
							
							TextView tvSelected = (TextView) view.findViewById(R.id.label);
							if(semester==lastSelectedSemester&&position==lastSelectedPos) {
								highlightClearAll();
								lastSelectedSemester=lastSelectedPos=-1;

							}
							else {



								highlightPreReqsFrom(sClasses.get(semester).get(position));
								if(missing==false) {
								tvSelected.setTextAppearance(context, R.style.List_item_prereq_nonmissing_selected);
								}
								if(missing==true) {
									tvSelected.setTextAppearance(context, R.style.List_item_prereq_missing_selected);
								}
								lastSelectedPos=position;
								lastSelectedSemester=semester;
							}

						}

					}
				});
			}
		}

		public void inflate_dbu() {

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

				}
				Log.d("HERE?", ""+sCourses.get(i).size());
				for(int j=0;j<8-sCourses.get(i).size();j++) {
					sClasses.get(i).add(new Class());
					sCourses.get(i).add("");
				}

				sTerms.add(termL);

				vChilds.add(View.inflate(context, R.layout.linear_explore_classes, null));

				if(getResources().getConfiguration().orientation==getResources().getConfiguration().ORIENTATION_LANDSCAPE) { //if landscape
					lists.add((ListView)vChilds.get(i).findViewById(R.id.list));
					tvTerms.add((TextView) vChilds.get(i).findViewById(R.id.tvTerm));
				}
				if(getResources().getConfiguration().orientation==getResources().getConfiguration().ORIENTATION_PORTRAIT) { //if portrait
					lists.add((ListView)vChilds.get(i).findViewById(R.id.fall));
					tvTerms.add((TextView) vChilds.get(i).findViewById(R.id.tvfall));

					lists.add((ListView)vChilds.get(i).findViewById(R.id.spring));					
					tvTerms.add((TextView) vChilds.get(i).findViewById(R.id.tvspring));
				}

				lists.get(i).setId(i);

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
						Log.d("removeallviews","here1");
						missingprereqs.clear();
						llmissingprereqs.removeAllViews();


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
								if(missing==false) {
									tvSelected.setTextAppearance(context, R.style.List_item_prereq_nonmissing_selected);
									}
									if(missing==true) {
										tvSelected.setTextAppearance(context, R.style.List_item_prereq_missing_selected);
									}
								
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
			missingprereqs = new ArrayList<Class>();
			ArrayList<Class> prereqs = new ArrayList<Class>();
			prereqs.addAll(db.getAllPrereqs(c.getID()));
			highlightClearAll();

			ArrayList<Class> missingprereqstmp = new ArrayList<Class>();
			
			missingprereqstmp.addAll(prereqs);

			int prereqsmissingN = prereqs.size();

			for(Class p : prereqs) {
				//if(p.getTitle()==null) { prereqsmissingN--; continue; }
				//Log.d("P",p.getMajorN()+"."+p.getClassN()+" "+p.getTitle());
				for(int semester=0; semester<sTerms.size(); semester++) {

					for(int pos=0; pos<sClasses.get(semester).size(); pos++) {
						Class ac = sClasses.get(semester).get(pos);
						if(ac.getID()==p.getID()&&ac.getTitle()!=null) {
							missingprereqstmp.remove(p);
						}
					}
				}
			}
			for(Class ct : missingprereqstmp) {
				if(ct.getMajorN()!=null) { missingprereqs.add(ct); }
			}
			if(missingprereqs.size()>0) {
				missing=true;
			} else {
				missing=false;
			}
			
			for(Class p : prereqs) {
				if(p.getTitle()==null) { prereqsmissingN--; continue; }
				Log.d("P",p.getMajorN()+"."+p.getClassN()+" "+p.getTitle());
				for(int semester=0; semester<sTerms.size(); semester++) {

					for(int pos=0; pos<sClasses.get(semester).size(); pos++) {
						Class ac = sClasses.get(semester).get(pos);
						if(ac.getID()==p.getID()&&ac.getTitle()!=null) {
							prereqsmissingN--;
							TextView tv = (TextView) lists.get(semester).getChildAt(pos).findViewById(R.id.label);
							if(missing==false) {
								tv.setTextAppearance(context, R.style.List_item_prereq_nonmissing);
								}
								if(missing==true) {
									tv.setTextAppearance(context, R.style.List_item_prereq_missing);
								}
						}

					}
				}

			}
			
			//Log.d("prereqsmissing",""missingprereqs.);
			
			
			for(int i=0; i<missingprereqs.size();i++) {
				LinearLayout llMissing = (LinearLayout) getLayoutInflater().inflate(R.layout.list_explore_missingprereqs, null);
				TextView tvmissing = (TextView) llMissing.findViewById(R.id.tvMissingprereqs);
				tvmissing.setText(missingprereqs.get(i).getMajorN()+"."+missingprereqs.get(i).getClassN());
				llmissingprereqs.addView(llMissing);//,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
			}

		}
		public void highlightClearAll() {
			missingprereqs.clear();
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

	public static String SerializeArray(ArrayList<String> array) {
		String strArr = "";
		for (int i=0; i<array.size(); i++) {
			strArr += array.get(i) + "~";
		}
		strArr = strArr.substring(0, strArr.length() -1); // get rid of last comma
		return strArr;
	}

	public static ArrayList<String> ParseArray(String str) {

		String[] strArr = str.split("~");
		ArrayList<String> array = new ArrayList<String>();
		for (int i=0; i<strArr.length; i++) {
			array.add(strArr[i]);
		}
		return array; 
	}

}
