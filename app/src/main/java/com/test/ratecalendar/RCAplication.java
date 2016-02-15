package com.test.ratecalendar;

import android.app.Application;
import android.content.Context;

/**
 * Created by Пользователь on 18.11.2015.
 */
public class RCAplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
