package com.example.academicsmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.widget.TextView;

public class notescontentview extends Activity {
	DBAdapter newdb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notes_content);
		newdb = new DBAdapter(this);
		Intent intent = getIntent();
		String title = intent.getStringExtra("title");
		newdb.open();
		String content = newdb.getContent(title);
		newdb.close();
		SpannableString spancontent = new SpannableString(Html.fromHtml(content));
		TextView textview = (TextView) findViewById(R.id.textViewContent);
		textview.setText(spancontent);	
	}
}
