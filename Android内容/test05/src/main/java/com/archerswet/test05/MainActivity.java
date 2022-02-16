package com.archerswet.test05;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    //创建集合
    List<ListItem> list = new ArrayList<>();
    //定义一个变量
    int n = 8;
    //定义适配器
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        //往集合中添加数据
        list.add(new ListItem(R.drawable.a1,"标题1","2020-1"));
        list.add(new ListItem(R.drawable.a2,"标题21111111111111111111111111111111111111111","2020-2"));
        list.add(new ListItem(R.drawable.a3,"标题3111111","2020-3"));
        list.add(new ListItem(R.drawable.a4,"标题41","2020-4"));
        list.add(new ListItem(R.drawable.a5,"标题51111111111111111111111111111111111111111111111","2020-5"));
        list.add(new ListItem(R.drawable.a6,"标题6","2020-6"));
        list.add(new ListItem(R.drawable.a7,"标题711111111111111111111111111","2020-7"));

        //设置列表
        //设置列表的呈现形式 LinearLayoutManager 垂直，且从上往下的列表
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //以瀑布流的方式显示列表
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));

        adapter = new ListAdapter(list,this);
        recyclerView.setAdapter(adapter);



    }

    @OnClick(R.id.btn_add)
    public void addList(){
        //启用带返回值的跳转
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,AddActivity.class);
        this.startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       String title = data.getStringExtra("title");
       String time = data.getStringExtra("time");
       //集合中增加记录
        list.add(new ListItem(R.drawable.a6,title,time));
        //刷新页面
        adapter.notifyDataSetChanged();
    }
}