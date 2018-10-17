package com.example.academicsmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
	private static final int MY_NOTIFICATION_ID=1;
	 NotificationManager notificationManager;
	 Notification myNotification;

	 @Override
	 public void onReceive(Context context, Intent intent) {

		 Bundle bundle =  intent.getExtras();
		 String alarmname = bundle.getString("aname");
		  
		 Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		    vibrator.vibrate(2000);
		    
	     myNotification = new NotificationCompat.Builder(context)
	       .setContentTitle("Reminder")
	       .setContentText(alarmname)
	       .setTicker("Academics Manager Notification")
	       .setWhen(System.currentTimeMillis())
	       .setDefaults(Notification.DEFAULT_SOUND)
	       .setAutoCancel(true)
	       .setSmallIcon(R.drawable.thumb20)
	       .build();
	     
	     notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
	     notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
	 }
}