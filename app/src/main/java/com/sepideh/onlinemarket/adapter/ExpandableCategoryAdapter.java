package com.sepideh.onlinemarket.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.sepideh.onlinemarket.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by pc on 5/18/2019.
 */

public class ExpandableCategoryAdapter extends BaseExpandableListAdapter {

    Context context;
    List<String> categoryHeadrsList;
    private HashMap<String, List<String>> categorychildrenList;

    public ExpandableCategoryAdapter(Context context, List<String> categoryHeadrsList, HashMap<String, List<String>> categorychildrenList) {
        this.context = context;
        this.categoryHeadrsList = categoryHeadrsList;
        this.categorychildrenList = categorychildrenList;
    }

    @Override
    public int getGroupCount() {
        return categoryHeadrsList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.categorychildrenList.get(this.categoryHeadrsList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categoryHeadrsList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return categorychildrenList.get(this.categoryHeadrsList.get(groupPosition))
                .get(childPosition);
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

        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.category_header_row, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.txv_category_header);
       // lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.category_child_row, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.txv_category_child);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
