package com.test.ratecalendar.adapter;

import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.ratecalendar.MainActivity;
import com.test.ratecalendar.R;
import com.test.ratecalendar.utils.CUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Пользователь on 12.02.2016.
 */
public class DPAdapter extends BaseAdapter {

    public JSONArray items;
    public MainActivity mainActivity;
    public String[] monthName;
    public String[] monthNameFull;
    public String[] dayName;

    public DPAdapter(MainActivity mAct, JSONArray ja) {
        items = ja;
        mainActivity = mAct;
        monthName = new String[]{"Янв", "Фев", "Март", "Апр", "Май", "Июнь", "Июль", "Авг", "Сент", "Окт", "Нояб", "Дек"};
        monthNameFull = new String[]{"Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"};
        dayName = new String[]{"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
    }

    @Override
    public int getCount() {
        return items.length();
    }

    @Override
    public JSONObject getItem(int position) {
        return items.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(mainActivity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.template_dp_item, null);
        }

        final JSONObject jo = getItem(position);
        JSONObject joNext = new JSONObject();
        Log.e("position", "--> " + position + " " + (getCount()-1));
        if(position != getCount()-1) {
            joNext = getItem(position + 1);
        }

        LinearLayout wrapper = (LinearLayout) convertView.findViewById(R.id.itemWrapp);
        Typeface type= Typeface.createFromAsset(mainActivity.getAssets(), "fonts/HelveticaNeueLTPro-Md.ttf");

        final TextView lt = (TextView) convertView.findViewById(R.id.tLine);
        final TextView bt = (TextView) convertView.findViewById(R.id.bLine);

        final TextView tv = (TextView) convertView.findViewById(R.id.rightListItem);
        tv.setTextSize(CUtils.dp2pixel(10));
        final TextView ltv = (TextView) convertView.findViewById(R.id.leftListItem);
        ltv.setTextSize(CUtils.dp2pixel(6));

        tv.setTypeface(type, Typeface.NORMAL);
        ltv.setTypeface(type, Typeface.NORMAL);

        if(joNext != null) {
            if (joNext.optInt("month_num") != jo.optInt("month_num")) {
                ltv.setText(monthName[jo.optInt("month_num")]);
                ltv.setTextColor(mainActivity.getResources().getColor(R.color.workd));
            } else {
                ltv.setText("");
            }
        } else {
            ltv.setText(monthName[jo.optInt("month_num")]);
        }

        final String[] days = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09"};

        wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.currentPos = position;
                String day = jo.optString("day");
                if (jo.optInt("day") <= 9) {
                    day = days[jo.optInt("day")];
                }

                mainActivity.currDate = jo.optString("day") + " " + monthNameFull[jo.optInt("month_num")] + " " + jo.optString("year");
                mainActivity.currDateForServer = day + "/" + jo.optString("month") + "/" + jo.optString("year");

                notifyDataSetChanged();
            }
        });

        if(jo.optInt("dayOFWeek") == 6 || jo.optInt("dayOFWeek") == 7) {
            tv.setTextColor(mainActivity.getResources().getColor(R.color.weekend));
        } else {
            tv.setTextColor(mainActivity.getResources().getColor(R.color.workd));
        }

        if(mainActivity.currentPos != null) {
            if(mainActivity.currentPos == position){
                lt.setVisibility(View.VISIBLE);
                bt.setVisibility(View.VISIBLE);
                ltv.setText(jo.optString("year") + " " + monthName[jo.optInt("month_num")]);
                tv.setText(dayName[jo.optInt("dayOFWeek") - 1] + " " + jo.optString("day"));
                tv.setTextColor(mainActivity.getResources().getColor(R.color.checked));
                ltv.setTextColor(mainActivity.getResources().getColor(R.color.checked));
            } else {
                lt.setVisibility(View.GONE);
                bt.setVisibility(View.GONE);
                tv.setText(dayName[jo.optInt("dayOFWeek") - 1] + " " + jo.optString("day"));
            }
        } else {
            lt.setVisibility(View.GONE);
            bt.setVisibility(View.GONE);
            tv.setText(dayName[jo.optInt("dayOFWeek") - 1] + " " + jo.optString("day"));
        }

        return convertView;
    }
}
