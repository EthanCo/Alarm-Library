package com.lib.alarm.control;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lib.alarm.Alarm;
import com.lib.alarm.control.abs.BaseAlarmControl;
import com.lib.alarm.utils.log.AlarmLog;

import java.util.Calendar;
import java.util.Date;

/**
 * 重复性闹铃的控制
 *
 * @author EthanCo
 * @since 2017/6/8
 */

public class RepeatingAlarmControl extends BaseAlarmControl {
    private long interval;

    public RepeatingAlarmControl() {
        interval = AlarmManager.INTERVAL_DAY;
    }

    /**
     * @param interval 闹铃间隔时间 (毫秒)
     */
    public RepeatingAlarmControl(long interval) {
        this.interval = interval;
    }

    @Override
    public void setAlarmClock(Context context, String action, final Alarm alarm) {
        Date time = alarm.getTime();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        checkTime(calendar);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
        Intent intent = new Intent(action);
        intent.putExtra(KEY_ALARM_ID, alarm.getId());
        PendingIntent pi = PengingIntentFactory.create(context, intent, alarm.getId(),
                PendingIntent.FLAG_UPDATE_CURRENT, BroadcastReceiver.class);

        AlarmLog.i(TAG, "setAlarmClock alarm:" + alarm);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pi);
    }

    public void checkTime(Calendar calendar) {
        //如果闹钟时间比现在的时间要早，那么设置闹铃日期到明天
        if (calendar.before(Calendar.getInstance())) {
            Calendar tomorrow = Calendar.getInstance();
            tomorrow.setTime(getTomorrow(new Date()));
            calendar.set(Calendar.YEAR, tomorrow.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, tomorrow.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, tomorrow.get(Calendar.DAY_OF_MONTH));
            AlarmLog.i(TAG, "checkTime:" + calendar.toString());
        }
    }

    public Date getTomorrow(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        return calendar.getTime();
    }
}
