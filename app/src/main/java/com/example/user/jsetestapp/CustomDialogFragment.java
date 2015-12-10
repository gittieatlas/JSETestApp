package com.example.user.jsetestapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

public class CustomDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get dialog info from bundle and assign to variables
        String title = getArguments().getString("title");
        String message = getArguments().getString("message");
        String positiveButton = getArguments().getString("positiveButton");
        String negativeButton = getArguments().getString("negativeButton");
        String neutralButton = getArguments().getString("neutralButton");
        int icon = getArguments().getInt("icon");
        final String TAG_LISTENER = getArguments().getString("tagListener");

        // create a new editText for when dialog needs this control
        final EditText inputEditText = new EditText(getActivity());

        // create a new alertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // if TAG_LISTENER equals forgot_password
        if (TAG_LISTENER != null && TAG_LISTENER.equals("forgot_password")) {
            // create a new LinearLayout params
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            // set the LinearLayout params to the inputEditText
            inputEditText.setLayoutParams(lp);

            // add the inputEditText as a view to the builder
            builder.setView(inputEditText);
        }

        // Set Dialog Title
        if (title != null)
            builder.setTitle(title);

        // Set Dialog Message
        if (message != null)
            builder.setMessage(message);

        // Set Alert Dialog Icon
        //if (icon != null)
            builder.setIcon(icon);

        // Set Positive Button
        if (positiveButton != null) {
            builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        // if current activity is of type LoginActivity
                        if (Util.getActivity().getClass().equals(LoginActivity.class)) {
                            // call dialogFragmentPositiveClick in the LoginActivity class
                            ((LoginActivity) getActivity()).dialogFragmentPositiveClick(TAG_LISTENER);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    // if current activity is of type MainActivity
                    try {
                        if (Util.getActivity().getClass().equals(MainActivity.class)) {
                        // call dialogFragmentClick in the MainActivity class
                            ((MainActivity) getActivity()).dialogFragmentPositiveClick(TAG_LISTENER);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    // if current activity is of type SplashActivity
                    try {
                        if (Util.getActivity().getClass().equals(SplashActivity.class)) {
                            // call dialogFragmentPositiveClick in the SplashActivity class
                            ((SplashActivity) getActivity()).dialogFragmentPositiveClick(TAG_LISTENER);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }

            });
        }

        // Set Negative Button
        if (negativeButton != null) {
            builder.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }

        // Set Neutral Button
        if (neutralButton != null) {
            builder.setNeutralButton(neutralButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        // if current activity is of type LoginActivity
                        if (Util.getActivity().getClass().equals(LoginActivity.class)) {
                            // call dialogFragmentNeutralClick in the LoginActivity class
                            ((LoginActivity) getActivity()).dialogFragmentNeutralClick(TAG_LISTENER);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }

        return builder.create();
    }

}