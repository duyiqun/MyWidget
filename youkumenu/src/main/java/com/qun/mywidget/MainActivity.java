package com.qun.mywidget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private YouKuMenu mYouKuMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mYouKuMenu = (YouKuMenu) findViewById(R.id.youkuMenu);
        mYouKuMenu.setOnMenuStateChangeListener(new YouKuMenu.onMenuStateChangeListener() {
            @Override
            public void onLevel3Changed(boolean isOpen) {
                Toast.makeText(MainActivity.this, "3级菜单" + (isOpen ? "打开了" : "关闭了"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLevel2Changed(boolean isOpen) {
                Toast.makeText(MainActivity.this, "2级菜单" + (isOpen ? "打开了" : "关闭了"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void closeMenu(View view) {
        mYouKuMenu.closeMenu();
    }

    public void openMenu(View view) {
        mYouKuMenu.openMenu();
    }
}
