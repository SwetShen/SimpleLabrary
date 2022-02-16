package com.archerswet.test07.admin;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.archerswet.test07.R;
import com.archerswet.test07.adapter.SelectListAdapter;
import com.archerswet.test07.bean.User;
import com.archerswet.test07.request.RequestInterface;
import com.archerswet.test07.request.RetrofitTool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * @description:message
 * @author:archerswet@163.com
 * @date:2021/12/23
 */
public class SelectUserActivity extends AppCompatActivity {

    @BindView(R.id.select_name)
    EditText editText;

    @BindView(R.id.select_list)
    RecyclerView recyclerView;

    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String result = msg.getData().getString("data");
            List<User> users = new Gson().fromJson(result,new TypeToken<List<User>>(){}.getType());
            List<String> strlist = new ArrayList<>();
            for (User user : users) {
                Log.d("data_",user.getUname());
                strlist.add(user.getUid() + ":" + user.getUname());
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(SelectUserActivity.this));
            SelectListAdapter adapter = new SelectListAdapter(strlist,SelectUserActivity.this);
            recyclerView.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        ButterKnife.bind(this);

        getAllUsers();
    }

    //监听输入框的内容改变
    @OnTextChanged(R.id.select_name)
    public void textChange(CharSequence charSequence){
        String uname = charSequence.toString();
        getUsersByName(uname);
    }

    //查询所有用户
    private void getAllUsers(){
        Retrofit retrofit = RetrofitTool.getRetrofitInstance();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<ResponseBody> call = requestInterface.selectUser();
        RetrofitTool.getRetrofitResult(call,handler);
    }

    //根据名字查询用户
    private void getUsersByName(String uname){
        Retrofit retrofit = RetrofitTool.getRetrofitInstance();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<ResponseBody> call = requestInterface.selectUser(uname);
        RetrofitTool.getRetrofitResult(call,handler);
    }
}
