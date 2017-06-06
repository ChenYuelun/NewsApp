package com.example.newsapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myutils_library.Utils.ConstantUtils;
import com.example.newsapp.MenuDetailPager.TabDetaiPager;
import com.example.newsapp.R;
import com.example.newsapp.domain.PictureNewsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/6/6.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private final Context context;
    private final List<PictureNewsBean.DataBean.NewsBean> newsBeanList;


    public MyRecyclerViewAdapter(Context context, List<PictureNewsBean.DataBean.NewsBean> newsBeanList) {
        this.context = context;
        this.newsBeanList = newsBeanList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_picture_recyclerview, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PictureNewsBean.DataBean.NewsBean newsBean = newsBeanList.get(position);
        holder.tvTitle.setText(newsBean.getTitle());
        Glide.with(context).load(ConstantUtils.BASE_URL+newsBean.getLargeimage()).apply(TabDetaiPager.myOptions).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return newsBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
