package com.example.academicsmanager;

import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AlarmActivity extends Activity {
	
	private DBAdapter newDB;
	DatePicker pickerDate;
	 TimePicker pickerTime;
	 Button buttonSetAlarm;
	 
	 final static int RQS_1 = 1;

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_alarm);
	  newDB = new DBAdapter(this);
	  newDB.open();
	 
	  pickerDate = (DatePicker)findViewById(R.id.pickerdate);
	  pickerTime = (TimePicker)findViewById(R.id.pickertime);
	  
	  Calendar now = Calendar.getInstance();
	  pickerDate.setCalendarViewShown(false);
	  pickerDate.init(
	    now.get(Calendar.YEAR), 
	    now.get(Calendar.MONTH), 
	    now.get(Calendar.DAY_OF_MONTH), 
	    null);
	  
	  pickerTime.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
	  pickerTime.setCurrentMinute(now.get(Calendar.MINUTE));
	  
	  buttonSetAlarm = (Button)findViewById(R.id.setalarm);
	  buttonSetAlarm.setOnClickListener(new OnClickListener(){
	   @Override
	   public void onClick(View arg0) {
		   newDB.open();
		 TextView  alarmName = (TextView)findViewById(R.id.alarmname);
		   String aname = alarmName.getText().toString();
	    Calendar current = Calendar.getInstance();
	    pickerDate.setCalendarViewShown(false);
	    Calendar cal = Calendar.getInstance();
	    cal.set(pickerDate.getYear(), 
	      pickerDate.getMonth(), 
	      pickerDate.getDayOfMonth(), 
	      pickerTime.getCurrentHour(), 
	      pickerTime.getCurrentMinute(), 
	      00);
	    long atime = cal.getTimeInMillis();
	    if(cal.compareTo(current) <= 0){
	     //The set Date/Time already passed
	        Toast.makeText(getApplicationContext(), 
	          "Invalid Date/Time", 
	          Toast.LENGTH_LONG).show();
	    }else{
	    	newDB.insertAlarm(aname, atime);
	        setAlarm(cal,aname);
	    }
	    
	   }});
	 }
	 
	 public  void setAlarm(Calendar cal, String aname) {
		 
		  Toast.makeText(getApplicationContext(), "Alarm is set@ " + cal.getTime(), Toast.LENGTH_LONG).show();
				  
				  Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
				  intent.putExtra("aname",aname);	
				  PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, Intent.FILL_IN_DATA);
				  AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
				  alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent); 
		}
}