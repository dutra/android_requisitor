package com.dds.requisitor;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelpActivity extends BaseMenuActivity{
	
	private View view;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
//		ExpandablePanel panel = (ExpandablePanel)view.findViewById(R.id.panel_help);
////		panel.setContentView(R.layout.activity_help);
//		panel.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
//		    public void onCollapse(View handle, View content) {
//		        Button btn = (Button)handle;
//		        btn.setText("More");
//		    }
//		    public void onExpand(View handle, View content) {
//		        Button btn = (Button)handle;
//		        btn.setText("Less");
//		    }
//		});
		
		
		

		
		
		
		Resources res = getResources();
		TextView tv = (TextView) findViewById(R.id.tv1);
		String text = String.format("<h2>What is Requisitor?</h2><br></br><p>Requisitor is an app designed to help MIT students plan out and maintain their class schedules quickly and painlessly.</p>");
		CharSequence  styledText = Html.fromHtml(text);
		tv.setText(styledText);
		
		tv = (TextView) findViewById(R.id.tv2);
		text = String.format("<h2>What can I do on Requisitor?</h2><br></br><p>You can use Requisitor to explore classes available across departments, check pre-reqs, plan class trajectories throughout your MIT career, taking into account pre-reqs and offering availability. </p>");
		styledText = Html.fromHtml(text);
		tv.setText(styledText);
		
		tv = (TextView) findViewById(R.id.tv3);
		text = String.format("<h2>How do I use \"Search Classes\"?</h2><br></br><p> To find a class, limit your search by course, (partial) class number or keyword and scroll to browse. To input a class number or keyword to search, click on the button with a course number followed by a period (in position 1). A keyboard opens and the list updates with every input. To browse by course number or reset the list, click the button with just a course number (in position 2) and select a course from the drop down list. To see a class description, click on the class (as in position 3). To add a class, click on the class, select the term to add to from the drop down menu, and hit \"Add Class\".</p>");
		styledText = Html.fromHtml(text);
		tv.setText(styledText);
		
		
		tv = (TextView) findViewById(R.id.tv4);
		text = String.format("<h2>How can I see which classes I have added? </h2><br></br><p>All chosen classes can be seen by semester in either the “List Classes\" or the “Explore Classes\" View.</p>");
		styledText = Html.fromHtml(text);
		tv.setText(styledText);
		
		tv = (TextView) findViewById(R.id.tv5);
		text = String.format("<h2>How can I adjust the semesters in my List and Explore Views?</h2><br></br><p> The semesters available are based on a typical 4-year MIT career and are adjusted based on the year that you reported to be in. To change your year, go to Settings from the main menu.</p> ");
		styledText = Html.fromHtml(text);
		tv.setText(styledText);
		
		tv = (TextView) findViewById(R.id.tv6);
		text = String.format("<h2>How do I add classes that I have taken to the app? </h2><br></br><p> You can do this in three ways: \n Search: From the Search view, follow the directions above to add a class to any semester. \n List: From the List view, press on the semester you want to add to and hold for a second or two. A context menu as shown below opens. From there, press “Add Another Class\" and follow the Search directions. \n Explore: As in the List view, find the semester you want to add to in Explore view and click and hold to open an identical context menu.</p>");
		styledText = Html.fromHtml(text);
		tv.setText(styledText);
		
		
		tv = (TextView) findViewById(R.id.tv7);
		text = String.format("<h2>How do I remove classes that I have chosen?</h2><br></br><p> From either List or Explore view, click and hold the class you want to remove. From the context menu that opens, select “Remove\" or “Replace\". </p>");
		styledText = Html.fromHtml(text);
		tv.setText(styledText);
		
		tv = (TextView) findViewById(R.id.tv8);
		text = String.format("<h2>How can I see if I have the pre-reqs for a class I am trying to add? </h2><br></br><p> Enter the class into the semester you are considering it for in Explore view. If you are missing pre-reqs, the class will turn red upon selection and the missing pre-reqs will appear at the bottom of the screen. </p>");
		styledText = Html.fromHtml(text);
		tv.setText(styledText);
		
		tv = (TextView) findViewById(R.id.tv9);
		text = String.format("<h2>How can I move between views? </h2><br></br><p> rom any view, open a menu with navigation options by clicking the on the three dots on the bottom right of your screen (shown below).</p>");
		styledText = Html.fromHtml(text);
		tv.setText(styledText);
		
		
		
		
		
		
		ExpandablePanel panel1 = (ExpandablePanel) findViewById(R.id.ep1);
		panel1.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
		    public void onCollapse(View handle, View content) {
		        Button btn = (Button)handle;
		        btn.setText("More");
		    }
		    public void onExpand(View handle, View content) {
		        Button btn = (Button)handle;
		        btn.setText("Less");
		    }
		});
	
		ExpandablePanel panel2 = (ExpandablePanel) findViewById(R.id.ep2);
		panel2.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
		    public void onCollapse(View handle, View content) {
		        Button btn = (Button)handle;
		        btn.setText("More");
		    }
		    public void onExpand(View handle, View content) {
		        Button btn = (Button)handle;
		        btn.setText("Less");
		    }
		});
		
		ExpandablePanel panel3 = (ExpandablePanel) findViewById(R.id.ep3);
		panel2.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
		    public void onCollapse(View handle, View content) {
		        Button btn = (Button)handle;
		        btn.setText("More");
		    }
		    public void onExpand(View handle, View content) {
		        Button btn = (Button)handle;
		        btn.setText("Less");
		    }
		});
		
		/*ExpandablePanel panel4 = (ExpandablePanel) findViewById(R.id.ep4);
		panel2.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
		    public void onCollapse(View handle, View content) {
		        Button btn = (Button)handle;
		        btn.setText("More");
		    }
		    public void onExpand(View handle, View content) {
		        Button btn = (Button)handle;
		        btn.setText("Less");
		    }
		});*/
		
		ExpandablePanel panel5 = (ExpandablePanel) findViewById(R.id.ep5);
		panel2.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
		    public void onCollapse(View handle, View content) {
		        Button btn = (Button)handle;
		        btn.setText("More");
		    }
		    public void onExpand(View handle, View content) {
		        Button btn = (Button)handle;
		        btn.setText("Less");
		    }
		});
		
		ExpandablePanel panel6 = (ExpandablePanel) findViewById(R.id.ep6);
		panel2.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
		    public void onCollapse(View handle, View content) {
		        Button btn = (Button)handle;
		        btn.setText("More");
		    }
		    public void onExpand(View handle, View content) {
		        Button btn = (Button)handle;
		        btn.setText("Less");
		    }
		});
		
	/*	ExpandablePanel panel7 = (ExpandablePanel) findViewById(R.id.ep7);
		panel2.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
		    public void onCollapse(View handle, View content) {
		        Button btn = (Button)handle;
		        btn.setText("More");
		    }
		    public void onExpand(View handle, View content) {
		        Button btn = (Button)handle;
		        btn.setText("Less");
		    }
		});*/
		
		ExpandablePanel panel8 = (ExpandablePanel) findViewById(R.id.ep8);
		panel2.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
		    public void onCollapse(View handle, View content) {
		        Button btn = (Button)handle;
		        btn.setText("More");
		    }
		    public void onExpand(View handle, View content) {
		        Button btn = (Button)handle;
		        btn.setText("Less");
		    }
		});
		
		ExpandablePanel panel9 = (ExpandablePanel) findViewById(R.id.ep9);
		panel2.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
		    public void onCollapse(View handle, View content) {
		        Button btn = (Button)handle;
		        btn.setText("More");
		    }
		    public void onExpand(View handle, View content) {
		        Button btn = (Button)handle;
		        btn.setText("Less");
		    }
		});
		
	}
	

	
	
	

}
