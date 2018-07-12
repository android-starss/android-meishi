package com.example.lihongzheng.meishi.HXF;

import java.io.Serializable;

public class Person  implements Serializable {

    private String name;
    private String sex;
    private String signature;
    private String psd;
    private String photo_uri;

    public String getPhoto() {
        return photo_uri;
    }

    public void setPhoto(String photo) {
        this.photo = photo_uri;
    }

    private String photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    //构造函数
    public Person() {
        this.name = name;
        this.sex = sex;
        this.signature=signature;
        this.psd=psd;
        this.photo_uri = photo_uri;
    }

}