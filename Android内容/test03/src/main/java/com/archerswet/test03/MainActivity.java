package com.archerswet.test03;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    //商品的名称
    String name = "苹果";
    //苹果的数量
    int count = 0;
    //请求码
    int RequestCode = 100;

    @BindView(R.id.first_tv)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //显示当前的商品信息
        textView.setText("商品名称：" + name + ",数量为：" + count);
    }

    @OnClick(R.id.btn_to)
    public void toSecond(){
        //跳转
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,SecondActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("count",count);
        //启动一个带返回值的跳转
        startActivityForResult(intent,RequestCode);
    }

    //onActivityResult 接收回传的参数
    //requestCode 请求码
    //resultCode 响应码
    //intent data 就时SecondActivity 回传的信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == 200){
            count = data.getIntExtra("count",0);
            //再次将商品信息设置
            textView.setText("商品名称：" + name + ",数量为：" + count);
        }
    }
}