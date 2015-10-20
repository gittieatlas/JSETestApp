package com.example.user.jsetestapp;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MyAdapter extends Adapter<MyViewHolder> {

    private ArrayList<DataObject> mDataset;

    private MyItemClickListener mItemClickListener;
    private MyItemLongClickListener mItemLongClickListener;
    private MyItemImageClickListener mItemImageClickListener;

    public MyAdapter(ArrayList<DataObject> data) {
        this.mDataset = data;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.location.setText(mDataset.get(position).getmText1());
        holder.testDay.setText(mDataset.get(position).getmText2());
        holder.testTime.setText(mDataset.get(position).getmText3());
        holder.testDate.setText(mDataset.get(position).getmText4());
        holder.testDedlineTitle.setText(mDataset.get(position).getmText5());
        holder.testDedlineDetails.setText(mDataset.get(position).getmText6());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        MyViewHolder vh = new MyViewHolder(itemView, mItemClickListener, mItemLongClickListener, mItemImageClickListener);
        return vh;
    }


    /**
     * ÉèÖÃItemµã»÷¼àÌý
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setOnItemImageClickListener(MyItemImageClickListener listener) {
        this.mItemImageClickListener = listener;
    }

    public void setOnItemLongClickListener(MyItemLongClickListener listener) {
        this.mItemLongClickListener = listener;
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
