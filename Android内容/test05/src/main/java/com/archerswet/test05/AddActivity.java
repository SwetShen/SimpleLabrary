package com.archerswet.test05;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @description:message
 * @author:archerswet@163.com
 * @date:2021/12/10
 */
public class AddActivity extends AppCompatActivity {

    @BindView(R.id.add_edit_title)
    EditText edit_title;

    @BindView(R.id.add_edit_time)
    EditText edit_time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.add_btn_sure)
    public void addSure(){
        //将输入的数据返回到第一个页面
        String title = edit_title.getText().toString();
        String time = edit_time.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("title",title);
        intent.putExtra("time",time);
        setResult(200,intent);
        this.finish();
    }


}
