package com.archerswet.test06;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.vp)
    ViewPager viewPager;

    @BindView(R.id.tab)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //准备Fragment的集合
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new OneFragment());
        fragments.add(new TwoFragment());
        fragments.add(new ThreeFragment());

        //设置ViewPager的适配器
        //getSupportFragmentManager()  Fragment管理对象
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),fragments));

        //使用ViewPager绑定TabLayout
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //TabLayout绑定ViewPager
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }
}