package com.archerswet.test07.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.archerswet.test07.R;

/**
 * @description:排行 分类
 * @author:archerswet@163.com
 * @date:2021/12/20
 */
public class CategoryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_category,container,false);
        return view;
    }
}
