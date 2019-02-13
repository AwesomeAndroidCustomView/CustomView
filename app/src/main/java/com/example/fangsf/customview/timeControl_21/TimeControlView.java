package com.example.fangsf.customview.timeControl_21;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.fangsf.customview.R;
import com.example.fangsf.customview.utils.BitmapUtils;
import com.example.fangsf.customview.utils.ChartUtils;

/**
 * Created by fangsf on 2018/10/3.
 * Useful:  时间控制view
 */
public class TimeControlView extends View {

    private static final String TAG = "TimeControlView";
    private Context mContext;

    // 外圆背景
    private Paint mBgOutPaint;
    // 外圆背景的宽度
    private int mOutStroke = 8;
    // 外圆背景的大小
    private int mBgOutWidth;

    // 画笔
    private Paint mBitmapPaint;
    // 圆环中心点
    private float mCenterX, mCenterY;
    // 当前的角度
    private float mCurrentAngle = 0;
    //圆的半径
    private int mRadius;
    //上一次点所在象限
    private int lastQuadrant = 1;
    //上一次的弧边角
    private float lastAngle;
    // 进度圆的RectF
    private RectF mOvalRectF;
    private Bitmap mOutDragBitmap;
    private int mOutDragDrawableHeight;

    // 可以触摸的范围
    private int mMinValidateTouchArcRadius;
    private int mMaxValidateTouchArcRadius;

    // 是否按在了圆上
    private boolean isDownArc = false;


    public TimeControlView(Context context) {
        this(context, null);
    }

    public TimeControlView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        initPaint();
    }

    private void initPaint() {
        mBgOutPaint = new Paint();
        mBgOutPaint.setAntiAlias(true);
        mBgOutPaint.setColor(Color.RED);
        mBgOutPaint.setStrokeWidth(dp2px(mOutStroke));
        //  画笔实心
        mBgOutPaint.setStyle(Paint.Style.STROKE);
        //圆弧
        mBgOutPaint.setStrokeCap(Paint.Cap.ROUND);

        mOutDragBitmap = BitmapUtils.getBitmap(mContext, R.mipmap.alarmclock_minutesliderhandle);
//        mInnerDragBitmap = BitmapUtils.getBitmap(mContext, R.mipmap.alarmclock_hoursliderhandle);
        // 设置图片的大小
//        mOutDragBitmap = BitmapUtils.conversionBitmap(mOutDragBitmap, (int) dp2px(mDragBitmapWidth), (int) dp2px(mDragBitmapWidth));
//        mInnerDragBitmap = BitmapUtils.conversionBitmap(mInnerDragBitmap, (int) dp2px(mDragBitmapWidth), (int) dp2px(mDragBitmapWidth));

        mBitmapPaint = new Paint();
        mBitmapPaint.setDither(true);
        mBitmapPaint.setFilterBitmap(true);
        mBitmapPaint.setAntiAlias(true);

        mOvalRectF = new RectF();

    }

    private float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

