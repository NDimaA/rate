package com.test.ratecalendar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.test.ratecalendar.adapter.DPAdapter;
import com.test.ratecalendar.utils.CUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends BaseActivity {

    public ListView datePicker;
    public DPAdapter dpAdapter;
    public Integer currentPos = null;
    public TextView rateBtn;
    public String currDate = "";
    public String currDateForServer = "";
    public MainActivity mAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAct = (MainActivity) this;

        rateBtn = (TextView) findViewById(R.id.showRateBtn);
        rateBtn.setTextSize(CUtils.dp2pixel(10));
        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CUtils.isNetAvailable()) {
                    Bundle b = new Bundle();
                    b.putString("date", currDateForServer);
                    b.putString("dateTitle", currDate);

                    openActivity(ResultActivity.class, b);
                } else {
                    CUtils.showAlert(mAct, "", "Отсутствует подключение к интернету", "OK", "");
                }
            }
        });

        datePicker = (ListView) findViewById(R.id.datePicker);
        dpAdapter = new DPAdapter(this, getDaysForDP(2015, 3));
        datePicker.setAdapter(dpAdapter);
    }


    JSONArray getDaysForDP(int year, int numYears) {
        JSONArray ja = new JSONArray();

        for(int q=0;q<numYears;q++) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.add(Calendar.YEAR, q);

            int numOfDays = cal.getActualMaximum(Calendar.DAY_OF_YEAR);


            for(int d=1;d<=numOfDays;d++){
                Calendar dCal = Calendar.getInstance();
                dCal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
                dCal.set(Calendar.DAY_OF_YEAR, d);

                String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

                JSONObject jo = new JSONObject();
                try {
                    jo.put("year", dCal.get(Calendar.YEAR));
                    jo.put("month",month[dCal.get(Calendar.MONTH)]);
                    jo.put("month_num",dCal.get(Calendar.MONTH));
                    jo.put("day", dCal.get(Calendar.DAY_OF_MONTH));
                    jo.put("dayOFWeek", dCal.get(Calendar.DAY_OF_WEEK));
                } catch (JSONException error){
                    Log.e("error", "--> " + error);
                }
                ja.put(jo);
            }
        }

        return ja;
    }

}
