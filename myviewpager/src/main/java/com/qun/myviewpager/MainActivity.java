package com.qun.myviewpager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private int[] resIds = {R.mipmap.a1,R.mipmap.a2,R.mipmap.a3,R.mipmap.a4,R.mipmap.a5,R.mipmap.a6};
    private MyViewPager mMyViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMyViewPager = (MyViewPager) findViewById(R.id.myViewPager);
        mMyViewPager.setData(resIds);
    }
}
