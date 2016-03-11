package com.lnpdit.mobilealarm.mservice;

import com.lnpdit.mobilealarm.base.framework.BaseService;
import com.lnpdit.mobilealarm.entity.UserEntity;
import com.lnpdit.mobilealarm.entity.http.request.RegisterEntity;

public interface UserService extends BaseService {
    
    void userRegister(RegisterEntity registerEntity);

    void userLogin(String username, String password);

    void getUserInfo(String userid);

    void getContacts(String userid);
    
    void updateUserInfo(UserEntity userEntity);
    
    void test(String cityCode);

}
