package com.ys.yoosir.nestedscrolldemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

public class SwitchActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout upScreen;
    private FrameLayout downScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.i("HalfLinearLayout","SwitchActivity -- onTouchEvent event.getAction()="+event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.up_screen:
                Toast.makeText(SwitchActivity.this,"Up Screen is Clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.down_screen:
                Toast.makeText(SwitchActivity.this,"Down Screen is Clicked",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
