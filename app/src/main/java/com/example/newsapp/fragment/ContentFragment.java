package com.example.newsapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.newsapp.R;
import com.example.newsapp.acitivyty.MainActivity;
import com.example.newsapp.base.BaseFragment;
import com.example.newsapp.base.BasePager;
import com.example.newsapp.pager.HomePager;
import com.example.newsapp.pager.NewsPager;
import com.example.newsapp.pager.SettingPager;
import com.example.newsapp.view.NoSlideViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenyuelun on 2017/6/2.
 */

public class ContentFragment extends BaseFragment {
    @BindView(R.id.content_vp)
    NoSlideViewPager contentVp;
    @BindView(R.id.rg_content)
    RadioGroup rgContent;
    Unbinder unbinder;

    private ArrayList<BasePager> pagers;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_content, null);
        unbinder = ButterKnife.bind(this, view);

        rgContent.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        rgContent.check(R.id.rb_home);
        contentVp.addOnPageChangeListener(new MyOnPageChangeListener());
        return view;


    }

    @Override
    public void initData() {
        super.initData();
        pagers = new ArrayList<>();
        pagers.add(new HomePager(context));
        pagers.add(new NewsPager(context));
        pagers.add(new SettingPager(context));

        contentVp.setAdapter(new MyPagerAdapter());
        pagers.get(0).initData();
        isEnableSlideMenu((Context) context,SlidingMenu.TOUCHMODE_NONE);



    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public NewsPager getNewsPager() {
        return (NewsPager) pagers.get(1);
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = pagers.get(position);
            View rootView = basePager.rootView;
            container.addView(rootView);
//            basePager.initData();
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_home :
                    contentVp.setCurrentItem(0,false);
                    break;
                case R.id.rb_news :
                    contentVp.setCurrentItem(1,false);
                    break;
                case R.id.rb_setting :
                    contentVp.setCurrentItem(2,false);
                    break;
            }
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            pagers.get(position).initData();
            if(position == 1) {
                ContentFragment.this.isEnableSlideMenu(context, SlidingMenu.TOUCHMODE_MARGIN);
            }else {
                isEnableSlideMenu((MainActivity) context, SlidingMenu.TOUCHMODE_NONE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }






    }

    private static void isEnableSlideMenu(Context context, int touchmodeMargin) {
        MainActivity mainActivity = (MainActivity) context;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
        slidingMenu.setTouchModeAbove(touchmodeMargin);
    }
}
