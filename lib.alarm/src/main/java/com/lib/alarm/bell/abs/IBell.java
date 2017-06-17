package com.lib.alarm.bell.abs;

/**
 * 铃声 - 接口
 *
 * @author EthanCo
 * @since 2017/6/15
 */
public interface IBell {
    void play(String bellName);

    void pause();
}
