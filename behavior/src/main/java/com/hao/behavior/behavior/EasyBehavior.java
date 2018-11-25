package com.hao.behavior.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EasyBehavior extends CoordinatorLayout.Behavior<TextView> {

    public EasyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    // 寻找被观察的View，如果是返回true，则表示要对该View的变化作出响应
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        //告知监听的dependency是Button
        return dependency instanceof Button;
    }

    @Override
    //当被观察的View变化的时候，可以对观察者作出响应，进行操作
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        child.setX(dependency.getX() + 200);
        child.setY(dependency.getY() + 200);
        child.setText(String.valueOf(dependency.getX()).concat(",").concat(String.valueOf(dependency.getY())));

        return true;
    }
}
