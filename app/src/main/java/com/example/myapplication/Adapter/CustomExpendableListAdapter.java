package com.example.myapplication.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public class CustomExpendableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listTittle;
    private Map<String,List<String>> listitem;

    public CustomExpendableListAdapter(Context context, List<String> listTittle, Map<String,List<String>> listitem) {
        this.context = context;
        this.listTittle = listTittle;
        this.listitem = listitem;

    }

    @Override
    public int getGroupCount() {
        return listitem.size();


    }

    @Override
    public int getChildrenCount(int groupPosition) {

        int re = 0;
        if(listTittle.get(groupPosition).equals("Products For Office")){
            re = 2;
        }
        if(listTittle.get(groupPosition).equals("Clothings")){
            re = 3;
        }
        if(listTittle.get(groupPosition).equals("Business Cards")){
            re = 7;
        }
        if(listTittle.get(groupPosition).equals("Photo_Gifts")){
            re = 2;
        }

        return re;

    }

    @Override
    public Object getGroup(int groupPosition) {
        return listTittle.get(groupPosition);

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listitem.get(listTittle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String)getGroup(groupPosition);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_group,null);
        }
        TextView txtTitle = convertView.findViewById(R.id.listTitle);
        txtTitle.setText(title);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String title = (String)getChild(groupPosition,childPosition);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item,null);
        }
        TextView txtchild = convertView.findViewById(R.id.expandabledlistitem);
        txtchild.setText(title);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
