package com.example.newsapp.MenuDetailPager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapp.R;
import com.example.newsapp.base.MenuDetailBasePager;
import com.example.newsapp.domain.NewsControlBean;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyuelun on 2017/6/4.
 */

public class NewsDetailPager extends MenuDetailBasePager {
    private final List<NewsControlBean.DataBean.ChildrenBean> datas;
    private ViewPager viewpager;
    private ArrayList<TabDetaiPager> tabDetaiPagers;
    private TabPageIndicator indicator;
    public NewsDetailPager(Context context, List<NewsControlBean.DataBean.ChildrenBean> children) {
        super(context);
        this.datas = children;
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_tab_detail,null);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        tabDetaiPagers = new ArrayList<>();
        for(int i = 0; i < datas.size(); i++)
            tabDetaiPagers.add(new TabDetaiPager(context,datas.get(i)));

        viewpager.setAdapter(new NewsDetailPagerAdapter());
        indicator.setViewPager(viewpager);
    }

    private class NewsDetailPagerAdapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            return datas.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return tabDetaiPagers == null? 0 :tabDetaiPagers.size();
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
            TabDetaiPager tabDetaiPager = tabDetaiPagers.get(position);
            View rootView = tabDetaiPager.rootView;
            container.addView(rootView);
            tabDetaiPager.initData();
            return rootView;
        }
    }
}
