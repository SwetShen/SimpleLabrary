package com.archerswet.test07;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.archerswet.test07.bean.User;
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
 * @date:2021/12/17
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_edit_uid)
    EditText edit_uid;

    @BindView(R.id.login_edit_upassword)
    EditText edit_upassword;

    @BindView(R.id.login_rg_usite)
    RadioGroup radioGroup;

    private int usite = 0;

    //内部储存
    private SharedPreferences sharedPreferences;

    //设置一个线程通信对象
    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //处理了线程消息
            //取出发送的包
            Bundle bundle = msg.getData();
            //从包中取出结果
            String result = bundle.getString("data");
            //在控制台打印出这个结果
            Log.d("data_", result);
            //解析这个结果
            Gson gson = new Gson();
            //,new TypeToken<左边需要得到的类型>(){}.getType()
            List<User> users = gson.fromJson(result, new TypeToken<List<User>>() {
            }.getType());
            //判断集合
            if (users.size() > 0) {
                //如果结果取出来大于0 证明成功登录
                Intent intent = new Intent();
                //管理员 和 普通用户
                if(users.get(0).getUsite() == 0){
                    //保存用户的登录uid(记住密码功能)
                    //sharedPreferences 会在手机中生成一个xml临时文件保存 SQLite
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("uid",users.get(0).getUid());
                    editor.putString("uname",users.get(0).getUname());
                    editor.putString("uimg",users.get(0).getUimg());
                    editor.commit();//一定要提交信息
                    //普通用户的主页面
                    intent.setClass(LoginActivity.this,MainActivity.class);
                }else{
                    //管理员的主页面
                    intent.setClass(LoginActivity.this,AdminActivity.class);
                }
                LoginActivity.this.startActivity(intent);
            } else {
                //如果结果小于0 证明登录失败
                Toast.makeText(LoginActivity.this, "登录名或密码错误", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //缓存初始化
        sharedPreferences = getSharedPreferences("info",MODE_PRIVATE);

        ButterKnife.bind(this);

        //设置radioGroup默认选择普通用户
        radioGroup.check(usite + 1);// 1 普通用户 2 管理员
        //设置radioGroup监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                usite = i - 1;
            }
        });
    }

    @OnClick(R.id.login_btn_login)
    public void login(){
        String uid = edit_uid.getText().toString();
        String upassword = edit_upassword.getText().toString();

        if("".equals(uid)){
            Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if("".equals(upassword)){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        //初始化retrofit对象
        Retrofit retrofit = RetrofitTool.getRetrofitInstance();
        //设置请求的内容
        RequestInterface requestInterface =  retrofit.create(RequestInterface.class);
        //通过RequestInterface 对象开始执行里面对应方法
        Call<ResponseBody> call = requestInterface.login(uid,upassword,usite);
        //开始请求 将call里面的login方法进行执行，然后返回的结果会发往handler的handleMessage方法
        RetrofitTool.getRetrofitResult(call,handler);

    }

    @OnClick(R.id.login_btn_reg)
    public void reg(){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this,RegActivity.class);
        this.startActivity(intent);
    }
}
