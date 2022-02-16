package com.archerswet.test07;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.archerswet.test07.admin.SelectUserActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @description:message
 * @author:archerswet@163.com
 * @date:2021/12/23
 */
public class AdminActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.admin_btn_query_user)
    public void selectAllUsers(){
        intent = new Intent();
        intent.setClass(AdminActivity.this, SelectUserActivity.class);
        this.startActivity(intent);
    }
}
