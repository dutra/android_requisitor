package com.dds.requisitor;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import com.dds.requisitor.Class;


public class ClassesParser {

	private ArrayList<Class> _classes = new ArrayList<Class>();
	
	private String _data;
	
	private int _id;
	private String _majorN;
	private String _classN;
	private String _title;
	private Class.units _units;
	private String _description;
	private int _fall=0;
	private int _spring=0;
	private ArrayList<Integer> _prereq = new ArrayList<Integer>();
	private ArrayList<Integer> _cored = new ArrayList<Integer>();

	public void parseClasses(String d) {
		
		_data = d;
		try {
			JSONObject mainJSON = new JSONObject(_data);
			JSONArray aj = mainJSON.optJSONArray("items");
			for(int i=0; i < aj.length(); i++) {
				_fall=_spring=0;
				if(!aj.getJSONObject(i).has("shortLabel")) continue;
				
				_id = aj.getJSONObject(i).getString("id").hashCode();
				_title = aj.getJSONObject(i).getString("shortLabel");
				_units = new Class.units(aj.getJSONObject(i).getString("units"));
				_description = aj.getJSONObject(i).getString("description");
				//_fallInstructor = aj.getJSONObject(i).getString(name)
				
				JSONArray semester = aj.getJSONObject(i).getJSONArray("semester");
				for(int j=0; j < semester.length(); j++) {
					if(_fall==0) _fall = semester.getString(j).equals("Fall")? 1 : 0;
					if(_spring==0)_spring = semester.getString(j).equals("Spring")? 1 : 0;
				}
				
				String[] prereq = aj.getJSONObject(i).getString("prereqs").split(", ");
				for(String s : prereq) {
					Log.d("PREREQ", s);
					_prereq.add(s.hashCode());
				}
			
				Class _c = new Class();
				_c.setClass(_id, _majorN, _classN, _title, _units.getN(), _description, _fall, _spring); 
				_classes.add(_c);
				Log.d("PARSER", _title+" ID: "+_id+" "+_fall+_spring+" Units: "+_units);
				
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<Class> getClasses() {
		return _classes;
	}
}