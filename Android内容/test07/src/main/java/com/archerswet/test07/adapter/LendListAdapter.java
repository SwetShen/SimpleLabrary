package com.archerswet.test07.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.archerswet.test07.R;
import com.archerswet.test07.bean.LendTemp;

import org.w3c.dom.Text;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description:message
 * @author:archerswet@163.com
 * @date:2021/12/22
 */
public class LendListAdapter extends RecyclerView.Adapter {

    private List<LendTemp> list;
    private Context context;

    public LendListAdapter(List<LendTemp> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_lend_item,parent,false);
        return new MyLendListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyLendListHolder listHolder = (MyLendListHolder) holder;
        listHolder.item_title.setText(list.get(position).getBname());



//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        listHolder.item_datetime.setText(list.get(position).getLstart().toString());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyLendListHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.lend_item_title)
        TextView item_title;

        @BindView(R.id.lend_item_datetime)
        TextView item_datetime;

        public MyLendListHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
