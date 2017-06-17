package com.lib.alarm;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.lib.alarm.enums.AlarmMode;
import com.lib.alarm.rule.abs.IRule;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 闹钟
 *
 * @author EthanCo
 * @since 2017/6/10
 */

public class Alarm extends RealmObject {
    @PrimaryKey
    private int id;
    private int mode;
    private boolean enabled = true;
    private Date date;
    private String ruleJson;
    //由于Realm不支持接口，故采用json数组的方式保存
    private String ruleClassName;
    private String bellClassName;
    private String bellName;
    private String routerIntentAction; //BoadcastReceiver的Intent Action，用作中转
    private String targetIntentAction; //目标IntentAction

    public Alarm() {
    }

    public Alarm(Date date) {
        this.date = date;
        this.date.setSeconds(0);
        this.mode = AlarmMode.AROUSE;
    }

    public Alarm(Date date, int mode) {
        date.setSeconds(0);
        this.date = date;
        this.mode = mode;
    }

    public IRule getRule() {
        if (TextUtils.isEmpty(ruleJson) || TextUtils.isEmpty(ruleClassName)) {
            return null;
        }
        Class<IRule> cls = null;
        try {
            cls = (Class<IRule>) Class.forName(ruleClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (cls == null) {
            return null;
        }
        return new Gson().fromJson(ruleJson, cls);
    }

    public String getTargetIntentAction() {
        return targetIntentAction;
    }

    public void setTargetIntentAction(String targetIntentAction) {
        this.targetIntentAction = targetIntentAction;
    }

    public void setAlarmClock(Context context) {
        getRule().setAlarmClock(context, routerIntentAction, this);
    }

    public void cancelAlarmClock(Context context) {
        getRule().cancelAlarmClock(context, routerIntentAction, this);
        enabled = false;
    }

    public void onAlarmed(Context context) {
        getRule().onAlarmed(context, this);
    }

    public void setRule(IRule rule) {
        this.ruleJson = new Gson().toJson(rule);
        this.ruleClassName = rule.getClass().getName();
    }

    public String getRouterIntentAction() {
        return routerIntentAction;
    }

    public void setRouterIntentAction(String routerIntentAction) {
        this.routerIntentAction = routerIntentAction;
    }

    public String getRuleJson() {
        return ruleJson;
    }

    public void setRuleJson(String ruleJson) {
        this.ruleJson = ruleJson;
    }

    public String getRuleClassName() {
        return ruleClassName;
    }

    public void setRuleClassName(String ruleClassName) {
        this.ruleClassName = ruleClassName;
    }

    public String getBellClassName() {
        return bellClassName;
    }

    public void setBell(String bellClassName, String bellName) {
        this.bellClassName = bellClassName;
        this.bellName = bellName;
    }

    public void setBellClassName(String bellClassName) {
        this.bellClassName = bellClassName;
    }

    public String getBellName() {
        return bellName;
    }

    public void setBellName(String bellName) {
        this.bellName = bellName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @AlarmMode
    public int getMode() {
        return mode;
    }

    //@AlarmMode
    public void setMode(@AlarmMode int mode) {
        this.mode = mode;
    }

    public Date getTime() {
        return date;
    }

    public void setTime(Date date) {
        date.setSeconds(0);
        this.date = date;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", mode=" + mode +
                ", enabled=" + enabled +
                ", date=" + date +
                ", ruleJson='" + ruleJson + '\'' +
                ", ruleClassName='" + ruleClassName + '\'' +
                ", bellClassName='" + bellClassName + '\'' +
                ", bellName='" + bellName + '\'' +
                ", routerIntentAction='" + routerIntentAction + '\'' +
                ", targetIntentAction='" + targetIntentAction + '\'' +
                '}';
    }
}
