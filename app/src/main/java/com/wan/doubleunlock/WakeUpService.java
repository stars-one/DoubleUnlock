package com.wan.doubleunlock;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.wan.doubleunlock.receiver.LockReceiver;

public class WakeUpService extends Service {

    private LockReceiver receiver;

    public WakeUpService() {
    }

    @Override
    public IBinder onBind(Intent intent) {


        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("----进入Services", "onBind: -----测试---");
        //拦截广播，当屏幕锁定的时候就进入WakeUpActivity
        // WakeUpActivity需要强制浮窗
        //自定义一个View来反应用户的操作
        //双击操作就结束WakeUpActivity
        receiver = new LockReceiver();
        IntentFilter filter = new IntentFilter();
        //添加action
        filter.addAction("android.intent.action.SCREEN_OFF");
        //注册广播接收者
        this.registerReceiver(receiver,filter);
        /*Intent intent1 = new Intent(getBaseContext(), WakeUpActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent1);*/
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        this.unregisterReceiver(receiver);
        super.onDestroy();
    }
}
