package com.lib.alarm.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.lib.alarm.Alarm;
import com.lib.alarm.control.abs.BaseAlarmControl;
import com.lib.alarm.realm.RealmWrap;
import com.lib.alarm.utils.log.AlarmLog;

import io.realm.Realm;

/**
 * AlarmReceiver
 *
 * @author EthanCo
 * @since 2017/6/8
 */

public class AlarmReceiver extends BroadcastReceiver {
    public static final String TAG = "Z-AlarmReceiver";
    public static final String ALARM_RECEIVER_ACTION = "com.hope.launcher.AlarmReceiver";

    @Override
    public void onReceive(final Context context, Intent intent) {
        AlarmLog.i(TAG, "onReceive");
        Bundle extras = intent.getExtras();
        AlarmLog.i(TAG, "extras:" + extras);
        if (extras == null) {
            return;
        }
        String operation = extras.getString("operation");
        int alarmId = extras.getInt(BaseAlarmControl.KEY_ALARM_ID, -1);
        if (alarmId < 0) {
            AlarmLog.i(TAG, "alarmId is null.");
        }
        AlarmLog.i("operation:" + operation);
        if (TextUtils.isEmpty(operation)) {
            AlarmLog.i(TAG, "receive alarmId:" + alarmId);
            Realm realm = RealmWrap.getRealm();
            final Alarm realmAlarm = realm.where(Alarm.class).equalTo("id", alarmId).findFirst();
            if (realmAlarm == null) {
                AlarmLog.i(TAG, "realmAlarm is Null.");
                return;
            }
            AlarmLog.i(TAG, "receive realmAlarm:" + realmAlarm);

            Intent activityIntent = new Intent(realmAlarm.getTargetIntentAction());
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(activityIntent);

            if (!realm.isInTransaction()) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realmAlarm.onAlarmed(context);
                    }
                });
            } else {
                realmAlarm.onAlarmed(context);
            }
        }
    }
}
