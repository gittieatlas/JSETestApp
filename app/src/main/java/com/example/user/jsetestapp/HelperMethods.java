package com.example.user.jsetestapp;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class HelperMethods extends Activity {

    MainActivity mainActivity;

    public HelperMethods() {

    }

    public void replaceFragment(int container, Fragment fragment) {
        mainActivity.scrollView.scrollTo(0, 0); // Scroll to top
        mainActivity.getFragmentManager().beginTransaction().replace(container, fragment).addToBackStack(null).commit();
    }

    public void addFragment(int container, Fragment fragment) {
        mainActivity.scrollView.scrollTo(0, 0); // Scroll to top
        mainActivity.getFragmentManager().beginTransaction().add(container, fragment).addToBackStack(null).commit();
    }

    public void addDataToSpinner(ArrayList<String> arrayList, Spinner spinner, String tag) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mainActivity.getApplicationContext(),
                R.layout.spinner_dropdown_item, arrayList);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);
        spinner.setAdapter(adapter);

        if (tag.equals("location")) spinner.setSelection(2); // ToDo set posotion to defaultLocation
        else spinner.setSelection(0);
    }

    public void scheduleTest(){
        if (mainActivity.user.isJseMember) {
            mainActivity.showDialog("Schedule a Test", null, "CALL", "CANCEL", null, R.drawable.ic_calendar_clock_grey600_24dp, "schedule_test");
        } else {
            String message ="To schedule a test you need to be a JSE member. Please call the JSE office to register.";
            mainActivity.showDialog("Become a JSE Member", message, "CALL", "CANCEL", null, R.drawable.ic_calendar_clock_grey600_24dp, "become_jse_member");
        }

    }

    /**
     * Function to make the first letter caps and the rest lowercase.
     *
     * @param data          - capitalize this
     * @return String       - alert message?
     *
     */
    static public String firstLetterCaps(String data) {
        String firstLetter = data.substring(0, 1).toUpperCase();
        String restLetters = data.substring(1).toLowerCase();
        return firstLetter + restLetters;
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    /**
     * Sets ListView height dynamically based on the height of the items.
     *
     * @param listView to be resized
     * @return true if the listView is successfully resized, false otherwise
     */
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

}