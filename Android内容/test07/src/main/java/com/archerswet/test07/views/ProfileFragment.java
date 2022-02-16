package com.archerswet.test07.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.archerswet.test07.R;
import com.archerswet.test07.adapter.LendListAdapter;
import com.archerswet.test07.bean.LendTemp;
import com.archerswet.test07.request.RequestData;
import com.archerswet.test07.request.RequestInterface;
import com.archerswet.test07.request.RetrofitTool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * @description:message
 * @author:archerswet@163.com
 * @date:2021/12/20
 */
public class ProfileFragment extends Fragment {

    @BindView(R.id.profile_icon)
    ImageView profile_icon;

    @BindView(R.id.profile_nikename)
    TextView profile_nikename;

    @BindView(R.id.profile_lend_list)
    RecyclerView recyclerView;

    @BindView(R.id.profile_lend_refresh)
    SwipeRefreshLayout refreshLayout;

    private Integer uid;

    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String result = msg.getData().getString("data");
            List<LendTemp> list = new Gson().fromJson(result,new TypeToken<List<LendTemp>>(){}.getType());

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            LendListAdapter adapter = new LendListAdapter(list,getContext());
            recyclerView.setAdapter(adapter);

            //结束刷新小球
            refreshLayout.setRefreshing(false);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_profile,container,false);
        ButterKnife.bind(this,view);
        getUserInfo();
        getUserLend();
        //列表刷新
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //重新获取列表的信息
                getUserLend();
            }
        });
        return view;
    }

    //查询用户信息
    private void getUserInfo(){
        SharedPreferences preferences = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        uid = preferences.getInt("uid",0);
        String uname = preferences.getString("uname","");
        String uimg = preferences.getString("uimg","");
        Picasso.with(getContext()).load(RequestData.BASEURL + uimg).into(profile_icon);
        profile_nikename.setText(uname);

    }

    //查询该用户的借书记录
    private void getUserLend(){
        Retrofit retrofit = RetrofitTool.getRetrofitInstance();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<ResponseBody> call = requestInterface.selectLend(uid);
        RetrofitTool.getRetrofitResult(call,handler);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView = null;
        refreshLayout = null;
        profile_icon = null;
        profile_nikename = null;
    }
}
