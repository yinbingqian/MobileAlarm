package com.lnpdit.mobilealarm.page.activity.alarmnote;

import java.util.ArrayList;
import java.util.List;

import com.lnpdit.mobilealarm.R;
import com.lnpdit.mobilealarm.base.component.BaseActivity;
import com.lnpdit.mobilealarm.db.DBHelper;
import com.lnpdit.mobilealarm.entity.TransforDate;
import com.lnpdit.mobilealarm.http.RdaResultPack;
import com.lnpdit.mobilealarm.page.adapter.AlarmListAdapter;
import com.lnpdit.mobilealarm.webservice.SoapRes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class AlarmListActivity extends BaseActivity{

    Context context;
    ListView alarm_list;
    Button back_bt;
    AlarmListAdapter AlarmListAdapter;
    List<TransforDate> list;
    String myphone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmlist);
        
        context = this;
        
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo",
                Activity.MODE_PRIVATE);
        myphone = sharedPreferences.getString("mobileNo", "");
        
        initView();
        getAlarmListFromDB();
    }
    
    private void getAlarmListFromDB(){
        DBHelper dbh = new DBHelper(context);
        list = new ArrayList<TransforDate>();
        list = dbh.queryAlarmInfo(myphone);
        AlarmListAdapter adapter = new AlarmListAdapter(context, list);
        alarm_list.setAdapter(adapter);
    }


    private void initView(){
        alarm_list = (ListView)findViewById(R.id.alarm_list);
        back_bt = (Button)findViewById(R.id.back_bt);
        back_bt.setOnClickListener(this);
        
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
//      webservice result
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.back_bt:
            finish();
            break;
        default:
            break;
        }
    }

}
