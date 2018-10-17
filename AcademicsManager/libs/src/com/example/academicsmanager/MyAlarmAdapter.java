package com.example.academicsmanager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

	public class MyAlarmAdapter extends BaseAdapter {
        Context context;
    	List<Alarm> alarmList;
    	
		public MyAlarmAdapter(Context context, List<Alarm> objects) {
			super();
			alarmList = new ArrayList<Alarm>();
    	alarmList=objects;
    	this.context=context;
		Log.e("Result Data in Adapter", alarmList.toString());
    	}
	 
		private class ViewHolder {
			TextView txtTitle;
			TextView txtTime;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return alarmList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return alarmList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return alarmList.indexOf(getItem(position));
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Log.d("getview", "getview is executed");
			View view;
			ViewHolder holder = null;			
			Alarm alarm = alarmList.get(position);
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			if (convertView == null) {
				view = inflater.inflate(R.layout.alarmlistinnerview, null);
				holder = new ViewHolder();
				holder.txtTitle = (TextView) view.findViewById(R.id.alarm_title);
				holder.txtTime = (TextView) view.findViewById(R.id.alarm_time);		
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag(); 
				}
		    	holder.txtTitle.setText(alarm.getAlarmName());
		    	long cal = alarm.getCalender();
		    	Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(cal);
			    holder.txtTime.setText(""+ calendar.getTime());
			return view;
		}
}