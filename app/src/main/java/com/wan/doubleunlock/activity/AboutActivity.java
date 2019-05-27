package com.wan.doubleunlock.activity;

import android.didikee.donate.AlipayDonate;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wan.doubleunlock.BaseActivity;
import com.wan.doubleunlock.R;


public class AboutActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingttoolbar;
    /**
     * 支付宝捐赠
     */
    private Button mDonateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();

        //设置显示返回箭头
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        mCollapsingttoolbar.setExpandedTitleGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initView() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsingttoolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingttoolbar);
        mDonateBtn = (Button) findViewById(R.id.donateBtn);
        mDonateBtn.setOnClickListener(this);
    }
    /**
     * 支付宝支付
     *
     * @param payCode 收款码后面的字符串；例如：收款二维码里面的字符串为 https://qr.alipay.com/stx00187oxldjvyo3ofaw60 ，则
     *                payCode = stx00187oxldjvyo3ofaw60
     *                注：不区分大小写
     */
    private void donateAlipay(String payCode) {
        boolean hasInstalledAlipayClient = AlipayDonate.hasInstalledAlipayClient(this);
        if (hasInstalledAlipayClient) {
            AlipayDonate.startAlipayClient(this, payCode);
        } else {
            Toast.makeText(this, "您的手机未安装支付宝哦", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.donateBtn:
                donateAlipay("fkx09316opdsamnwrivcud2");
                break;
            default:
                break;
        }
    }
}