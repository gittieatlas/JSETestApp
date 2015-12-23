package com.example.user.jsetestapp;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TestsAdapter extends Adapter<RecyclerViewViewHolder> {

    // Declare ArrayList
    private ArrayList<TestDataObject> arrayListTestDataObject;

    // Declare RecyclerViewItemClickListener
    private RecyclerViewItemClickListener itemClickListener;

    // Declare RecyclerViewItemImageClickListener
    private RecyclerViewItemImageClickListener itemImageClickListener;

    public TestsAdapter(ArrayList<TestDataObject> data) {
        // assign data to arrayListTestDataObject
        this.arrayListTestDataObject = data;
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
        // get data from your arrayListTestDataObject at this position
        // replace the contents of the view with that arrayListTestDataObject
        holder.location.setText(arrayListTestDataObject.get(position).getLocation());
        holder.testDay.setText(arrayListTestDataObject.get(position).getTestDay());
        holder.testTime.setText(arrayListTestDataObject.get(position).getTestTime());
        holder.testDate.setText(arrayListTestDataObject.get(position).getTestDate());
        holder.testDeadlineTitle.setText(arrayListTestDataObject.get(position)
                .getTestDeadlineTitle());
        holder.testDeadlineDetails.setText(arrayListTestDataObject.get(position).getTestDeadlineDay());
        holder.testDeadlineTime.setText(arrayListTestDataObject.get(position).getTestDeadlineTime());
        holder.testDeadlineDate.setText(arrayListTestDataObject.get(position).getTestDeadlineDate());
    }

    /**
     * Listener for item click
     *
     * @param listener - for item click
     */
    public void setOnItemClickListener(RecyclerViewItemClickListener listener) {
        // assign listener to itemClickListener
        this.itemClickListener = listener;
    }

    /**
     * Listener for image click
     *
     * @param listener - for item click
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
        return arrayListTestDataObject.size();
    }
}
