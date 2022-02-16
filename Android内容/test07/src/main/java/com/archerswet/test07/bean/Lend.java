package com.archerswet.test07.bean;

import java.sql.Date;

/**
 * @description:message
 * @author:archerswet@163.com
 * @date:2021/12/21
 */
public class Lend {

    private Integer uid;
    private Integer bid;
    private String lstart;
    private String lfinish;

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

    public String getLstart() {
        return lstart;
    }

    public void setLstart(String lstart) {
        this.lstart = lstart;
    }

    public String getLfinish() {
        return lfinish;
    }

    public void setLfinish(String lfinish) {
        this.lfinish = lfinish;
    }
}
