package com.dds.requisitor;

import java.util.ArrayList;

public class Class {

	public static class session {
		String where;
		String when;
		
		public session(String s) {
			where = s.split(" ", 2)[1];
			when = s.split(" ", 2)[0];
		}
		
	}

	public static class units {
		private int _lec;
		private int _lab;
		private int _hw;

		public units(int i) {
			_lec = i>>8;
			_lab = i>>4&15;
			_hw = i&15;
		}
		public units(String s) {
			_lec = Integer.parseInt(s.split("-", 3)[0]);
			_lab = Integer.parseInt(s.split("-", 3)[1]);
			_hw = Integer.parseInt(s.split("-", 3)[2]);		
		}
		@Override public String toString() {
			return _lec+"-"+_lab+"-"+_hw;
		}
		public int getN() {
			return  (_lec<<8)+(_lab<<4)+(_hw);
		}
		public void setN(int i) {
			_lec = i>>8;
			_lab = i>>4&15;
			_hw = i&15;
		}

	}
	private int _id;
	private String _majorN;
	private String _classN;
	private String _title;
	private units _units;
	private String _description;
	private session _session;
	private int _fall;
	private int _spring;
	private String _takenIn;

	private ArrayList<Integer> _prereq = new ArrayList<Integer>();
	private ArrayList<Integer> _cored = new ArrayList<Integer>();


	public Class(int id,  String majorN, String classN, String title, int unitsN, String description, int fall, int spring) {
		_id = id;
		_majorN = majorN;
		_classN = classN;
		_title = title;
		_units = new units(unitsN);
		_description = description;
		_fall = fall;
		_spring = spring;


	}
	
	public Class(int id,  String majorN, String classN, String title, int unitsN, String description, int fall, int spring, String takenIn) {
		_id = id;
		_majorN = majorN;
		_classN = classN;
		_title = title;
		_units = new units(unitsN);
		_description = description;
		_fall = fall;
		_spring = spring;
		_takenIn = takenIn;


	}
	
	public Class(int id, String takenIn) {
		_id = id;
		_takenIn = takenIn;


	}
	
	public Class() {
	
	}

	public void setClass(int id,  String majorN, String classN, String title, int unitsN, String description, int fall, int spring) {
		_id = id;
		_majorN = majorN;
		_classN = classN;
		_title = title;
		_units = new units(unitsN);
		_description = description;
		_fall = fall;
		_spring = spring;


	}
	
	public void setClass(int id,  String majorN, String classN, String title, int unitsN, String description, int fall, int spring, String takenIn) {
		_id = id;
		_majorN = majorN;
		_classN = classN;
		_title = title;
		_units = new units(unitsN);
		_description = description;
		_fall = fall;
		_spring = spring;
		_takenIn = takenIn;


	}
	
	public int getID() {
		return _id;
	}
	public String getMajorN() {
		return _majorN;
	}
	public String getClassN() {
		return _classN;
	}
	public String getTitle() {
		return _title;
	}
	public int getUnits() {
		return _units.getN();
	}
	public String getDescription() {
		return _description;
	}

	public int getFall() {
		return _fall;
	}
	public int getSpring() {
		return _spring;
	}
	public String getTakenIn() {
		return _takenIn;
	}
	public void setTakenIn(String takenIn) {
		this._takenIn = takenIn;
	}


}
