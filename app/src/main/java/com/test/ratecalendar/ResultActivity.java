package com.test.ratecalendar;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.test.ratecalendar.utils.CUtils;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Пользователь on 13.02.2016.
 */
public class ResultActivity extends BaseActivity {

    public TextView tDate, tEur, tDol;
    public Typeface tf;
    public ProgressBar pbProcessing;
    public ResultActivity rAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rAct = (ResultActivity) this;

        setContentView(R.layout.activity_result);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tf = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLTPro-Md.ttf");

        Bundle b = getIntent().getExtras();

        updateView(b.getString("date"));

        pbProcessing = (ProgressBar) findViewById(R.id.pbProcessing);

        tDate = (TextView) findViewById(R.id.tDate);
        tDate.setTextSize(CUtils.dp2pixel(15));
        tDate.setTypeface(tf);
        tDate.setText(b.getString("dateTitle"));
        tEur = (TextView) findViewById(R.id.tEur);
        tEur.setTextSize(CUtils.dp2pixel(15));
        tEur.setTypeface(tf);
        tDol = (TextView) findViewById(R.id.tDol);
        tDol.setTextSize(CUtils.dp2pixel(15));
        tDol.setTypeface(tf);
    }

    public void updateView(String date) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + date, new CustomSaxRespHandler(new ValuteHandler()) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, ValuteHandler valuteHandler) {
                pbProcessing.setVisibility(View.GONE);
                Log.e("valute ", "--> " + valuteHandler.getValList());
                if(valuteHandler.getValList().size() > 0) {
                    for (int q = 0; q < valuteHandler.getValList().size(); q++) {
                        Valute valute = valuteHandler.getValList().get(q);
                        Animation animation = AnimationUtils.loadAnimation(rAct, R.anim.anim_bottom_to_top);
                        if (valute.getCharCode().equals("EUR")) {
                            tEur.startAnimation(animation);
                            tEur.setText("1Eur = " + valute.getValue() + " Rub");
                        } else if (valute.getCharCode().equals("USD")) {
                            tDol.startAnimation(animation);
                            tDol.setText("1$ = " + valute.getValue() + " Rub");
                        }
                    }
                } else {
                    CUtils.showAlert(rAct, "", "Произошла ошибка попробуйте позже", "", "OK");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, ValuteHandler valuteHandler) {
                CUtils.showAlert(rAct, "", "Произошла ошибка попробуйте позже", "", "OK");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
