package com.archerswet.test08;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.archerswet.test08.adapter.GoodsAdapter;
import com.archerswet.test08.adapter.ShopCarAdapter;
import com.archerswet.test08.bean.Good;
import com.archerswet.test08.bean.ShopCar;
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

/**
 * @description:message
 * @author:archerswet@163.com
 * @date:2021/12/28
 */
public class ShopCarActivity extends AppCompatActivity {

    @BindView(R.id.shop_goods)
    RecyclerView recyclerView;

    @BindView(R.id.shop_total)
    TextView textView;

    private ShopCarAdapter adapter;

    SharedPreferences sharedPreferences;
    private Integer uid;

    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {

            if(msg.what == 1){
                String result = msg.getData().getString("data");
                List<ShopCar> shopCars = new Gson().fromJson(result,new TypeToken<List<ShopCar>>(){}.getType());

                if(shopCars != null){
                    recyclerView.setLayoutManager(new LinearLayoutManager(ShopCarActivity.this));
                    adapter = new ShopCarAdapter(shopCars,ShopCarActivity.this,handler);
                    recyclerView.setAdapter(adapter);

                }
            }else if(msg.what == 2){
                Log.d("data_",adapter.getTotalNumber() + ":" + adapter.getTotalPrice());
                textView.setText("????????????" +adapter.getTotalNumber()+"????????????"+adapter.getTotalPrice()+"???");
            }else if(msg.what == 4){
                Toast.makeText(ShopCarActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                getGoods();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcar);

        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("info",MODE_PRIVATE);
        uid = sharedPreferences.getInt("uid",0);

        getGoods();
    }

    @OnClick(R.id.shop_submit)
    public void submitOrder(){
        Retrofit retrofit = RetrofitTool.getRetrofitInstance();
        RequestInterface requestInterface =  retrofit.create(RequestInterface.class);
        Call<ResponseBody> call = requestInterface.takeOrder(uid,adapter.getTotalNumber(),adapter.getTotalPrice(),adapter.getGids());
        RetrofitTool.getRetrofitResult(call,handler,4);
    }

    private void getGoods(){
        //?????????retrofit??????
        Retrofit retrofit = RetrofitTool.getRetrofitInstance();
        //?????????????????????
        RequestInterface requestInterface =  retrofit.create(RequestInterface.class);
        //??????RequestInterface ????????????????????????????????????
        Call<ResponseBody> call = requestInterface.getFromCar(uid);
        //???????????? ???call?????????login???????????????????????????????????????????????????handler???handleMessage??????
        RetrofitTool.getRetrofitResult(call,handler,1);
    }

}
