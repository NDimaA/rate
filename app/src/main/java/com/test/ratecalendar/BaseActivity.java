package com.test.ratecalendar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Пользователь on 13.02.2016.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public <T> void openActivity(Class<T> tClass, Bundle b) {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if(view != null) {
            view.clearFocus();
        }
        Intent i = new Intent(this, tClass);
        i.putExtras(b);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.anim_zoom_out_right);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_zoom_in_left, R.anim.slide_out_right);
    }
}
