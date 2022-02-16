package com.archerswet.test06;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @description:message
 * @author:archerswet@163.com
 * @date:2021/12/13
 */
public class MyAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public MyAdapter(@NonNull FragmentManager fm, List<Fragment> fragments) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
    }

    //获取对应位置的Fragment
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    //获得Fragment的数量
    @Override
    public int getCount() {
        return fragments.size();
    }
}
