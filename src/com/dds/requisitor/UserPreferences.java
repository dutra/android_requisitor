package com.dds.requisitor;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class UserPreferences {
	private static ArrayList<String> termsL;
	private static ArrayList<String> termsS;
	private static String name;
	private static int graduation;
	private static String grade;
	private static String courseN;
	private static String courseS;
	private static ArrayList<String> courseNall;
	private static ArrayList<String> courseSall;
	private static ArrayList<String> courseURLall;
	private static String currentTerm;
	private static Context context;

	UserPreferences(Context c) {
		
		currentTerm = "Spring 2012";
		courseNall = new ArrayList<String>();
		courseSall = new ArrayList<String>();
		courseURLall = new ArrayList<String>();
		courseNall.add("6");
		courseSall.add("Electrical Engineering and Computer Science");
		courseNall.add("8");
		courseSall.add("Physics");
		courseNall.add("7");
		courseSall.add("Biology");
		courseNall.add("14");
		courseSall.add("Economics");
		courseNall.add("18");
		courseSall.add("Math");
		context = c;
		
	}

	private void initializeTerms() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		if(grade.equals("Freshman")) {
			termsS = initializeTermsS(year);
			termsL = initializeTermsL(year);
		}
		if(grade.equals("Sophomore")) {
			termsS = initializeTermsS(year-1);
			termsL = initializeTermsL(year-1);
		}
		if(grade.equals("Junior")) {
			termsS = initializeTermsS(year-2);
			termsL = initializeTermsL(year-2);
		}
		if(grade.equals("Senior")) {
			termsS = initializeTermsS(year-3);
			termsL = initializeTermsL(year-3);
		}
	}
	private ArrayList<String> initializeTermsS(int initialyear) {
		ArrayList<String> termsS = new ArrayList<String>();
		for(int i=0; i<4; i++) {
			termsS.add(Integer.toString(initialyear+i)+"FA");
			termsS.add(Integer.toString(initialyear+i)+"SP");
		}
		return termsS;
	}
	private ArrayList<String> initializeTermsL(int initialyear) {
		ArrayList<String> termsL = new ArrayList<String>();
		for(int i=0; i<4; i++) {
			termsL.add("Fall"+Integer.toString(initialyear+i));
			termsL.add("Spring"+Integer.toString(initialyear+i));
		}
		return termsL;
	}

	public void setGraduation(int g) {
		graduation = g;
	}
	public int getGraduation() {
		return graduation;
	}
	public void setGrade(String g) {
		grade = g;
	}
	public String getGrade() {
		return grade;
	}
	public void setCourseN(String c) {
		courseN = c;
	}
	public String getCourseN() {
		return courseN;
	}
	public ArrayList<String> getcourseNall() {
		return courseNall;
	}
	public ArrayList<String> getcourseSall() {
		return courseSall;
	}
	public ArrayList<String> getcourseURLall() {
		return courseURLall;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String s) {
		name = s;
	}
	public void save() {
		SharedPreferences userDetails = context.getSharedPreferences("userdetails", context.MODE_PRIVATE);
		
		Editor edit = userDetails.edit();
		edit.clear();
		edit.putString("COURSEN", courseN);
		edit.putString("COURSES", courseS);
		edit.putString("GRADE", grade);
		
		
		edit.putString("COURSENALL", SerializeArray(courseNall));
		
		edit.putString("COURSESALL", SerializeArray(courseSall));
		initializeTerms();
		edit.commit();
	}
	public int load() {
		
		SharedPreferences userDetails = context.getSharedPreferences("userdetails", context.MODE_PRIVATE);
				
		courseN = userDetails.getString("COURSEN", null);
		if(courseN==null) return 1;
		courseS = userDetails.getString("COURSES", null);
		grade = userDetails.getString("GRADE", null);
		
		courseNall = ParseArray(userDetails.getString("COURSENALL", null));
		courseSall = ParseArray(userDetails.getString("COURSESALL", null));
					
	return 0;	
	}
	
	public static String SerializeArray(ArrayList<String> array) {
		String strArr = "";
        for (int i=0; i<array.size(); i++) {
            strArr += array.get(i) + ",";
        }
        strArr = strArr.substring(0, strArr.length() -1); // get rid of last comma
        return strArr;
    }

    public static ArrayList<String> ParseArray(String str) {

        String[] strArr = str.split(",");
        ArrayList<String> array = new ArrayList<String>();
        for (int i=0; i<strArr.length; i++) {
            array.add(strArr[i]);
        }
        return array; 
    }

}
