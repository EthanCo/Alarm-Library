package com.lib.alarm.enums;

import android.support.annotation.IntDef;

import static com.lib.alarm.enums.WeekAnno.FRIDAY;
import static com.lib.alarm.enums.WeekAnno.MONDAY;
import static com.lib.alarm.enums.WeekAnno.SATURDAY;
import static com.lib.alarm.enums.WeekAnno.SUNDAY;
import static com.lib.alarm.enums.WeekAnno.THURSDAY;
import static com.lib.alarm.enums.WeekAnno.TUESDAY;
import static com.lib.alarm.enums.WeekAnno.WEDNESDAY;

/**
 * 星期
 *
 * @author EthanCo
 * @since 2017/6/10
 */

@IntDef({SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY})
public @interface WeekAnno {
    int SUNDAY = 1;
    int MONDAY = 2;
    int TUESDAY = 3;
    int WEDNESDAY = 4;
    int THURSDAY = 5;
    int FRIDAY = 6;
    int SATURDAY = 7;
}
