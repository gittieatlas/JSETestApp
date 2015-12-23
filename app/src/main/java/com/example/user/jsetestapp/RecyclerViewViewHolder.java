package com.example.user.jsetestapp;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

// class to hold a reference to each item of the recyclerView
public class RecyclerViewViewHolder extends ViewHolder {

    // Declare Controls for ItemView
    TextView location;
    TextView testDay;
    TextView testTime;
    TextView testDate;
    TextView testDeadlineTitle;
    TextView testDeadlineDetails;
    TextView testDeadlineDate;
    TextView testDeadlineTime;
    ImageButton imageButton;
    FrameLayout imageButtonWrapper;

    // Declare interfaces
    private RecyclerViewItemClickListener mListener;
    private RecyclerViewItemImageClickListener mImageListener;

    public RecyclerViewViewHolder(View itemView, RecyclerViewItemClickListener listener ,
                                  RecyclerViewItemImageClickListener imageListener) {
        super(itemView);

        // initialize and reference TextViews
        location = (TextView) this.itemView.findViewById(R.id.locationTextView);
        testDay = (TextView) this.itemView.findViewById(R.id.testDayTextView);
        testTime = (TextView) this.itemView.findViewById(R.id.testTimeTextView);
        testDate = (TextView) this.itemView.findViewById(R.id.testDateTextView);
        testDeadlineTitle = (TextView) this.itemView.findViewById(R.id.testDeadlineTitleTextView);
        testDeadlineDetails = (TextView) this.itemView.findViewById(R.id.testDeadlineDayTextView);
        testDeadlineDate = (TextView) this.itemView.findViewById(R.id.testDeadlineDateTextView);
        testDeadlineTime = (TextView) this.itemView.findViewById(R.id.testDeadlineTimeTextView);

        // initialize and reference ImageButton
        imageButton = (ImageButton) this.itemView.findViewById(R.id.imageButton);

        // initialize and reference FrameLayout
        imageButtonWrapper = (FrameLayout) this.itemView.findViewById(R.id.imageButtonWrapper);

        // set onClickListeners
        itemView.setOnClickListener(itemClickListener);
        imageButton.setOnClickListener(imageClickListener);
        imageButtonWrapper.setOnClickListener(imageClickListener);

        // set interfaces
        this.mListener = listener;
        this.mImageListener = imageListener;

    }

    /**
     * OnClickListener for itemView
     */
    OnClickListener itemClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // if interface is not null
            if (mListener != null) {
                // call onItemClick method
                mListener.onItemClick(v, getPosition());
            }
        }
    };

    /**
     * OnClickListener for imageButton and imageButtonWrapper
     */
    OnClickListener imageClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // if interface is not null
            if (mImageListener != null) {
                // call onImageItemClick
                mImageListener.onImageItemClick(v, getPosition());
            }
        }
    };
}
