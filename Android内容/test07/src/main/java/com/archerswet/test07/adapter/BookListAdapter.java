package com.archerswet.test07.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.archerswet.test07.LendActivity;
import com.archerswet.test07.R;
import com.archerswet.test07.bean.Book;
import com.archerswet.test07.bean.Lend;
import com.archerswet.test07.request.RequestData;
import com.archerswet.test07.request.RequestInterface;
import com.archerswet.test07.request.RetrofitTool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * @description:message
 * @author:archerswet@163.com
 * @date:2021/12/20
 */
public class BookListAdapter extends RecyclerView.Adapter {

    private List<Book> bookList;
    private Context context;//MainActivity.this
    private Intent intent;

    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String result = msg.getData().getString("data");
            List<Lend> lends = new Gson().fromJson(result,new TypeToken<List<Lend>>(){}.getType());

            if(lends.size() > 0){
                //你已借过该书
                Toast.makeText(context, "你已借过该书", Toast.LENGTH_SHORT).show();
            }else{
                //跳转借书页面
                context.startActivity(intent);
            }

        }
    };

    public BookListAdapter(List<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_book_item,parent,false);
        return new BookListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookListHolder bookListHolder = (BookListHolder) holder;

        Book book = bookList.get(position);
        //利用picasso加载图片到对应的ImageView中
        Picasso.with(context).load(RequestData.BASEURL + book.getBimg()).into(bookListHolder.book_iv);
        bookListHolder.book_name_tv.setText(book.getBname());
        bookListHolder.book_author_tv.setText(book.getBauthor());
        bookListHolder.book_type_tv.setText(book.getBtype());

        //列表项的点击事件
        bookListHolder.book_layout.setOnClickListener(view -> {

            SharedPreferences sharedPreferences = context.getSharedPreferences("info",context.MODE_PRIVATE);
            Integer uid = sharedPreferences.getInt("uid",0);

            intent = new Intent();
            intent.setClass(context, LendActivity.class);
            //携带bid传给LendActivity
            intent.putExtra("bid",bookList.get(position).getBid());

            Retrofit retrofit = RetrofitTool.getRetrofitInstance();
            RequestInterface requestInterface = retrofit.create(RequestInterface.class);
            Call<ResponseBody> call =  requestInterface.exist(uid,bookList.get(position).getBid());
            RetrofitTool.getRetrofitResult(call,handler);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class BookListHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.book_item_iv)
        ImageView book_iv;

        @BindView(R.id.book_item_name_tv)
        TextView book_name_tv;

        @BindView(R.id.book_item_author_tv)
        TextView book_author_tv;

        @BindView(R.id.book_item_type_tv)
        TextView book_type_tv;

        //列表项的所有内容
        @BindView(R.id.book_item_layout)
        LinearLayout book_layout;

        public BookListHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
