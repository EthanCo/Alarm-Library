package com.lib.alarm;

import com.lib.alarm.control.RepeatingAlarmControl;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * 闹铃时间过时 重设 测试
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class OutmodedTimeUnitTest {

    @Test
    public void checkYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 16);

        RepeatingAlarmControl alarmControl = new RepeatingAlarmControl();
        alarmControl.checkTime(calendar);

        assertEquals(18, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void checkToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 17);

        RepeatingAlarmControl alarmControl = new RepeatingAlarmControl();
        alarmControl.checkTime(calendar);

        assertEquals(18, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void checkTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 18);

        RepeatingAlarmControl alarmControl = new RepeatingAlarmControl();
        alarmControl.checkTime(calendar);

        assertEquals(18, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void checkTheDayOfTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 19);

        RepeatingAlarmControl alarmControl = new RepeatingAlarmControl();
        alarmControl.checkTime(calendar);

        assertEquals(19, calendar.get(Calendar.DAY_OF_MONTH));
    }
}