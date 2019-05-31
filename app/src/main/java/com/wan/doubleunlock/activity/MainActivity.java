package com.wan.doubleunlock.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.wan.doubleunlock.BaseActivity;
import com.wan.doubleunlock.R;
import com.wan.doubleunlock.WakeUpService;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "---主界面---";

    private static final int UNLOCK = 0;//解锁
    private static final int LIGHT = 1;//唤醒屏幕
    private int currentFlag;//当前的设置
    /**
     * 启用
     */
    private TextView mTvOpen;
    private Switch mOpenSwitch;
    /**
     * 双击屏幕
     */
    private TextView mTvDoubleclick;
    /**
     * 唤醒并解锁
     */
    private TextView mTvUnlcok;
    private LinearLayout mDoubleclickSetting;
    private ImageView mImgDoubleClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        load();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            startActivity(AboutActivity.class);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mian_menu, menu);
        return true;
    }

    /**
     * 加载获得SharedPreferences的对象里面保存的状态
     */
    private void load() {
        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);//获得SharedPreferences的对象
        //得到数据，开启一个后台进程，并将字体设置为黑色
        if (preferences.getBoolean("switchStatus", false)) {
            if (!mOpenSwitch.isChecked()) {
                mOpenSwitch.setChecked(true);
            }
            changeTextBlack();
            openService();
        }

    }

    /**
     * 保存功能开关的状态
     */
    private void saveStatus(boolean flag) {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();//获得SHaredPreferences.Editor对象
        editor.putBoolean("switchStatus", flag);
        editor.apply();//提交
    }

    /**
     * 保存设置
     */
    private void saveSetting() {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();//获得SHaredPreferences.Editor对象
        editor.putInt("doubleClickMode", currentFlag);
        editor.apply();//提交
    }

    @Override
    public void initData() {
        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);//获得SharedPreferences的对象
        currentFlag = preferences.getInt("doubleClickMode", 0);//默认为0
    }

    @Override
    public void initView() {

        mTvOpen = (TextView) findViewById(R.id.tv_open);
        mOpenSwitch = (Switch) findViewById(R.id.open_switch);
        mTvDoubleclick = (TextView) findViewById(R.id.tv_doubleclick);
        mTvUnlcok = (TextView) findViewById(R.id.tv_unlcok);
        mDoubleclickSetting = (LinearLayout) findViewById(R.id.doubleclick_setting);
        mDoubleclickSetting.setOnClickListener(this);
        mOpenSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    openService();
                    changeTextBlack();
                } else {
                    Intent intent = new Intent(mOpenSwitch.getContext(), WakeUpService.class);
                    stopService(intent);
                    changeTextGrey();
                }

            }
        });
        mImgDoubleClick = (ImageView) findViewById(R.id.img_double_click);

        changeImg(currentFlag);


    }

    /**
     * 开启一个后台服务
     */
    private void openService() {
        Intent intent = new Intent(this, WakeUpService.class);
        startService(intent);
    }

    /**
     * 字体颜色变灰
     */
    private void changeTextGrey() {
        int color = getResources().getColor(R.color.colorGrey);
        mTvDoubleclick.setTextColor(color);
        mTvOpen.setTextColor(color);
        mTvUnlcok.setTextColor(color);
    }

    /**
     * 把字体颜色变黑
     */
    private void changeTextBlack() {
        int color = getResources().getColor(R.color.colorBlack);
        mTvDoubleclick.setTextColor(color);
        mTvOpen.setTextColor(color);
        mTvUnlcok.setTextColor(color);
    }

    /**
     * 更改图片
     * @param which
     */
    private void changeImg(int which) {
        switch (which) {
            case 0:
                mImgDoubleClick.setImageResource(R.drawable.vector_drawable_unlock);
                currentFlag = UNLOCK;
                mTvUnlcok.setText("唤醒并解锁");
                break;
            case 1:
                mImgDoubleClick.setImageResource(R.drawable.vector_drawable_light);
                mTvUnlcok.setText("唤醒屏幕");
                currentFlag=LIGHT;
                break;
            default:
                break;
        }
        saveSetting();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doubleclick_setting:

                new AlertDialog.Builder(this).setSingleChoiceItems(new String[]{"解锁", "点亮屏幕"}, currentFlag, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeImg(which);
                        dialog.dismiss();
                    }
                }).setTitle("选择动作").show();
                break;
            default:
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveStatus(mOpenSwitch.isChecked());//退出前保存功能开关的状态
    }
}
