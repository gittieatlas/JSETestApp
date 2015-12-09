package com.example.user.jsetestapp;

import android.view.View;

public interface RecyclerViewItemClickListener {
    /**
     * This method is called when a item in the RecyclerView is clicked
     * @param view - view that was clicked
     * @param position - position of item in the recyclerView
     */
    public void onItemClick(View view,int position);
}
