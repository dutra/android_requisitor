package com.dds.requisitor;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "userClasses.db";
	private static final String TABLE_USERCLASSES = "userClasses";

	// Classes Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_MAJORN = "majorn";
	private static final String KEY_CLASSN = "classn";
	private static final String KEY_TAKENIN = "takenIn";

	public UserDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USERCLASSES
				+ " (" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_MAJORN
				+ " TEXT, " + KEY_CLASSN + " TEXT, " + KEY_TAKENIN + " TEXT)";
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
		values.put(KEY_MAJORN, c.getMajorN());
		values.put(KEY_CLASSN, c.getClassN());
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

		Cursor cursor = db.query(TABLE_USERCLASSES, new String[] { KEY_ID,
				KEY_MAJORN, KEY_CLASSN, KEY_TAKENIN }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();

			Class c = new Class(Integer.parseInt(cursor.getString(0)),
					cursor.getString(1), cursor.getString(2),
					cursor.getString(3));
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
		return classes;
	}

	public ArrayList<Class> getClassesBySemester(String semester) {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Class> classes = new ArrayList<Class>();

		Cursor cursor = db.query(TABLE_USERCLASSES, new String[] { KEY_ID,
				KEY_MAJORN, KEY_CLASSN, KEY_TAKENIN }, KEY_TAKENIN + "=?",
				new String[] { semester }, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();

			Class c = new Class(Integer.parseInt(cursor.getString(0)),
					cursor.getString(1), cursor.getString(2),
					cursor.getString(3), Integer.parseInt(cursor.getString(4)),
					cursor.getString(5), Integer.parseInt(cursor.getString(6)),
					Integer.parseInt(cursor.getString(7)), cursor.getString(8));
			classes.add(c);
			if (!cursor.moveToFirst() == true) {
				return classes;
			}
			;
		}
		return null;
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

	public void erase(String semester) {
		String[] whereArg = new String[] { semester };
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_USERCLASSES, "KEY_TAKENIN=?", whereArg);
		onCreate(db);
		db.close();
	}

}
