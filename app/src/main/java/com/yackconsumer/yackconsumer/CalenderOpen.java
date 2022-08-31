package com.yackconsumer.yackconsumer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import org.threeten.bp.DayOfWeek;

import java.util.Calendar;
import java.util.List;

public class CalenderOpen extends AppCompatActivity {

    Button bt_save;
    MaterialCalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_open);
        bt_save = findViewById(R.id.bt_save);
        calendar = findViewById(R.id.calendar);

        calendar.state().edit().setFirstDayOfWeek(DayOfWeek.of(Calendar.SATURDAY)).commit();
        calendar.addDecorators(new SaturdayDecorator(), new SundayDecorator());

        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String startDay = date.getDate().toString();
                Log.d("날짜확인", "시작일 : "+ startDay);
            }
        });

        calendar.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
                String startDay = dates.get(0).getDate().toString();
                String endDay = dates.get(dates.size()-1).getDate().toString();
                Log.d("날짜확인", "시작일 : "+ startDay +", 종료일 : "+ endDay);
            }
        });

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCalendarDate();
            }
        });
    }


    public void getCalendarDate(){


    }

}
