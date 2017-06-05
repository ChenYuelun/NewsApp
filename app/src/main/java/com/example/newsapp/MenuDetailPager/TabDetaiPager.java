package com.example.newsapp.MenuDetailPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.newsapp.R;
import com.example.newsapp.Utils.ConstantUtils;
import com.example.newsapp.base.MenuDetailBasePager;
import com.example.newsapp.domain.NewsControlBean;
import com.example.newsapp.domain.NewsDetailBean;
import com.google.gson.Gson;
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
    @BindView(R.id.topNewsTitle)
    TextView topNewsTitle;
    @BindView(R.id.ll_pointgroup)
    LinearLayout llPointgroup;
    @BindView(R.id.listview_tab_detail)
    ListView listviewTabDetail;
    @BindView(R.id.viewpager_topNews)
    ViewPager viewpagerTopNews;
    private NewsControlBean.DataBean.ChildrenBean childrenBean;
    private List<NewsDetailBean.DataBean.TopnewsBean> topnews;

    public TabDetaiPager(Context context, NewsControlBean.DataBean.ChildrenBean childrenBean) {
        super(context);
        this.childrenBean = childrenBean;
    }


    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_tab_detail, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
//        topNewsTitle.setText(childrenBean.getTitle());
        String url = ConstantUtils.BASE_URL + childrenBean.getUrl();
        getDataFromNet(url);

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
                    }


                });
    }

    private void processData(String json) {
        NewsDetailBean newsDetailBean = new Gson().fromJson(json, NewsDetailBean.class);
        topnews = newsDetailBean.getData().getTopnews();
    }


}
