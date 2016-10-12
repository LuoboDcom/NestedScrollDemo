package com.ys.yoosir.nestedscrolldemo.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.ys.yoosir.nestedscrolldemo.R;

/** 头像移动
 * Created by ys on 2016/10/12 0012.
 */
public class AvatarBehavior extends CoordinatorLayout.Behavior<ImageView>{

    private Context mContext;

    //头像的最终大小
    private float mCustomFinalHeight;

    //最终头像的Y
    private float mFinalAvatarY;

    private float mStartAvatarY;

    private float mStartAvatarX;

    private int mAvatarMaxHeight;

    private BounceInterpolator interpolator = new BounceInterpolator();

    public AvatarBehavior() {
    }

    public AvatarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if(attrs != null){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AvatarBehavior);
            mCustomFinalHeight = a.getDimension(R.styleable.AvatarBehavior_finalHeight,30);
            a.recycle();
        }
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ImageView child, View dependency) {
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ImageView child, View dependency) {
        //初始化属性
        mFinalAvatarY = dependency.getHeight()/2;
        if(mStartAvatarY == 0){
            mStartAvatarY = dependency.getY();
        }
        if(mStartAvatarX == 0){
            mStartAvatarX = child.getX();
        }

        if(mAvatarMaxHeight == 0){
            mAvatarMaxHeight = child.getHeight();
        }

        //让ImageView跟随toolbar垂直移动
        child.setY(dependency.getY() + dependency.getHeight()/2 - mCustomFinalHeight/2);

        float percent = dependency.getY()/mStartAvatarY;

        float x = mStartAvatarX * (1+interpolator.getInterpolation(percent));

        //当toolbar 到达位置，就不改变了
        if(dependency.getY() > dependency.getHeight()/2){
            child.setX(x);
        }else{
            child.setX(mStartAvatarX + (mAvatarMaxHeight - mCustomFinalHeight)/2);
        }

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        layoutParams.height = (int) ((mAvatarMaxHeight - mCustomFinalHeight)*percent+mCustomFinalHeight);
        layoutParams.width = (int) ((mAvatarMaxHeight - mCustomFinalHeight)*percent+mCustomFinalHeight);
        child.setLayoutParams(layoutParams);
        return true;
    }
}
