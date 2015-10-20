package com.example.user.jsetestapp;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MyViewHolder extends ViewHolder {


    TextView location;
    TextView testDay;
    TextView testTime;
    TextView testDate;
    TextView testDedlineTitle;
    TextView testDedlineDetails;
    ImageButton imageButton;


    private MyItemClickListener mListener;
    private MyItemLongClickListener mLongClickListener;
    private MyItemImageClickListener mImageListener;

    public MyViewHolder(View itemView, MyItemClickListener listener, MyItemLongClickListener longClickListener, MyItemImageClickListener imageListener) {
        super(itemView);
        location = (TextView) this.itemView.findViewById(R.id.locationTextView);
        testDay = (TextView) this.itemView.findViewById(R.id.testDayTextView);
        testTime = (TextView) this.itemView.findViewById(R.id.testTimeTextView);
        testDate = (TextView) this.itemView.findViewById(R.id.testDateTextView);
        testDedlineTitle = (TextView) this.itemView.findViewById(R.id.testDealineTitleTextView);
        testDedlineDetails = (TextView) this.itemView.findViewById(R.id.testDealineDetailsTextView);
        imageButton = (ImageButton) this.itemView.findViewById(R.id.imageButton);

        this.mListener = listener;
        this.mLongClickListener = longClickListener;
        this.mImageListener = imageListener;
        itemView.setOnClickListener(itemClickListener);

        itemView.setOnLongClickListener(itemLongClickListener);
        imageButton.setOnClickListener(imageClickListener);

    }

    OnClickListener itemClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }
    };

    OnClickListener imageClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mImageListener != null) {
                mImageListener.onImageItemClick(v, getPosition());
            }
        }
    };

    OnLongClickListener itemLongClickListener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mLongClickListener != null) {
                mLongClickListener.onItemLongClick(v, getPosition());
            }
            return true;
        }
    };

}
