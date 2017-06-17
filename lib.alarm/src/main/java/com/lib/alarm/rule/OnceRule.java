package com.lib.alarm.rule;

import android.content.Context;

import com.lib.alarm.Alarm;
import com.lib.alarm.control.OnceAlarmControl;
import com.lib.alarm.rule.abs.IRule;

import java.util.Calendar;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * 仅一次
 *
 * @author EthanCo
 * @since 2017/6/10
 */
public class OnceRule extends RealmObject implements IRule<Date> {
    private Date date;
    @Ignore
    private OnceAlarmControl alarmControl;

    public OnceRule() {
        alarmControl = new OnceAlarmControl();
    }

    @Override
    public boolean isEffectiveAlarm() {
        Calendar calendar = Calendar.getInstance();
        return date.after(calendar.getTime());
    }

    @Override
    public void setRuleEnforce(Date date) {
        date.setSeconds(0);
        this.date = date;
    }

    @Override
    public void onAlarmed(Context context, Alarm alarm) {
        alarm.cancelAlarmClock(context);
    }

    @Override
    public void setAlarmClock(Context context, String action, Alarm alarm) {
        alarmControl.setAlarmClock(context, action, alarm);
    }

    @Override
    public void cancelAlarmClock(Context context, String action, Alarm alarm) {
        alarmControl.cancelAlarmClock(context, action, alarm);
    }
}
