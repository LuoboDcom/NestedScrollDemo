package com.ys.yoosir.nestedscrolldemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class SwitchActivity extends AppCompatActivity {

    private FrameLayout upScreen;
    private FrameLayout downScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        int height = displayMetrics.heightPixels;
        int displayHeight = height/2;
        setContentView(R.layout.activity_switch);
        upScreen = (FrameLayout) findViewById(R.id.up_screen);
        downScreen = (FrameLayout) findViewById(R.id.down_screen);
        ViewGroup.LayoutParams upLayoutParam =  upScreen.getLayoutParams();
        upLayoutParam.height = displayHeight;
        ViewGroup.LayoutParams downLayoutParam = downScreen.getLayoutParams();
        downLayoutParam.height = displayHeight;
//        upScreen.setLayoutParams(upLayoutParam);
//        downScreen.setLayoutParams(downLayoutParam);
    }
}
