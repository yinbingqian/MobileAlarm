package com.lnpdit.mobilealarm.webservice;

/**
 * webService请求接口
 * @author huanyu 类名称：ISoapService 创建时间:2014-11-4 下午7:08:50
 */
public interface ISoapService extends IASoapService{
	/**
	 * 用户登录--手机号|密码
	 * 
	 * @param property_va
	 */
	void CheckUserLogin(Object[] property_va);
	
	/**
     * 用户注册--手机号|密码|性别|验证码
     * 
     * @param property_va
     */
    void AddUsers(Object[] property_va);
    
    /**
     * 生成验证码--手机号
     * 
     * @param property_va
     */
    void SetIdentifyCode(Object[] property_va);
    
    /**
     * 根据验证码修改密码--手机号，密码，验证码
     * 
     * @param property_va
     */
    void updateByCode(Object[] property_va);
    
    /**
     * 生成验证码重置密码--手机号
     * 
     * @param property_va
     */
    void  SetIdentifyCode_ResetPWD(Object[] property_va);
    
    /**
     * 用户密码修改-已知旧密码--手机号码，旧密码，新密码
     * 
     * @param property_va
     */
    void  updateUserPWD(Object[] property_va);
    
    /**
     * 手机传输信息-传输发布信息--
     * 
     * @param property_va
     */
    void  transforDate(Object[] property_va);
    
    /**
     * 保存文件到远程服务器
     * 
     * @param property_va
     */
    void  SaveFile(Object[] property_va);
    
    /**
     * 手机用户-标签项按倒序返回
     * 
     * @param property_va
     */
    void  GetTagByDesc(Object[] property_va);
}
