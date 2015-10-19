package com.example.user.jsetestapp;

import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ResultsFragment extends Fragment implements MyItemClickListener, MyItemLongClickListener, MyItemImageClickListener {


    private RecyclerView mRecyclerView;

    private List<DataObject> mData;
    private MyAdapter mAdapter;


    //Controls
    FloatingActionButton fab;
    View rootView;

    //Fragments

    //Activities
    MainActivity mainActivity;
    SearchFragment searchFragment;

    //Variables
//    private RecyclerView mRecyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager mLayoutManager;
//    private static String LOG_TAG = "RecyclerViewActivity";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_results, container, false);

        initializeViews(rootView);
        mainActivity.setToolbarTitle(R.string.toolbar_title_results);
        setupFab();
        //Set icon
        mainActivity.toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_left_white_24dp));

        initView();
        initData();

//        setUpRecyclerView();

        return rootView;
    }

    /**
     * ³õÊ¼»¯RecylerView
     */
    private void initView(){
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        MyLayoutManager manager = new MyLayoutManager(mainActivity.getApplicationContext());
        manager.setOrientation(LinearLayout.VERTICAL);//Ä¬ÈÏÊÇLinearLayout.VERTICAL
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData(){
//        this.mData = new ArrayList<DataObject>();
//        for(int i=0;i<20;i++){
//            MyItemBean bean = new MyItemBean();
//            bean.tv = "Xmy"+i;
//            mData.add(bean);
//        }
        this.mAdapter = new MyAdapter(getDataSet());
        this.mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration decoration = new MyDecoration(mainActivity.getApplicationContext());
        this.mRecyclerView.addItemDecoration(decoration);
        this.mAdapter.setOnItemClickListener(this);
        this.mAdapter.setOnItemLongClickListener(this);
       this.mAdapter.setOnItemImageClickListener(this);


    }

    /**
     * Item click
     */
    @Override
    public void onItemClick(View view, int postion) {
      //  MyItemBean bean = mData.get(postion);
        //if(bean != null){
        //Toast.makeText(mainActivity.getApplicationContext(), "item clicked", Toast.LENGTH_SHORT).show();

        mainActivity.showDialog("CALL", "Call JSE");
    }

    @Override
    public void onItemLongClick(View view, int postion) {
      //  MyItemBean bean = mData.get(postion);
//        if(bean != null){
            Toast.makeText(mainActivity.getApplicationContext(), "Long Click ", Toast.LENGTH_SHORT).show();
       // }
    }

    @Override
    public void onImageItemClick(View view, int postion) {
        Toast.makeText(mainActivity.getApplicationContext(), "Image Click ", Toast.LENGTH_SHORT).show();
    }


    private ArrayList<DataObject> getDataSet() {
        return mainActivity.getTestsFitlteredArrayList();
    }

    private void initializeViews(View rootView) {

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
    }


    private void setupFab() {

        fab.setOnClickListener(fabListener);
    }

    OnClickListener fabListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            mainActivity.showDialog("test", "fab");
        }
    };

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    @Override
    public void onStop() {

        super.onStop();
        // Set navigation icon
        mainActivity.toolbar.setNavigationIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));


    }


}