package com.archerswet.test01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends RecyclerView.Adapter {

    private List<String> list;
    private Context context;

    public ListAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new MHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MHolder mHolder = (MHolder) holder;

        mHolder.textView.setWidth(ScreenUtil.getScreenWidth(context));
        mHolder.textView.setText(list.get(position));

        mHolder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mHolder.hsv.setSmoothScrollingEnabled(true);
                AnimationSet set = new AnimationSet(true);
                TranslateAnimation translateAnimation = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF,0f,
                        Animation.RELATIVE_TO_SELF,-0.02f,
                        Animation.RELATIVE_TO_SELF,0f,
                        Animation.RELATIVE_TO_SELF,0f
                );
                translateAnimation.setDuration(500);
                set.addAnimation(translateAnimation);
                mHolder.hsv.setAnimation(set);
                mHolder.hsv.scrollTo(200,0);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.list_item_tv)
        TextView textView;

        @BindView(R.id.hsv)
        HorizontalScrollView hsv;


        public MHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
