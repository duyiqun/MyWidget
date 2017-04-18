package com.qun.refreshlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RefreshListView mRefreshListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            dataList.add("数据" + i);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        mRefreshListView = (RefreshListView) findViewById(R.id.refreshListView);
        mRefreshListView.setAdapter(arrayAdapter);
    }
}
