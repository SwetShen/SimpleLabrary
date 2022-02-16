package com.archerswet.test07.views;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.archerswet.test07.MainActivity;
import com.archerswet.test07.R;
import com.archerswet.test07.adapter.BookListAdapter;
import com.archerswet.test07.bean.Book;
import com.archerswet.test07.request.RequestInterface;
import com.archerswet.test07.request.RetrofitTool;
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
 * @date:2021/12/20
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.home_book_rv)
    RecyclerView recyclerView;

    @BindView(R.id.home_edit_content)
    EditText edit_content;

    //处理线程消息
    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //取出网络请求的数据
            Bundle bundle = msg.getData();
            String result = bundle.getString("data");
            //解析数据
            Gson gson = new Gson();
            List<Book> books = gson.fromJson(result,new TypeToken<List<Book> >(){}.getType());
            //列表放置的模式
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            //将数据设置到列表中
            BookListAdapter adapter = new BookListAdapter(books,getContext());
            recyclerView.setAdapter(adapter);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home,container,false);
        //绑定
        ButterKnife.bind(this,view);
        getBookData();
        return view;
    }

    @OnClick(R.id.home_btn_search)
    public void search(){
        String str = edit_content.getText().toString();
        //发送网络请求
        Retrofit retrofit = RetrofitTool.getRetrofitInstance();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<ResponseBody> call = requestInterface.selectByName(str);
        RetrofitTool.getRetrofitResult(call,handler);
    }

    private void getBookData(){
        //发送网路请求
        Retrofit retrofit = RetrofitTool.getRetrofitInstance();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<ResponseBody> call = requestInterface.selectAll();
        RetrofitTool.getRetrofitResult(call,handler);
    }

}
