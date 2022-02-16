package com.archerswet.test01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnItemSelected;
import butterknife.OnLongClick;
import butterknife.OnPageChange;
import butterknife.OnTextChanged;
import butterknife.OnTouch;

public class MainActivity extends AppCompatActivity {

//    黄油刀的所有事件 @OnXXXX 针对各种组件的触发方式

    @BindView(R.id.tv1)
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        使用黄油刀的第一步，让黄油刀初始化
        ButterKnife.bind(this);

        tv1.setText("hello");

    }

    //点击事件
    @OnClick({R.id.btn})
    public void btnClick(View view){
        Toast.makeText(this, "按钮被点击", Toast.LENGTH_SHORT).show();
    }

    //长按事件
    @OnLongClick(R.id.btn)
    public void btnLongClick(View view){
        Toast.makeText(this, "按钮被长按", Toast.LENGTH_SHORT).show();
    }

    //编辑框获取焦点 和 失去焦点  提示
    @OnFocusChange(R.id.edit)
    public void editFocus(View view,boolean state){
        Toast.makeText(this, "焦点状态 ：" + state, Toast.LENGTH_SHORT).show();
    }

    //编辑框的输入内容变化
    @OnTextChanged(R.id.edit)
    public void editChange(CharSequence charSequence){//CharSequence 字符串
        Toast.makeText(this, "当前的内容:" + charSequence, Toast.LENGTH_SHORT).show();
    }

    //多选 勾选框是否勾选的监听
    @OnCheckedChanged(R.id.ck)
    public void ckChange(boolean checked){
        Toast.makeText(this, "勾选状态：" + checked, Toast.LENGTH_SHORT).show();
    }


}