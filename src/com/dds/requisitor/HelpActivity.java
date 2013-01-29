package com.dds.requisitor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
		
		ExpandablePanel panel4 = (ExpandablePanel) findViewById(R.id.ep4);
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
		
		ExpandablePanel panel7 = (ExpandablePanel) findViewById(R.id.ep7);
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
