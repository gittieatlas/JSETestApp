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

        String title = getArguments().getString("title");
        String message = getArguments().getString("message");
        String positiveButton = getArguments().getString("positiveButton");
        String negativeButton = getArguments().getString("negativeButton");
        String neutralButton = getArguments().getString("neutralButton");
        Integer icon = getArguments().getInt("icon");
        final String TAG_LISTENER = getArguments().getString("tagListener");

        final EditText input = new EditText(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (TAG_LISTENER != null && TAG_LISTENER.equals("forgot_password")) {
            //input = new EditText(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            builder.setView(input);
        }

        // Setting Dialog Title
        if (title != null)
            builder.setTitle(title);

        // Setting Dialog Message
        if (message != null)
            builder.setMessage(message);

        // Setting alert dialog icon
        if (icon != null)
            builder.setIcon(icon);

        // Setting Positive Button
        if (positiveButton != null) {
            builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        if (Util.getActivity().getClass().equals(LoginActivity.class)) {
                            ((LoginActivity) getActivity()).dialogFragmentPositiveClick(TAG_LISTENER);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    try {
                        if (Util.getActivity().getClass().equals(MainActivity.class)) {
                            ((MainActivity) getActivity()).dialogFragmentPositiveClick(TAG_LISTENER);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    try {
                        if (Util.getActivity().getClass().equals(SplashActivity.class)) {
                            ((SplashActivity) getActivity()).dialogFragmentPositiveClick(TAG_LISTENER);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }

            });
        }

        // Setting Negative Button
        if (negativeButton != null) {
            builder.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // dismiss();

                }
            });
        }

        // Setting Neutral Button
        if (neutralButton != null) {
            builder.setNeutralButton(neutralButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        if (Util.getActivity().getClass().equals(LoginActivity.class)) {
                            ((LoginActivity) getActivity()).dialogFragmentNeutralClick(TAG_LISTENER);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    try {
                        if (Util.getActivity().getClass().equals(MainActivity.class)) {
                          //  ((MainActivity) getActivity()).dialogFragmentNeutralClick(TAG_LISTENER);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    try {
                        if (Util.getActivity().getClass().equals(SplashActivity.class)) {
                         //   ((SplashActivity) getActivity()).dialogFragmentNeutralClick(TAG_LISTENER);
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