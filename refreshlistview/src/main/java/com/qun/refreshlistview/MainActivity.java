package com.qun.refreshlistview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RefreshListView mRefreshListView;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final List<String> dataList = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        mRefreshListView = (RefreshListView) findViewById(R.id.refreshListView);
        mRefreshListView.setAdapter(arrayAdapter);
        mRefreshListView.setRefresh(true);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    dataList.add("数据" + i);
                }
                mRefreshListView.setRefresh(false);
                arrayAdapter.notifyDataSetChanged();
            }
        }, 3000);

        mRefreshListView.setOnRefreshingListener(new RefreshListView.onRefreshingListener() {
            @Override
            public void onRefreshing() {
                Toast.makeText(MainActivity.this, "要更新数据了", Toast.LENGTH_SHORT).show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dataList.add(0, "更新的新数据" + new Date());
                        arrayAdapter.notifyDataSetChanged();
                        mRefreshListView.setRefresh(false);
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dataList.add("加载更多的数据" + new Date());
                        arrayAdapter.notifyDataSetChanged();
                        mRefreshListView.stopLoadMore();
                    }
                }, 1000);
            }
        });
    }
}
