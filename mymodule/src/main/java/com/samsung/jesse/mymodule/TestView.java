package com.samsung.jesse.mymodule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 测量工具自定义view
 * Created by Jesse on 2018/10/8.
 */

public class TestView extends View {
    private final String TAG = this.getClass().getName();
    private float downX;
    private float downY;
    private Paint paint;
    private Paint paintTextLeft;
    private Paint paintTextRight;
    public int width;//父控件的宽高
    public int height;
    public int wt;//输入的宽高
    public int ht;
    public int thickness = 30;
    private int x;//当前x
    private int y;
    private Context context;
    private boolean isShowDetail = false;//显示距离字数
    public int heightText = 300;

    public TestView(Context context) {
        super(context);
        this.context = context;
        paint = new Paint();
        paintTextLeft = new Paint();
        paintTextRight = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        paintTextLeft.setColor(Color.RED);
        paintTextRight.setColor(ContextCompat.getColor(context, R.color.textRight));
        paintTextLeft.setTextSize(50);
        paintTextRight.setTextSize(50);
        paint.setStrokeWidth(thickness);
        //抗锯齿
        paint.setAntiAlias(true);
        paintTextLeft.setAntiAlias(true);
        paintTextRight.setAntiAlias(true);
        // 设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        paint.setDither(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(width - 2 * dip2px(context, 10), height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isShowDetail) {
            canvas.drawLine(getLeft() + width / 2 - 15, getTop() - 30, getLeft() + width / 2 - 15, getBottom(), paint);
        } else {
            canvas.drawLine(x, getTop() - 30, x, getBottom(), paint);
            canvas.drawLine(getLeft() - 30, getTop() + heightText, x - 15, getTop() + heightText, paintTextLeft);
//            Log.i(TAG, "----wt---" + wt + "-----width-----" + width + "-----x----" + x);
            float k = (float) (wt - thickness) / (float) (width - 30);
            canvas.drawText((int) (k * (x - 15)) + "", getLeft() + 100, getTop() + heightText + 50, paintTextLeft);
            canvas.drawLine(x + 15, getTop() + heightText - 100, getRight(), getTop() + heightText - 100, paintTextRight);
            canvas.drawText((int) (k * (width - x - 15)) + "", getRight() - 200, getTop() + heightText - 50, paintTextRight);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    final int xDistance = (int) (event.getX() - downX);//左滑- 右滑+ 每次1
                    x = (int) event.getX();
                    y = (int) event.getY();
                    isShowDetail = true;
                    invalidate();
//                    final float yDistance = event.getY() - downY;
//                    int t = (int) (getTop() + yDistance);
//                    int b = (int) (getBottom() + yDistance);
//                    offsetLeftAndRight(xDistance);
                    break;
            }
            return true;
        }
        return false;
    }

    /**
     * 将dp转换成px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
