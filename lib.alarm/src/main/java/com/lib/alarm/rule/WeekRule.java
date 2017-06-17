package com.lib.alarm.rule;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.lib.alarm.Alarm;
import com.lib.alarm.week.DayOfWeek;
import com.lib.alarm.week.WeekFlags;
import com.lib.alarm.control.RepeatingAlarmControl;
import com.lib.alarm.rule.abs.IRule;

import java.util.Calendar;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * 一周的规则
 *
 * @author EthanCo
 * @since 2017/6/10
 */
public class WeekRule extends RealmObject implements IRule<WeekFlags> {
    private WeekFlags week;
    @Ignore
    private RepeatingAlarmControl alarmControl;

    public WeekRule() {
        this.alarmControl = new RepeatingAlarmControl(AlarmManager.INTERVAL_DAY);
    }

    @Override
    public boolean isEffectiveAlarm() {
        DayOfWeek dayOfWeek = new DayOfWeek(Calendar.getInstance());
        return week.contains(dayOfWeek);
    }

    @Override
    public void setRuleEnforce(WeekFlags week) {
        this.week = week;
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

    protected WeekRule(Parcel in) {
        this.week = in.readParcelable(WeekFlags.class.getClassLoader());
    }

    public static final Parcelable.Creator<WeekRule> CREATOR = new Parcelable.Creator<WeekRule>() {
        @Override
        public WeekRule createFromParcel(Parcel source) {
            return new WeekRule(source);
        }

        @Override
        public WeekRule[] newArray(int size) {
            return new WeekRule[size];
        }
    };
}
