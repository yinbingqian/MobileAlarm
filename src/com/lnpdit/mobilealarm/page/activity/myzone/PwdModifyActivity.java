package com.lnpdit.mobilealarm.page.activity.myzone;

import com.lnpdit.mobilealarm.R;
import com.lnpdit.mobilealarm.base.component.BaseActivity;
import com.lnpdit.mobilealarm.http.RdaResultPack;
import com.lnpdit.mobilealarm.md5.MD5Plus;
import com.lnpdit.mobilealarm.utils.SOAP_UTILS;
import com.lnpdit.mobilealarm.webservice.SoapRes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PwdModifyActivity extends BaseActivity {
    Context context;
    private Button back_bt;
    private EditText oldpwd_et, pwd_et,repwd_et;
    private Button bt_submit;// 提交
    String mobileNo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_pwdmodify);
        context = this;
        SharedPreferences sharedPreferences= getSharedPreferences("userinfo", 
                Activity.MODE_PRIVATE); 
        mobileNo =sharedPreferences.getString("mobileNo", ""); 

        initView();
        setListeners();
    }

    private void initView() {
        back_bt = (Button) findViewById(R.id.back_bt);
        oldpwd_et = (EditText) findViewById(R.id.oldpwd_et);
        pwd_et = (EditText) findViewById(R.id.pwd_et);
        repwd_et = (EditText) findViewById(R.id.repwd_et);
        bt_submit = (Button) findViewById(R.id.bt_submit);
        pwd_et.setNextFocusForwardId(R.id.repwd_et);
    }

    private void setListeners() {
        back_bt.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.back_bt:
            finish();
            break;
        case R.id.bt_submit:
            // 旧密码校验
            if (oldpwd_et.getText().toString().trim().equals("")) {
                Toast.makeText(context, "请输入原始密码",Toast.LENGTH_SHORT).show();
                break;
            }
            // 密码校验
            if (pwd_et.getText().toString().trim().equals("")) {
                Toast.makeText(context, "请输入新密码",Toast.LENGTH_SHORT).show();
                break;
            }
            // 密码二次校验
            if (repwd_et.getText().toString().trim().equals("")) {
                Toast.makeText(context, "请输入确认密码",Toast.LENGTH_SHORT).show();
                break;
            }
            if (!repwd_et.getText().toString().trim().equals(pwd_et.getText().toString().trim())) {
                Toast.makeText(context, "确认密码错误",Toast.LENGTH_SHORT).show();
                break;
            }

            Object[] property_va = {mobileNo,oldpwd_et.getText().toString(),pwd_et.getText().toString()};
//            Object[] property_va = {mobileNo,MD5Plus.stringToMD5(oldpwd_et.getText().toString()),MD5Plus.stringToMD5(pwd_et.getText().toString())};
            soapService.updateUserPWD(property_va);
            break;
        default:
            break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    public void onEvent(SoapRes res) {
        if (res.getCode().equals(SOAP_UTILS.METHOD.UPDATEUSERPWD)) {
            if (res.getObj() != null) {
                if (res.getObj().toString().equals("ok")) {
                    Toast.makeText(context, "修改密码成功",Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(context, "修改密码失败，请检查信息后重新提交",Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "修改密码失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onEventMainThread(RdaResultPack http) {
        // TODO Auto-generated method stub
        
    }
}
