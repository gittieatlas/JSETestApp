package com.example.user.jsetestapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class LoginActivityDialogFragment extends android.app.DialogFragment {
    LoginActivity loginActivity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();

        String title = getArguments().getString("title");
        String message = getArguments().getString("message");
        String positiveButton = getArguments().getString("positiveButton");
        String negativeButton = getArguments().getString("negativeButton");
        String neutralButton = getArguments().getString("neutralButton");
        Integer icon = getArguments().getInt("icon");
        final String TAG_LISTENER = getArguments().getString("tagListener");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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
                    ((LoginActivity) getActivity()).dialogListeners.positiveButtonOnClickListener(TAG_LISTENER);
                }

            });
        }

        // Setting Negative Button
        if (negativeButton != null) {
            builder.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // dismiss();
                    ((LoginActivity) getActivity()).dialogListeners.negativeButtonOnClickListener(TAG_LISTENER);
                }
            });
        }

        if (neutralButton != null) {
        builder.setNeutralButton(neutralButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((LoginActivity) getActivity()).dialogListeners.neutralButtonOnClickListener(TAG_LISTENER);
            }
        });
        }

        return builder.create();
    }

    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }

}