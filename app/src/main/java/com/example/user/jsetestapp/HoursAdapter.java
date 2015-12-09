package com.example.user.jsetestapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HoursAdapter extends BaseAdapter {
    // Declare ArrayList
    ArrayList<HoursDataObject> myList;

    // Declare LayoutInflater
    LayoutInflater inflater;

    // Declare context
    Context context;

    // constructor
    public HoursAdapter(Context context, ArrayList<HoursDataObject> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    /**
     * Function that returns the number of items in myList
     * @return int
     */
    @Override
    public int getCount() {
        return myList.size();
    }

    /**
     * Function that returns a single object of the selected position
     * @param position - position number
     * @return HoursDataObject
     */
    @Override
    public HoursDataObject getItem(int position) {
        return myList.get(position);
    }

    /**
     * Function to get item id
     * @param position - position number
     * @return long
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * This method is called each time a row either needs to be CREATED or UPDATED.
     * @param position - position
     * @param convertView - convertView is our row
     * @param parent - is the parent of the current view.
     * @return View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // create a ViewHolder reference
        MyViewHolder mViewHolder;

        // check to see if the reused view is null or not, if is not null then reuse it
        if (convertView == null) {
            // inflate the layout
            convertView = inflater.inflate(R.layout.location_info_list_item, parent, false);
            // initialize mViewHolder
            mViewHolder = new MyViewHolder(convertView);
            // store the holder with the view.
            convertView.setTag(mViewHolder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
        // get a reference to the item at the current position and cast it to a currentListData
        HoursDataObject currentListData = getItem(position);
        // Call the getLibraryDay, getLibraryOpeningTime and getLibraryClosingTime methods
        // of HoursDataObject and then set the Text of our TextViews
        mViewHolder.libraryDayTextView.setText(currentListData.getLibraryDay());
        mViewHolder.libraryOpeningTimeTextView.setText(currentListData.getLibraryOpeningTime());
        mViewHolder.libraryClosingTimeTextView.setText(currentListData.getLibraryClosingTime());

        // return the completed view to render on screen
        return convertView;
    }

    // this will store the references to our view
    private class MyViewHolder {
        // declare textViews
        TextView libraryDayTextView, libraryOpeningTimeTextView, libraryClosingTimeTextView;

        public MyViewHolder(View item) {
            libraryDayTextView = (TextView) item.findViewById(R.id.libraryDayTextView);
            libraryOpeningTimeTextView = (TextView) item.findViewById(R.id.libraryOpeningTimeTextView);
            libraryClosingTimeTextView = (TextView) item.findViewById(R.id.libraryClosingTimeTextView);
        }
    }
}