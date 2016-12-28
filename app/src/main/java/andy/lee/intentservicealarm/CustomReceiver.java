package andy.lee.intentservicealarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * andy.lee.intentservicealarm
 * Created by andy on 16-12-28.
 */

public class CustomReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "静态注册收到自定义广播", Toast.LENGTH_SHORT).show();
    }
}
