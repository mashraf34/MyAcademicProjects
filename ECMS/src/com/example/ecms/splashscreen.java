package com.example.ecms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class splashscreen extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		Thread timer = new Thread(){
			public void run(){
			try{
			sleep(1500);
			}catch(InterruptedException e){
				e.printStackTrace();
			}finally{
				Intent openMainActivity = new Intent("android.intent.action.LOGINSCREEN");
				startActivity(openMainActivity);
			}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
