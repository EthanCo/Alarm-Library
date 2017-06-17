package com.lib.alarm.week;

import com.lib.alarm.enums.WeekAnno;

import java.util.Calendar;

import io.realm.RealmObject;

/**
 * 星期数据
 *
 * @author EthanCo
 * @since 2017/6/10
 */

public class DayOfWeek extends RealmObject {
    @WeekAnno
    private int whatDay;

    public DayOfWeek() {
    }

    public DayOfWeek(Calendar calendar) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        whatDay = createWeekDate(dayOfWeek);
    }

    @WeekAnno
    private int createWeekDate(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1:
                return WeekAnno.SUNDAY;
            case 2:
                return WeekAnno.MONDAY;
            case 3:
                return WeekAnno.TUESDAY;
            case 4:
                return WeekAnno.WEDNESDAY;
            case 5:
                return WeekAnno.THURSDAY;
            case 6:
                return WeekAnno.FRIDAY;
            case 7:
                //fall through
            default:
                return WeekAnno.FRIDAY;
        }
    }

    public DayOfWeek(int weekData) {
        this.whatDay = weekData;
    }

    @WeekAnno
    public int getWhatDay() {
        return whatDay;
    }

    public void setWhatDay(@WeekAnno int whatDay) {
        this.whatDay = whatDay;
    }
}
