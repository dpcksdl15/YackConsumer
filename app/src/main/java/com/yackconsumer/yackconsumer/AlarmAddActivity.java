package com.yackconsumer.yackconsumer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AlarmAddActivity extends AppCompatActivity {

    EditText et_alram_name, et_dayNum;
    TextView tv_everyday, tv_daysetting, tv_dayToday, tv_calenderSetting ,tv_day, tv_week, tv_month;
    Button bt_weekend, bt_dayCycle, bt_save;
    LinearLayout ll_weekend, ll_week, ll_view4, ll_view5, ll_view6,ll_view3;
    RecyclerView re_alarmView;

    View.OnClickListener cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_add);

        et_alram_name = findViewById(R.id.et_alram_name);
        et_dayNum = findViewById(R.id.et_dayNum);
        tv_everyday = findViewById(R.id.tv_everyday);
        tv_daysetting = findViewById(R.id.tv_daysetting);
        bt_weekend = findViewById(R.id.bt_weekend);
        tv_dayToday = findViewById(R.id.tv_dayToday);
        tv_calenderSetting = findViewById(R.id.tv_calenderSetting);
        tv_day = findViewById(R.id.tv_day);
        tv_week = findViewById(R.id.tv_week);
        tv_month = findViewById(R.id.tv_month);
        bt_dayCycle = findViewById(R.id.bt_dayCycle);
        bt_save = findViewById(R.id.bt_save);
        ll_weekend = findViewById(R.id.ll_weekend);
        ll_week = findViewById(R.id.ll_week);
        ll_view3 = findViewById(R.id.ll_view3);
        ll_view4 = findViewById(R.id.ll_view4);
        ll_view5 = findViewById(R.id.ll_view5);
        ll_view6 = findViewById(R.id.ll_view6);
        re_alarmView = findViewById(R.id.re_alarmView);

        cl = new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                switch (view.getId()){

                    case R.id.tv_everyday:
                        tv_everyday.setBackgroundResource(R.drawable.background_alarm_setting);
                        tv_daysetting.setBackgroundResource(R.drawable.md_price_search_text);
                        tv_everyday.setTextColor(Color.parseColor("#067bfe"));
                        tv_daysetting.setTextColor(Color.GRAY);
                        bt_dayCycle.setTextColor(Color.GRAY);
                        bt_weekend.setTextColor(Color.GRAY);
                        ll_view5.setVisibility(View.GONE);
                        ll_weekend.setVisibility(View.GONE);
                        ll_week.setVisibility(View.GONE);
                        bt_weekend.setBackgroundResource(R.drawable.background_alarm_daysetting_w);
                        bt_dayCycle.setBackgroundResource(R.drawable.background_alarm_daysetting_w);
                        break;

                    case R.id.tv_daysetting:
                        ll_view5.setVisibility(View.VISIBLE);
                        tv_everyday.setBackgroundResource(R.drawable.md_price_search_text);
                        tv_daysetting.setBackgroundResource(R.drawable.background_alarm_setting);
                        tv_everyday.setTextColor(Color.GRAY);
                        tv_daysetting.setTextColor(Color.parseColor("#067bfe"));
                        break;

                    case R.id.tv_calenderSetting:
                        Intent intent = new Intent(getApplicationContext(), CalenderOpen.class);
                        startActivity(intent);
                        break;

                    case R.id.bt_weekend:
                        ll_weekend.setVisibility(View.VISIBLE);
                        ll_week.setVisibility(View.GONE);
                        bt_weekend.setTextColor(Color.parseColor("#067bfe"));
                        bt_dayCycle.setTextColor(Color.GRAY);
                        bt_weekend.setBackgroundResource(R.drawable.background_alarm_daysetting);
                        bt_dayCycle.setBackgroundResource(R.drawable.background_alarm_daysetting_w);
                        break;

                    case R.id.bt_dayCycle:
                        ll_weekend.setVisibility(View.GONE);
                        ll_week.setVisibility(View.VISIBLE);
                        bt_weekend.setTextColor(Color.GRAY);
                        bt_dayCycle.setTextColor(Color.parseColor("#067bfe"));
                        bt_weekend.setBackgroundResource(R.drawable.background_alarm_daysetting_w);
                        bt_dayCycle.setBackgroundResource(R.drawable.background_alarm_daysetting);
                        break;

                }

            }
        };
        tv_everyday.setOnClickListener(cl);
        tv_daysetting.setOnClickListener(cl);
        bt_weekend.setOnClickListener(cl);
        bt_dayCycle.setOnClickListener(cl);
        tv_calenderSetting.setOnClickListener(cl);

    }

}