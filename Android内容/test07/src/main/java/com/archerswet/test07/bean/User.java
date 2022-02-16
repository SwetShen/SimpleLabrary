package com.archerswet.test07.bean;

/**
 * @description:User JavaBean
 * @author:archerswet@163.com
 * @date:2021/12/17
 */
public class User {

    private Integer uid;
    private String uname;
    private String upassword;
    private Integer usite;
    private String uimg;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }

    public Integer getUsite() {
        return usite;
    }

    public void setUsite(Integer usite) {
        this.usite = usite;
    }

    public String getUimg() {
        return uimg;
    }

    public void setUimg(String uimg) {
        this.uimg = uimg;
    }
}
