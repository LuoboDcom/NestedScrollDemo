package com.ys.yoosir.nestedscrolldemo.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**  最简单的自定义 behavior
 * Created by ys on 2016/10/11 0011.
 */
public class EasyBehavior extends CoordinatorLayout.Behavior<TextView>{

    public EasyBehavior() {
    }

    public EasyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *   寻找被观察的View
     * @param parent
     * @param child        target view
     * @param dependency   被观察的view
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        //告知监听的dependency 是 button
        return dependency instanceof Button;
    }

    /**
     *  被观察View 变化的时候回调方法
     * @param parent
     * @param child      target view
     * @param dependency 被观察的view
     * @return   返回true
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        //当 dependency(Button)变化的时候，可以对child(TextView)进行操作
        child.setX(dependency.getX() + 200);
        child.setY(dependency.getY() + 200);
        child.setText(dependency.getY() + "," +dependency.getY());
        return true;
    }
}
