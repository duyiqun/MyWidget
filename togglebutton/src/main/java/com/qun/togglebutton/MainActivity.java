package com.qun.togglebutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ToggleButton mToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToggleButton = (ToggleButton) findViewById(R.id.tb);
        mToggleButton.setOnOpenStateChangeListener(new ToggleButton.onOpenStateChangeListener() {
            @Override
            public void onOpenChange(boolean isOpen) {
                Toast.makeText(MainActivity.this, isOpen + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
