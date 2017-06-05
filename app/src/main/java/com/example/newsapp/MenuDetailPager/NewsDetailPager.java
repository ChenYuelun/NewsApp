package com.example.newsapp.MenuDetailPager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.newsapp.R;
import com.example.newsapp.acitivyty.MainActivity;
import com.example.newsapp.base.MenuDetailBasePager;
import com.example.newsapp.domain.NewsControlBean;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
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
    private ImageButton ib_next;
    private TabPageIndicator indicator;
    public NewsDetailPager(Context context, List<NewsControlBean.DataBean.ChildrenBean> children) {
        super(context);
        this.datas = children;
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_news_detail,null);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        ib_next = (ImageButton) view.findViewById(R.id.ib_next);
        ib_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.setCurrentItem(viewpager.getCurrentItem()+1);
            }
        });

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
                    MainActivity mainActivity = (MainActivity) context;
                    mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
                }else {
                    MainActivity mainActivity = (MainActivity) context;
                    mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
