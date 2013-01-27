package com.dds.requisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private String _units;
	private String _description;
	private int _fall = 0;
	private int _spring = 0;
	private ArrayList<Integer> _prereq = new ArrayList<Integer>();
	private ArrayList<Integer> _coreq = new ArrayList<Integer>();

	public void parseClasses(String d) {

		_data = d;
		try {
			JSONObject mainJSON = new JSONObject(_data);
			JSONArray aj = mainJSON.optJSONArray("items");
			for (int i = 0; i < aj.length(); i++) {
				_fall = _spring = 0;
				if (!aj.getJSONObject(i).has("shortLabel"))
					continue;

				_id = aj.getJSONObject(i).getString("id").hashCode();
				_majorN = aj.getJSONObject(i).getString("id").split("\\.", 2)[0];
				_classN = aj.getJSONObject(i).getString("id").split("\\.", 2)[1];
				_title = aj.getJSONObject(i).getString("shortLabel");
				_units = aj.getJSONObject(i).getString("units");
				_description = aj.getJSONObject(i).getString("description");
				// _fallInstructor = aj.getJSONObject(i).getString(name)


				JSONArray semester = aj.getJSONObject(i).getJSONArray(
						"semester");
				for (int j = 0; j < semester.length(); j++) {
					if (_fall == 0)
						_fall = semester.getString(j).equals("Fall") ? 1 : 0;
					if (_spring == 0)
						_spring = semester.getString(j).equals("Spring") ? 1
								: 0;
				}

/*				String[] prereq = aj.getJSONObject(i).getString("prereqs")
						.split(", ");
				for (String s : prereq) {
					Log.d("PREREQ", s);
					_prereq.add(s.hashCode());
				}*/
				
				String prereqString = aj.getJSONObject(i).getString("prereqs");
				Pattern p = Pattern.compile("(\\d{1,2})(\\.)(\\d{2,3})");
				Matcher m = p.matcher(prereqString);
				_prereq.clear();
				while (m.find()) { // Find each match in turn.
					String match = m.group(); // Access a submatch group.
//					Log.d("PREREQ",match);
					_prereq.add(match.hashCode());
				}
//				Log.d("PREREQ_COUNT",Integer.toString(_prereq.size()));

				Class _c = new Class();
				_c.setClass(_id, _majorN, _classN, _title, _units,
						_description, _fall, _spring);
				_c.setPrereqid(_prereq);
//				Log.d("SIZE",""+_c.getPrereqid().size());
				_classes.add(_c);
//				Log.d("PARSER", _title + " ID: " + _id + " " + _fall + _spring + " Units: " + _units + _prereq);

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