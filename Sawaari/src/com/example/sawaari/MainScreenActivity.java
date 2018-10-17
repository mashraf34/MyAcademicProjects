package com.example.sawaari;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainScreenActivity extends Activity{
	private Intent intent;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainscreen);
	}

	public void findbtn_click(View view){
		intent = new Intent("android.intent.action.FINDRIDEACTIVITY");
		startActivity(intent);
	}
	
	public void providebtn_click(View view){
		intent = new Intent("android.intent.action.PROVIDERIDEACTIVITY");
		startActivity(intent);
	}
}
