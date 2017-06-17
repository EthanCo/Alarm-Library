package com.lib.alarm.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.lib.alarm.enums.AlarmMode.AROUSE;
import static com.lib.alarm.enums.AlarmMode.SLEEP;

/**
 * 闹铃模式
 *
 * @author EthanCo
 * @since 2017/6/10
 */

@IntDef({AROUSE, SLEEP})
@Retention(RetentionPolicy.SOURCE)
public @interface AlarmMode {
    int AROUSE = 0; //音乐唤醒
    int SLEEP = 1; //音乐睡眠
}
