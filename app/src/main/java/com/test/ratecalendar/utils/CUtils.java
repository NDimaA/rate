package com.test.ratecalendar.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;

import com.test.ratecalendar.BaseActivity;
import com.test.ratecalendar.RCAplication;

/**
 * Created by Пользователь on 13.02.2016.
 */
public class CUtils {
    public static boolean isNetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) RCAplication.context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo acNetInfo = connectivityManager.getActiveNetworkInfo();
        return acNetInfo != null && acNetInfo.isAvailable() && acNetInfo.isConnected();
    }

    public static void showAlert(final BaseActivity con, String title, String msg, String posBtnText, String negBtnText) {
        AlertDialog.Builder offlineBuilder = new AlertDialog.Builder(con);
        offlineBuilder.setTitle("");
        offlineBuilder.setMessage(msg);
        if (posBtnText != null) {
            offlineBuilder.setPositiveButton(posBtnText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        if (negBtnText != null) {
            offlineBuilder.setNegativeButton(negBtnText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    con.onBackPressed();
                }
            });
        }

        offlineBuilder.show();
    }

    public static int pixel2dp(int pixel) {
        Resources r = RCAplication.context.getResources();
        int dip = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX,
                pixel,
                r.getDisplayMetrics()
        );

        return dip;
    }
    public static int dp2pixel(float dp) {
        Resources r = RCAplication.context.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );

        return px;
    }
}
