package com.archerswet.test05;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//Alt+Enter
public class ListAdapter extends RecyclerView.Adapter {

    //定义一个可以从外界接收的数据
    //集合 == 数组
    private List<ListItem> list;

    //上下文对象 ：页面的所有信息
    private Context context;

    //通过构造器的方式从外界初始化
    //右键 ---> generate --> constructer
    public ListAdapter(List<ListItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull//确认列表项的布局
    @Override //重写( RecyclerView.Adapter)里面的方法
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //获取列表项的布局
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        //需要返回视图代理 （列表项的所有内容）
        return new MyHolder(itemView);
    }

    @Override//设置列表里面的内容的设置和监听（列表项中有图片、文本，如果要设置，都必须在这个方法实现）position 0 ~ 最大
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
//       通过视图代理设置内容
//        list.get(position) 取出对应位置的列表项的数据
        myHolder.imageView.setImageResource(list.get(position).getDrawable());
        myHolder.textView.setText(list.get(position).getTitle());
        myHolder.tv_date.setText(list.get(position).getDate());

//        设置删除
        myHolder.delete.setOnClickListener(view -> {
            //删除集合对应的数据 position
            //remove 集合删除对应的下标的数据
            list.remove(position);
            //刷新
            notifyDataSetChanged();
        });

        myHolder.linearLayout.setOnClickListener(view -> {
            //当列表项被点击的时候，会触发该方法
            Intent intent = new Intent();
            intent.setClass(context,InfoActivity.class);
            intent.putExtra("name",list.get(position).getTitle());
            context.startActivity(intent);
        });
    }

    @Override// 设置列表的项数
    public int getItemCount() {
        return list.size();
    }

    //    创建一个视图代理的类
    //列表项的布局
    class MyHolder extends RecyclerView.ViewHolder {

        //列表项的两个组件
        @BindView(R.id.list_item_iv)
        ImageView imageView;

        @BindView(R.id.list_item_tv)
        TextView textView;

        @BindView(R.id.list_item_date)
        TextView tv_date;

        @BindView(R.id.list_item_del)
        TextView delete;

        @BindView(R.id.list_item_container)
        LinearLayout linearLayout;

        //itemView 列表项的布局
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //黄油刀绑定
            ButterKnife.bind(this,itemView);
        }
    }
}
