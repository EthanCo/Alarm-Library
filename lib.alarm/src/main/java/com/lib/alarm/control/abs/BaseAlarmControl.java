package com.lib.alarm.control.abs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lib.alarm.Alarm;
import com.lib.alarm.control.PengingIntentFactory;
import com.lib.alarm.utils.log.AlarmLog;

/**
 * 闹铃控制 - 基类
 *
 * @author EthanCo
 * @since 2017/6/16
 */

public abstract class BaseAlarmControl implements IAlarmControl {
    public static final String TAG = "Z-AlarmControl";
    public static final String KEY_ALARM_ID = "alarm_id";

    @Override
    public void cancelAlarmClock(Context context, String action, Alarm alarm) {
        cancelAlarmClock(context, action, alarm.getId());
    }

    public void cancelAlarmClock(Context context, String action, int alarmId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
        Intent intent = new Intent(action);
        PendingIntent pi = PengingIntentFactory.create(context, intent, alarmId,
                PendingIntent.FLAG_CANCEL_CURRENT, BroadcastReceiver.class);
        AlarmLog.i(TAG, "cancelAlarm:" + pi + " alarmId:" + alarmId);
        alarmManager.cancel(pi);
    }
}
