package com.hao.breatheanimator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvSize;
    private TextView tvTransparent;
    private Button btnSizeStart;
    private Button btnTransparentStart;
    private Button btnSizeEnd;
    private Button btnTransparentEnd;
    private boolean isSizeStart = false;
    private boolean isTransparentStart = false;

    private AnimatorSet sizeSet;
    private ObjectAnimator transparentAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvSize = (TextView) findViewById(R.id.tv_size);
        tvTransparent = (TextView) findViewById(R.id.tv_transparent);
        btnSizeStart = (Button) findViewById(R.id.size_start);
        btnTransparentStart = (Button) findViewById(R.id.transparent_start);
        btnSizeEnd = (Button) findViewById(R.id.size_end);
        btnTransparentEnd = (Button) findViewById(R.id.transparent_end);

        btnSizeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSizeStart){
                    sizeSet.resume();
                }
                startScaleBreathAnimation();
            }
        });
        btnTransparentStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTransparentStart){
                    transparentAnimator.reverse();
                }else {
                    startAlphaBreathAnimation();
                }
            }
        });

        btnSizeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sizeSet != null){
                    sizeSet.cancel();
                }
            }
        });

        btnTransparentEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transparentAnimator != null){
                    transparentAnimator.cancel();
                }
            }
        });
    }

    private void startScaleBreathAnimation() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(tvSize, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(tvSize, "scaleY", 1f, 0.5f);
        scaleX.setRepeatCount(ValueAnimator.INFINITE);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);
        scaleX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isSizeStart = true;
                btnSizeStart.setClickable(false);
                btnSizeEnd.setClickable(true);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                btnSizeStart.setClickable(true);
                btnSizeEnd.setClickable(false);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                btnSizeStart.setClickable(true);
                btnSizeEnd.setClickable(false);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        sizeSet=new AnimatorSet();
        sizeSet.playTogether(scaleX,scaleY);
        sizeSet.setDuration(4000);
        sizeSet.setInterpolator(new BreatheInterpolator());
        sizeSet.start();
    }

    private void startAlphaBreathAnimation() {
        transparentAnimator = ObjectAnimator.ofFloat(tvTransparent, "alpha", 1f, 0f);
        transparentAnimator.setDuration(4000);
        transparentAnimator.setInterpolator(new BreatheInterpolator());//使用自定义的插值器
        transparentAnimator.setRepeatCount(ValueAnimator.INFINITE);
        transparentAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isTransparentStart = true;
                btnTransparentStart.setClickable(false);
                btnTransparentEnd.setClickable(true);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                btnTransparentStart.setClickable(true);
                btnTransparentEnd.setClickable(false);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                btnTransparentStart.setClickable(true);
                btnTransparentEnd.setClickable(false);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        transparentAnimator.start();
    }
}
