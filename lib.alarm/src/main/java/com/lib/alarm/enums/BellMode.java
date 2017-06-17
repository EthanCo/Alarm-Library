package com.lib.alarm.enums;

import android.support.annotation.IntDef;

import static com.lib.alarm.enums.BellMode.PLAY_LIST;
import static com.lib.alarm.enums.BellMode.SYSTEM;

/**
 * 铃声模式
 *
 * @author EthanCo
 * @since 2017/6/15
 */

@IntDef({SYSTEM, PLAY_LIST})
public @interface BellMode {
    int SYSTEM = 0; //系统铃声
    int PLAY_LIST = 1; //歌单铃声
}
