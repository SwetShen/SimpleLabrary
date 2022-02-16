package com.archerswet.test07.bean;

/**
 * @description:message
 * @author:archerswet@163.com
 * @date:2021/12/20
 */
public class Book {

    private Integer bid;
    private String bname;
    private String bauthor;
    private String btype;
    private String bdesc;
    private Integer bcount;
    private String bimg;

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBauthor() {
        return bauthor;
    }

    public void setBauthor(String bauthor) {
        this.bauthor = bauthor;
    }

    public String getBtype() {
        return btype;
    }

    public void setBtype(String btype) {
        this.btype = btype;
    }

    public String getBdesc() {
        return bdesc;
    }

    public void setBdesc(String bdesc) {
        this.bdesc = bdesc;
    }

    public Integer getBcount() {
        return bcount;
    }

    public void setBcount(Integer bcount) {
        this.bcount = bcount;
    }

    public String getBimg() {
        return bimg;
    }

    public void setBimg(String bimg) {
        this.bimg = bimg;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bid=" + bid +
                ", bname='" + bname + '\'' +
                ", bauthor='" + bauthor + '\'' +
                ", btype='" + btype + '\'' +
                ", bdesc='" + bdesc + '\'' +
                ", bcount=" + bcount +
                ", bimg='" + bimg + '\'' +
                '}';
    }
}
