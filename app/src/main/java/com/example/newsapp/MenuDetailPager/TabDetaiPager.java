package com.example.newsapp.MenuDetailPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myutils_library.Utils.CacheUtils;
import com.example.myutils_library.Utils.ConstantUtils;
import com.example.myutils_library.Utils.DensityUtil;
import com.example.newsapp.R;
import com.example.newsapp.acitivyty.TabNewsDetailActivity;
import com.example.newsapp.base.MenuDetailBasePager;
import com.example.newsapp.domain.NewsControlBean;
import com.example.newsapp.domain.NewsDetailBean;
import com.example.newsapp.view.HorizontalScrollViewPager;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by chenyuelun on 2017/6/5.
 */

public class TabDetaiPager extends MenuDetailBasePager {
    public static final String READ_ID_LIST = "read_id_list";
    HorizontalScrollViewPager viewpagerTopNews;
    TextView topNewsTitle;
    LinearLayout llPointgroup;
    ListView listviewTabDetail;
    @BindView(R.id.pull_refresh_list)
    PullToRefreshListView pullRefreshList;
    private NewsControlBean.DataBean.ChildrenBean childrenBean;
    private List<NewsDetailBean.DataBean.TopnewsBean> topnews;
    public static RequestOptions myOptions = new RequestOptions().centerCrop().placeholder(R.drawable.news_pic_default).error(R.drawable.news_pic_default);
    private int prePosition;
    private List<NewsDetailBean.DataBean.NewsBean> newsBeanList;
    private String url;
    private String moreUrl;
    private TabNewsListAdapter tabNewsListAdapter;
    private boolean isMoreDataFromNet = false;

    public TabDetaiPager(Context context, NewsControlBean.DataBean.ChildrenBean childrenBean) {
        super(context);
        this.childrenBean = childrenBean;
    }


    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_tab_detail, null);
        ButterKnife.bind(this, view);
        listviewTabDetail = pullRefreshList.getRefreshableView();
        View topNewsView = View.inflate(context, R.layout.item_topnews, null);
        viewpagerTopNews = (HorizontalScrollViewPager) topNewsView.findViewById(R.id.viewpager_topNews);
        topNewsTitle = (TextView) topNewsView.findViewById(R.id.topNewsTitle);
        llPointgroup = (LinearLayout) topNewsView.findViewById(R.id.ll_pointgroup);
        listviewTabDetail.addHeaderView(topNewsView);

        viewpagerTopNews.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                String title = topnews.get(position).getTitle();
//                topNewsTitle.setText(title);
//
//                llPointgroup.getChildAt(prePosition).setEnabled(false);
//
//                llPointgroup.getChildAt(position).setEnabled(true);
//
//                prePosition = position;
            }

            @Override
            public void onPageSelected(int position) {
                String title = topnews.get(position).getTitle();
                topNewsTitle.setText(title);

                llPointgroup.getChildAt(prePosition).setEnabled(false);

                llPointgroup.getChildAt(position).setEnabled(true);

                prePosition = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING :
                        handler.removeCallbacksAndMessages(null);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        handler.removeCallbacksAndMessages(null);
                        handler.postDelayed(new MyRunnable(),4000);

                        break;
                }

            }
        });

        pullRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                isMoreDataFromNet = false;
                getDataFromNet(url);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if(moreUrl != null) {
                    isMoreDataFromNet = true;
                    getDataFromNet(moreUrl);
                    moreUrl = null;
                }

            }
        });
        
        
        listviewTabDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idArray = CacheUtils.getStringData(context, READ_ID_LIST);
                int realPosition = position -2;
                NewsDetailBean.DataBean.NewsBean newsBean = newsBeanList.get(realPosition);
                if(!idArray.contains(newsBean.getId()+"")) {
                    idArray = idArray + newsBean.getId();
                    CacheUtils.putStringData(context,READ_ID_LIST,idArray);
                    tabNewsListAdapter.notifyDataSetChanged();
                }

                Intent intent = new Intent(context, TabNewsDetailActivity.class);
                intent.putExtra("url",ConstantUtils.BASE_URL+newsBean.getUrl());
                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();
