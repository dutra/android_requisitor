package com.dds.requisitor;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDatabaseHandler extends SQLiteOpenHelper {
	private Context context;
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "userClasses.db";
	private static final String TABLE_USERCLASSES = "userClasses";
	
	// Classes Table Columns names
	private static final String KEY_ID = "id";
		private static final String KEY_TAKENIN = "takenIn";

	public UserDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USERCLASSES
				+ " (" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_TAKENIN + " TEXT)";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERCLASSES);

		// Create tables again
		onCreate(db);

	}

	// Methods for general course table

	public void addClass(Class c) { // adding a single class
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, c.getID());
		values.put(KEY_TAKENIN, c.getTakenIn());

		/*
		 * values.put(KEY_ID, 1234567890); values.put(KEY_MAJORN, "123");
		 * values.put(KEY_CLASSN, "123"); values.put(KEY_TITLE, "123");
		 * values.put(KEY_UNITS, 123); values.put(KEY_DESCRIPTION, "123");
		 * values.put(KEY_FALL, 1); values.put(KEY_SPRING, 0);
		 */
		// Inserting Row
		db.insert(TABLE_USERCLASSES, null, values);
		db.close(); // Closing database connection
	}

	public void addClasses(ArrayList<Class> classes) {
		for (Class c : classes)
			addClass(c);

	}

	public Class getClass(int id) { // Getting single class by id
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_USERCLASSES, new String[] { KEY_ID, KEY_TAKENIN }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();

			Class c = new Class(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
			return c;
		} // return class
		return null;
	}

	public ArrayList<Class> getClasses(ArrayList<Integer> ids) { // Getting
																	// multiple
																	// classes
		ArrayList<Class> classes = new ArrayList<Class>();
		for (int i : ids) {
			classes.add(getClass(i));
		}
		return postProcess(classes);
	}

	public ArrayList<Class> getClassesBySemester(String semester) {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Class> classes = new ArrayList<Class>();

		Cursor cursor = db.query(TABLE_USERCLASSES, new String[] { KEY_ID,
				KEY_TAKENIN }, KEY_TAKENIN + "=?",
				new String[] { semester }, null, null, null, null);
		if(cursor.moveToFirst()==true){
			do {
			Class c = new Class(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
			classes.add(c);
			//Log.d("CC", "ccc"+postProcess(c).size());
			
			} while(cursor.moveToNext() == true); 
			
			return postProcess(classes);
			
		}
		return new ArrayList<Class>();
	}

	public ArrayList<Class> getClassesBySemesters(ArrayList<String> semesters) { // Getting
		// multiple
		// classes
		ArrayList<Class> classes = new ArrayList<Class>();
		for (String s : semesters) {
			
			classes.addAll(getClassesBySemester(s));
			
		}
		
		
		return classes;
	}

	public int getCount() { // Getting classes Count
		String countQuery = "SELECT  * FROM " + TABLE_USERCLASSES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
		return cursor.getCount(); // return count
	}

	public void eraseAll() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_USERCLASSES, null, null);
		onCreate(db);
		db.close();
	}
	public Class postProcess(Class c) {
		Class f = new Class();
		
		DatabaseHandler db = new DatabaseHandler(context);
		
		f = db.getClass(c.getID());
		//Log.d("POSTPROCESS", "AAAA");
		f.setTakenIn(c.getTakenIn());
		
		return f;
	}
	public ArrayList<Class> postProcess(ArrayList<Class> classes) {
		ArrayList<Class> f = new ArrayList<Class>();
		for(Class c : classes) {
			f.add(postProcess(c));
		}
		return f;
	}

	public void erase(String semester) {
		String[] whereArg = new String[] { semester };
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_USERCLASSES, "KEY_TAKENIN=?", whereArg);
		onCreate(db);
		db.close();
	}

}
