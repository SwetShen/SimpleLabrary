package com.archerswet.test05;

//如何定义一个JavaBean类 1、声明变量（记住加private） 2、生成get、set
//作用：可以帮助我们整理结构，访问更加便捷
public class ListItem {

    private int drawable;//图片 int = R.drawable.xxxx
    private String title;//标题

    private String date;//时间

    //针对上述的两个变量，定义get set方法
    //右键 --> generate --> getter and setter


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //访问
    public int getDrawable() {
        return drawable;
    }

    //设置
    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //右键 --> generate --> constructer

    public ListItem(int drawable, String title,String date) {
        this.drawable = drawable;
        this.title = title;
        this.date = date;
    }

    public ListItem() {
    }
}