//        setMeasuredDimension(width > height ? width : height, width > height ? width : height);  // 200, 615, 的值, error

        //  上面的方法不正常, onMeasure 会调用多次, 测量的height, 有的时候会为615
        setMeasuredDimension(measure(widthMeasureSpec),
                measure(heightMeasureSpec));
    }

    /**
     * 计算宽/高值
     *
     * @param measureSpec measureSpec
     * @return measure后的宽/高值
     */
    private int measure(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            //wrap_content，最大宽/高为200dp, AT_MOST
            result = (int) dp2px(300);
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画外圆的背景
        drawBgOutCircle(canvas);

        // 外圆滑动滑块
        drawOutDragBitmap(canvas);

        // 外圆进度圆
        drawOutCircle(canvas);

    }

    private void drawOutCircle(Canvas canvas) {
        if (mCurrentAngle != 0) {
            mBgOutPaint.setColor(Color.BLUE);
            canvas.drawArc(mOvalRectF, 270, mCurrentAngle, false, mBgOutPaint);
        }
    }

    private void drawOutDragBitmap(Canvas canvas) {

        // 减去滑块图片的高度, mOutDragDrawableHeight, getPaddingTop 图片距离外部的padding
        int radius = mRadius - (mOutDragBitmap.getHeight() / 2) - getPaddingTop();
        PointF progressPoint = ChartUtils.calcArcEndPointXY(mCenterX, mCenterY, radius,
                mCurrentAngle, 270);

        int left = (int) progressPoint.x - (mOutDragBitmap.getWidth() / 2);
        int top = (int) progressPoint.y - (mOutDragBitmap.getHeight() / 2);

        drawRotateBitmap(canvas, mBitmapPaint, mOutDragBitmap, mCurrentAngle, left, top);

    }

    private void drawRotateBitmap(Canvas canvas, Paint paint, Bitmap bitmap,
                                  float rotation, float posX, float posY) {
        Matrix matrix = new Matrix();
        int offsetX = bitmap.getWidth() / 2;
        int offsetY = bitmap.getHeight() / 2;
        matrix.postTranslate(-offsetX, -offsetY);
        matrix.postRotate(rotation);
        matrix.postTranslate(posX + offsetX, posY + offsetY);
        canvas.drawBitmap(bitmap, matrix, paint);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;
        mRadius = getWidth() / 2;

        mBgOutWidth = getWidth() / 2;
        // 加上一点距离, 图片有误差
        mOutDragDrawableHeight = mOutDragBitmap.getHeight();

        // 设置外圆的大小
        int left = mOutStroke + mOutDragBitmap.getWidth() + getPaddingTop();
        int top = mOutStroke + mOutDragBitmap.getWidth() + getPaddingTop();
        int right = getWidth() - mOutStroke - mOutDragBitmap.getWidth() - getPaddingTop();
        int bottom = getWidth() - mOutStroke - mOutDragBitmap.getWidth() - getPaddingTop();
        mOvalRectF.set(left, top, right, bottom);

        mMinValidateTouchArcRadius = mRadius - (getPaddingTop() + mOutDragBitmap.getHeight());
        mMaxValidateTouchArcRadius = mRadius;
    }

    private void drawBgOutCircle(Canvas canvas) {
        mBgOutPaint.setColor(Color.RED);
        canvas.drawArc(mOvalRectF, 0, 360, false, mBgOutPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取点击位置的坐标
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isTouchArc(x, y)) {
                    isDownArc = true;
                    updateCurrentAngle(x, y);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDownArc) {
                    updateCurrentAngle(x, y);
                }
                break;
            case MotionEvent.ACTION_UP:
                isDownArc = false;
                break;
        }

        invalidate();
        return true;
    }

    /**
     * 按下时判断按下的点是否按在圆边范围内
     *
     * @param x x坐标点
     * @param y y坐标点
     */
    private boolean isTouchArc(float x, float y) {
        double d = getTouchRadius(x, y);
        Log.i(TAG, "isTouchArc: " + d + " min " + mMinValidateTouchArcRadius + " max " + mMaxValidateTouchArcRadius);
        return d >= mMinValidateTouchArcRadius && d <= mMaxValidateTouchArcRadius;
    }

    /**
     * 计算某点到圆点的距离
     *
     * @param x x坐标点
     * @param y y坐标点
     */
    private double getTouchRadius(float x, float y) {
        float cx = x - getWidth() / 2;
        float cy = y - getHeight() / 2;
        return Math.hypot(cx, cy);
    }

    /**
     * 更新当前进度对应弧度
     *
     * @param x 按下x坐标点
     * @param y 按下y坐标点
     */
    private void updateCurrentAngle(float x, float y) {
        //根据坐标转换成对应的角度
        float pointX = x - mCenterX;
        float pointY = y - mCenterY;
        float tan_x;//根据左边点所在象限处理过后的x值
        float tan_y;//根据左边点所在象限处理过后的y值
        double atan = 0;//所在象限弧边angle

        //01：第一象限-右上角区域
        if (pointX >= 0 && pointY <= 0) {
            tan_x = pointX;
            tan_y = pointY * (-1);   // 相对于屏幕坐标来说, 比centerY是更小的,所以乘以-1
            atan = Math.atan(tan_x / tan_y);//求弧边
            System.out.println("TAG  ->  " + " quadrant  " + lastQuadrant + "  Degrees " + (Math.toDegrees(atan)) + "  current  " + (Math.toDegrees(atan) + 360.f));
            mCurrentAngle = (int) Math.toDegrees(atan); //toDegrees方法用于将参数转化为角度。
            if (lastAngle >= 270.f) {
                mCurrentAngle = 359.f;
                lastQuadrant = 2;
            } else {
                lastQuadrant = 1;
            }
        }

        //02：第二象限-左上角区域
        if (pointX <= 0 && pointY <= 0) {
            tan_x = pointX * (-1);
            tan_y = pointY * (-1);
            atan = Math.atan(tan_y / tan_x);//求弧边
            //  System.out.println("TAG  ->  " + " quadrant  " + lastQuadrant + "  Degrees " + (Math.toDegrees(atan)) + "  current  " + (Math.toDegrees(atan)));
            mCurrentAngle = (int) Math.toDegrees(atan) + 270.f;
            if (lastAngle <= 90.f) {
                mCurrentAngle = 0;
                lastQuadrant = 1;
            } else {
                lastQuadrant = 2;
            }
        }

        //03：第三象限-左下角区域
        if (pointX <= 0 && pointY >= 0 && lastQuadrant != 1 && lastAngle < 359.f) {
            tan_x = pointX * (-1);
            tan_y = pointY;
            atan = Math.atan(tan_x / tan_y);//求弧边
            // System.out.println("TAG  ->  " + " quadrant  " + lastQuadrant + "  Degrees " + (Math.toDegrees(atan)) + "  current  " + (Math.toDegrees(atan) + 270f));
            mCurrentAngle = (int) Math.toDegrees(atan) + 180f;
            lastQuadrant = 3;
        }

        //04：第四象限-右下角区域
        if (pointX >= 0 && pointY >= 0 && lastQuadrant != 2 && lastAngle >0.f) {
            tan_x = pointX;
            tan_y = pointY;
            atan = Math.atan(tan_y / tan_x);//求弧边
            mCurrentAngle = (int) Math.toDegrees(atan) + 90.f;
            lastQuadrant = 4;
        }
        System.out.println("lastangle " + lastAngle);
        lastAngle = mCurrentAngle;
    }


}
