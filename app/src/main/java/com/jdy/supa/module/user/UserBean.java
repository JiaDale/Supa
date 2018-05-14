package com.jdy.supa.module.user;


import java.util.Date;

import cn.bmob.v3.BmobUser;

public class UserBean extends BmobUser{
    private String security;
    private  String account;
    private  int userType;
    private int sex;
    private Date birth;
    private String num;
    private  String address;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSex() {
        return sex;
    }

    public String getSecurity() {
        return security;
    }

    public String getAccount() {
        return account;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
