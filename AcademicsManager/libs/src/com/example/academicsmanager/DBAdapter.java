// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.
package com.example.academicsmanager;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Html;
import android.util.Log;
import android.widget.BaseExpandableListAdapter;


// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

	/////////////////////////////////////////////////////////////////////
	/////////////////////	Constants & Data   //////////////////////////
	/////////////////////////////////////////////////////////////////////
	// For logging:
	private static final String TAG = "DBAdapter";
	
	///////// DB Fields
	////////////////////////////Todochecklist fields///////////////////////////////////////////////
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	public static final String KEY_NAME = "name";
	public static final int COL_NAME = 1;	
	public static final String KEY_STATUS = "status";
	public static final int COL_STATUS = 2;
	public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_NAME, KEY_STATUS};
	////////////////////////////Reminders fields//////////////////////////////////////////////////////
	public static final String KEY_AID = "_id";
	public static final int COL_AID = 0;
	public static final String KEY_ANAME = "name";
	public static final int COL_ANAME = 1;
	public static final String KEY_ATIME = "time";
	public static final int COL_ATIME = 2;	
	public static final String[] ALL_AKEYS = new String[] {KEY_AID, KEY_ANAME, KEY_ATIME};
	////////////////////////////Notes fields/////////////////////////////////////////////////////////////
	public static final String KEY_NID = "_id";
	public static final int COL_NID = 0;
	public static final String KEY_NTITLE = "title";
	public static final int COL_NTITLE = 1;
	public static final String KEY_NCONTENT = "content";
	public static final int COL_NCONTENT = 2;
	public static final String KEY_NSUBJECT = "subject";
	public static final int COL_NSUBJECT = 3;
	public static final String[] ALL_NKEYS = new String[] {KEY_NID, KEY_NTITLE, KEY_NCONTENT, KEY_NSUBJECT};
	
	///////// DB info: it's name, and the tables we are using.
	public static final String DATABASE_NAME = "MyDatabase";
	public static final String DATABASE_TABLE = "TodoTable";
	public static final String DATABASE_TABLE2 = "AlarmTable";
	public static final String DATABASE_TABLE3 = "NotesTable";
	
	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 6;	
	
	//Create tables
	private static final String DATABASE_CREATE_SQL = 
			"create table " + DATABASE_TABLE 
			+ " (" + KEY_ROWID + " integer primary key autoincrement, "			
			+ KEY_NAME + " text not null, "
            + KEY_STATUS + " integer "
			+ ");";
	private static final String DATABASE_CREATE_ATABLE = 
			"create table " + DATABASE_TABLE2
			+ " (" + KEY_AID + " integer primary key autoincrement, "
			+ KEY_ANAME + " text not null, "
			+ KEY_ATIME + " long not null"		
			+ ");" ;
	private static final String DATABASE_CREATE_NTABLE = 
			"create table " + DATABASE_TABLE3
			+ " (" + KEY_NID + " integer primary key autoincrement, "
			+ KEY_NTITLE + " text not null, "
			+ KEY_NCONTENT + " text not null,"
			+ KEY_NSUBJECT + " text not null"
			+ ");" ;
	
	// Context of application who uses us.
	private final Context context;
	
	
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	/////////////////////////////////////////////////////////////////////
	//	Public methods:
	/////////////////////////////////////////////////////////////////////
	
	public DBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	// Open the database connection.
	public DBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	//	To do checklist methods:
	//////////////////////////////////////////////////////////////////////////////////////
	
	// Add a new set of values to the database.
	public void insertTask(Task task) 
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, task.getTaskName());
		initialValues.put(KEY_STATUS, task.getStatus());
		db.insert(DATABASE_TABLE, null, initialValues);
	}
	//delete all tasks
	public void deleteAllTasks() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));				
			} while (c.moveToNext());
		}
		c.close();
	}
	//delete selected tasks
	public void deleteSelectedTasks() {
		Cursor c = getSelectedRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));				
			} while (c.moveToNext());
		}
		c.close();
	}
	// Return all data in the database.
		public Cursor getAllRows() {
			String where = null;
			Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
								where, null, null, null, null, null);
			if (c != null) {
				c.moveToFirst();
			}
			return c;
		}
	// Return selected data in the database.
		public Cursor getSelectedRows() {
				String where = KEY_STATUS + "= 1";
				Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
									where, null, null, null, null, null);
				if (c != null) {
					c.moveToFirst();
				}
				return c;
			}
	//get all tasks for displaying
		public List<Task> getAllTasks() {
			List<Task> taskList = new ArrayList<Task>();
			String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {
			do {
			Task task = new Task();
			task.setId(cursor.getInt(0));
			task.setTaskName(cursor.getString(1));
			task.setStatus(cursor.getInt(2));
			taskList.add(task);
			} while (cursor.moveToNext());
			}
			return taskList;
			}
	//update a single task
		public void updateTask(Task task) {
			ContentValues values = new ContentValues();
			values.put(KEY_NAME, task.getTaskName());
			values.put(KEY_STATUS, task.getStatus());
			db.update(DATABASE_TABLE, values, KEY_ROWID + " = ?",
			new String[]{String.valueOf(task.getId())});
			}
	//////////////////////////////////////////////////////////////////////////////////////////////////	
		
		
		
		//////////////////////////////////////////////////////////////////////////////
	    //alarm table methods
		//////////////////////////////////////////////////////////////////////////////
		
		public long insertAlarm(String aname, long atime) {
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_ANAME, aname);
			initialValues.put(KEY_ATIME, atime);
			return db.insert(DATABASE_TABLE2, null, initialValues);
		}
		
		public List<Alarm> getAllAlarm() {
			List<Alarm> alarmList = new ArrayList<Alarm>();
			String selectQuery = "SELECT  * FROM " + DATABASE_TABLE2;
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {
			do {
			Alarm alarm = new Alarm();
			alarm.setId(cursor.getInt(DBAdapter.COL_AID));
			alarm.setAlarmName(cursor.getString(DBAdapter.COL_ANAME));
			alarm.setCalendar(cursor.getLong(DBAdapter.COL_ATIME));
			alarmList.add(alarm);
			} while (cursor.moveToNext());
			}
			Log.e("Result Data in Activity", alarmList.toString());
			return alarmList;
		}
		
		public void deleteAllAlarms() {
			String selectQuery = "SELECT  * FROM " + DATABASE_TABLE2;
			Cursor c = db.rawQuery(selectQuery, null);
			long rowId = c.getColumnIndexOrThrow(KEY_AID);
			if (c.moveToFirst()) {
				do {
					deleteRow(c.getLong((int) rowId));				
				} while (c.moveToNext());
			}
			c.close();
		}
		// Delete a row from the database, by rowId (primary key)
				public boolean deleteRow(long rowId) {
					String where = KEY_AID + "=" + rowId;
					return db.delete(DATABASE_TABLE2, where, null) != 0;
				}
		public boolean deleteSelectedAlarm(int rowId) {
			String where = KEY_AID + "=" + rowId;
			return db.delete(DATABASE_TABLE2, where, null) != 0;
		}
		
		public boolean editSelectedAlarm(long rowId, String aname, long atime) {
			String where = KEY_AID + "=" + rowId;
			ContentValues newValues = new ContentValues();
			newValues.put(KEY_ANAME, aname);
			newValues.put(KEY_ATIME, atime);
			return db.update(DATABASE_TABLE2, newValues, where, null) != 0;
		}
		
	/////////////////////////////////////////////////////////////////////////////////////////////
		
		
		//////////////////////////////////////////////////////////////////////////////
		//notes table methods
		//////////////////////////////////////////////////////////////////////////////
		
		public void insertNote(Note note) {
			// TODO Auto-generated method stub
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_NTITLE, note.getTitle());
			initialValues.put(KEY_NCONTENT, note.getContent());
			initialValues.put(KEY_NSUBJECT, note.getSubject());
			db.insert(DATABASE_TABLE3, null, initialValues);
		}
		
		public List<Note> getAllNotes() {
			List<Note> notesList = new ArrayList<Note>();
			String selectQuery = "SELECT  * FROM " + DATABASE_TABLE3;
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {
			do {
			Note note = new Note();
			note.setId(cursor.getInt(DBAdapter.COL_AID));
			note.setTitle(cursor.getString(DBAdapter.COL_NTITLE));
			note.setContent(cursor.getString(DBAdapter.COL_NCONTENT));
			note.setSubject(cursor.getString(DBAdapter.COL_NSUBJECT));
			notesList.add(note);
			} while (cursor.moveToNext());
			}
			Log.e("Result Data in DBAdapter", notesList.toString());
			return notesList;
		}
		
		public List<String> getSubjectNames(){
			List<String> subNames = new ArrayList<String>();
			String select = "SELECT DISTINCT "+ KEY_NSUBJECT + " FROM " + DATABASE_TABLE3;
			Cursor cursor = db.rawQuery(select, null);
		//	Cursor cursor = db.query(true, DATABASE_TABLE3, new String[] { KEY_NSUBJECT }, null, null, KEY_NSUBJECT, null, null, null);
			if (cursor.moveToFirst()) {
			do {
			subNames.add(cursor.getString(0));
			} while (cursor.moveToNext());
			}
			Log.e("Result Data in DBAdapter", subNames.toString());
			return subNames;
		}
		public List<String> getTitles(String subject){
			List<String> titles = new ArrayList<String>();
			String select = "SELECT * FROM " + DATABASE_TABLE3 + " WHERE "+ KEY_NSUBJECT + " = '"+ subject +"';";
			Cursor cursor = db.rawQuery(select, null);
			if (cursor.moveToFirst()) {
			do {
			titles.add(cursor.getString(1));
			} while (cursor.moveToNext());
			}
			Log.e("Result Data in DBAdapter", titles.toString());
			return titles;
		}
		public List<String> getContents(String title){
			List<String> content = new ArrayList<String>();
			String select = "SELECT "+ KEY_NCONTENT + "FROM " + DATABASE_TABLE3 + " WHERE " + KEY_NTITLE + "=" + title;
			Cursor cursor = db.rawQuery(select, null);
			if (cursor.moveToFirst()) {
			do {
			content.add(cursor.getString(DBAdapter.COL_NCONTENT));
			} while (cursor.moveToNext());
			}
			Log.e("Result Data in DBAdapter", content.toString());
			return content;
		}
		public String getContent(String title){
			String content = null;
			String select = "SELECT * FROM " + DATABASE_TABLE3 + " WHERE " + KEY_NTITLE + " = '" + title + "';";
			Cursor cursor = db.rawQuery(select, null);
			if (cursor.moveToFirst()) {
			do {
			content = cursor.getString(DBAdapter.COL_NCONTENT);
			} while (cursor.moveToNext());
			}
			Log.e("Result Data in DBAdapter", content);
			return content;
		}
		
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////////
	//	Private Helper Classes:
	/////////////////////////////////////////////////////////////////////
	
	/**
	 * Private class which handles database creation and upgrading.
	 * Used to handle low-level database access.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);
			_db.execSQL(DATABASE_CREATE_ATABLE);
			_db.execSQL(DATABASE_CREATE_NTABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE3);
			
			// Recreate new database:
			onCreate(_db);
		}
	}
}
