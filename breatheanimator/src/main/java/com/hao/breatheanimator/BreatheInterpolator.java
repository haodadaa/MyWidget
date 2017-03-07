package com.hao.breatheanimator;

import android.view.animation.Interpolator;

/**
 * 定义一个呼吸函数的插值器，在各种变化动画中使用该插值器，实现呼吸动画效果
 * Created by hao on 2017/3/7.
 */
public class BreatheInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float v) {

        float x = 6 * v;
        float k = 1.0f / 3;
        int t = 6;
        //控制函数周期，这里取此函数的第一个周期
        int n = 1;
        float PI = 3.1416f;
        float output = 0;

        if (x >= ((n - 1) * t) && x < ((n - (1 - k)) * t)) {
            output = (float) (0.5 * Math.sin((PI / (k * t)) * ((x - k * t / 2) - (n - 1) * t)) + 0.5);

        } else if (x >= (n - (1 - k)) * t && x < n * t) {
            output = (float) Math.pow((0.5 * Math.sin((PI / ((1 - k) * t)) * ((x - (3 - k) * t / 2) - (n - 1) * t)) + 0.5), 2);
        }
        return output;
    }
}
