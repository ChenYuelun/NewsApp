package com.example.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.example.newsapp.Utils.CacheUtils;
import com.example.newsapp.acitivyty.GuideActivity;
import com.example.newsapp.acitivyty.MainActivity;

public class WelcomeActivity extends AppCompatActivity {
    private RelativeLayout welcome_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcome_activity = (RelativeLayout)findViewById(R.id.welcome_activity);

        Animation set = AnimationUtils.loadAnimation(this, R.anim.welcome);
        welcome_activity.startAnimation(set);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean notFirstOpen = CacheUtils.getBooleanForGuide(WelcomeActivity.this, "notFirstOpen");
                if(notFirstOpen) {
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(WelcomeActivity.this,GuideActivity.class));
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
