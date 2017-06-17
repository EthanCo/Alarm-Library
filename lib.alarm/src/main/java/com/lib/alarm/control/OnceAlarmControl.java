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
 * 一次性闹铃的控制
 *
 * @author EthanCo
 * @since 2017/6/8
 */

public class OnceAlarmControl extends BaseAlarmControl {

    public OnceAlarmControl() {
    }

    @Override
    public void setAlarmClock(Context context, String action, Alarm alarm) {
        Date time = alarm.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
        Intent intent = new Intent(action);
        intent.putExtra(KEY_ALARM_ID, alarm.getId());
        PendingIntent pi = PengingIntentFactory.create(context, intent, alarm.getId()
                , PendingIntent.FLAG_UPDATE_CURRENT, BroadcastReceiver.class);
        AlarmLog.i(TAG, "setAlarmClock time:" + time);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
    }
}
