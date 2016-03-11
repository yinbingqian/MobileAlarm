package com.lnpdit.mobilealarm.base.component;

import com.lnpdit.mobilealarm.eventbus.EBCache;
import com.lnpdit.mobilealarm.http.RdaResultPack;
import com.lnpdit.mobilealarm.utils.EventCache;
import com.lnpdit.mobilealarm.webservice.ISoapService;
import com.lnpdit.mobilealarm.webservice.SoapRes;
import com.lnpdit.mobilealarm.webservice.SoapService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseActivity extends Activity implements OnClickListener{
    private boolean isEventBus_HTTP = true;// 是否注册EventBus
    /** soapService **/
    public ISoapService soapService = new SoapService();
    public Intent intent = new Intent();// 页面跳转
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // eventBus = EventBus.getDefault();
        // eventBus.register(this);
        if (isEventBus_HTTP) {
            EBCache.EB_HTTP.register(this);
        }

        EventCache.commandActivity.unregister(this);
        EventCache.commandActivity.register(this);
        EventCache.errorHttp.unregister(this);
        EventCache.errorHttp.register(this);
    }
    
    @Override
    protected void onDestroy() {
        if (isEventBus_HTTP) {
            EBCache.EB_HTTP.unregister(this);
        }
        EventCache.commandActivity.unregister(this);
        EventCache.errorHttp.unregister(this);
        super.onDestroy();

    }

    // public void onEventMainThread(MEvent mEvent) {
    // switch (mEvent.getEvent_code()) {
    // case 0:
    // System.out.println("HTTP");
    // System.out.println("http  " + mEvent.getObj());
    // break;
    // case 1:
    // System.out.println("ACTION");
    // break;
    // default:
    // break;
    // }
    //
    // }
    // 初始化HTTP结果EventBus方法
    protected abstract void onEventMainThread(RdaResultPack http);

    /**
     * 异常返回
     * 
     * @param className
     *            类名
     */
    protected void onEventMainThread(String className) {

    }
    
    /**
     * http回调SoapObject
     * @param obj   
     */
    public void onEvent(Object obj) {
        SoapRes res = (SoapRes) obj;
//        if (res.getObj() == null && loading != null) {
//            loading.setState(1,res.getCode());
//        }else{
//            removeLoading();
//        }        
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
