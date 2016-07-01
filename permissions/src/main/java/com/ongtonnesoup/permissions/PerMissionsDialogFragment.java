package com.ongtonnesoup.permissions;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class PerMissionsDialogFragment extends DialogFragment {

    private static final String KEY_TITLE = "PERMISSIONS_ KEY_TITLE";
    private static final String KEY_MESSAGE = "PERMISSIONS_KEY_MESSAGE";

    private String title;
    private String message;
    private DialogInterface.OnClickListener onClickListener;

    public static PerMissionsDialogFragment newInstance(String title, String message, DialogInterface.OnClickListener onClickListener) {
        PerMissionsDialogFragment fragment = new PerMissionsDialogFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putString(KEY_MESSAGE, message);
        fragment.setArguments(args);
        fragment.setClickListener(onClickListener);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        title = args.getString(KEY_TITLE);
        message = args.getString(KEY_MESSAGE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(android.R.string.ok), onClickListener)
                .create();
    }

    public void setClickListener(DialogInterface.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
