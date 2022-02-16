package com.archerswet.test03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.second_tv)
    TextView tv;

//    如果在全局中声明变量，可以在Activity下的所有方法中使用
    String name;
    int count;

//    响应码
    int resultCode = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ButterKnife.bind(this);

//        获取来自MainActivity的信息
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        count = intent.getIntExtra("count",0);
        tv.setText("阿克苏" + name + ":" + count + "(点击添加苹果)");


    }

    @OnClick(R.id.second_tv)
    public void add(){
        count = count + 1;//添加一个苹果
        tv.setText("阿克苏" + name + ":" + count + "(点击添加苹果)");
    }

    @OnClick(R.id.btn_back)
    public void back(){
        //向MainActivity回传信息
        Intent intent2 = new Intent();
        intent2.putExtra("count",count);
        //setResult 向之前的页面 MainActivity传递数值
        setResult(resultCode,intent2);
        //返回上一个页面
        this.finish();
    }
}
