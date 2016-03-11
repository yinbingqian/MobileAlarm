package com.lnpdit.mobilealarm.page.activity.myzone;

import com.lnpdit.mobilealarm.R;
import com.lnpdit.mobilealarm.base.component.BaseActivity;
import com.lnpdit.mobilealarm.http.RdaResultPack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyZoneActivity extends BaseActivity {
    private RelativeLayout layout_update,layout_aboutus,layout_modifypwd;
    private Button logout_bt,back_bt;
    private TextView username_tv;
    Context context;
    String mobileNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_myzone);

        context = this;
        initView();
        

    }


    private void initView() {
        SharedPreferences sharedPreferences= getSharedPreferences("userinfo", 
                Activity.MODE_PRIVATE); 
        mobileNo =sharedPreferences.getString("mobileNo", ""); 
        layout_update = (RelativeLayout) findViewById(R.id.layout_update);
        layout_update.setClickable(true);
        layout_update.setOnClickListener(this);
        layout_aboutus = (RelativeLayout) findViewById(R.id.layout_aboutus);
        layout_aboutus.setClickable(true);
        layout_aboutus.setOnClickListener(this);
        layout_modifypwd = (RelativeLayout) findViewById(R.id.layout_modifypwd);
        layout_modifypwd.setClickable(true);
        layout_modifypwd.setOnClickListener(this);
        username_tv = (TextView) findViewById(R.id.username_tv);
        username_tv.setText(mobileNo);
        back_bt = (Button) findViewById(R.id.back_bt);
        back_bt.setOnClickListener(this);
        logout_bt = (Button) findViewById(R.id.logout_bt);
        logout_bt.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.layout_update:
             Toast.makeText(context, "版本更新", Toast.LENGTH_SHORT).show();
            break;
        case R.id.layout_aboutus:

            Intent intent_aboutus = new Intent();
            intent_aboutus.setClass(context, AboutUsActivity.class);
            startActivity(intent_aboutus);
            break;
        case R.id.layout_modifypwd:

            Intent intent_pwd = new Intent();
            intent_pwd.setClass(context, PwdModifyActivity.class);
            startActivity(intent_pwd);
            break;
        case R.id.logout_bt: 
            SharedPreferences sp = context.getSharedPreferences("userinfo",
                MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString("Id", "");
        editor.putString("mobileNo","" );
        editor.putString("passWd","");
        editor.putString("sex", "");
        editor.putString("hdPhoto", "");
        editor.putString("islock", "");
        editor.putString("crTime", "");
        editor.commit();
            
        finish();
        
            break;
        case R.id.back_bt:
            finish();
            break;
        default:
            break;
        }
    }


    @Override
    public void onEvent(Object obj) {
        // TODO Auto-generated method stub
        super.onEvent(obj);
    }

    @Override
    protected void onEventMainThread(RdaResultPack http) {
        // TODO Auto-generated method stub
        
    }
}
