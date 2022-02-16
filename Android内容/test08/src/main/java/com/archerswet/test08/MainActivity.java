package com.archerswet.test08;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.archerswet.test08.adapter.MainGoodsAdapter;
import com.archerswet.test08.bean.Good;
import com.archerswet.test08.request.RequestInterface;
import com.archerswet.test08.request.RetrofitTool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_list)
    RecyclerView recyclerView;

    SharedPreferences sharedPreferences;

    Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {

            if(msg.what == 1){
                String result = msg.getData().getString("data");
                List<Good> goods = new Gson().fromJson(result,new TypeToken<List<Good>>(){}.getType());
                if(goods != null){
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(new MainGoodsAdapter(goods,MainActivity.this,handler));
                }
            }else if(msg.what == 2){
                Toast.makeText(MainActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
            }

        }
    };

    @OnClick(R.id.main_car)
    public void toCar(){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,ShopCarActivity.class);
        MainActivity.this.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("info",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("uid",1001);
        editor.commit();

        getGoods();
    }

    private void getGoods(){
        //初始化retrofit对象
        Retrofit retrofit = RetrofitTool.getRetrofitInstance();
        //设置请求的内容
        RequestInterface requestInterface =  retrofit.create(RequestInterface.class);
        //通过RequestInterface 对象开始执行里面对应方法
        Call<ResponseBody> call = requestInterface.getgoods();
        //开始请求 将call里面的login方法进行执行，然后返回的结果会发往handler的handleMessage方法
        RetrofitTool.getRetrofitResult(call,handler,1);
    }
}