package com.archerswet.test08.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.archerswet.test08.R;
import com.archerswet.test08.bean.Good;
import com.archerswet.test08.request.RequestInterface;
import com.archerswet.test08.request.RetrofitTool;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * @description:message
 * @author:archerswet@163.com
 * @date:2021/12/28
 */
public class MainGoodsAdapter extends RecyclerView.Adapter {

    List<Good> goods;
    Context context;
    Handler handler;
    SharedPreferences sharedPreferences;

    public MainGoodsAdapter(List<Good> goods, Context context,Handler handler) {
        this.goods = goods;
        this.context = context;
        this.handler = handler;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_good_item,parent,false);
        return new MyGoodsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyGoodsHolder goodsHolder = (MyGoodsHolder) holder;
        Good good = goods.get(position);
        goodsHolder.tv_name.setText(good.getGname());
        goodsHolder.tv_price.setText(good.getGprice() + "元");
        goodsHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu menu = new PopupMenu(context,view);
                menu.getMenuInflater().inflate(R.menu.add,menu.getMenu());
                menu.show();

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.add_to_car:
                                sharedPreferences = context.getSharedPreferences("info",Context.MODE_PRIVATE);
                                int uid = sharedPreferences.getInt("uid",0);
                                int gid = good.getGid();
                                int shopnumber = 1;

                                Retrofit retrofit = RetrofitTool.getRetrofitInstance();
                                RequestInterface requestInterface =  retrofit.create(RequestInterface.class);
                                Call<ResponseBody> call = requestInterface.addToCar(uid,gid,shopnumber);
                                RetrofitTool.getRetrofitResult(call,handler,2);
                                break;
                        }
                        return true;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return goods.size();
    }

    class MyGoodsHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.good_item_name)
        TextView tv_name;

        @BindView(R.id.good_item_price)
        TextView tv_price;

        @BindView(R.id.good_item_ll)
        LinearLayout ll;

        public MyGoodsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
