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

	}
	
//	ExpandablePanel panel = (ExpandablePanel)view.findViewById(R.id.foo);
//	panel.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
//	    public void onCollapse(View handle, View content) {
//	        Button btn = (Button)handle;
//	        btn.setText("More");
//	    }
//	    public void onExpand(View handle, View content) {
//	        Button btn = (Button)handle;
//	        btn.setText("Less");
//	    }
//	});
	
	
	

}
