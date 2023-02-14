package com.odos.smartaqua.API;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;

import com.odos.smartaqua.R;


public class Loader {
    static Dialog d;
    ImageView progressBar;

    public Loader(Context ctx) {

        d = new Dialog(ctx, DialogInterface.BUTTON_NEGATIVE);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        d.setCancelable(true);
        d.setCanceledOnTouchOutside(false);
        d.setContentView(R.layout.dialog_customdialog);
        progressBar = (ImageView) d.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
      //  Ion.with(progressBar).load("file:///android_asset/appgifloader.gif");
    }

    public void show() {
        d.show();

    }

    public void dismiss() {
        d.dismiss();
    }
}
