package com.archerswet.test02;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseFragment extends Fragment {

    private String name;

    public BaseFragment(String name) {
        this.name = name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment,container,false);
        ButterKnife.bind(this,view);
        textView.setText(name);
        return view;
    }

    @BindView(R.id.frag_tv)
    TextView textView;
}
