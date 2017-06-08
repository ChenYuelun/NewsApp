package com.example.newsapp.acitivyty;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.newsapp.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;


public class PicassoSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        final PhotoView photoView = (PhotoView) findViewById(R.id.iv_photo);
        Uri uri = getIntent().getData();
        Picasso.with(this)
                .load(uri)
                .into(photoView);
    }
}
