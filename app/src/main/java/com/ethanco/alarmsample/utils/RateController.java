package com.ethanco.alarmsample.utils;

/**
 * @Description 频率控制
 * Created by YOLANDA on 2015-12-07.
 */
public class RateController {
    private static final int RATE_TIME = 500;//500毫秒内按钮无效，可自己调整频率
    private long lastClickTime;

    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        if (0 < timeD && timeD < RATE_TIME) {
            return true;
        }
        return false;
    }
}