//        topNewsTitle.setText(childrenBean.getTitle());
        url = ConstantUtils.BASE_URL + childrenBean.getUrl();
        getDataFromNet(url);


    }
    private InternalHandler handler;
    class InternalHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int item = (viewpagerTopNews.getCurrentItem()+1)%topnews.size();
            viewpagerTopNews.setCurrentItem(item);
            handler.postDelayed(new MyRunnable(),4000);
        }
    }

    class MyRunnable implements Runnable{
        @Override
        public void run() {
            handler.sendEmptyMessage(0);
        }
    }

    private void getDataFromNet(String url) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "请求失败==" + e.getMessage());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "请求成功==" + response);
                        processData(response);
                        pullRefreshList.onRefreshComplete();

                    }


                });
    }

    private void processData(String json) {
        NewsDetailBean newsDetailBean = new Gson().fromJson(json, NewsDetailBean.class);
        String more = newsDetailBean.getData().getMore();
        if(!TextUtils.isEmpty(more)) {
            moreUrl = ConstantUtils.BASE_URL+more;
        }

        if(!isMoreDataFromNet) {
            topnews = newsDetailBean.getData().getTopnews();
            newsBeanList = newsDetailBean.getData().getNews();
            viewpagerTopNews.setAdapter(new TopNewsPagerAdapter());


            topNewsTitle.setText(topnews.get(prePosition).getTitle());
            llPointgroup.removeAllViews();
            for (int i = 0; i < topnews.size(); i++) {
                ImageView point = new ImageView(context);
                point.setBackgroundResource(R.drawable.point_selector);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 10), DensityUtil.dip2px(context, 10));
                point.setLayoutParams(params);

                if (i == 0) {
                    point.setEnabled(true);
                } else {
                    point.setEnabled(false);
                    params.leftMargin = DensityUtil.dip2px(context, 10);
                }

                llPointgroup.addView(point);
            }


            /**
             * Add Sound Event Listener
             */
            SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(context);
            soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
            soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
            soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
            pullRefreshList.setOnPullEventListener(soundListener);

            //listView数据适配
            tabNewsListAdapter = new TabNewsListAdapter();
            listviewTabDetail.setAdapter(tabNewsListAdapter);



        }else {
            newsBeanList.addAll(newsDetailBean.getData().getNews());
            tabNewsListAdapter.notifyDataSetChanged();
        }

        if(handler == null) {
            handler = new InternalHandler();
            handler.postDelayed(new MyRunnable(),4000);
        }


    }


    private class TopNewsPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            NewsDetailBean.DataBean.TopnewsBean topnewsBean = topnews.get(position);
            String imageUrl = ConstantUtils.BASE_URL + topnewsBean.getTopimage();
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.news_pic_default);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            Glide.with(context).load(imageUrl).apply(myOptions).into(imageView);

            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN :
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_UP :
                            handler.postDelayed(new MyRunnable(),4000);
                            break;
                    }
                    return true;
                }
            });
            return imageView;
        }
    }

    private class TabNewsListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return newsBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_tab_detail, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            NewsDetailBean.DataBean.NewsBean newsBean = newsBeanList.get(position);
            viewHolder.newsTitle.setText(newsBean.getTitle());
            viewHolder.newsTime.setText(newsBean.getPubdate());
            String imageUrl = ConstantUtils.BASE_URL + newsBean.getListimage();
            Glide.with(context).load(imageUrl).apply(myOptions).into(viewHolder.ivPicture);

            String idArray = CacheUtils.getStringData(context, READ_ID_LIST);
            if(idArray.contains(newsBean.getId()+"")) {
                viewHolder.newsTitle.setTextColor(Color.GRAY);
            }else {
                viewHolder.newsTitle.setTextColor(Color.BLACK);
            }
            return convertView;
        }


    }

    static class ViewHolder {
        @BindView(R.id.iv_picture)
        ImageView ivPicture;
        @BindView(R.id.newsTitle)
        TextView newsTitle;
        @BindView(R.id.newsTime)
        TextView newsTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
