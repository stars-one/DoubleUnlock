package com.wan.doubleunlock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wan.doubleunlock.activity.WakeUpActivity;

public class LockReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // an Intent broadcast.
        Intent intent1 = new Intent(context, WakeUpActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
