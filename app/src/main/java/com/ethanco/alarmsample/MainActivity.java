package com.ethanco.alarmsample;

import android.app.ActivityManager;
import android.app.TimePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ethanco.alarmsample.databinding.ActivityMainBinding;
import com.ethanco.alarmsample.utils.RateController;
import com.lib.alarm.Alarm;
import com.lib.alarm.AlarmFacade;
import com.lib.alarm.enums.AlarmMode;
import com.lib.alarm.enums.AlarmRepeat;
import com.lib.alarm.enums.WeekAnno;
import com.lib.alarm.realm.RealmWrap;
import com.lib.alarm.rule.RuleFactory;
import com.lib.alarm.rule.abs.IRule;
import com.lib.alarm.week.WeekFlags;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Z-MainActivity";
    private ActivityMainBinding binding;
    private RateController rateController = new RateController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                new TimePickerDialog(MainActivity.this, 0, new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.i(TAG, "onTimeSet");

                        if (rateController.isFastDoubleClick()) {
                            Log.i(TAG, "onTimeSet inner");
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            calendar.set(Calendar.MINUTE, minute);
                            Date time = calendar.getTime();

                            Alarm alarm = new AlarmFacade.Builder()
                                    .setTime(time)
                                    .setMode(AlarmMode.AROUSE)
                                    .setRule(createRule())
                                    .setTargetIntentAction("action.ethanco.AlarmActivity")
                                    .setBell(new PlayListBell(), "Boy.pm3")
                                    .build();
                            alarm.setAlarmClock(MainActivity.this);
                            Toast.makeText(MainActivity.this, "闹铃设置成功", Toast.LENGTH_LONG).show();
                        }
                    }
                }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false).show();
            }
        });

        binding.btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmResults<Alarm> alarms = AlarmFacade.getAllAlarm();
                Log.i(TAG, "alarms.count:" + alarms.size());
                for (Alarm alarm : alarms) {
                    Log.i(TAG, alarm.toString());
                }
            }
        });


        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = RealmWrap.getRealm();
                final Number count = realm.where(Alarm.class).count();
                Log.i(TAG, "alarm count:" + count);
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        final Alarm alarm = realm.where(Alarm.class)
                                .equalTo("id", count.intValue() - 1).findFirst();
                        alarm.cancelAlarmClock(MainActivity.this);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this, "取消闹铃成功", Toast.LENGTH_SHORT).show();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(MainActivity.this, "取消闹铃失败", Toast.LENGTH_SHORT).show();
                    }
                });
                //AlarmFacade.cancleAlarmClock(MainActivity.this, alarm.getId());
            }
        });

        binding.btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);

                ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                manager.killBackgroundProcesses(getPackageName());
            }
        });
    }

    private IRule createRule() {
        IRule rule;
        if (binding.radiobtnRepeatOnce.isChecked()) {
            rule = RuleFactory.create(AlarmRepeat.ONCE, null);
        } else if (binding.radiobtnRepeatEveryday.isChecked()) {
            rule = RuleFactory.create(AlarmRepeat.EVERYDAY, null);
        } else if (binding.radiobtnRepeatWeekday.isChecked()) {
            rule = RuleFactory.create(AlarmRepeat.WEEKDAY, null);
        } else if (binding.radiobtnRepeatWeekend.isChecked()) {
            rule = RuleFactory.create(AlarmRepeat.WEEKEND, null);
        } else {
            WeekFlags weekFlags = new WeekFlags();
            weekFlags.addDayOfWeek(WeekAnno.MONDAY);
            weekFlags.addDayOfWeek(WeekAnno.SUNDAY);
            rule = RuleFactory.create(AlarmRepeat.CUSTOME, weekFlags);
        }
        return rule;
    }
}
