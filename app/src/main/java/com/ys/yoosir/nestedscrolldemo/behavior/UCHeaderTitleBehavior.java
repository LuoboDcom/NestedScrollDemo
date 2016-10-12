package com.ys.yoosir.nestedscrolldemo.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**  UC 头部 标题 behavior
 * Created by ys on 2016/10/12 0012.
 */
public class UCHeaderTitleBehavior extends CoordinatorLayout.Behavior<TextView>{

    private int mFrameMaxHeight = 100;
    private int mStartY;

    public UCHeaderTitleBehavior() {
    }

    public UCHeaderTitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        //记录开始的Y坐标 也就是toolbar起始Y坐标
        if(mStartY == 0){
            mStartY = (int) dependency.getY();
        }

        //计算toolbar 从开始移动到最后百分比
        float percent = dependency.getY()/mStartY;
        Log.i("behavior","percent=="+percent);
        //改变child的坐标（从消失，到可见）
        child.setY(child.getHeight()*(1-percent) - child.getHeight());

        return true;
    }
}
