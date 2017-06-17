package com.lib.alarm.control;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * PengingIntent 工厂
 *
 * @author EthanCo
 * @since 2017/6/8
 */

public class PengingIntentFactory {
    /**
     * requestCode和flags为0
     *
     * @param context
     * @param intent
     * @param cls
     * @return
     */
    public static PendingIntent create(Context context, Intent intent, Class<?> cls) {
        return create(context, intent, 0, 0, cls);
    }

    public static PendingIntent create(Context context, Intent intent, int requestCode, int flags, Class<?> cls) {
        if (asSubclass(cls, Activity.class)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return PendingIntent.getActivity(context, requestCode, intent, flags);
        } else if (asSubclass(cls, Service.class)) {
            return PendingIntent.getService(context, requestCode, intent, flags);
        } else if (asSubclass(cls, BroadcastReceiver.class)) {
            return PendingIntent.getBroadcast(context, requestCode, intent, flags);
        } else {
            throw new IllegalArgumentException("class type is not support,class is " + cls);
        }
    }

    /**
     * @param actual 实际的class
     * @param expect 期望的class
     * @return true:类型相同或是子类，false:类型不同
     */
    private static boolean asSubclass(Class<?> actual, Class<?> expect) {
        try {
            actual.asSubclass(expect);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
