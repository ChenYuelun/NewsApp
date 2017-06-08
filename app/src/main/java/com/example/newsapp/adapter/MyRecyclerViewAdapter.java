package com.example.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myutils_library.Utils.BitmapCacheUtils;
import com.example.myutils_library.Utils.ConstantUtils;
import com.example.myutils_library.Utils.NetCacheUtils;
import com.example.newsapp.MenuDetailPager.TabDetaiPager;
import com.example.newsapp.R;
import com.example.newsapp.acitivyty.PicassoSampleActivity;
import com.example.newsapp.domain.PictureNewsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.newsapp.R.id.recyclerView;

/**
 * Created by chenyuelun on 2017/6/6.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private final Context context;
    private final List<PictureNewsBean.DataBean.NewsBean> newsBeanList;
    private final RecyclerView recyclerView;

    private BitmapCacheUtils bitmapCacheUtils;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NetCacheUtils.SUCESS :
                    Bitmap bitmap = (Bitmap) msg.obj;
                    int position = msg.arg1;
                    ImageView imageView = (ImageView) recyclerView.findViewWithTag(position);
                    if(bitmap != null &&  imageView!= null) {
                        imageView.setImageBitmap(bitmap);
                        notifyDataSetChanged();
                    }
                    break;
                case NetCacheUtils.FAIL :

                    break;
            }
        }
    };


    public MyRecyclerViewAdapter(Context context, List<PictureNewsBean.DataBean.NewsBean> newsBeanList,RecyclerView recyclerView) {
        this.context = context;
        this.newsBeanList = newsBeanList;
        bitmapCacheUtils = new BitmapCacheUtils(handler);
        this.recyclerView =recyclerView;
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
        //Glide.with(context).load(ConstantUtils.BASE_URL+newsBean.getLargeimage()).apply(TabDetaiPager.myOptions).into(holder.ivImage);
        String imageUrl = ConstantUtils.BASE_URL + newsBean.getListimage();
        Bitmap bitmap = bitmapCacheUtils.getBitmap(imageUrl,position);
        holder.ivImage.setTag(position);
        if(bitmap != null){//来自内存和本地，不包括网络
            holder.ivImage.setImageBitmap(bitmap);
        }
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String iamageUrl = ConstantUtils.BASE_URL + newsBeanList.get(getLayoutPosition()).getLargeimage();
                    Intent intent = new Intent(context, PicassoSampleActivity.class);
                    intent.putExtra("imageUrl",iamageUrl);
                    context.startActivity(intent);
                }
            });
        }
    }
}
