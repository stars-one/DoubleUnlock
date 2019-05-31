package com.wan.doubleunlock.activity;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wan.doubleunlock.BaseActivity;
import com.wan.doubleunlock.MyTouchListener;
import com.wan.doubleunlock.R;


public class WakeUpActivity extends BaseActivity {


    private ConstraintLayout mWakeupLayout;
    private int currentFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        getWindow().addFlags(flags);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        getWindow().setAttributes(params);


        setContentView(R.layout.activity_wake_up);
        initView();


        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
        //点亮屏幕
        wl.acquire(1000);
        //释放
        wl.release();
    }

    @Override
    public void initData() {
        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);//获得SharedPreferences的对象
        currentFlag = preferences.getInt("doubleClickMode", 0);//默认为0
    }

    @Override
    public void initView() {

        mWakeupLayout = (ConstraintLayout) findViewById(R.id.wakeup_layout);
        mWakeupLayout.setOnTouchListener(new MyTouchListener(new MyTouchListener.DoubleClickCallback() {
            @Override
            public void onDoubleClick() {
                switch (currentFlag) {
                    case 0:
                        wakeUpAndUnlock(mWakeupLayout.getContext());
                        break;
                    case 1:
                        wakeUp();
                        break;
                    default:
                        break;
                }

            }
        }));

    }

    /**
     * 唤醒
     *
     */
    private void wakeUp() {
        Log.e("--测试", "wakeUp: 进入唤醒");
        finish();
    }

    /**
     * 唤醒并解锁
     *
     * @param context
     */
    public void wakeUpAndUnlock(Context context) {
        Log.e("--测试", "wakeUp: 进入解锁");
        //屏锁管理器
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        //解锁
        kl.disableKeyguard();
        //获取电源管理器对象

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
        //点亮屏幕
        wl.acquire(1000);
        //释放
        wl.release();
        finish();
    }


}