package com.lib.alarm.utils.log;

import com.ethanco.logbase.IEntireLog;

/**
 * 闹铃 Log
 *
 * @author EthanCo
 * @since 2017/6/17
 */

public class AlarmLog {
    private static IEntireLog log = new EmptyLog();

    public static void setLog(IEntireLog ICommonLog) {
        log = ICommonLog;
    }

    public static void v(String msg) {
        log.v(msg);
    }

    public static void d(String msg) {
        log.d(msg);
    }

    public static void i(String msg) {
        log.i(msg);
    }

    public static void w(String msg) {
        log.w(msg);
    }

    public static void e(String msg) {
        log.e(msg);
    }

    public static void v(String tag, String message) {
        log.v(tag, message);
    }

    public static void d(String tag, String message) {
        log.d(tag, message);
    }

    public static void i(String tag, String message) {
        log.i(tag, message);
    }

    public static void w(String tag, String message) {
        log.w(tag, message);
    }

    public static void e(String tag, String message) {
        log.e(tag, message);
    }

    public static void postCatchedException(Exception e) {
        log.postCatchedException(e);
    }
}
