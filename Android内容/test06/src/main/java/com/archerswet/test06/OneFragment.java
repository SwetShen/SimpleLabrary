package com.archerswet.test06;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @description:message
 * @author:archerswet@163.com
 * @date:2021/12/13
 */
public class OneFragment extends Fragment {

    //Activity = onCreate \  Fragment = onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //将布局和Fragment进行关连
        // LayoutInflater inflater 动态获取并设置布局
        View view = inflater.inflate(R.layout.frag_one,container,false);
        Button btn = view.findViewById(R.id.home_btn);
        btn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "按钮被点击", Toast.LENGTH_SHORT).show();
        });
        return view;
    }

}
