package com.example.user.jsetestapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Rochel on 10/9/2015.
 */
public class MyDialogFragment extends android.app.DialogFragment {
    MainActivity mainActivity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();

        String title = getArguments().getString("title");
        String message = getArguments().getString("message");
        String positiveButton = getArguments().getString("positiveButton");
        String negativeButton = getArguments().getString("negativeButton");
        int icon = getArguments().getInt("icon");
        final String TAG_LISTENER = getArguments().getString("tagListener");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(icon);
        builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ((MainActivity) getActivity()).helperMethods.positiveButtonOnClickListener(TAG_LISTENER);

            }

        });

        builder.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // dismiss();
                ((MainActivity) getActivity()).helperMethods.negativeButtonOnClickListener(TAG_LISTENER);
            }
        });

//            builder.setNeutralButton(positiveButton, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }

        return builder.create();
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

}