package com.ys.yoosir.nestedscrolldemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class CustomFirstBehaviorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_first_behavior);

        findViewById(R.id.btn).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        view.setX(motionEvent.getRawX() - view.getWidth()/2);
                        view.setY(motionEvent.getRawY() - view.getHeight()/2);
                        break;
                }
                return false;
            }
        });
    }
}
