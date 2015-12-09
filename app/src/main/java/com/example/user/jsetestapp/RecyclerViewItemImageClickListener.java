package com.example.user.jsetestapp;

import android.view.View;

public interface RecyclerViewItemImageClickListener {
    /**
     * This method is called when a image in the RecyclerView is clicked
     * @param view - view that was clicked
     * @param position - position of item where image is in the recyclerView
     */
    public void onImageItemClick(View view, int position);
}
