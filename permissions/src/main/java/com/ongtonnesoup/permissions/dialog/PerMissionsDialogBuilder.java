package com.ongtonnesoup.permissions.dialog;

import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;

public interface PerMissionsDialogBuilder {

    DialogFragment build(String title, String message, DialogInterface.OnClickListener onClickListener);

}
