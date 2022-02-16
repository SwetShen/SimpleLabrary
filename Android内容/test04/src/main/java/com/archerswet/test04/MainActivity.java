package com.archerswet.test04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.login_edit_user)
    EditText edit_user;

    @BindView(R.id.login_edit_password)
    EditText edit_pwd;

    @BindView(R.id.login_ck_remember)
    CheckBox checkBox;

    //预置的账号和密码
    String user = "123";
    String password = "123";

    //共享内存
    SharedPreferences preferences;

    //是否记住密码
    boolean remember = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        //完成共享内存的初始化
        //getSharedPreferences(共享内存的名称，访问模式)
        //MODE_PRIVATE 私有模式
        preferences = getSharedPreferences("note",MODE_PRIVATE);
        //第一次打开程序的时候，没有相应的账号和密码的保存记录
        String user = preferences.getString("user","");
        String password = preferences.getString("password","");
        if("".equals(user) || "".equals(password)){
            //证明没有记住密码
        }else{
            //记住了密码 显示到编辑框中
            edit_user.setText(user);
            edit_pwd.setText(password);
            //如果记住了密码，那么下一次登录的时候，记住密码会被勾选
            checkBox.setChecked(true);
        }
    }

    @OnCheckedChanged(R.id.login_ck_remember)
    public void checkRemember(boolean checked){
        //勾选的状态 为true 代表记住密码 勾选状态 为false的时候，代码 不记住密码
        remember = checked;
    }

    @OnClick(R.id.login_btn_load)
    public void login(){
        //基本数据类型 可以用==判断  引用数据类型都是equals
        //基本数据类型 byte short int long float double boolean char
        //获取编辑框的内容
        String user = edit_user.getText().toString();
        String password = edit_pwd.getText().toString();
        if(this.user.equals(user) && this.password.equals(password)){
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            //如果记住了密码
            if(remember){
                //将正确的账号和密码存入共享内存
                //Editor 共享内存的编辑对象
                SharedPreferences.Editor editor = preferences.edit();
                //在编辑对象中写入对应的内容
                editor.putString("user",user);
                editor.putString("password",password);
                //提交到共享内存
                editor.commit();
            }
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,SecondActivity.class);
            this.startActivity(intent);
        }else{
            Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
        }
    }


}