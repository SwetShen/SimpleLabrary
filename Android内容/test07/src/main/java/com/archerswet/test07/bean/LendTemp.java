package com.archerswet.test07.bean;

import java.sql.Timestamp;

/**
 * @description:message
 * @author:archerswet@163.com
 * @date:2021/12/22
 */
public class LendTemp {


    private Integer uid;
    private String uname;
    private Integer bid;
    private String bname;
    private Timestamp lstart;
    private Timestamp lfinish;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Timestamp getLstart() {
        return lstart;
    }

    public void setLstart(Timestamp lstart) {
        this.lstart = lstart;
    }

    public Timestamp getLfinish() {
        return lfinish;
    }

    public void setLfinish(Timestamp lfinish) {
        this.lfinish = lfinish;
    }
}
