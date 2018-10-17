package com.example.academicsmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TodoActivity extends Activity {
	DBAdapter myDb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);	
		openDB();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();	
		closeDB();
	}
	private void openDB() {
		myDb = new DBAdapter(this);
		myDb.open();
	}
	private void closeDB() {
		myDb.close();
	}

	public void onClick_AddRecord(View v) {
		TextView textView = (TextView) findViewById(R.id.textDisplay);
		String s = textView.getText().toString();
        if (s.equalsIgnoreCase("")) {
            Toast.makeText(this, "Enter the task description first!",
                    Toast.LENGTH_LONG).show();
            
        } else {
            Task task = new Task(s, 0);
            myDb.insertTask(task);
            Toast.makeText(this, "To-Do added to your list ",
                    Toast.LENGTH_LONG).show();
            textView.setText("");
	}
}
}
