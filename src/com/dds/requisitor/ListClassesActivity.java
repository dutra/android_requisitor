package com.dds.requisitor;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;

public class ListClassesActivity extends BaseMenuActivity {
	int replaceID=-1;
	DatabaseHandler db = new DatabaseHandler(ListClassesActivity.this);
	UserDatabaseHandler dbU = new UserDatabaseHandler(ListClassesActivity.this);
	private ExpandableListView mExpandableList;
	UserPreferences up = new UserPreferences(ListClassesActivity.this);
	ArrayList<String> terms = new ArrayList<String>();
	ListClassesAdapter adapter;
	ArrayList<ListClassesParent> arrayParents;
	ArrayList<String> arrayChildren;
	ArrayList<Class> classes;
	ArrayList<String> mSelectedItems;
	public void refresh() {
		inflate();
		adapter.notifyDataSetChanged();
		
	}
	public int inflate() {
		 classes.clear();
		//arrayChildren.clear();
		arrayParents.clear();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			terms = extras.getStringArrayList("TERMS");
		}
		if (terms.isEmpty()) {
			terms=up.getTermsS();
		}
		//Toast.makeText(ListClassesActivity.this, terms.toString(), Toast.LENGTH_LONG).show();
		//Log.d("TERMS", terms.toString()+classes.size());

		classes = dbU.getClassesBySemesters(terms);
		Log.d("classes size", classes.size()+"");
		if(classes.size()==0) {
			/*Log.d("classes size!!!!", classes.size()+"");
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
			return -1;*/
		
			for(String t : terms) {
			
			arrayChildren = new ArrayList<String>();
			//arrayChildren.add("aaaaa");
			ListClassesParent parent = new ListClassesParent();
			parent.setTitle(up.getTermsL().get(up.getTermsS().indexOf(t)));
			
			parent.setArrayChildren(arrayChildren);
			arrayParents.add(parent);
			
			}
			
			return 0;

		}
		
		 

		for(String t : terms) {

			
			arrayChildren = new ArrayList<String>();
			ListClassesParent parent = new ListClassesParent();
			parent.setTitle(up.getTermsL().get(up.getTermsS().indexOf(t)));
			for(Class c : classes) {
				Log.d("c", ""+c.getID());
				if(c.getTakenIn().equals(t))
					arrayChildren.add(c.getMajorN()+"."+c.getClassN()+" "+c.getTitle());

			}
			parent.setArrayChildren(arrayChildren);
			arrayParents.add(parent);
			
		}
		return 0;
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_list_classes);
		mExpandableList = (ExpandableListView)findViewById(R.id.expandable_list);
		arrayParents = new ArrayList<ListClassesParent>();
		
		classes = new ArrayList<Class>();
		if(inflate()==0) {
			adapter = new ListClassesAdapter(ListClassesActivity.this,arrayParents);
		mExpandableList.setAdapter(adapter);
		//Log.d("BUG????", "HERE???");
		//sets the adapter that provides data to the list.
		
		}

	}

	public boolean onContextItemSelected(MenuItem menuItem) {
		Intent i;
		ExpandableListContextMenuInfo info =
				(ExpandableListContextMenuInfo) menuItem.getMenuInfo();
		int semesterPos = 0, pos = 0;
		int type = ExpandableListView.getPackedPositionType(info.packedPosition);
		if(type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
			semesterPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
		}
		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			semesterPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
			pos = ExpandableListView.getPackedPositionChild(info.packedPosition);
		}
		//Pull values from the array we built when we created the list

		switch (menuItem.getItemId()) {
		
		case R.id.add:
			i = new Intent(ListClassesActivity.this, SearchClassActivity.class);
			i.putExtra("FIND", 1);
			i.putExtra("SEMESTER", terms.get(semesterPos));
			startActivityForResult(i,1);
			//Log.d("ADD",""+semester+pos);
			return true;
		case R.id.replace:
			return true;
		case R.id.delete :
			return true;

		default:
			return super.onContextItemSelected(menuItem);
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if(resultCode == RESULT_OK){

				int id = data.getIntExtra("ID",0);
				String chosenSemester = data.getStringExtra("CHOSENSEMESTER");
				if (replaceID>-1) {
					dbU.delete(replaceID);
					replaceID=-1;
				}
				
					Class c = db.getClass(id);
					c.setTakenIn(chosenSemester);
					dbU.addClass(db.getClass(id));
					refresh();
				
				
				Log.d("ID returned", ""+id+chosenSemester);

			}
		}
	}

	public void onCreateContextMenu(ContextMenu menu,View v,ContextMenuInfo info)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_explore_classes_context_menu, menu);
	}

	/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_list_classes, menu);
		return true;
	}*/
	public void makeTermDialog() {
		mSelectedItems = new ArrayList<String>();

		AlertDialog.Builder builder = new AlertDialog.Builder(ListClassesActivity.this);
		builder.setTitle("List Courses");

		builder.setMultiChoiceItems(
				up.getTermsL().toArray(new String[up.getTermsL().size()]),
				null, new DialogInterface.OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {
						// String buffer = new String();
						if (isChecked) {
							// buffer = semesterList.get(which);
							// If the user checked the item, add it to the
							// selected items
							mSelectedItems.add(up.getTermsS().get(which)
									.toString());
						} else if (mSelectedItems.contains(up.getTermsS()
								.get(which).toString())) {
							// Else, if the item is already in the array, remove
							// it
							mSelectedItems.remove(up.getTermsS().get(which)
									.toString());
						}
					}
				});

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				if (mSelectedItems.size() == 0) {
					Toast.makeText(ListClassesActivity.this,
							"You should choose at least one semester",
							Toast.LENGTH_SHORT).show();
				} else {

				/*	Intent i = new Intent(MainActivity.this,
							ListClassesActivity.class);
					i.putStringArrayListExtra("TERMS", mSelectedItems);
					startActivity(i);*/
				}

			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});

		AlertDialog dialog = builder.create();
		dialog.show();

	}
}

