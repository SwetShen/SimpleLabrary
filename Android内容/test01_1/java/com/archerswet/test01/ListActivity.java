package com.archerswet.test01;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list);

        ButterKnife.bind(this);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("选项" + i) ;
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ListAdapter(list,this));

    }
}
