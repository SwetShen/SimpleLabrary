package com.archerswet.test05;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description:message
 * @author:archerswet@163.com
 * @date:2021/12/13
 */
public class InfoActivity extends AppCompatActivity {

    @BindView(R.id.info_tv)
    TextView info_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String title = intent.getStringExtra("name");

        info_tv.setText(title + "的详情页面");

    }
}
