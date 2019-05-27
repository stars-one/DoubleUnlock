package com.wan.doubleunlock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wan.doubleunlock.WakeUpService;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            //开机自动启动一个后台服务
            Intent intent1 = new Intent(context, WakeUpService.class);
            context.startService(intent1);
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
