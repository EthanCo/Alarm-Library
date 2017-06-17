package com.lib.alarm.week;

import com.lib.alarm.enums.WeekAnno;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * 星期 容器
 *
 * @author EthanCo
 * @since 2017/6/10
 */

public class WeekFlags extends RealmObject {
    public RealmList<DayOfWeek> dayOfWeeks = new RealmList<>();

    public void setWeekdayFlag() {
        dayOfWeeks.clear();
        addDayOfWeek(WeekAnno.MONDAY);
        addDayOfWeek(WeekAnno.SATURDAY);
        addDayOfWeek(WeekAnno.THURSDAY);
        addDayOfWeek(WeekAnno.THURSDAY);
        addDayOfWeek(WeekAnno.FRIDAY);
    }

    public void setWeekendFlag() {
        dayOfWeeks.clear();
        addDayOfWeek(WeekAnno.SATURDAY);
        addDayOfWeek(WeekAnno.SUNDAY);
    }

    public void addDayOfWeek(DayOfWeek weekData) {
        if (!dayOfWeeks.contains(weekData)) {
            dayOfWeeks.add(weekData);
        }
    }

    public void addDayOfWeek(@WeekAnno int weekData) {
        DayOfWeek dayofWeek = new DayOfWeek(weekData);
        if (!dayOfWeeks.contains(dayofWeek)) {
            dayOfWeeks.add(dayofWeek);
        }
    }

    public void removeDayOfWeek(DayOfWeek other) {
        DayOfWeek weekDate;
        for (int i = 0; i < dayOfWeeks.size(); i++) {
            weekDate = dayOfWeeks.get(i);
            if (compareDayOfWeek(weekDate, other)) {
                dayOfWeeks.remove(weekDate);
                break;
            }
        }
    }

    public void removeDayOfWeek(@WeekAnno Integer other) {
        DayOfWeek weekData;
        for (int i = 0; i < dayOfWeeks.size(); i++) {
            weekData = dayOfWeeks.get(i);
            if (weekData.getWhatDay() == other) {
                dayOfWeeks.remove(weekData);
                break;
            }
        }
    }

    public boolean contains(DayOfWeek other) {
        for (DayOfWeek week : dayOfWeeks) {
            if (compareDayOfWeek(week, other)) {
                return true;
            }
        }
        return false;
    }

    private boolean compareDayOfWeek(DayOfWeek weekData, DayOfWeek other) {
        return weekData.getWhatDay() == other.getWhatDay();
    }

    public WeekFlags() {
    }
}
