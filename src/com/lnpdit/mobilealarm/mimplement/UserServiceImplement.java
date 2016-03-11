package com.lnpdit.mobilealarm.mimplement;

import com.lnpdit.mobilealarm.base.framework.BaseServiceImplement;
import com.lnpdit.mobilealarm.common.Instance;
import com.lnpdit.mobilealarm.config.MLog;
import com.lnpdit.mobilealarm.entity.UserEntity;
import com.lnpdit.mobilealarm.entity.http.request.RegisterEntity;
import com.lnpdit.mobilealarm.http.BaseRdaHttp;
import com.lnpdit.mobilealarm.http.MAsycnHttpHandler;
import com.lnpdit.mobilealarm.http.MHttpClient;
import com.lnpdit.mobilealarm.method.UserMethod;
import com.lnpdit.mobilealarm.mservice.UserService;
import com.lnpdit.mobilealarm.utils.JsonUtil;
import com.loopj.android.http.RequestParams;

public class UserServiceImplement extends BaseServiceImplement implements
        UserService {

    @Override
    public void userRegister(RegisterEntity registerEntity) {
        String json = Instance.gson.toJson(registerEntity);
        MHttpClient.post(UserMethod.METHOD_USER_REGISTER.getMethodUrl(), json, new MAsycnHttpHandler(this, Method("SetUserIdentfyInfo")) {
            @Override
            public void onSuccess(String json) {
                MLog.I(json);
                if (JsonUtil.isSuccess(json)) {
                    postSuccessData("");
                } else {
                    postServerError(JsonUtil.getErrorCode(json));
                }
            }
        });

    }

    @Override
    public void userLogin(String username, String password) {
        RequestParams req = new RequestParams();
        // TODO Auto-generated method stub

    }

    @Override
    public void getUserInfo(String userid) {
        // TODO Auto-generated method stub

    }

    @Override
    public void getContacts(String userid) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateUserInfo(UserEntity userEntity) {
        // TODO Auto-generated method stub

    }

    @Override
    public void test(String cityCode) {
//        String json = Instance.gson.toJson(registerEntity);
        RequestParams req = new RequestParams();
        req.put("cityCode", cityCode);
        
        
        MHttpClient.post(UserMethod.METHOD_USER_TEST.getMethodUrl(), req, new MAsycnHttpHandler(this, Method("test")) {
            @Override
            public void onSuccess(String json) {
                MLog.I(json);
                if (JsonUtil.isSuccess(json)) {
                    postSuccessData(json);
                } else {
                    postServerError(JsonUtil.getErrorCode(json));
                }
            }
        });
        
    }

    @Override
    public BaseRdaHttp This() {
        // TODO Auto-generated method stub
        return this;
    }

}
