package com.lib.alarm.control.abs;

import android.content.Context;

import com.lib.alarm.Alarm;

/**
 * 闹铃 控制
 *
 * @author EthanCo
 * @since 2017/6/16
 */

public interface IAlarmControl {
    void setAlarmClock(Context context, String action, Alarm alarm);

    void cancelAlarmClock(Context context, String action, Alarm alarm);
}
