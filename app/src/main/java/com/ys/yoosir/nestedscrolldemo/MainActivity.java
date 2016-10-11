package com.ys.yoosir.nestedscrolldemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_scrolling:
                startActivity(new Intent(this,ScrollingActivity.class));
                break;
            case R.id.btn_switch:
                startActivity(new Intent(this,SwitchActivity.class));
                break;
            case R.id.btn_first_custom:
                startActivity(new Intent(this,CustomFirstBehaviorActivity.class));
                break;
        }
    }
}
