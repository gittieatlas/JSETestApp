package com.example.user.jsetestapp;

/**
 * Created by Rochel on 10/18/2015.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyBaseAdapter extends BaseAdapter {
    ArrayList<HoursDataObject> myList;
    LayoutInflater inflater;
    Context context;


    public MyBaseAdapter(Context context, ArrayList<HoursDataObject> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);

    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public HoursDataObject getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // create a ViewHolder reference
        MyViewHolder mViewHolder;

        //check to see if the reused view is null or not, if is not null then reuse it
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.location_info_list_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            // save the view holder on the cell view to get it back latter
            convertView.setTag(mViewHolder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        HoursDataObject currentListData = getItem(position);

        mViewHolder.libraryDayTextView.setText(currentListData.getmText1().toString());
        mViewHolder.libraryOpeningTimeTextView.setText(currentListData.getmText2().toString());
        mViewHolder.libraryClosingTimeTextView.setText(currentListData.getmText3().toString());

        return convertView;
    }

    private class MyViewHolder {
        TextView libraryDayTextView, libraryOpeningTimeTextView, libraryClosingTimeTextView;


        public MyViewHolder(View item) {
            libraryDayTextView = (TextView) item.findViewById(R.id.libraryDayTextView);
            libraryOpeningTimeTextView = (TextView) item.findViewById(R.id.libraryOpeningTimeTextView);
            libraryClosingTimeTextView = (TextView) item.findViewById(R.id.libraryClosingTimeTextView);
        }
    }
}