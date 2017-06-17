package com.lib.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.gson.Gson;
import com.lib.alarm.control.PengingIntentFactory;
import com.lib.alarm.enums.AlarmMode;
import com.lib.alarm.enums.WeekAnno;
import com.lib.alarm.realm.RealmWrap;
import com.lib.alarm.rule.EverydayRule;
import com.lib.alarm.rule.OnceRule;
import com.lib.alarm.rule.WeekRule;
import com.lib.alarm.rule.abs.IRule;
import com.lib.alarm.week.DayOfWeek;
import com.lib.alarm.week.WeekFlags;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static junit.framework.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    public static final String TAG = "Z-Test";
    private Context appContext;
    private Gson gson;

    @Before
    public void setUp() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();
        gson = new Gson();
        Realm.init(appContext);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("testRealm84.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(config);
    }

    @Test
    public void testRealm() throws Exception {
        Realm realm = RealmWrap.getRealm();
        realm.beginTransaction();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 3);
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.SECOND, 5);
        calendar.set(Calendar.YEAR, 2017);
        Alarm alarm = realm.createObject(Alarm.class, RealmWrap.autoIncrement(Alarm.class));
        alarm.setTime(calendar.getTime());
        realm.commitTransaction();

        Number count = realm.where(Alarm.class).count();
        System.out.println("count:" + count.intValue());
        assertEquals(true, count.intValue() >= 1);
    }

    public static final String ALARM_RECEIVER_ACTION = "com.hope.launcher.AlarmReceiver";

    @Test
    public void AlarmTest() {
        Log.i(TAG, "onTimeSet inner");
        Realm realm = RealmWrap.getRealm();
        realm.beginTransaction();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 3);
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.SECOND, 5);
        calendar.set(Calendar.YEAR, 2017);
        Date time = calendar.getTime();

        Alarm alarm = realm.createObject(Alarm.class,
                RealmWrap.autoIncrement(Alarm.class));
        alarm.setTime(time);
        alarm.setMode(AlarmMode.SLEEP);
        //TODO setRule

        alarm.setAlarmClock(appContext);
        Log.i(TAG, "闹铃设置成功");
        //Toast.makeText(MainActivity.this, "闹铃设置成功", Toast.LENGTH_LONG).show();

        realm.commitTransaction();
    }

    @Test
    public void cancelAlarm() {
        for (int i = 0; i <= 10; i++) {
            AlarmManager alarmManager = (AlarmManager) appContext.getSystemService(Service.ALARM_SERVICE);
            Intent intent = new Intent(ALARM_RECEIVER_ACTION);
            PendingIntent pi = PengingIntentFactory.create(appContext, intent, i,
                    PendingIntent.FLAG_CANCEL_CURRENT, BroadcastReceiver.class);
            Log.i(TAG, "cancelAlarm:" + pi);
            alarmManager.cancel(pi);
        }


//        Realm realm = RealmWrap.getRealm();
//        final Number count = realm.where(Alarm.class).count();
//        Log.i(TAG, "alarm count:" + count);
//
//
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                final Alarm alarm = realm.where(Alarm.class)
//                        .equalTo("id", count.intValue() - 1).findFirst();
//
//                Log.i(TAG, "cancel alarm:" + alarm);
//                if (alarm == null) {
//                    return;
//                }
//                AlarmDirector.cancelAlarm(appContext, ALARM_RECEIVER_ACTION, alarm);
//
//                alarm.deleteFromRealm();
//                Log.i(TAG, "delete success");
//            }
//        });
    }

    @Test
    public void testEveryDayRule() {
        EverydayRule rule = new EverydayRule();
        refreshIRule(rule);
    }

    @Test
    public void testOnceRule() {
        OnceRule rule = new OnceRule();
        Realm realm = RealmWrap.getRealm();
        realm.beginTransaction();
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        realm.commitTransaction();

        rule.setRuleEnforce(time);
        refreshIRule(rule);
    }

    @Test
    public void testWeekRule() {
        WeekRule weekRule = new WeekRule();
        WeekFlags weekFlags = new WeekFlags();
        weekFlags.addDayOfWeek(new DayOfWeek(WeekAnno.SUNDAY));
        weekFlags.addDayOfWeek(new DayOfWeek(WeekAnno.MONDAY));
        weekRule.setRuleEnforce(weekFlags);
        refreshIRule(weekRule);
    }

//    @Test
//    public void testE() {
//        Realm realm = RealmWrap.getRealm();
//        realm.beginTransaction();
//        MyTest myTest = realm.createObject(MyTest.class);
//        myTest.setDate(new java.util.Date(System.currentTimeMillis()));
//        realm.commitTransaction();
//
//        Log.i(TAG, "myTest:" + myTest.getDate().toString());
//
//        SystemClock.sleep(1000);
//        RealmQuery<MyTest> result = realm.where(MyTest.class).lessThan("date", new Date());
//        Log.i(TAG, "result count:" + result.count());
//        Log.i(TAG, "result date:" + result.findFirst().getDate());
//    }

    private void refreshIRule(IRule rule) {
        String json = gson.toJson(rule);
        String ruleClassName = rule.getClass().getName();
        Log.i(TAG, "ruleClassName:" + ruleClassName);

        try {
            Class<IRule> cls = (Class<IRule>) Class.forName(ruleClassName);
            IRule rule2 = gson.fromJson(json, cls);
            Log.i(TAG, "rule fromJson:" + rule2.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
