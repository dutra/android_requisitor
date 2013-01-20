package com.dds.requisitor;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
	private static final String KEY_LECWHERE = "lecwhere";
	private static final String KEY_LECWHEN = "lecwhen";
	private static final String KEY_LABWHERE = "labwhere";
	private static final String KEY_LABWHEN = "labwhen";
	private static final String KEY_FALL = "fall";
	private static final String KEY_SPRING = "spring";
	private static final String KEY_INSTRUCTOR = "instructor";


	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE "+TABLE_CLASSES+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_MAJORN+" TEXT, "+KEY_CLASSN+" TEXT, "+KEY_TITLE+" TEXT, "+KEY_UNITS+" INT, "+KEY_DESCRIPTION+" TEXT, "+KEY_LECWHERE+" TEXT, "+KEY_LECWHEN+" TEXT, "+KEY_LABWHERE+" TEXT, "+KEY_LABWHEN+" TEXT, "+KEY_FALL+" INT, "+KEY_SPRING+" INT, "+KEY_INSTRUCTOR+" TEXT)";
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
		values.put(KEY_MAJORN, c.getMajorN()); // Contact Phone Number
		values.put(KEY_CLASSN, c.getClassN());
		values.put(KEY_TITLE, c.getTitle());
		values.put(KEY_UNITS, c.getUnits());
		values.put(KEY_DESCRIPTION, c.getDescription());
		values.put(KEY_LECWHERE, c.getLecWhere());
		values.put(KEY_LECWHEN, c.getLecWhen());
		values.put(KEY_LABWHERE, c.getLabWhere());
		values.put(KEY_LABWHEN, c.getLabWhen());
		values.put(KEY_FALL, c.getFall());
		values.put(KEY_SPRING, c.getSpring());
		values.put(KEY_INSTRUCTOR, c.getInstructor());
		// Inserting Row
		db.insert(TABLE_CLASSES, null, values);
		db.close(); // Closing database connection
	}

	public Class getClass(int id) {  // Getting single class by id
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CLASSES, new String[] { KEY_ID,
				KEY_MAJORN, KEY_CLASSN, KEY_TITLE, KEY_UNITS, KEY_DESCRIPTION, KEY_LECWHERE, KEY_LECWHEN, KEY_LABWHERE, KEY_LABWHEN, KEY_FALL, KEY_SPRING, KEY_INSTRUCTOR },
				KEY_ID + "=?",	new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Class c = new Class(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)),
				cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), Integer.parseInt(cursor.getString(10)),
				Integer.parseInt(cursor.getString(11)), cursor.getString(12));
		return c;		// return class
	}

	public ArrayList<Class> getClasses(ArrayList<Integer> ids) { // Getting multiple classes
		ArrayList<Class> classes = new ArrayList<Class>();
		for(int i : ids) {
			classes.add(getClass(i));
		}
		return classes;
	}
	
    public int getCount() { // Getting classes Count
        String countQuery = "SELECT  * FROM " + TABLE_CLASSES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount(); // return count
    }
    public void eraseAll() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_CLASSES, null, null);
    	onCreate(db);
    	db.close();
    }
    
}
