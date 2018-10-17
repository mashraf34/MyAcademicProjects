package com.example.ecms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class loginscreen extends Activity {

	EditText edtxtu, edtxtp;
	TextView txtview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginscreen);
		edtxtu = (EditText) findViewById(R.id.editText1);
		edtxtp = (EditText) findViewById(R.id.editText2);
		txtview = (TextView) findViewById(R.id.textView1);
	}

	public void signbtn_click(View view){
		
		if(edtxtu.getText().toString().equals("amjadali") && edtxtp.getText().toString().equals("abc123456"))
		{
		Intent intent = new Intent("android.intent.action.DISPLAYSCREEN");
		startActivity(intent);
		this.finish();
		}
		else
		{
			txtview.setText("Invalid Username or Password");
		}
	}
	
}
