package com.example.academicsmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class MyExpandableAdapter extends BaseExpandableListAdapter {

	private Activity activity;
    private ArrayList<Object> childtems;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems, child;
    private Context context;
	private String childern[][]={{"NotesActivity","NotesListViewActivity"},{"AlarmActivity","AlarmListViewActivity"},{"TodoActivity","ListViewActivity"}};

	public MyExpandableAdapter(Context context, ArrayList<String> parents, ArrayList<Object> childern) {
		// TODO Auto-generated constructor stub
		this.parentItems = parents;
        this.childtems = childern;
        this.context = context;
	}

	public void setInflater(LayoutInflater inflater, Activity activity) {
		// TODO Auto-generated method stub
		this.inflater = inflater;
        this.activity = activity;
	}
	 @Override
	    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) 
	    {

	        child = (ArrayList<String>) childtems.get(groupPosition);

	        TextView textView = null;

	        if (convertView == null) {
	            convertView = inflater.inflate(R.layout.child_view, null);
	        }
	    
	         // get the textView reference and set the value
	        textView = (TextView) convertView.findViewById(R.id.textViewChild);
	        textView.setText(child.get(childPosition));

	        // set the ClickListener to handle the click event on child item
	        convertView.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View view) {
			String cheese = childern[groupPosition][childPosition];
			try{
				Class ourClass = Class.forName("com.example.academicsmanager." + cheese);
				Intent ourIntent = new Intent(context , ourClass);
				context.startActivity(ourIntent);
			}catch(ClassNotFoundException e){
				e.printStackTrace();
			}
	            }
	        });	        
	        return convertView;
	    }
	 
	 @Override
	    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) 
	    {
		    
	        if (convertView == null) {
	            convertView = inflater.inflate(R.layout.parent_view, null);
	        }

	        ((CheckedTextView) convertView).setText(parentItems.get(groupPosition));
	        ((CheckedTextView) convertView).setChecked(isExpanded);

	        return convertView;
	    }

	    @Override
	    public Object getChild(int groupPosition, int childPosition) 
	    {
	        return null;
	    }

	    @Override
	    public long getChildId(int groupPosition, int childPosition) 
	    {
	        return 0;
	    }

	    @Override
	    public int getChildrenCount(int groupPosition) 
	    {
	        return ((ArrayList<String>) childtems.get(groupPosition)).size();
	    }

	    @Override
	    public Object getGroup(int groupPosition) 
	    {
	        return null;
	    }

	    @Override
	    public int getGroupCount() 
	    {
	        return parentItems.size();
	    }

	    @Override
	    public void onGroupCollapsed(int groupPosition) 
	    {
	        super.onGroupCollapsed(groupPosition);
	    }

	    @Override
	    public void onGroupExpanded(int groupPosition)
	    {
	        super.onGroupExpanded(groupPosition);
	    }

	    @Override
	    public long getGroupId(int groupPosition) 
	    {
	        return 0;
	    }

	    @Override
	    public boolean hasStableIds() 
	    {
	        return false;
	    }

	    @Override
	    public boolean isChildSelectable(int groupPosition, int childPosition)
	    {
	        return false;
	    }
}
