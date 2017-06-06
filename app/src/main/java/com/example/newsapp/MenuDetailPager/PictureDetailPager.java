package com.example.newsapp.MenuDetailPager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.myutils_library.Utils.CacheUtils;
import com.example.myutils_library.Utils.ConstantUtils;
import com.example.newsapp.R;
import com.example.newsapp.adapter.MyRecyclerViewAdapter;
import com.example.newsapp.base.MenuDetailBasePager;
import com.example.newsapp.domain.NewsControlBean;
import com.example.newsapp.domain.PictureNewsBean;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by chenyuelun on 2017/6/4.
 */

public class PictureDetailPager extends MenuDetailBasePager {
    private final NewsControlBean.DataBean dataBean;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.pb_picture)
    ProgressBar pbPicture;
    private List<PictureNewsBean.DataBean.NewsBean> newsBeanList;
    private MyRecyclerViewAdapter adapter;

    public PictureDetailPager(Context context, NewsControlBean.DataBean dataBean) {
        super(context);
        this.dataBean = dataBean;
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_picture_detail, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        String url = ConstantUtils.BASE_URL + dataBean.getUrl();
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
                        Log.e("TAG", "组图请求失败" + e.getMessage());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "组图请求成功");
                        processData(response);
                    }


                });
    }

    private void processData(String json) {
        PictureNewsBean pictureNewsBean = new Gson().fromJson(json, PictureNewsBean.class);

        if (pictureNewsBean != null) {
            pbPicture.setVisibility(View.GONE);
            newsBeanList = pictureNewsBean.getData().getNews();
            adapter = new MyRecyclerViewAdapter(context, newsBeanList);
            recyclerView.setAdapter(adapter);
            StaggeredGridLayoutManager layoutManger = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManger);
        }
    }
}
