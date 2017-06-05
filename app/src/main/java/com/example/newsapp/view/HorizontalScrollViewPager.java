package com.example.newsapp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by chenyuelun on 2017/6/5.
 */

public class HorizontalScrollViewPager extends ViewPager {
    private float statX;
    private float statY;

    public HorizontalScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                statX = ev.getX();
                statY = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float newX = ev.getX();
                float newY = ev.getY();
                float distanceX = Math.abs(newX - statY);
                float distanceY = Math.abs(newY - statY);
                if(distanceX > distanceY && distanceX > 8) {
                    if(newX > statY && getCurrentItem() == 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else if(statX > newX && getCurrentItem() == getAdapter().getCount() - 1){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }

                }else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }



                break;
            case MotionEvent.ACTION_UP :

                break;
        }



        return super.dispatchTouchEvent(ev);
    }
}
