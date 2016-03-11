package com.lnpdit.mobilealarm.page.activity;

import com.lnpdit.mobilealarm.R;
import com.lnpdit.mobilealarm.base.component.BaseActivity;
import com.lnpdit.mobilealarm.http.RdaResultPack;
import com.lnpdit.mobilealarm.page.activity.main.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class WelcomeActivity extends BaseActivity {

    // private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {

            public void run() {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), MainActivity.class);
                startActivity(intent);
                
                finish();
            }

        }, 1500);
    }

    @Override
    public void onEvent(Object obj) {
        // TODO Auto-generated method stub
        super.onEvent(obj);
    }

    @Override
    protected void onEventMainThread(RdaResultPack http) {
        // if (http.Match(userService.This(), "test")) {
        // if (http.Success()) {
        // // TODO
        // } else if (http.HttpFail()) {
        // Toast.makeText(this, "服务器连接失败，请稍后再试！", Toast.LENGTH_SHORT)
        // .show();
        // // Alert("");
        // } else if (http.ServerError()) {
        // if (http.Message().toString().trim().equals("E003")) {
        // Toast.makeText(this, "未查询到相关数据", Toast.LENGTH_SHORT).show();
        // // Alert("暂无车会活动！");
        // } else {
        // Toast.makeText(this, "服务器请求失败，请稍后再试！", Toast.LENGTH_SHORT)
        // .show();
        // }
        // }
        // }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        
    }
}
