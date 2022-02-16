package com.archerswet.test08.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.archerswet.test08.R;
import com.archerswet.test08.bean.Good;
import com.archerswet.test08.bean.ShopCar;
import com.archerswet.test08.request.RequestInterface;
import com.archerswet.test08.request.RetrofitTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * @description:message
 * @author:archerswet@163.com
 * @date:2021/12/27
 */
public class ShopCarAdapter extends RecyclerView.Adapter {


    List<ShopCar> shopCars;
    Context context;
    Handler handler;

    //作为临时购物车
    List<ShopCar> temp = new ArrayList<>();

    //uid
    SharedPreferences sharedPreferences;
    private int uid;

    //总数量 总价格
    double total_price;
    int total_number;

    public ShopCarAdapter(List<ShopCar> shopCars, Context context,Handler handler) {
        this.shopCars = shopCars;
        this.context = context;
        this.handler = handler;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_item,parent,false);
        sharedPreferences = context.getSharedPreferences("info",Context.MODE_PRIVATE);
        uid = sharedPreferences.getInt("uid",0);
        return new MyGoodsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyGoodsHolder goodsHolder = (MyGoodsHolder) holder;
        ShopCar shopCar = shopCars.get(position);
        goodsHolder.tv_name.setText(shopCar.getGname());
        goodsHolder.tv_price.setText(shopCar.getGprice() + "");
        goodsHolder.tv_number.setText(shopCar.getShopnumber() + "");
        goodsHolder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    temp.add(shopCar);
                }else{
                    temp.remove(shopCar);
                }
                handler.sendEmptyMessage(2);
            }
        });

        //添加一个商品
        goodsHolder.tv_increase.setOnClickListener(view -> {
            int n = shopCar.getShopnumber();
            shopCar.setShopnumber(++n);
            goodsHolder.tv_number.setText(shopCar.getShopnumber() + "");
            increaseOne(shopCar.getGid());
            handler.sendEmptyMessage(2);
        });

        //减少一个商品
        goodsHolder.tv_decrease.setOnClickListener(view -> {
            int n = shopCar.getShopnumber();
            if(n <= 1){
                return;
            }
            shopCar.setShopnumber(--n);
            goodsHolder.tv_number.setText(shopCar.getShopnumber() + "");
            decreaseOne(shopCar.getGid());
            handler.sendEmptyMessage(2);
        });
    }

    //算总数量
    public Integer getTotalNumber(){
        total_number = 0;
        for (ShopCar shopCar : temp) {
            int n = shopCar.getShopnumber();
            total_number += n;
        }
        return total_number;
    }

    //算总价
    public Double getTotalPrice(){
        total_price = 0;
        for (ShopCar shopCar : temp) {
            int n = shopCar.getShopnumber();
            total_price += n* shopCar.getGprice();
        }
        return total_price;
    }

    //获取选择的所有的商品id
    public List<Integer> getGids(){
        List<Integer> gids = new ArrayList<>();
        for (ShopCar shopCar : temp) {
            gids.add(shopCar.getGid());
        }
        return gids;
    }

    @Override
    public int getItemCount() {
        return shopCars.size();
    }

    class MyGoodsHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_check)
        CheckBox check;

        @BindView(R.id.item_name)
        TextView tv_name;

        @BindView(R.id.item_price)
        TextView tv_price;

        @BindView(R.id.item_number)
        TextView tv_number;

        @BindView(R.id.item_increase)
        TextView tv_increase;

        @BindView(R.id.item_decrease)
        TextView tv_decrease;

        public MyGoodsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private void increaseOne(int gid){
        //初始化retrofit对象
        Retrofit retrofit = RetrofitTool.getRetrofitInstance();
        //设置请求的内容
        RequestInterface requestInterface =  retrofit.create(RequestInterface.class);
        //通过RequestInterface 对象开始执行里面对应方法
        Call<ResponseBody> call = requestInterface.increase(uid,gid);
        //开始请求 将call里面的login方法进行执行，然后返回的结果会发往handler的handleMessage方法
        RetrofitTool.getRetrofitResult(call,handler,3);
    }

    private void decreaseOne(int gid){
        //初始化retrofit对象
        Retrofit retrofit = RetrofitTool.getRetrofitInstance();
        //设置请求的内容
        RequestInterface requestInterface =  retrofit.create(RequestInterface.class);
        //通过RequestInterface 对象开始执行里面对应方法
        Call<ResponseBody> call = requestInterface.decrease(uid,gid);
        //开始请求 将call里面的login方法进行执行，然后返回的结果会发往handler的handleMessage方法
        RetrofitTool.getRetrofitResult(call,handler,3);
    }
}
