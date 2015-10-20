package com.example.user.jsetestapp;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class RecyclerViewViewHolder extends ViewHolder {


    TextView location;
    TextView testDay;
    TextView testTime;
    TextView testDate;
    TextView testDeadlineTitle;
    TextView testDeadlineDetails;
    ImageButton imageButton;


    private RecyclerViewItemClickListener mListener;
    private RecyclerViewItemImageClickListener mImageListener;

    public RecyclerViewViewHolder(View itemView, RecyclerViewItemClickListener listener , RecyclerViewItemImageClickListener imageListener) {
        super(itemView);
        location = (TextView) this.itemView.findViewById(R.id.locationTextView);
        testDay = (TextView) this.itemView.findViewById(R.id.testDayTextView);
        testTime = (TextView) this.itemView.findViewById(R.id.testTimeTextView);
        testDate = (TextView) this.itemView.findViewById(R.id.testDateTextView);
        testDeadlineTitle = (TextView) this.itemView.findViewById(R.id.testDealineTitleTextView);
        testDeadlineDetails = (TextView) this.itemView.findViewById(R.id.testDealineDetailsTextView);
        imageButton = (ImageButton) this.itemView.findViewById(R.id.imageButton);

        this.mListener = listener;
        this.mImageListener = imageListener;

        itemView.setOnClickListener(itemClickListener);
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
}
