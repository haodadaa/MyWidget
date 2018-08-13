package com.hao.mywidget.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.hao.mywidget.R;

public class RoundFrameLayout extends FrameLayout {

    // Layout的宽高
    private int mViewWidth = 1;
    private int mViewHeight = 1;
    // 是否为圆形
    private boolean mRoundAsCircle = false;
    // 四个角是否为圆角
    private boolean mRoundTopLeft = true;
    private boolean mRoundTopRight = true;
    private boolean mRoundBottomLeft = true;
    private boolean mRoundBottomRight = true;
    // 用于设置哪些角为圆角
    private float[] mRoundCorners = new float[8];
    // 圆角半径
    private float mRoundCornerRadius = 0;
    // 阴影大小
    private float mShadowRadius = 0;
    // 阴影在X方向的偏移
    private float mShadowDx = 0;
    // 阴影在Y方向的偏移
    private float mShadowDy = 0;
    // 阴影的颜色
    private int mShadowColor = 0x00000000;
    // Paint默认颜色
    private int mPaintColor = 0xffffffff;

    // 绘制圆角的画笔（实际是裁剪出圆角）
    private Paint mRoundPaint;
    // 绘制子View的画笔
    private Paint mImagePaint;
    // 绘制阴影的画笔
    private Paint mShadowPaint;
    // 子View的区域
    private RectF mImageRect;
    // 用于计算圆角区域
    private Path mPath;


    public RoundFrameLayout(Context context) {
        this(context, null);
    }

