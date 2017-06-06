package com.example.newsapp.acitivyty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.myutils_library.Utils.CacheUtils;
import com.example.myutils_library.Utils.DensityUtil;
import com.example.newsapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.guide_vp)
    ViewPager guideVp;
    @BindView(R.id.btn_start_main)
    Button btnStartMain;
    @BindView(R.id.red_point)
    ImageView redPoint;
    @BindView(R.id.ll_point_group)
    LinearLayout llPointGroup;
    private ArrayList<ImageView> imageViews;
    private int[] ids = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    private int leftMarin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initData();
        guideVp.setAdapter(new MyPagerAdapter());

        redPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                redPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                leftMarin = llPointGroup.getChildAt(1).getLeft() - llPointGroup.getChildAt(0).getLeft();

            }
        });

        guideVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float left = leftMarin * (positionOffset + position);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) redPoint.getLayoutParams();
                params.leftMargin = (int) left;
                redPoint.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                if(position != imageViews.size()-1) {
                    btnStartMain.setVisibility(View.GONE);
                }else {
                    btnStartMain.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);
            imageViews.add(imageView);

            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.point_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(GuideActivity.this,10),DensityUtil.dip2px(GuideActivity.this,10));
            if(i!= 0) {
                params.leftMargin = DensityUtil.dip2px(GuideActivity.this,10);
            }
            point.setLayoutParams(params);

            llPointGroup.addView(point);

        }

    }

    @OnClick(R.id.btn_start_main)
    public void onViewClicked() {
        CacheUtils.setBooleanForGuide(GuideActivity.this, "notFirstOpen", true);
        startActivity(new Intent(GuideActivity.this, MainActivity.class));
        finish();
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
