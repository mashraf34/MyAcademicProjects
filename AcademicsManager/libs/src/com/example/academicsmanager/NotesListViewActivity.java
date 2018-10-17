package com.example.academicsmanager;

import java.util.ArrayList;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;

public class NotesListViewActivity extends ExpandableListActivity {
	private DBAdapter newDB;	
	private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     // Create Expandable List and set it's properties
        ExpandableListView expandableList = getExpandableListView(); 
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        // Set the Items of Parent
        setparents();
        // Set The Child Data
        setchildren();

        // Create the Adapter
        NotesExpandableAdapter adapter = new NotesExpandableAdapter(this,parentItems, childItems);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        
        // Set the Adapter to expandableList
        expandableList.setAdapter(adapter);
      //  expandableList.setOnChildClickListener(this);
       
        int count = adapter.getGroupCount();
        for (int position = 1; position <= count; position++)
            expandableList.expandGroup(position - 1);  
        
    }
    
    public void setparents(){
    	newDB = new DBAdapter(this);
        newDB.open();
        parentItems = (ArrayList<String>) newDB.getSubjectNames();
        newDB.close();
    }
    
    public void setchildren(){
    	newDB = new DBAdapter(this);
        newDB.open();
        for (int i =0;i<parentItems.size();i++)
        {
	        ArrayList<String> child = new ArrayList<String>();
        	child =  (ArrayList<String>) newDB.getTitles(parentItems.get(i));
        	childItems.add(child);
        }
        newDB.close();
    }
    
    
}