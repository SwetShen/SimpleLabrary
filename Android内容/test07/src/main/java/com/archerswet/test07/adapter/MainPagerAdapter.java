package com.archerswet.test07.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @description:ViewPager的适配器
 * @author:archerswet@163.com
 * @date:2021/12/20
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public MainPagerAdapter(@NonNull FragmentManager fm,  List<Fragment> list) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