    public RoundFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundFrameLayout);
        mRoundAsCircle = array.getBoolean(R.styleable.RoundFrameLayout_roundAsCircle, false);
        mRoundTopLeft = array.getBoolean(R.styleable.RoundFrameLayout_roundTopLeft, true);
        mRoundTopRight = array.getBoolean(R.styleable.RoundFrameLayout_roundTopRight, true);
        mRoundBottomLeft = array.getBoolean(R.styleable.RoundFrameLayout_roundBottomLeft, true);
        mRoundBottomRight = array.getBoolean(R.styleable.RoundFrameLayout_roundBottomRight, true);
        mRoundCornerRadius = array.getDimensionPixelSize(R.styleable.RoundFrameLayout_roundCornerRadius, 0);
        mShadowRadius = array.getDimensionPixelSize(R.styleable.RoundFrameLayout_shadowRadius, 0);
        mShadowDx = array.getDimensionPixelSize(R.styleable.RoundFrameLayout_shadowDx, 0);
        mShadowDy = array.getDimensionPixelSize(R.styleable.RoundFrameLayout_shadowDy, 0);
        mShadowColor = array.getColor(R.styleable.RoundFrameLayout_shadowColor, 0x00000000);
        mPaintColor = array.getColor(R.styleable.RoundFrameLayout_paintColor, 0xffffffff);
        array.recycle();

        init();
    }

    private void init() {
        // 如果ViewGroup需要重写onDraw方法，需要清除此标志位
        setWillNotDraw(false);
        // 使用setShadowLayer生成阴影效果，需要关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mRoundPaint = new Paint();
        mRoundPaint.setColor(mPaintColor);
        mRoundPaint.setAntiAlias(true);
        mRoundPaint.setStyle(Paint.Style.FILL);
        mRoundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        mImagePaint = new Paint();
        mImagePaint.setXfermode(null);

        mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadowPaint.setColor(mPaintColor);
        updateShadowLayer();

        mImageRect = new RectF();
        mPath = new Path();
    }

    public boolean isRoundAsCircle() {
        return mRoundAsCircle;
    }

    public void setRoundAsCircle(boolean roundAsCircle) {
        mRoundAsCircle = roundAsCircle;
        invalidate();
    }

    public boolean isRoundTopLeft() {
        return mRoundTopLeft;
    }

    public void setRoundTopLeft(boolean roundTopLeft) {
        this.mRoundTopLeft = roundTopLeft;
        invalidate();
    }

    public boolean isRoundTopRight() {
        return mRoundTopRight;
    }

    public void setRoundTopRight(boolean roundTopRight) {
        this.mRoundTopRight = roundTopRight;
        invalidate();
    }

    public boolean isRoundBottomLeft() {
        return mRoundBottomLeft;
    }

    public void setRoundBottomLeft(boolean roundBottomLeft) {
        this.mRoundBottomLeft = roundBottomLeft;
        invalidate();
    }

    public boolean isRoundBottomRight() {
        return mRoundBottomRight;
    }

    public void setRoundBottomRight(boolean roundBottomRight) {
        this.mRoundBottomRight = roundBottomRight;
        invalidate();
    }

    public float getRoundCornerRadius() {
        return mRoundCornerRadius;
    }

    public void setRoundCornerRadius(float radius) {
        mRoundCornerRadius = radius;
        invalidate();
    }

    public float getShadowRadius() {
        return mShadowRadius;
    }

    public void setShadowRadius(float radius) {
        mShadowRadius = radius;
        updateShadowLayer();
        updateImageRect();
        invalidate();
    }

    public int getShadowColor() {
        return mShadowColor;
    }

    public void setShadowColor(int color) {
        mShadowColor = color;
        updateShadowLayer();
        invalidate();
    }

    public float getShadowDx() {
        return mShadowDx;
    }

    public void setShadowDx(float dx) {
        this.mShadowDx = dx;
        updateShadowLayer();
        updateImageRect();
        setChildMargin();
        invalidate();
    }

    public float getShadowDy() {
        return mShadowDy;
    }

    public void setShadowDy(float dy) {
        this.mShadowDy = dy;
        updateShadowLayer();
        updateImageRect();
        setChildMargin();
        invalidate();
    }

    // 调整阴影大小、偏移或颜色后，更新ShadowLayer
    private void updateShadowLayer() {
        mShadowPaint.setShadowLayer(mShadowRadius, mShadowDx, mShadowDy, mShadowColor);
    }

    // 调整阴影大小或偏移后，更新绘制子View的区域
    private void updateImageRect() {
        mImageRect.set(mShadowRadius - mShadowDx,
                mShadowRadius - mShadowDy,
                mViewWidth - mShadowRadius - mShadowDx,
                mViewHeight - mShadowRadius - mShadowDy);
    }

    // 根据标志位设置哪些角为圆角
    private void setRoundCorners() {
        if (mRoundTopLeft) {
            mRoundCorners[0] = mRoundCornerRadius;
            mRoundCorners[1] = mRoundCornerRadius;
        } else {
            mRoundCorners[0] = 0;
            mRoundCorners[1] = 0;
        }

        if (mRoundTopRight) {
            mRoundCorners[2] = mRoundCornerRadius;
            mRoundCorners[3] = mRoundCornerRadius;
        } else {
            mRoundCorners[2] = 0;
            mRoundCorners[3] = 0;
        }

        if (mRoundBottomRight) {
            mRoundCorners[4] = mRoundCornerRadius;
            mRoundCorners[5] = mRoundCornerRadius;
        } else {
            mRoundCorners[4] = 0;
            mRoundCorners[5] = 0;
        }

        if (mRoundBottomLeft) {
            mRoundCorners[6] = mRoundCornerRadius;
            mRoundCorners[7] = mRoundCornerRadius;
        } else {
            mRoundCorners[6] = 0;
            mRoundCorners[7] = 0;
        }
    }

    private void setChildMargin() {
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                if (view != null) {
                    LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                    layoutParams.rightMargin = (int) mShadowDx;
                    layoutParams.bottomMargin = (int) mShadowDy;
                    view.setLayoutParams(layoutParams);
                }
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mShadowDx > 0 || mShadowDy > 0) {
            setChildMargin();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        updateImageRect();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        // Path path = new Path();
        // path.addRoundRect(mImageRect, mRoundCornerRadius, mRoundCornerRadius, Path.Direction.CW);
        // canvas.clipPath(path); // not support anti-alias.

        if (mRoundAsCircle || mRoundCornerRadius > 0) {
            int count = canvas.saveLayer(mImageRect, mImagePaint, Canvas.ALL_SAVE_FLAG);
            super.dispatchDraw(canvas);

            mPath.reset();
            mPath.setFillType(Path.FillType.EVEN_ODD);
            // 添加一个矩形路径
            mPath.addRect(0, 0, mViewWidth, mViewHeight, Path.Direction.CW);
            // 添加一个圆角矩形路径，因为FillType是EVEN_ODD，相当于在上面矩形中“裁剪”掉该圆角矩形的区域
            if (mRoundAsCircle) {
                float radius;
                if (mViewWidth > mViewHeight) {
                    radius = mViewHeight / 2 - mShadowRadius;
                } else {
                    radius = mViewWidth / 2 - mShadowRadius;
                }
                mPath.addCircle(mViewWidth / 2 - mShadowDx, mViewHeight / 2 - mShadowDy, radius, Path.Direction.CW);

            } else if (mRoundCornerRadius > 0) {
                setRoundCorners();
                mPath.addRoundRect(mImageRect, mRoundCorners, Path.Direction.CW);
            }
            // “裁剪”掉路径内的画面，形成圆角效果
            canvas.drawPath(mPath, mRoundPaint);

            canvas.restoreToCount(count);

        } else {
            super.dispatchDraw(canvas);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制阴影
        if (mRoundAsCircle) {
            float radius;
            if (mViewWidth > mViewHeight) {
                radius = mViewHeight / 2 - mShadowRadius;
            } else {
                radius = mViewWidth / 2 - mShadowRadius;
            }
            canvas.drawCircle(mViewWidth / 2 - mShadowDx, mViewHeight / 2 - mShadowDy, radius, mShadowPaint);

        } else if (mShadowRadius > 0) {
            mPath.reset();
            setRoundCorners();
            mPath.addRoundRect(mImageRect, mRoundCorners, Path.Direction.CW);
            canvas.drawPath(mPath, mShadowPaint);
        }
    }

}
