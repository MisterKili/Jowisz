package com.example.jowisz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jowisz.Model.Category;

import java.util.ArrayList;

public class CategoryArrayAdapter extends ArrayAdapter<Category> {

    public CategoryArrayAdapter(Context context, ArrayList<Category> items){
        super(context, 0, items);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        // Get the data item for this position
        Category category = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        // Lookup view for data population
        TextView tvCategory = (TextView) convertView.findViewById(android.R.id.text1);
        // Populate the data into the template view using the data object
        tvCategory.setText(category.getCategoryName());
        // Return the completed view to render on screen
        return convertView;
    }
}