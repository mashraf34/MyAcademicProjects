package com.example.academicsmanager;

public class Alarm {
	private String alarmName;
    private long cal;
    private int id;
    public Alarm()
    {
        this.alarmName=null;
        this.cal=0;
    }
    public Alarm(String alarmName, long cal) {
        super();
        this.alarmName = alarmName;
        this.cal = cal;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAlarmName() {
        return alarmName;
    }
    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }
    public long getCalender() {
    	return cal;
    }
    public void setCalendar(long cal) {
        this.cal = cal;
    }
}
