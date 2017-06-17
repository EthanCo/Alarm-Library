package com.lib.alarm.rule;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.lib.alarm.Alarm;
import com.lib.alarm.control.RepeatingAlarmControl;
import com.lib.alarm.rule.abs.IRule;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * 每天的规则
 *
 * @author EthanCo
 * @since 2017/6/10
 */

public class EverydayRule extends RealmObject implements IRule {

    private boolean isAllow = true;
    @Ignore
    private RepeatingAlarmControl alarmControl;

    public EverydayRule() {
        this.alarmControl = new RepeatingAlarmControl(AlarmManager.INTERVAL_DAY);
        //this.alarmControl = new RepeatingAlarmControl(60 * 1000 * 2);
    }

    @Override
    public boolean isEffectiveAlarm() {
        return isAllow;
    }

    @Override
    public void setRuleEnforce(Object o) {
        //do nothing
    }

    @Override
    public void onAlarmed(Context context, Alarm alarm) {
        //do nothing
    }

    @Override
    public void setAlarmClock(Context context, String action, Alarm alarm) {
        alarmControl.setAlarmClock(context, action, alarm);
    }

    @Override
    public void cancelAlarmClock(Context context, String action, Alarm alarm) {
        alarmControl.cancelAlarmClock(context, action, alarm);
    }

    protected EverydayRule(Parcel in) {
        this.isAllow = in.readByte() != 0;
    }

    public static final Parcelable.Creator<EverydayRule> CREATOR = new Parcelable.Creator<EverydayRule>() {
        @Override
        public EverydayRule createFromParcel(Parcel source) {
            return new EverydayRule(source);
        }

        @Override
        public EverydayRule[] newArray(int size) {
            return new EverydayRule[size];
        }
    };
}
