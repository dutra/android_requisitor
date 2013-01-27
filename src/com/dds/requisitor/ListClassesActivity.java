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
	UserPreferences up = new UserPreferences(ListClassesActivity.this);
	
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
