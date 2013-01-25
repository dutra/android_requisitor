package com.dds.requisitor;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListClassesAdapter extends ArrayAdapter<Class> {
	private final Context context;
	private final ArrayList<Class> classes;
	
	
	public ListClassesAdapter(Context context, ArrayList<Class> c) {

		super(context, R.layout.list_classes_adapter_row_layout, c);
		this.context = context;
		this.classes = c;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_classes_adapter_row_layout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.course);
	    textView.setText(classes.get(position).getTitle());
	    
		return rowView;
	}
} 

