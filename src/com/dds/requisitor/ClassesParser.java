package com.dds.requisitor;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import com.dds.requisitor.Class;


public class ClassesParser {

	private String _data;

	private int _id;
	private String _majorN;
	private String _classN;
	private String _title;
	private Class.units _units;
	private String _description;
	private String _lecwhere;
	private String _lecwhen;
	private String _labwhere;
	private String _labwhen;
	private int _fall=0;
	private int _spring=0;
	private String _instructor;
	private ArrayList<Integer> _prereq = new ArrayList<Integer>();
	private ArrayList<Integer> _cored = new ArrayList<Integer>();

	public void parseClasses(String d) {
		_data = d;
		try {
			JSONObject mainJSON = new JSONObject(_data);
			JSONArray aj = mainJSON.optJSONArray("items");
			for(int i=0; i < aj.length(); i++) {
				if(!aj.getJSONObject(i).has("shortLabel")) continue;
				
				_id = aj.getJSONObject(i).getString("id").hashCode();
				_title = aj.getJSONObject(i).getString("shortLabel");
				_units = new Class.units(aj.getJSONObject(i).getString("units"));

				_description = aj.getJSONObject(i).getString("description");
				
				JSONArray semester = aj.getJSONObject(i).getJSONArray("semester");
				for(int j=0; j < semester.length(); j++) {
					if(_fall==0) _fall = semester.getString(j).equals("Fall")? 1 : 0;
					if(_spring==0)_spring = semester.getString(j).equals("Spring")? 1 : 0;
				}
				
				Log.d("PARSER", _title+" ID: "+_id+" "+_fall+_spring+" Units: "+_units);
				_fall=_spring=0;
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}