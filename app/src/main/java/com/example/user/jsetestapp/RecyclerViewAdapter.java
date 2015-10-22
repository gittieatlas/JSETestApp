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

    private ArrayList<DataObject> arrayListDataObject;

    private RecyclerViewItemClickListener itemClickListener;
    private RecyclerViewItemImageClickListener itemImageClickListener;

    public RecyclerViewAdapter(ArrayList<DataObject> data) {
        this.arrayListDataObject = data;
    }

    @Override
    public void onBindViewHolder(RecyclerViewViewHolder holder, int position) {

        holder.location.setText(arrayListDataObject.get(position).getmText1());
        holder.testDay.setText(arrayListDataObject.get(position).getmText2());
        holder.testTime.setText(arrayListDataObject.get(position).getmText3());
        holder.testDate.setText(arrayListDataObject.get(position).getmText4());
        holder.testDeadlineTitle.setText(arrayListDataObject.get(position).getmText5());
        holder.testDeadlineDetails.setText(arrayListDataObject.get(position).getmText6());
    }


    @Override
    public RecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        RecyclerViewViewHolder vh = new RecyclerViewViewHolder(itemView, itemClickListener, itemImageClickListener);
        return vh;
    }

    /**
     * Listeners
     *
     * @param listener
     */
    public void setOnItemClickListener(RecyclerViewItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnItemImageClickListener(RecyclerViewItemImageClickListener listener) {
        this.itemImageClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return arrayListDataObject.size();
    }
}
