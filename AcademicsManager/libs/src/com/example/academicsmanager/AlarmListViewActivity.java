package com.example.academicsmanager;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class AlarmListViewActivity extends Activity {
	private DBAdapter newDB;
	ListView alarmList;	
	MyAlarmAdapter adapt;
	List<Alarm> ListAlarm;

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		 String menuItem = "Delete All";
		 menu.add(Menu.NONE, 0, 0, menuItem);
         return true;
	}

	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	        switch (item.getItemId())
	        {
	        case 1:
	            newDB.open();newDB.deleteAllAlarms();Intent intent = getIntent();finish();startActivity(intent); newDB.close();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    } 

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		if (v.getId()==R.id.alarmListView) {
		    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		    Alarm alarm = ListAlarm.get(info.position);
			  String listItemName = alarm.getAlarmName();
		    menu.setHeaderTitle(listItemName);
		    String[] menuItems = {"Edit","Delete"};
		    for (int i = 0; i<menuItems.length; i++) {
		      menu.add(Menu.NONE, i, i, menuItems[i]);
		    }
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		  int menuItemIndex = item.getItemId();
		  Alarm alarm = ListAlarm.get(info.position);
		  int alarmID = alarm.getId();
		  String alarmName = alarm.getAlarmName();
		  long alarmTime = alarm.getCalender();
		//Log.d("value of item id", ""+menuItemIndex);
		  switch (menuItemIndex)
	        {
	        case 0:
	        	editSelectedAlarm(alarmID,alarmName,alarmTime);
	            return true;
	        case 1:
	            newDB.open();newDB.deleteSelectedAlarm(alarmID);Intent intents = getIntent();finish();startActivity(intents);newDB.close();
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	        }
	}

	private void editSelectedAlarm(int alarmID, String aname, long atime){
		// TODO Auto-generated method stub
		 Intent intent = new Intent(Intent.ACTION_EDIT);
		    intent.setClass(getApplicationContext(), AlarmActivity.class);
		 //s   intent.putExtra(Intents.Insert., email);
        newDB.open();
        newDB.editSelectedAlarm(alarmID,aname,atime);
        newDB.close();

	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		
		newDB = new DBAdapter(this);
		newDB.open();
		
	    alarmList = (ListView) findViewById(R.id.alarmListView);
		ListAlarm = new ArrayList<Alarm>();
		ListAlarm = newDB.getAllAlarm();
	    adapt = new MyAlarmAdapter(this, ListAlarm);
		alarmList.setAdapter(adapt);
		registerForContextMenu(alarmList);
     }	       
}	