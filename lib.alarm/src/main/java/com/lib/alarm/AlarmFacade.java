package com.lib.alarm;

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;

import com.ethanco.logbase.IEntireLog;
import com.lib.alarm.bell.abs.IBell;
import com.lib.alarm.control.AlarmReceiver;
import com.lib.alarm.enums.AlarmMode;
import com.lib.alarm.realm.RealmWrap;
import com.lib.alarm.rule.OnceRule;
import com.lib.alarm.rule.abs.IRule;
import com.lib.alarm.utils.log.AlarmLog;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.content.ContentValues.TAG;

/**
 * Alarm 门面
 *
 * @author EthanCo
 * @since 2017/6/16
 */

public class AlarmFacade {
    public static void init(Context context) {
        init(context, null);
    }

    public static void init(Context context, IEntireLog entireLog) {
        RealmWrap.init(context);
        if (entireLog != null) {
            AlarmLog.setLog(entireLog);
        }
        resetAlarmClock(context);
    }

    //重新设置闹钟 (应用退出后闹铃会被清除)
    private static void resetAlarmClock(final Context context) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                SystemClock.sleep(1000 * 5);

                Realm realm = RealmWrap.getRealm();
                RealmResults<Alarm> alarms = realm.where(Alarm.class).findAll();
                AlarmLog.i(TAG, "alarms.count:" + alarms.size());
                for (final Alarm alarm : alarms) {

                    AlarmLog.i(TAG, "alarm:" + alarm);
                    if (alarm.isEnabled()) {
                        if (alarm.getRule().isEffectiveAlarm()) {
                            AlarmLog.i(TAG, "alarm.setAlarmClock:" + alarm);
                            final Calendar calendar = Calendar.getInstance();
                            calendar.setTime(alarm.getTime());
                            AlarmLog.i(TAG, "calendar:" + calendar);
                            AlarmLog.i(TAG, calendar.get(Calendar.HOUR_OF_DAY) + ":"
                                    + calendar.get(Calendar.MINUTE) + ":"
                                    + calendar.get(Calendar.SECOND));

                            alarm.setAlarmClock(context);
                        } else {
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    AlarmLog.i(TAG, "alarm.setEnabled(false):" + alarm);
                                    alarm.setEnabled(false);
                                }
                            });
                        }
                    }
                }
            }
        }.start();
    }

    public static Realm getRealm() {
        return RealmWrap.getRealm();
    }

    public static RealmResults<Alarm> getAllAlarm() {
        Realm realm = RealmWrap.getRealm();
        return realm.where(Alarm.class).findAll();
    }

    public static boolean removeAlarm(int alarmId) {
        Realm realm = AlarmFacade.getRealm();
        realm.beginTransaction();
        Alarm alarm = realm.where(Alarm.class).equalTo("id", alarmId).findFirst();
        if (alarm == null) return false;
        alarm.deleteFromRealm();
        realm.commitTransaction();
        return true;
    }

    public static boolean cancleAlarmClock(Context context, int alarmId) {
        Realm realm = AlarmFacade.getRealm();
        realm.beginTransaction();
        Alarm alarm = realm.where(Alarm.class).equalTo("id", alarmId).findFirst();
        if (alarm == null) return false;
        alarm.cancelAlarmClock(context);
        realm.commitTransaction();
        return true;
    }

    public static class Builder {
        private Alarm alarm;
        private Realm realm;

        public Builder() {
            realm = RealmWrap.getRealm();
            realm.beginTransaction();
            alarm = realm.createObject(Alarm.class, RealmWrap.autoIncrement(Alarm.class));
            alarm.setRouterIntentAction(AlarmReceiver.ALARM_RECEIVER_ACTION);
            alarm.setEnabled(true);
            alarm.setTime(new Date());
        }

        public Builder setMode(@AlarmMode int mode) {
            alarm.setMode(mode);
            return this;
        }

        public Builder setEnabled(boolean enabled) {
            alarm.setEnabled(enabled);
            return this;
        }

        public Builder setYear(int year) {
            Calendar calendar = getCalendar();
            getCalendar().set(Calendar.YEAR, year);
            alarm.setTime(calendar.getTime());
            return this;
        }

        public Builder setMonth(int month) {
            Calendar calendar = getCalendar();
            getCalendar().set(Calendar.MONTH, month);
            alarm.setTime(calendar.getTime());
            return this;
        }

        public Builder setDayOfMonth(int dayOfMonth) {
            Calendar calendar = getCalendar();
            getCalendar().set(Calendar.DAY_OF_MONTH, dayOfMonth);
            alarm.setTime(calendar.getTime());
            return this;
        }

        public Builder setHour(int hour) {
            Calendar calendar = getCalendar();
            getCalendar().set(Calendar.HOUR, hour);
            alarm.setTime(calendar.getTime());
            return this;
        }

        public Builder setHourOfDay(int hourOfDay) {
            Calendar calendar = getCalendar();
            getCalendar().set(Calendar.HOUR_OF_DAY, hourOfDay);
            alarm.setTime(calendar.getTime());
            return this;
        }

        public Builder setMinute(int minute) {
            Calendar calendar = getCalendar();
            getCalendar().set(Calendar.MINUTE, minute);
            alarm.setTime(calendar.getTime());
            return this;
        }

        public Builder setSecond(int second) {
            Calendar calendar = getCalendar();
            getCalendar().set(Calendar.SECOND, second);
            alarm.setTime(calendar.getTime());
            return this;
        }

        @NonNull
        private Calendar getCalendar() {
            Date time = alarm.getTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            return calendar;
        }

        public Builder setTime(Date time) {
            alarm.setTime(time);
            return this;
        }

        public Builder setRule(IRule rule) {
            alarm.setRule(rule);
            return this;
        }

        public Builder setBell(IBell bell, String BellName) {
            alarm.setBell(bell.getClass().toString(), BellName);
            return this;
        }

        public Builder setRouterIntentAction(String intentAction) {
            alarm.setRouterIntentAction(intentAction);
            return this;
        }

        public Builder setTargetIntentAction(String targetIntentAction) {
            alarm.setTargetIntentAction(targetIntentAction);
            return this;
        }

        public Alarm build() {
            if (alarm.getRule() instanceof OnceRule) {
                IRule<Date> rule = (OnceRule) alarm.getRule();
                rule.setRuleEnforce(alarm.getTime());
                alarm.setRule(rule);
            }
            realm.commitTransaction();
            return alarm;
        }
    }
}
