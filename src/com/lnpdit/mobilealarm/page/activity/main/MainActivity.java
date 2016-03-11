package com.lnpdit.mobilealarm.page.activity.main;

import com.lnpdit.mobilealarm.R;
import com.lnpdit.mobilealarm.base.component.BaseActivity;
import com.lnpdit.mobilealarm.http.RdaResultPack;
import com.lnpdit.mobilealarm.page.activity.alarmnote.AlarmListActivity;
import com.lnpdit.mobilealarm.page.activity.login.LoginActivity;
import com.lnpdit.mobilealarm.page.activity.myzone.MyZoneActivity;
import com.lnpdit.mobilealarm.page.activity.networkalarm.NetwortAlarmActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements OnClickListener {

    Context context;

    LinearLayout layoutlogin;
    LinearLayout layoutphone;
    Button login_bt;
    ImageButton imageicon;
    TextView phone_tv;
    TextView alarmimg;

    Button keyalarm_bt;
    Button networkalarm_bt;
    Button alarmlist_bt;
    String myphone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        context = this;
        initView();
    }

    private void initView() {
        layoutlogin = (LinearLayout) findViewById(R.id.layoutlogin);
        layoutlogin.setClickable(true);
        layoutlogin.setOnClickListener(this);
        layoutphone = (LinearLayout) findViewById(R.id.layoutphone);
        layoutphone.setClickable(true);
        layoutphone.setOnClickListener(this);
        login_bt = (Button) findViewById(R.id.login_bt);
        login_bt.setOnClickListener(this);
        imageicon = (ImageButton) findViewById(R.id.imageicon);
        phone_tv = (TextView) findViewById(R.id.phone_tv);
        alarmimg = (TextView) findViewById(R.id.alarmimg);
        keyalarm_bt = (Button) findViewById(R.id.keyalarm_bt);
        keyalarm_bt.setOnClickListener(this);
        networkalarm_bt = (Button) findViewById(R.id.networkalarm_bt);
        networkalarm_bt.setOnClickListener(this);
        alarmlist_bt = (Button) findViewById(R.id.alarmlist_bt);
        alarmlist_bt.setOnClickListener(this);

        checkUserState();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        checkUserState();
    }

    private void checkUserState() {

        SharedPreferences sharedPreferences = getSharedPreferences("userinfo",
                Context.MODE_PRIVATE); // 私有数据
        myphone = sharedPreferences.getString("mobileNo", "");
        if (myphone.equals("")) {
            layoutlogin.setVisibility(1);
            layoutphone.setVisibility(8);
        } else {
            layoutlogin.setVisibility(8);
            layoutphone.setVisibility(1);
            phone_tv.setText(myphone);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.login_bt:
            Intent intent_login = new Intent();
            intent_login.setClass(context, LoginActivity.class);
            startActivity(intent_login);
            break;
        case R.id.keyalarm_bt:
            Intent intent = new Intent(Intent.ACTION_CALL,
                    Uri.parse("tel:" + "110"));
            startActivity(intent);
            break;
        case R.id.networkalarm_bt:
            if (myphone.equals("")) {
                Intent intent_login3 = new Intent();
                intent_login3.setClass(context, LoginActivity.class);
                startActivity(intent_login3);
            } else {
                Intent intent_networkalarm = new Intent();
                intent_networkalarm.putExtra(myphone, myphone);
                intent_networkalarm.setClass(context,
                        NetwortAlarmActivity.class);
                startActivity(intent_networkalarm);
            }
            break;
        case R.id.alarmlist_bt:
            if (myphone.equals("")) {
                Intent intent_login2 = new Intent();
                intent_login2.setClass(context, LoginActivity.class);
                startActivity(intent_login2);
            } else {

                Intent intent_alarmlist = new Intent();
                intent_alarmlist.putExtra(myphone, myphone);
                intent_alarmlist.setClass(context, AlarmListActivity.class);
                startActivity(intent_alarmlist);
            }
            break;
        case R.id.layoutphone:
            Intent intent_userinfo = new Intent();
            intent_userinfo.setClass(context, MyZoneActivity.class);
            startActivity(intent_userinfo);
            break;
        default:
            break;
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
    }

}
