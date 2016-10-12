package com.ys.yoosir.nestedscrolldemo.widgets;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**  分屏布局
 * Created by ys on 2016/10/12 0012.
 */
public class HalfLinearLayout extends LinearLayout{

    private final static String TAG = HalfLinearLayout.class.getSimpleName();

    private float upPercent = 0.5f;
    private int height;
    private int width;

    public HalfLinearLayout(Context context) {
        this(context,null);
    }

    public HalfLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HalfLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics dd = getResources().getDisplayMetrics();
        height = dd.heightPixels;
        width = dd.widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        Log.i(TAG,"onMeasure");
        Log.i(TAG,"(width,height)="+width+","+height);
        updateChildViewSize();
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG,"onDraw");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i(TAG,"onLayout");
    }

    private float oldY;
    private float newY;
    private float basePerent = 0.5f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG,"onTouchEvent -- event.getAction()="+event.getAction());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                oldY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(oldY == 0) oldY = event.getY();
                newY = event.getY();
                Log.i(TAG,"newY - oldY = "+ (newY - oldY));
                if(Math.abs(newY - oldY) > 8) {
                    float percent = (newY - oldY) / height;

                    if(basePerent == 0){

                    }else if(basePerent == 1){
                        percent  += 1;
                    }else{
                        percent += 0.5;
                    }
                    Log.i(TAG, "percent = " + percent);
                    if (percent < 0) {
                        percent = 0;
                    }
                    if (percent > 1) {
                        percent = 1.0f;
                    }
                    upPercent = percent;
                    Log.i(TAG, "onTouchEvent--upPercent=" + upPercent);
                    updateChildViewSize();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int type;
                if(upPercent <= 0.35){
                    type = 1;
                    basePerent = 0;
                }else if(upPercent <= 0.5 && upPercent > 0.35){
                    type = 2;
                    basePerent = 0.5f;
                }else if(upPercent <= 0.65 && upPercent > 0.5 ){
                    type = 3;
                    basePerent = 0.5f;
                }else {
                    type = 4;
                    basePerent = 1.0f;
                }
                animation(type);
                oldY = 0;
                break;
        }
//        if(upPercent == 0 || upPercent == 1){
//            return super.onTouchEvent(event);
//        }else{
            return true;
//        }
    }

    private void updateChildViewSize() {
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            ViewGroup.LayoutParams lp = child.getLayoutParams();
            lp.width = width;
            if (i == 0) {
                lp.height = (int) (height * upPercent);
            } else {
                lp.height = (int) (height * (1 - upPercent));
            }
            child.setLayoutParams(lp);
        }
    }

    private void animation(final int type){
        ValueAnimator anim = ValueAnimator.ofFloat(0,1);
        anim.setDuration(500);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if(type == 1){
                    upPercent -= upPercent * valueAnimator.getAnimatedFraction();
                }else if(type == 2){
                    upPercent += (0.5 - upPercent)*valueAnimator.getAnimatedFraction();
                }else if(type == 3){
                    upPercent -= (upPercent - 0.5)*valueAnimator.getAnimatedFraction();
                }else{
                    upPercent += (1 - upPercent) * valueAnimator.getAnimatedFraction();
                }
                updateChildViewSize();
            }
        });
        anim.start();
    }
}
