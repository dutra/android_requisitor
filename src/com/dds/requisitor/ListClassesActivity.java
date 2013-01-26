package com.dds.requisitor;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ExpandableListView;

public class ListClassesActivity extends BaseMenuActivity {
	DatabaseHandler db = new DatabaseHandler(ListClassesActivity.this);
	UserDatabaseHandler dbU = new UserDatabaseHandler(ListClassesActivity.this);
	private ExpandableListView mExpandableList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ArrayList<String> terms = new ArrayList<String>();
		ArrayList<Class> classes = new ArrayList<Class>();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_classes);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			terms = extras.getStringArrayList("TERMS");
		}
		if (terms.isEmpty()) {
			finish();
		}
		//Toast.makeText(ListClassesActivity.this, terms.toString(), Toast.LENGTH_LONG).show();
		//Log.d("TERMS", terms.toString()+classes.size());
		
		classes = dbU.getClassesBySemesters(terms);
		
		if(classes.size()==0) {

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
		
	    mExpandableList = (ExpandableListView)findViewById(R.id.expandable_list);
	    
        ArrayList<ListClassesParent> arrayParents = new ArrayList<ListClassesParent>();
        ArrayList<String> arrayChildren;
        for(String t : terms) {
        	String term = new String();
        	arrayChildren = new ArrayList<String>();
        	if(t.equals("2012SP")) {term = "Spring 2012";}
        	if(t.equals("2012FA")) {term = "Fall 2012";}
        	if(t.equals("2013SP")) {term = "Spring 2013";}
        	
        	ListClassesParent parent = new ListClassesParent();
        	parent.setTitle(term);
        	for(Class c : classes) {
        		Log.d("c", ""+c.getID());
        		if(c.getTakenIn().equals(t))
        				arrayChildren.add(c.getTitle());
        	
        	}
        	parent.setArrayChildren(arrayChildren);
        	arrayParents.add(parent);
        }
        
        
/*        //here we set the parents and the children
        for (int i = 0; i < 10; i++){
            //for each "i" create a new Parent object to set the title and the children
            ListClassesParent parent = new ListClassesParent();
            parent.setTitle("Parent " + i);
            arrayChildren.add("Child " + i);
            parent.setArrayChildren(arrayChildren);
 
            //in this array we add the Parent object. We will use the arrayParents at the setAdapter
            arrayParents.add(parent);
        }
 */
        //sets the adapter that provides data to the list.
       mExpandableList.setAdapter(new ListClassesAdapter(ListClassesActivity.this,arrayParents));
 
    }
	

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_list_classes, menu);
		return true;
	}*/

}
