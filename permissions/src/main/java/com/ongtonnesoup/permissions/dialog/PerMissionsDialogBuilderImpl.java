package com.ongtonnesoup.permissions.dialog;

import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;

import com.ongtonnesoup.permissions.PerMissionsDialogFragment;

public class PerMissionsDialogBuilderImpl implements PerMissionsDialogBuilder {

    @Override
    public DialogFragment build(String title, String message, DialogInterface.OnClickListener onClickListener) {
        return PerMissionsDialogFragment.newInstance(title, message, onClickListener);
    }
    
}
