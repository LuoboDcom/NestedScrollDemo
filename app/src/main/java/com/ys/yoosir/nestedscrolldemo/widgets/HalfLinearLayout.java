package com.ys.yoosir.nestedscrolldemo.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
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

    public HalfLinearLayout(Context context) {
        this(context,null);
    }

    public HalfLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HalfLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //记录按下的 Y 坐标点
    private float oldY;
    //滑动时的Y坐标点
    private float newY;

    /**
     * basePercent 表示滑动前 upPercent的值
     *
     * 如果，滑动前，布局是 上布局隐藏，下布局全屏，则 basePercent = 0；
     * 如果，滑动前，布局是 上布局全屏，下布局隐藏，则 basePercent = 1；
     * 如果，滑动前，布局是 上布局 和 下布局都是显示，则 basePercent = 0.5；
     */
    private float basePercent = 0.5f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG,"onTouchEvent -- event.getAction()="+event.getAction());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //记录按下的 Y 坐标点
                oldY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(oldY == 0) oldY = event.getY();
                //滑动时的Y坐标点
                newY = event.getY();
                Log.i(TAG,"newY - oldY = "+ (newY - oldY));
                //主要防止误触
                if(Math.abs(newY - oldY) > 8) {
                    //计算滑动的高度 占整个布局高度的占比
                    float percent = (newY - oldY) / getHeight();

                    //根据滑动前，布局的状态，来修正 滑动距离的高度占比值
                    percent += basePercent;
                    Log.i(TAG, "percent = " + percent);
                    //防止越界
                    if (percent < 0) {
                        percent = 0;
                    }
                    if (percent > 1) {
                        percent = 1.0f;
                    }
                    //赋值 上布局高度占比值
                    upPercent = percent;
                    Log.i(TAG, "onTouchEvent--upPercent=" + upPercent);
                    //动态修改子布局的高度
                    updateChildViewSize();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //滑动操作结束时
                int type;
                if(upPercent <= 0.35){
                    //下布局展示状态，向上底部滑动动画
                    type = 1;
                    basePercent = 0;
                }else if(upPercent <= 0.5 && upPercent > 0.35){
                    //上下布局都展示，从上向中回弹动画
                    type = 2;
                    basePercent = 0.5f;
                }else if(upPercent <= 0.65 && upPercent > 0.5 ){
                    //上下布局都展示，从下向中回弹动画
                    type = 3;
                    basePercent = 0.5f;
                }else {
                    //上布局都展示，向下底部滑动动画
                    type = 4;
                    basePercent = 1.0f;
                }
                //执行动画
                animation(type);
                oldY = 0;
                break;
        }
        return true;
    }

    /**
     *   upPercent：上子布局的高度占比
     *   1 - upPercent ：下子布局高度的占比
     */
    private void updateChildViewSize() {
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            ViewGroup.LayoutParams lp = child.getLayoutParams();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            if (i == 0) {
                lp.height = (int) (getHeight() * upPercent);
            } else {
                lp.height = (int) (getHeight() * (1 - upPercent));
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
                    //下布局展示状态，向上底部滑动动画
                    //上子布局高度占比 = 未完成的滑动高度占比 - 动画消耗的高度占比
                    //未完成的高度占比 = 之前的上子布局高占比
                    upPercent -= upPercent * valueAnimator.getAnimatedFraction();
                }else if(type == 2){
                    //上下布局都展示，从上向中回弹动画
                    //上子布局高度占比 = 未完成的滑动高度占比 + 动画消耗的高度占比
                    //未完成的高度占比 = 0.5 - 之前的上子布局高占比
                    upPercent += (0.5 - upPercent)*valueAnimator.getAnimatedFraction();
                }else if(type == 3){
                    //上下布局都展示，从下向中回弹动画
                    //上子布局高度占比 = 未完成的滑动高度占比 - 动画消耗的高度占比
                    //未完成的高度占比 = 之前的上子布局高占比 - 0.5
                    upPercent -= (upPercent - 0.5)*valueAnimator.getAnimatedFraction();
                }else{
                    //上布局都展示，向下底部滑动动画
                    //上子布局高度占比 = 未完成的滑动高度占比 + 动画消耗的高度占比
                    //未完成的高度占比 =  1- 之前的上子布局高占比
                    upPercent += (1 - upPercent) * valueAnimator.getAnimatedFraction();
                }
                //动态改变子View的高度
                updateChildViewSize();
            }
        });
        anim.start();
    }
}
