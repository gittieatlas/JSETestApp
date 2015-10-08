package com.example.user.jsetestapp;

/**
 * Created by Rochel on 9/21/2015.
 */
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView
        .Adapter<RecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "RecyclerViewAdapter";
    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView location;
        TextView testDay;
        TextView testTime;
        TextView testDate;
        TextView testDedlineTitle;
        TextView testDedlineDetails;

        public DataObjectHolder(View itemView) {
            super(itemView);
            location = (TextView) itemView.findViewById(R.id.locationTextView);
            testDay = (TextView) itemView.findViewById(R.id.testDayTextView);
            testTime = (TextView) itemView.findViewById(R.id.testTimeTextView);
            testDate = (TextView) itemView.findViewById(R.id.testDateTextView);
            testDedlineTitle = (TextView) itemView.findViewById(R.id.testDealineTitleTextView);
            testDedlineDetails = (TextView) itemView.findViewById(R.id.testDealineDetailsTextView);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public RecyclerViewAdapter(ArrayList<DataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.location.setText(mDataset.get(position).getmText1());
        holder.testDay.setText(mDataset.get(position).getmText2());
        holder.testTime.setText(mDataset.get(position).getmText3());
        holder.testDate.setText(mDataset.get(position).getmText4());
        holder.testDedlineTitle.setText(mDataset.get(position).getmText5());
        holder.testDedlineDetails.setText(mDataset.get(position).getmText6());
    }

    public void addItem(DataObject dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }


    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}