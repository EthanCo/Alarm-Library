package com.lib.alarm.rule.abs;

import android.content.Context;

import com.lib.alarm.Alarm;
import com.lib.alarm.control.abs.IAlarmControl;

import io.realm.RealmModel;

/**
 * @author EthanCo
 * @since 2017/6/10
 */

public interface IRule<T> extends IAlarmControl, RealmModel {
    //是否是有效的闹钟 (是否过期)
    boolean isEffectiveAlarm();

    //设置Rule的核心对象
    void setRuleEnforce(T t);

    //当闹铃响时的逻辑处理
    void onAlarmed(Context context, Alarm alarm);
}
