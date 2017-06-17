package com.ethanco.alarmsample;

import android.app.Application;
import android.util.Log;

import com.ethanco.tracelog.TraceLog;
import com.ethanco.tracelog.logs.LocalRecordLog;
import com.lib.alarm.AlarmFacade;

/**
 * Application
 *
 * @author EthanCo
 * @since 2017/6/9
 */

public class App extends Application {
    public static final String TAG = "Z-App";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "App onCreate");
        AlarmFacade.init(this,getLocalLog());
    }

    public TraceLog getLocalLog(){
        return new TraceLog.Builder(this) //对于某些实现了IInit接口的ILog实现类，需要传入Context
                //.addLog(new DefaultLog())     //默认日志 和设置setBaseEnable(true)的作用相同
                .addLog(new LocalRecordLog()) //日志保存至本地文件
                .setMaxFileCacheSize(1024 * 1024 * 10) //日志文件最大缓存，以B为单位
                .setFolder("HopeLauncher/Alarm") //日志保存文件夹，如果不设置，默认为TraceLog
                .setFileName("Alarm") //日志文件文件名，如果不设置，默认为TraceLog
                .setDefaultTag("Z-AlarmLib") //默认tag
                .setEnable(true) //是否启用
                .setBaseEnable(true)
                .build();
    }
}
