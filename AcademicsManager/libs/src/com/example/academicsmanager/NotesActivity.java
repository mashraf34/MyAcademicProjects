package com.example.academicsmanager;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NotesActivity extends Activity
{
	DBAdapter newDB;
	Note note;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notes_activity);
		newDB = new DBAdapter(this);
	}
	
	public void onBoldClick(View v)
	{
		EditText et = (EditText) findViewById(R.id.contentEditText);

		int startSelection=et.getSelectionStart();
		int endSelection=et.getSelectionEnd();
		
		SpannableString span = new SpannableString(et.getText());
		span.setSpan(new StyleSpan(Typeface.BOLD), startSelection, endSelection ,  0);
		 et.setText(span, EditText.BufferType.SPANNABLE);
	}
	
	public void onItalicClick(View v)
	{
		EditText et = (EditText) findViewById(R.id.contentEditText);

		int startSelection=et.getSelectionStart();
		int endSelection=et.getSelectionEnd();
		
		SpannableString span = new SpannableString(et.getText());
		 span.setSpan(new StyleSpan(Typeface.ITALIC), startSelection, endSelection , 0);
		 et.setText(span, EditText.BufferType.SPANNABLE);
	}
	
	public void onUnderlineClick(View v)
	{
		EditText et = (EditText) findViewById(R.id.contentEditText);

		int startSelection=et.getSelectionStart();
		int endSelection=et.getSelectionEnd();

		SpannableString span = new SpannableString(et.getText());
		span.setSpan(new UnderlineSpan(),startSelection, endSelection , 0);
		 et.setText(span, EditText.BufferType.SPANNABLE);
	}

	public void onStrikethroughClick(View v)
	{
		EditText et = (EditText) findViewById(R.id.contentEditText);

		int startSelection=et.getSelectionStart();
		int endSelection=et.getSelectionEnd();

		SpannableString span = new SpannableString(et.getText());
		span.setSpan(new StrikethroughSpan(),startSelection, endSelection , 0);
		 et.setText(span, EditText.BufferType.SPANNABLE);
	}

	public void onSubmitClick(View v)
	{	
		note = new Note();
		
		EditText ett = (EditText) findViewById(R.id.titleEditText);
		EditText etc = (EditText) findViewById(R.id.contentEditText);
		EditText ets = (EditText) findViewById(R.id.subjectEditText);
		
		SpannableString spanContent = new SpannableString(etc.getText());
		
		String title = ett.getText().toString();
		String content = Html.toHtml(spanContent);
		String subject = ets.getText().toString();
		
		note.setTitle(title);
		note.setContent(content);
		note.setSubject(subject);
		
		newDB.open();
		newDB.insertNote(note);
		Toast.makeText(this, "Note Added!", Toast.LENGTH_LONG).show();
		newDB.close();
		this.finish();
		
	}

}
