package com.lnpdit.mobilealarm.page.activity.register;

import com.lnpdit.mobilealarm.R;
import com.lnpdit.mobilealarm.base.component.BaseActivity;
import com.lnpdit.mobilealarm.entity.LoginUser;
import com.lnpdit.mobilealarm.http.RdaResultPack;
import com.lnpdit.mobilealarm.md5.MD5Plus;
import com.lnpdit.mobilealarm.utils.SOAP_UTILS;
import com.lnpdit.mobilealarm.webservice.SoapRes;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPwdActivity extends BaseActivity {
        private Button code_bt,bt_submit,back_bt;// 提交
        private EditText phone_et,code_et,pwd_et,repwd_et;
        Context context;

        CheckTimer checkTimer;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setContentView(R.layout.activity_forgetpwd);

            context = this;
            initView();
        }

        private void initView() {
            back_bt = (Button) findViewById(R.id.back_bt);
            back_bt.setOnClickListener(this);
            code_bt = (Button) findViewById(R.id.code_bt);
            code_bt.setOnClickListener(this);
            bt_submit = (Button) findViewById(R.id.bt_submit);
            bt_submit.setOnClickListener(this);
            phone_et = (EditText) findViewById(R.id.phone_et);
            code_et = (EditText) findViewById(R.id.code_et);
            pwd_et = (EditText) findViewById(R.id.pwd_et);
            repwd_et = (EditText) findViewById(R.id.repwd_et);
        }

        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.code_bt:
                code_bt.setEnabled(false);
                if (phone_et.getText().length() != 11
                        || phone_et.getText() == null
                        || phone_et.getText().toString().trim().equals("")) {
                    Toast.makeText(context, "手机号码错误",Toast.LENGTH_SHORT).show();

                } else {
                    String[] property_va = new String[] { phone_et.getText().toString() };
                    soapService. SetIdentifyCode_ResetPWD(property_va);

//                  // actv_checkCode.setText(code);
//                  btn_getvcode.setText("发送中...");
//                  btn_getvcode.setEnabled(false);
//                  String[] property_va1 = new String[] { actv_phone.getText()
//                          .toString() };
//                  soapService.getCode(property_va1);      
                }
                break;
            case R.id.bt_submit:
                // 手机号校验
                if (phone_et.getText().length() != 11
                        || phone_et.getText() == null
                        || phone_et.getText().equals("")) {
                  Toast.makeText(context, "手机号码错误",Toast.LENGTH_SHORT).show();
                    break;
                }
                // 验证码校验
                if (code_et.getText().equals("")) {
                    Toast.makeText(context, "请输入验证码",Toast.LENGTH_SHORT).show();
                    break;
                }
             // 密码校验
                if (pwd_et.getText().toString().trim().equals("")) {
                    Toast.makeText(context, "请输入密码",Toast.LENGTH_SHORT).show();
                    break;
                }
                // 密码二次校验
                if (repwd_et.getText().toString().trim().equals("")) {
                    Toast.makeText(context, "请输入密码",Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!repwd_et.getText().toString().trim()
                        .equals(pwd_et.getText().toString().trim())) {
                    Toast.makeText(context, "确认密码错误",Toast.LENGTH_SHORT).show();
                    break;
                }

                Object[] property_va = { phone_et.getText().toString(),MD5Plus.stringToMD5(pwd_et.getText().toString()),
                        code_et.getText().toString(), };
                soapService.updateByCode(property_va);
//              intent.putExtra("sim", actv_phone.getText().toString());
//              intent.setClass(RetrievePassword_Activity.this, ResetPassword_Activity.class);
//              startActivity(intent);
                break;
            case R.id.back_bt:
                finish();
                break;

            default:
                break;
            }
        }

        Handler checkHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    code_bt.setText(msg.obj.toString());
                } else {
                    code_bt.setEnabled(true);
                    code_bt.setText("重新获取");
                }
            }
        };

        class CheckTimer implements Runnable {

            public boolean stop = false;
            private int time = 60;

            public void Restart() {
                this.stop = false;
                this.time = 60;
                new Thread(this).start();
            }

            @Override
            public void run() {
                Message mes = new Message();
                mes.what = 0;
                while (!stop && time != 0) {
                    time--;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mes = new Message();
                    mes.obj = time + "(S)";
                    checkHandler.sendMessage(mes);
                }
                if (!stop) {
                    mes = new Message();
                    mes.what = 1;
                    checkHandler.sendMessage(mes);
                } else {
                    mes = new Message();
                    mes.what = 2;
                    checkHandler.sendMessage(mes);
                }
            }

            public void end() {
                this.stop = true;
                checkHandler.sendEmptyMessage(2);
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
            if (res.getCode().equals(SOAP_UTILS.METHOD.SETIDENTIFYCODE_RESETPWD)) {
                if (res.getObj() != null) {
                    if (res.getObj().toString().equals("2")) {
                        checkTimer = new CheckTimer();
                        Thread thread = new Thread(checkTimer);
                        thread.start();
                    } else if (res.getObj().toString().equals("0")) {
                        Toast.makeText(context, "验证码请求失败",
                                Toast.LENGTH_SHORT).show();
                    } else if (res.getObj().toString().equals("400")) {
                        Toast.makeText(context, "非法IP访问",
                                Toast.LENGTH_SHORT).show();
                    }else if(res.getObj().toString().equals("non-existent")){
                        Toast.makeText(context, "用户不存在",
                                Toast.LENGTH_SHORT).show();
                    }else if(res.getObj().toString().equals("4051")){
                        Toast.makeText(context, "验证码已发送，请稍等",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (res.getCode().equals(SOAP_UTILS.METHOD.UPDATEBYCODE)) {
                if (res.getObj() != null) {
                    LoginUser lu = (LoginUser) res.getObj();
                    String userid = lu.getId().toString();
                     
                    if (userid.equals("-1")) {
                        
                        Toast.makeText(context, "验证码超时，请重新申请验证码", Toast.LENGTH_SHORT).show();
                       
                    } else if (userid.equals("-2")) {
                        Toast.makeText(context, "验证码错误，请检查信息后重新提交", Toast.LENGTH_SHORT).show();

                    }else{

                        Toast.makeText(context, "找回密码成功",Toast.LENGTH_SHORT).show(); 
                        finish();
                    }
                } else {
                    Toast.makeText(context, "找回密码失败，请检查信息后重新提交",Toast.LENGTH_SHORT).show();
                }
            }
        }   

        @Override
        protected void onEventMainThread(RdaResultPack http) {
            // TODO Auto-generated method stub
            
        }
}
