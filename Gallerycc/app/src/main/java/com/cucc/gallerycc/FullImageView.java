package com.cucc.gallerycc;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class FullImageView extends AppCompatActivity {

    String[] url = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);
        Intent intent = getIntent();
        url[0] = intent.getStringExtra("extra");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(FullImageView.this, url);
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onBackPressed(){
        finish();
    }



}
