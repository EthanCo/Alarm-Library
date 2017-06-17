package com.lib.alarm.enums;

/**
 * 重复模式
 *
 * @author EthanCo
 * @since 2017/6/16
 */

import android.support.annotation.IntDef;

import static com.lib.alarm.enums.AlarmRepeat.CUSTOME;
import static com.lib.alarm.enums.AlarmRepeat.EVERYDAY;
import static com.lib.alarm.enums.AlarmRepeat.ONCE;
import static com.lib.alarm.enums.AlarmRepeat.WEEKDAY;
import static com.lib.alarm.enums.AlarmRepeat.WEEKEND;

@IntDef({ONCE, EVERYDAY, CUSTOME, WEEKDAY, WEEKEND})
public @interface AlarmRepeat {
    int ONCE = 0;
    int EVERYDAY = 1;
    int CUSTOME = 2;
    int WEEKDAY = 3;
    int WEEKEND = 4;
}
