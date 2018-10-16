package com.cocos.jesse.mygame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 自定义
 * Created by Jesse on 2018/7/20.
 */

public class MyView extends View {
    private float height;
    private Path path;
    private Paint paint;
    private Paint paintText;
    private String text;
    private int left;

    public MyView(Context context) {
        super(context);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x11) {
                left += 10;
                sendEmptyMessageDelayed(0x11, 1000);
                invalidate();
            }
        }
    };

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        handler.sendEmptyMessage(0x11);
        super.setOnClickListener(l);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        paint = new Paint();
        paintText = new Paint();
        paint.setStrokeWidth(2);
        paint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        paintText.setStrokeWidth(10);
        paintText.setTextSize(50);
        paintText.setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        height = t.getDimension(R.styleable.MyView_height_my_view, 1);
        text = t.getString(R.styleable.MyView_text_my_view);
        t.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("measure", "width" + getWidth() + "------" + getMeasuredWidth()
                + "height" + getMeasuredHeight() + "--------" + height);
        setMeasuredDimension(getMeasuredWidth(), (int) height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (left < 500)
            path.moveTo(left, 0);
        else left -= 500;
        path.cubicTo(100, 0, 200, 200, 300, 300);
        canvas.drawPath(path, paint);
        canvas.drawText(text, 0, 200, paintText);
    }
}
