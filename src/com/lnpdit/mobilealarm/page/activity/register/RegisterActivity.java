package com.lnpdit.mobilealarm.page.activity.register;

import com.lnpdit.mobilealarm.R;
import com.lnpdit.mobilealarm.base.component.BaseActivity;
import com.lnpdit.mobilealarm.http.RdaResultPack;
import com.lnpdit.mobilealarm.utils.SOAP_UTILS;
import com.lnpdit.mobilealarm.webservice.SoapRes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity implements OnClickListener {
    Context context;
    EditText phone_et;
    EditText pwd_et;
    EditText repwd_et;
    EditText code_et;
    Button back_bt;
    Button register_bt;
    Button code_bt;
//    TextView tv_register;
    TextView read_tx;

    CheckTimer checkTimer;
    
    RadioButton m_rb;
    RadioButton w_rb;
    String sex= "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        context = this;
        initView();
        
        if(m_rb.isChecked()){
            //male
            sex = "男";
        }else{
            //female
            sex = "女";
        }

    }

    private void initView() {
        m_rb = (RadioButton) findViewById(R.id.m_rb);
        w_rb = (RadioButton) findViewById(R.id.w_rb);
        back_bt = (Button) findViewById(R.id.back_bt);
        back_bt.setOnClickListener(this);
        code_bt = (Button) findViewById(R.id.code_bt);
        code_bt.setOnClickListener(this);
        register_bt = (Button) findViewById(R.id.register_bt);
        register_bt.setOnClickListener(this);
        phone_et = (EditText) findViewById(R.id.phone_et);
        pwd_et = (EditText) findViewById(R.id.pwd_et);
        repwd_et = (EditText) findViewById(R.id.repwd_et);
        code_et = (EditText) findViewById(R.id.code_et);
        read_tx = (TextView) findViewById(R.id.read_tx);
        read_tx.setClickable(true);
        read_tx.setOnClickListener(this);
//        tv_register = (TextView) findViewById(R.id.tv_register);
//        tv_register.setClickable(true);
//        tv_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.register_bt:
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

            Object[] property_va = { phone_et.getText().toString(),pwd_et.getText().toString(),sex,
                    code_et.getText().toString(), };
            soapService.AddUsers(property_va);
            break;
        case R.id.read_tx:
            Intent intent_networkalarm = new Intent();
            intent_networkalarm.setClass(context, ForgetPwdActivity.class);
            startActivity(intent_networkalarm);

            break;
        case R.id.back_bt:
            finish();
            break;
            
        case R.id.code_bt:
            code_bt.setEnabled(false);
            if (phone_et.getText().length() != 11
                    || phone_et.getText() == null
                    || phone_et.getText().toString().trim().equals("")) {
                Toast.makeText(context, "手机号码错误",Toast.LENGTH_SHORT).show();

            } else {
                String[] property_vacode = new String[] { phone_et.getText().toString()};
                soapService. SetIdentifyCode(property_vacode);

//              // actv_checkCode.setText(code);
//              btn_getvcode.setText("发送中...");
//              btn_getvcode.setEnabled(false);
//              String[] property_va1 = new String[] { actv_phone.getText()
//                      .toString() };
//              soapService.getCode(property_va1);      
            }
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
    protected void onEventMainThread(RdaResultPack http) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onEvent(Object obj) {
        // TODO Auto-generated method stub
        super.onEvent(obj);
        SoapRes res = (SoapRes) obj;
            if (res.getCode().equals(SOAP_UTILS.METHOD.ADDUSERS)) {
                if (res.getObj() != null) {

//                    /* 只有用户名、密码不为空，并且用户名为小于11位手机号码才允许登陆 */
//
//                    if (res.getObj().toString().equals("true")) {
//                        boolean isTel = true; // 标记位：true-是手机号码；false-不是手机号码
//                        /* 判断输入的用户名是否是电话号码 */
//                        if (phone_et.getText().toString().length() <= 11) {
//                            for (int i = 0; i < phone_et.getText().toString().length(); i++) {
//                                char c = phone_et.getText().toString().charAt(i);
//                                if (!Character.isDigit(c)) {
//                                    isTel = false;
//                                    break; // 只要有一位不符合要求退出循环
//                                }
//                            }
//                        } else {
//                            isTel = false;
//                        }
//                        if (phone_et.getText().toString().trim().equals("")) {
//
//                            Toast.makeText(context, "用户名不可为空",Toast.LENGTH_SHORT).show();
//                        } else if (!isTel) {
//
//                            Toast.makeText(context, "用户名请输入11位手机号码！",Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(context, "用户名可用",Toast.LENGTH_SHORT).show(); 
//                        }
//                    } else {
//                        Toast.makeText(context, "用户名不可用",Toast.LENGTH_SHORT).show();
//                    }
                    
                    if (res.getObj().toString().equals("ok")) {

                      Toast.makeText(context, "注册成功",Toast.LENGTH_SHORT).show();
                    }else if (res.getObj().toString().equals("false")) {

                        Toast.makeText(context, "注册失败",Toast.LENGTH_SHORT).show();
                    }else if (res.getObj().toString().equals("codeOvertime ")) {

                        Toast.makeText(context, "超时",Toast.LENGTH_SHORT).show();
                    }else if (res.getObj().toString().equals("codeError ")) {

                        Toast.makeText(context, "验证码错误",Toast.LENGTH_SHORT).show();
                    }else if (res.getObj().toString().equals("mobileExist ")) {

                        Toast.makeText(context, "手机号码已经存在",Toast.LENGTH_SHORT).show();
                    }
                    
                }
            }else if (res.getCode().equals(SOAP_UTILS.METHOD.SETIDENTIFYCODE)) {
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
                    }
                }
            } 
    }
}
