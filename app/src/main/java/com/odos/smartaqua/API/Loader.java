package com.odos.smartaqua.API;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;

import com.odos.smartaqua.R;


public class Loader {
    ProgressDialog progress;

    public Loader(Context ctx) {
        progress = new ProgressDialog(ctx);
        progress.setMessage("Loading..");
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(true);
    }

    public void show() {
        if (null!= progress && !progress.isShowing())
            progress.show();

    }

    public void dismiss() {
        if (null!= progress && progress.isShowing())
            progress.dismiss();
    }
}
