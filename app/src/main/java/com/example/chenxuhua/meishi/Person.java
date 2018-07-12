package com.example.chenxuhua.meishi;

import cn.bmob.v3.BmobObject;

/**
 * Created by ASUS on 2018/7/11.
 */

public class Person extends BmobObject {
    private String id;
    private String username;
    private String name;
    private String psd;
    private String qianming;
    private String sex;


    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }





    public String getQianming() {
        return qianming;
    }

    public void setQianming(String qianming) {
        this.qianming = qianming;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
