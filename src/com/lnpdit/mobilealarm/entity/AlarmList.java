package com.lnpdit.mobilealarm.entity;

public class AlarmList {
    private String MobileNumber; // 用户名
    private String TagName;// 标签名
    private String cTime;// 上传时间
    public String getMobileNumber() {
        return MobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }
    public String getTagName() {
        return TagName;
    }
    public void setTagName(String tagName) {
        TagName = tagName;
    }
    public String getcTime() {
        return cTime;
    }
    public void setcTime(String cTime) {
        this.cTime = cTime;
    }
    
}
