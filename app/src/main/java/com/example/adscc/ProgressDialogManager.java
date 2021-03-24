package com.example.adscc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

public class ProgressDialogManager

{

    public static AlertDialog getProgressDialog(Context context)
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ProgressBar progressBar = new ProgressBar(context);
        Sprite doubleBounce = new DoubleBounce();
        doubleBounce.setColor(context.getResources().getColor(R.color.mycolor));
        progressBar.setIndeterminateDrawable(doubleBounce);
        builder.setView(progressBar);
        builder.setCancelable(false);
        AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return alertDialog;
    }

}
