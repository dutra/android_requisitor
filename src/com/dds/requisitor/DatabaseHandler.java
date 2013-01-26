package com.dds.requisitor;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "classes.db";
	private static final String TABLE_CLASSES = "classes";

	// Classes Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_MAJORN = "majorn";
	private static final String KEY_CLASSN = "classn";
	private static final String KEY_TITLE = "title";
	private static final String KEY_UNITS = "units";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_FALL = "fall";
	private static final String KEY_SPRING = "spring";



	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE "+TABLE_CLASSES+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_MAJORN+" TEXT, "+KEY_CLASSN+" TEXT, "+KEY_TITLE
				+" TEXT, "+KEY_UNITS+" INT, "+KEY_DESCRIPTION+" TEXT, "+KEY_FALL+" INT, "+KEY_SPRING+" INT)";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSES);

		// Create tables again
		onCreate(db);

	}

	public void addClass(Class c) { // adding a single class
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, c.getID());
		values.put(KEY_MAJORN, c.getMajorN());
		values.put(KEY_CLASSN, c.getClassN());
		values.put(KEY_TITLE, c.getTitle());
		values.put(KEY_UNITS, c.getUnits());
		values.put(KEY_DESCRIPTION, c.getDescription());
		values.put(KEY_FALL, c.getFall());
		values.put(KEY_SPRING, c.getSpring());

		/*		values.put(KEY_ID, 1234567890);
		values.put(KEY_MAJORN, "123");
		values.put(KEY_CLASSN, "123");
		values.put(KEY_TITLE, "123");
		values.put(KEY_UNITS, 123);
		values.put(KEY_DESCRIPTION, "123");
		values.put(KEY_FALL, 1);
		values.put(KEY_SPRING, 0);
		 */
		// Inserting Row
		db.insert(TABLE_CLASSES, null, values);
		db.close(); // Closing database connection
	}

	public void addClasses(ArrayList<Class> classes) {
		for(Class c : classes)
			addClass(c);

	}


	public Class getClass(int id) {  // Getting single class by id
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CLASSES, new String[] { KEY_ID,
				KEY_MAJORN, KEY_CLASSN, KEY_TITLE, KEY_UNITS, KEY_DESCRIPTION, KEY_FALL, KEY_SPRING},
				KEY_ID + "=?",	new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor.moveToFirst()) {


			Class c = new Class(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)),
					cursor.getString(5), Integer.parseInt(cursor.getString(6)),
					Integer.parseInt(cursor.getString(7)));
			db.close();
			return c;		// return class
		}
		db.close();
		return new Class();

	}

	public ArrayList<Class> getClasses(ArrayList<Integer> ids) { // Getting multiple classes
		ArrayList<Class> classes = new ArrayList<Class>();
		for(int i : ids) {
			classes.add(getClass(i));
		}
		return classes;
	}

	public ArrayList<Class> getClassesByInitial(String search){
		String[] splitSearch = search.split("\\.");
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Class> classes = new ArrayList<Class>();
		Cursor cursor = null;
		String majorN = new String();
		String classN = new String();
		
		if(splitSearch.length==1) {
			majorN = splitSearch[0];
			cursor = db.query(TABLE_CLASSES, new String[] { KEY_ID,
				KEY_MAJORN, KEY_CLASSN, KEY_TITLE, KEY_UNITS, KEY_DESCRIPTION, KEY_FALL, KEY_SPRING},
					KEY_MAJORN + " =?",	new String[] { majorN }, null, null, null, null);

		}
		if(splitSearch.length==2) {
			majorN = splitSearch[0];
			classN = splitSearch[1];
			Log.d("LIKE", majorN+" "+classN);
			cursor = db.query(TABLE_CLASSES, new String[] { KEY_ID,
					KEY_MAJORN, KEY_CLASSN, KEY_TITLE, KEY_UNITS, KEY_DESCRIPTION, KEY_FALL, KEY_SPRING},
					KEY_MAJORN + " =? AND " + KEY_CLASSN + " LIKE ?",	new String[] { majorN, classN.toString()+"%" }, null, null, null, null);
		}


		if(cursor.moveToFirst()==true){
			do {
				Class c = new Class(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)),
						cursor.getString(5), Integer.parseInt(cursor.getString(6)),
						Integer.parseInt(cursor.getString(7)));
				classes.add(c);

			} while(cursor.moveToNext() == true); 
			db.close();
			return classes;

		}
		db.close();
		return classes;
	}

	public int getCount() { // Getting classes Count
		String countQuery = "SELECT  * FROM " + TABLE_CLASSES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int i = cursor.getCount();
		cursor.close();
		db.close();
		return i; // return count

	}
	public void eraseAll() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CLASSES, null, null);
		//onCreate(db);
		db.close();
	}

}
