package com.example.user.jsetestapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;

/**
 * Created by Rochel on 10/9/2015.
 */
public class MyDialogFragment extends android.app.DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();

        String title = getArguments().getString("title");
        String message = getArguments().getString("message");
        String number = getArguments().getString("number");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dismiss();
                String number = getArguments().getString("number");
                Uri call = Uri.parse("tel:" + number);
                Intent intent = new Intent(Intent.ACTION_CALL, call);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        return builder.create();
    }
}