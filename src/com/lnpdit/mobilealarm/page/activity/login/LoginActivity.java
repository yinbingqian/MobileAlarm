package com.lnpdit.mobilealarm.page.activity.login;

import com.lnpdit.mobilealarm.R;
import com.lnpdit.mobilealarm.base.component.BaseActivity;
import com.lnpdit.mobilealarm.entity.LoginUser;
import com.lnpdit.mobilealarm.http.RdaResultPack;
import com.lnpdit.mobilealarm.md5.MD5Plus;
import com.lnpdit.mobilealarm.page.activity.main.MainActivity;
import com.lnpdit.mobilealarm.page.activity.register.ForgetPwdActivity;
import com.lnpdit.mobilealarm.page.activity.register.RegisterActivity;
import com.lnpdit.mobilealarm.utils.SOAP_UTILS;
import com.lnpdit.mobilealarm.webservice.SoapRes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener {
    Context context;
    EditText username_edit;
    EditText password_edit;
    Button login_bt;
    TextView tv_register;
    TextView tv_password;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        initView();

    }

    private void initView() {
        username_edit = (EditText) findViewById(R.id.username_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);
        login_bt = (Button) findViewById(R.id.login_bt);
        login_bt.setOnClickListener(this);
        tv_password = (TextView) findViewById(R.id.tv_password);
        tv_password.setClickable(true);
        tv_password.setOnClickListener(this);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_register.setClickable(true);
        tv_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.login_bt:
            login_validate(username_edit.getText().toString().trim(), MD5Plus.stringToMD5(password_edit.getText().toString().trim()));  //加密
           
            progressDialog = new ProgressDialog(this);
            String stri = getResources().getString(R.string.Is_sending_a_request);
            progressDialog.setMessage(stri);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            
            break;
            
        case R.id.tv_password:
            Intent intent_forget = new Intent();
            intent_forget.setClass(context, ForgetPwdActivity.class);
            startActivity(intent_forget);

            break;
        case R.id.tv_register:
            Intent intent_alarmlist = new Intent();
            intent_alarmlist.setClass(context, RegisterActivity.class);
            startActivity(intent_alarmlist);
            break;
        default:
            break;
        }
        
    }
    /**
     * 用户登录
     * 
     * @param username
     * @param password
     */
    private void login_validate(String username, String password) {
        if (username == null || username.equals("")) {
            Toast.makeText(this, "用户名为空", Toast.LENGTH_SHORT).show();
        } else if (password == null || password.equals("")) {
            Toast.makeText(this, "密码为空", Toast.LENGTH_SHORT).show();
        } else {
            Object[] property_va = { username_edit.getText().toString(), password_edit.getText().toString()};
            soapService.CheckUserLogin(property_va);
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
        if (res.getCode().equals(SOAP_UTILS.METHOD.CHECKUSERLOGIN)) {
            if (res.getObj() != null) {
                progressDialog.dismiss();
                LoginUser loginUser = (LoginUser) res.getObj();
                    if (loginUser.getId().equals("0")) {
                        Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    } else {
                        loginUser.setPassWd(password_edit.getText().toString().trim());
                        SharedPreferences sp = context.getSharedPreferences("userinfo",
                                MODE_PRIVATE);
                        Editor editor = sp.edit();
                        editor.putString("Id", loginUser.getId());
                        editor.putString("mobileNo", loginUser.getMobileNo());
                        editor.putString("passWd", loginUser.getPassWd());
                        editor.putString("sex", loginUser.getSex());
                        editor.putString("hdPhoto", loginUser.getHdPhoto());
                        editor.putString("islock", loginUser.getIslock());
                        editor.putString("crTime", loginUser.getCrTime());
                        editor.commit();
//
//                        Toast.makeText(context, username_edit.getText().toString().trim(), Toast.LENGTH_LONG).show();
//                        Intent intent_login = new Intent();
//                        intent_login.setClass(context, MainActivity.class);
//                        startActivity(intent_login);
                        finish();
                    }
               
            } 
        }
    }
}
