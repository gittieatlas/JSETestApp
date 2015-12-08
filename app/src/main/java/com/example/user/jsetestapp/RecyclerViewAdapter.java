package com.example.user.jsetestapp;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecyclerViewAdapter extends Adapter<RecyclerViewViewHolder> {

    // Declare ArrayList
    private ArrayList<DataObject> arrayListDataObject;

    // Declare RecyclerViewItemClickListener
    private RecyclerViewItemClickListener itemClickListener;

    // Declare RecyclerViewItemImageClickListener
    private RecyclerViewItemImageClickListener itemImageClickListener;

    public RecyclerViewAdapter(ArrayList<DataObject> data) {
        // assign data to arrayListDataObject
        this.arrayListDataObject = data;
    }

    // create new views (invoked by the layout manager)
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // initialize itemView
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        // initialize ViewHolder
        RecyclerViewViewHolder vh = new RecyclerViewViewHolder(itemView,
                itemClickListener, itemImageClickListener);
        return vh;
    }

    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerViewViewHolder holder, int position) {
        // get data from your arrayListDataObject at this position
        // replace the contents of the view with that arrayListDataObject
        holder.location.setText(arrayListDataObject.get(position).getmText1());
        holder.testDay.setText(arrayListDataObject.get(position).getmText2());
        holder.testTime.setText(arrayListDataObject.get(position).getmText3());
        holder.testDate.setText(arrayListDataObject.get(position).getmText4());
        holder.testDeadlineTitle.setText(arrayListDataObject.get(position).getmText5());
        holder.testDeadlineDetails.setText(arrayListDataObject.get(position).getmText6());
    }

    /**
     * Listener for item click
     *
     * @param listener
     */
    public void setOnItemClickListener(RecyclerViewItemClickListener listener) {
        // assign listener to itemClickListener
        this.itemClickListener = listener;
    }

    /**
     * Listener for image click
     *
     * @param listener
     */
    public void setOnItemImageClickListener(RecyclerViewItemImageClickListener listener) {
        // assign listener to itemImageClickListener
        this.itemImageClickListener = listener;
    }

    /**
     * Function to get the number of elements the RecyclerView will display
     * @return int
     */
    @Override
    public int getItemCount() {
        return arrayListDataObject.size();
    }
}
