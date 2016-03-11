package com.lnpdit.mobilealarm.entity;

public class TagInfo {
    private String Id; // 用户id
    private String TagName;// 标签名
    private String TCount;// 标签数

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTagName() {
        return TagName;
    }

    public void setTagName(String tagName) {
        TagName = tagName;
    }

    public String getTCount() {
        return TCount;
    }

    public void setTCount(String tCount) {
        TCount = tCount;
    }

}
