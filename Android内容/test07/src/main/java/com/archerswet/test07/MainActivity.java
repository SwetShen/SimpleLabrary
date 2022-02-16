package com.archerswet.test07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.archerswet.test07.adapter.BookListAdapter;
import com.archerswet.test07.adapter.MainPagerAdapter;
import com.archerswet.test07.bean.Book;
import com.archerswet.test07.request.RequestInterface;
import com.archerswet.test07.request.RetrofitTool;
import com.archerswet.test07.views.CategoryFragment;
import com.archerswet.test07.views.HomeFragment;
import com.archerswet.test07.views.ProfileFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
*@description:首页
*@author:archerswet@163.com
*@date:2021/12/17 8:18
*/
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_vp)
    ViewPager viewPager;

    @BindView(R.id.main_tab)
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //动态权限申请
        chekPermission();
        ButterKnife.bind(this);

        List<Fragment> list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new CategoryFragment());
        list.add(new ProfileFragment());

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(),list);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    private void chekPermission(){
        if(Build.VERSION.SDK_INT >= 23){
            int REQUEST_CODE_CONTRACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

            for(String str : permissions){
                if(this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED){
                    this.requestPermissions(permissions,REQUEST_CODE_CONTRACT);
                }
            }

        }
    }
}