package andy.lee.intentservicealarm;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

public class MyIntentService extends IntentService {
    private static final String TAG = "MyIntentService";
    private static final int POLL_INTERVAL = 1000 * 15;

    public MyIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "received intent " + intent);
    }


    public static boolean isServiceAlarmOn(Context context) {
        Intent intent = new Intent(context, MyIntentService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        return pendingIntent != null;
    }


    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent intent = new Intent(context, MyIntentService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (isOn) {
            //此方法在6.0以上便不再适用,查看6.0源码得知,其最小时间已经规定为60s,估计是和Doze模式有关
            //更多请看https://developer.android.com/training/monitoring-device-state/doze-standby.html#restrictions
            manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), POLL_INTERVAL, pendingIntent);
        } else {
            manager.cancel(pendingIntent);
            pendingIntent.cancel();
        }

        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(Constant.IS_SERVICE_START,isOn)
                .apply();
    }
}
