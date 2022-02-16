package com.archerswet.test07.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.archerswet.test07.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description:message
 * @author:archerswet@163.com
 * @date:2021/12/23
 */
public class SelectListAdapter extends RecyclerView.Adapter {

    private List<String> list;
    private Context context;

    public SelectListAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SelectListHolder(LayoutInflater.from(context).inflate(R.layout.layout_select_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SelectListHolder listHolder = (SelectListHolder) holder;
        listHolder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SelectListHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.select_item_title)
        TextView textView;

        public SelectListHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
