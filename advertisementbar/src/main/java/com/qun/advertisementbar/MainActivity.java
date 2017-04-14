package com.qun.advertisementbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private AdvertisementBar mAdvertisementBar;
    private int[] imgIds = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};
    private String[] titles = {"巩俐不低俗，我也不低俗", "我爱玩大咖", "东风吹过春满地", "乐视网TV大派送", "热血屌丝的逆袭"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdvertisementBar = (AdvertisementBar) findViewById(R.id.advertisementBar);
        mAdvertisementBar.setData(imgIds, titles);
    }
}
