package com.archerswet.test02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.vp)
    ViewPager viewPager;

    @BindView(R.id.tb)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        List<Fragment> list = new ArrayList<>();
        list.add(new BaseFragment("开始"));
        list.add(new BaseFragment("继续"));
        list.add(new BaseFragment("结束"));
        viewPager.setAdapter(new MyPagerAdapter(this.getSupportFragmentManager(),list));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

//        tabLayout.addTab(tabLayout.newTab().setCustomView(setCustomView(R.mipmap.ic_launcher,"首页")));
//        tabLayout.addTab(tabLayout.newTab().setCustomView(setCustomView(R.mipmap.ic_launcher,"详情")));
//        tabLayout.addTab(tabLayout.newTab().setCustomView(setCustomView(R.mipmap.ic_launcher,"个人")));

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

    }

    public View setCustomView(int drawable,String title){
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item,null);
        ImageView imageView = view.findViewById(R.id.item_iv);
        TextView textView = view.findViewById(R.id.item_tv);
        imageView.setImageResource(drawable);
        textView.setText(title);
        return view;
    }
}