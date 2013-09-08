package com.mobius.legend.ui;

import com.mobius.legend.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class UIUtils {
	public static void createConfirmationDialog(Context ctx, String message, OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setMessage(message);
		builder.setPositiveButton(R.string.Yes, listener);
		builder.setNegativeButton(R.string.No, null);
		builder.show();
	}
}
