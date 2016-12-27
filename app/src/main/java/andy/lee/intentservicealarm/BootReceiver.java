package andy.lee.intentservicealarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * andy.lee.intentservicealarm
 * 监听设备重启之后,启动service
 * Created by andy on 16-12-27.
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isOn = sp.getBoolean(Constant.IS_SERVICE_START, false);
        MyIntentService.setServiceAlarm(context, isOn);
    }
}
