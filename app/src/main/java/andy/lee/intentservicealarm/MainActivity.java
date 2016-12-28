package andy.lee.intentservicealarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mStartService, mDynamicRegister,mCustom,mStaticCustom;
    private NetWorkChangeReceiver mNetWorkChangeReceiver;
    private CustomReceiver mCustomReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartService = (Button) findViewById(R.id.button);
        mDynamicRegister = (Button) findViewById(R.id.btn_dynamic);
        mCustom = (Button) findViewById(R.id.btn_custom);
        mStaticCustom = (Button) findViewById(R.id.btn_Static_custom);
        mStartService.setOnClickListener(this);
        mDynamicRegister.setOnClickListener(this);
        mCustom.setOnClickListener(this);
        mStaticCustom.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                boolean isOn = !MyIntentService.isServiceAlarmOn(MainActivity.this);
                MyIntentService.setServiceAlarm(MainActivity.this, isOn);
                break;
            case R.id.btn_dynamic:
                mNetWorkChangeReceiver = new NetWorkChangeReceiver();
                registerReceiver(mNetWorkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                break;
            case R.id.btn_custom:
                mCustomReceiver = new CustomReceiver();
                registerReceiver(mCustomReceiver, new IntentFilter(Constant.CUSTOM_RECEIVER));
                Intent intent = new Intent(Constant.CUSTOM_RECEIVER);
                sendBroadcast(intent);
                break;
            case R.id.btn_Static_custom:
                Intent i = new Intent(Constant.CUSTOM_RECEIVER);
                sendBroadcast(i);
        }
    }


    class NetWorkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isAvailable()) {
                int netWorkType = networkInfo.getType();
                if (netWorkType == ConnectivityManager.TYPE_MOBILE) {
                    Toast.makeText(context, "network is available and you are now in mobile network", Toast.LENGTH_SHORT).show();
                } else if (netWorkType == ConnectivityManager.TYPE_WIFI) {
                    Toast.makeText(context, "network is available and you are now in WIFI", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class CustomReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "收到动态注册自定义的广播", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetWorkChangeReceiver);
        unregisterReceiver(mCustomReceiver);
    }
}
