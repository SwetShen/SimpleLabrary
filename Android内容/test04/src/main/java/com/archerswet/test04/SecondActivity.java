package com.archerswet.test04;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.second_tv)
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ButterKnife.bind(this);

        SharedPreferences preferences = getSharedPreferences("note",MODE_PRIVATE);
        String str = preferences.getString("user","");
        textView.setText(str);
    }
}
