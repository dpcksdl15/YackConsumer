package com.yackconsumer.yackconsumer;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.threeten.bp.DayOfWeek;

import java.util.Calendar;

public class SaturdayDecorator implements DayViewDecorator {

    private final Calendar calendar = Calendar.getInstance();

    public SaturdayDecorator(){

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        int week = day.getDate().with(DayOfWeek.SATURDAY).getDayOfMonth();
        return week == day.getDay();

        //copyTo 사용 불가로 위 방법으로 토요일만 받아오기
//        int week = calendar.get(Calendar.DAY_OF_WEEK);
//        return week == Calendar.SATURDAY;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.BLUE));
    }
}
