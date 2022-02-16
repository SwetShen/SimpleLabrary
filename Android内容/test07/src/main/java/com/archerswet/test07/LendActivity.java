package com.archerswet.test07;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.archerswet.test07.bean.Book;
import com.archerswet.test07.bean.PostResult;
import com.archerswet.test07.request.RequestData;
import com.archerswet.test07.request.RequestInterface;
import com.archerswet.test07.request.RetrofitTool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @description:message
 * @author:archerswet@163.com
 * @date:2021/12/21
 */
public class LendActivity extends AppCompatActivity {

    @BindView(R.id.lend_iv_book)
    ImageView lend_iv;

    @BindView(R.id.lend_title_tv)
    TextView lend_title;

    @BindView(R.id.lend_info_tv)
    TextView lend_info;

    private int count = 0;
    private int uid;
    private int bid;

    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String result = msg.getData().getString("data");

            if(msg.what == 1){
                List<Book> books = new Gson().fromJson(result,new TypeToken<List<Book>>(){}.getType());
                Book book = books.get(0);

                Picasso.with(LendActivity.this).load(RequestData.BASEURL + book.getBimg()).into(lend_iv);
                lend_title.setText(book.getBname());
                lend_info.setText(book.getBauthor() + "\n" + book.getBtype() + "\n" + book.getBdesc() + "\n" + book.getBcount());
                //获取书籍的数量
                count = book.getBcount();
            }else if(msg.what == 2){
                if(count <= 0){//当书籍数量为0时，不可以借书
                    Toast.makeText(LendActivity.this, "书籍暂无", Toast.LENGTH_SHORT).show();
                    return;
                }
                //借书是否成功
                PostResult postResult = new Gson().fromJson(result,new TypeToken<PostResult>(){}.getType());
                if(postResult.getAffectedRows() > 0){
                    Toast.makeText(LendActivity.this, "借书成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LendActivity.this, "借书失败", Toast.LENGTH_SHORT).show();
                }
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend);

        //在缓存中获取登陆时存入的uid
        SharedPreferences sharedPreferences = getSharedPreferences("info",MODE_PRIVATE);
        uid = sharedPreferences.getInt("uid",0);

        ButterKnife.bind(this);
        //获取从Adapter传值的bid
        Intent intent = getIntent();
        bid = intent.getIntExtra("bid",0);

        //通过bid查询这个书的所有信息
        Retrofit retrofit = RetrofitTool.getRetrofitInstance();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<ResponseBody> call = requestInterface.selectById(bid);

        RetrofitTool.getRetrofitResult(call,handler,1);
    }

    @OnClick(R.id.lend_btn_lend)
    public void lend(){
        Retrofit retrofit = RetrofitTool.getRetrofitInstance();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<ResponseBody> call = requestInterface.lend(uid,bid);
        RetrofitTool.getRetrofitResult(call,handler,2);
    }

    @OnClick(R.id.lend_btn_cancel)
    public void cancel(){
        finish();
    }
}
