package com.example.academicsmanager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import android.view.MenuInflater;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

public class ListViewActivity extends Activity {
	private DBAdapter newDB;	
	List<Task> list;
    MyAdapter adapt;  
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
    	 MenuInflater menuInflater = getMenuInflater();
         menuInflater.inflate(R.layout.menu_todo, menu);
         return true;
	}
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case R.id.menu_deleteall:
            newDB.open();newDB.deleteAllTasks();Intent intent = getIntent();finish();startActivity(intent); newDB.close();
            return true;
        case R.id.menu_deleteselected:
            newDB.open();newDB.deleteSelectedTasks();Intent intents = getIntent();finish();startActivity(intents);newDB.close();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }    

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolistview);
    	list = new ArrayList<Task>();
        newDB= new DBAdapter(this);
        newDB.open();
        list = newDB.getAllTasks();
        adapt = new MyAdapter(this, R.layout.list_inner_view, list);
        ListView listTask = (ListView) findViewById(R.id.listView1);
        listTask.setAdapter(adapt); 
    }
    
    public class MyAdapter extends ArrayAdapter<Task> {
    	Context context;
    	List<Task> taskList=new ArrayList<Task>();
    	int layoutResourceId;
    	
    	public MyAdapter(Context context, int layoutResourceId,
    	List<Task> objects) {
    	super(context, layoutResourceId, objects);
    	this.layoutResourceId = layoutResourceId;
    	this.taskList=objects;
    	this.context=context;
    	}

    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
    		CheckBox chk = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_inner_view,
                        parent, false);
                chk = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(chk);
                chk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Task changeTask = (Task) cb.getTag();
                        changeTask.setStatus(cb.isChecked() == true ? 1 : 0);
                        newDB.updateTask(changeTask);
                    }
                });
            } else {
                chk = (CheckBox) convertView.getTag();
            }
            Task current = taskList.get(position);
            chk.setText(current.getTaskName());
            chk.setChecked(current.getStatus() == 1 ? true : false);
            chk.setTag(current);
            Log.d("listener", String.valueOf(current.getId()));
            return convertView;
    	}
    }
}