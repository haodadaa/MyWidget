package com.hao.behavior.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.hao.behavior.R;
import com.hao.behavior.base.HeaderScrollingViewBehavior;

import java.util.List;

/**
 * Created by hao on 2017/3/10.
 */

public class ContentBehavior extends HeaderScrollingViewBehavior{

    private int mTitleViewHeight = 0;
    private int mTabViewHeight = 0;

    public ContentBehavior() {
        super();
    }

    public ContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void layoutChild(CoordinatorLayout parent, View child, int layoutDirection) {

        mTitleViewHeight = parent.findViewById(R.id.news_view_title_layout).getMeasuredHeight();
        mTabViewHeight = parent.findViewById(R.id.news_view_tab_layout).getMeasuredHeight();
        super.layoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isDependency(dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        int headerOffsetRange = -mTitleViewHeight;
        //因为Content是往上滑，所以setTranslationY值为负值，而dependency.getTranslationY()和headerOffsetRange均为负值，所以前面加-号
        child.setTranslationY(-dependency.getTranslationY() / (headerOffsetRange * 1.0f) * getScrollRange(dependency));
        return false;
    }

    @Override
    protected int getScrollRange(View v) {
        if(isDependency(v)) {
            //Header Icon导航区的高度，减去最后Tab和Title的高度就是Content最终要滑动的高度
            return v.getMeasuredHeight() - mTitleViewHeight - mTabViewHeight;
        }
        return super.getScrollRange(v);
    }

    @Override
    public View findFirstDependency(List<View> views) {
        for(int i = 0; i < views.size(); i++) {
            if(isDependency(views.get(i))) {
                return views.get(i);
            }
        }
        return null;
    }

    private boolean isDependency(View dependency) {

        return dependency != null && dependency.getId() == R.id.news_view_header_layout;
    }
}
