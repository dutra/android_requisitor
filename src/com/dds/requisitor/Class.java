package com.dds.requisitor;

import java.util.ArrayList;

public class Class {

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
	private String _lecwhere;
	private String _lecwhen;
	private String _labwhere;
	private String _labwhen;
	private int _fall;
	private int _spring;
	private String _instructor;
	private ArrayList<Integer> _prereq = new ArrayList<Integer>();
	private ArrayList<Integer> _cored = new ArrayList<Integer>();


	public Class(int id,  String majorN, String classN, String title, int unitsN, String description, String lecwhere, String lecwhen, String labwhere, String labwhen, int fall, int spring,  String instructor) {
		_id = id;
		_majorN = majorN;
		_classN = classN;
		_title = title;
		_units = new units(unitsN);
		_description = description;
		_lecwhere = lecwhere;
		_lecwhen = lecwhen;
		_labwhere = labwhere;
		_labwhen = labwhen;
		_fall = fall;
		_spring = spring;

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
	public String getLecWhere() {
		return _lecwhere;
	}
	public String getLecWhen() {
		return _lecwhen;
	}
	public String getLabWhere() {
		return _labwhere;
	}
	public String getLabWhen() {
		return _labwhen;
	}
	public int getFall() {
		return _fall;
	}
	public int getSpring() {
		return _spring;
	}
	public String getInstructor() {
		return _instructor;
	}

}
