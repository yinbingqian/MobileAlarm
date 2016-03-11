package com.lnpdit.mobilealarm.entity;

public class LoginUser {
	private String Id; // 用户id
	private String mobileNo;// 用户名
	private String passWd;// 用户密码
	private String sex;// 用户性别
	private String hdPhoto ;// 头像
	private String islock;//
	private String crTime;// 创建时间
    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    public String getPassWd() {
        return passWd;
    }
    public void setPassWd(String passWd) {
        this.passWd = passWd;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getHdPhoto() {
        return hdPhoto;
    }
    public void setHdPhoto(String hdPhoto) {
        this.hdPhoto = hdPhoto;
    }
    public String getIslock() {
        return islock;
    }
    public void setIslock(String islock) {
        this.islock = islock;
    }
    public String getCrTime() {
        return crTime;
    }
    public void setCrTime(String crTime) {
        this.crTime = crTime;
    }
	
	

}
