package com.example.academicsmanager;

import java.util.ArrayList;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;

public class ExpList extends ExpandableListActivity {
	
	 private ArrayList<String> parentItems = new ArrayList<String>();
	    private ArrayList<Object> childItems = new ArrayList<Object>();
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) 
	    {
	        super.onCreate(savedInstanceState);
	        
	        // Create Expandable List and set it's properties
	        ExpandableListView expandableList = getExpandableListView(); 
	        expandableList.setDividerHeight(2);
	        expandableList.setGroupIndicator(null);
	        expandableList.setClickable(true);

	        // Set the Items of Parent
	        setGroupParents();
	        // Set The Child Data
	        setChildData();

	        // Create the Adapter
	        MyExpandableAdapter adapter = new MyExpandableAdapter(this,parentItems, childItems);
	        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
	        
	        // Set the Adapter to expandableList
	        expandableList.setAdapter(adapter);
	    //  expandableList.setOnChildClickListener(this);
	       
	        int count = adapter.getGroupCount();
	        for (int position = 1; position <= count; position++)
	            expandableList.expandGroup(position - 1);  
	    }
	   
		// method to add parent Items
	    public void setGroupParents() 
	    {
	        parentItems.add("Notes");
	        parentItems.add("Reminders");
	        parentItems.add("To-Do CheckList");
	    }

	    // method to set child data of each parent
	    public void setChildData() 
	    {
	        // Add Child Items for Fruits
	        ArrayList<String> child = new ArrayList<String>();
	        child.add("Create New");
	        child.add("View All");
	        
	        childItems.add(child);

	        // Add Child Items for Flowers
	        child = new ArrayList<String>();
	        child.add("Create New");
	        child.add("View All");
	        
	        childItems.add(child);

	        // Add Child Items for Animals
	        child = new ArrayList<String>();
	        child.add("Add a To-Do");
	        child.add("View Checklist");
	        
	        childItems.add(child);
	    }
}