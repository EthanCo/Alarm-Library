package com.ethanco.alarmsample.enforce;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * AlarmService
 *
 * @author EthanCo
 * @since 2017/6/8
 */

public class AlarmService extends Service {
    private static final String TAG = "Z-AlarmService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(AlarmService.this, "Alarm Service start", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Alarm Service start");
    }
}
