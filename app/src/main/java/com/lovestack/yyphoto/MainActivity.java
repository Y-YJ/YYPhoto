package com.lovestack.yyphoto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lovestack.yyphotoview.YYDragViewPagerFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private YYDragViewPagerFragment yyDragViewPagerFragment;
    private List<String> images = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        images.add("http://p1.ifengimg.com/haina/2016_51/fa0accacf8c6e09_w550_h367.jpg");
        images.add("http://p1.ifengimg.com/haina/2016_51/9b7c7d7158e76b4_w550_h367.jpg");
        images.add("http://p1.ifengimg.com/haina/2016_51/bcf3de90e67dea8_w550_h367.jpg");

        findViewById(R.id.iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yyDragViewPagerFragment=new YYDragViewPagerFragment();
                yyDragViewPagerFragment.setImages(images,0);
                yyDragViewPagerFragment.showDialog(MainActivity.this);
            }
        });

    }
}
