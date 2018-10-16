package com.samsung.jesse.mymodule;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Jesse on 2018/10/6.
 */

public class MainActivity extends Activity {

    private LinearLayout linearLayout;
    private EditText width;
    private EditText height;
    private EditText thickness;
    private Button btn;
    private Button btn1;
    private RelativeLayout viewGroup;
    private int childCount;
    private int wt;//输入宽高厚
    private int ht;
    private int thick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findId();
        childCount = linearLayout.getChildCount();
        onClick();
        initView();
    }

    private void onClick() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(width.getText()) || TextUtils.isEmpty(height.getText()) || TextUtils.isEmpty(thickness.getText()))
                    return;
                if (linearLayout.getChildCount() > childCount)
                    linearLayout.removeViewAt(childCount);
                float w = Float.parseFloat(width.getText().toString());
                float h = Float.parseFloat(height.getText().toString());
                thick = Integer.parseInt(thickness.getText().toString());
                wt = (int) w;
                ht = (int) h;
                float k = h / w;
                viewGroup = new RelativeLayout(MainActivity.this);
                LinearLayout.LayoutParams params;
                if (k < 1)
                    params = new LinearLayout.LayoutParams(linearLayout.getWidth(), (int) (k * linearLayout.getWidth()));
                else
                    params = new LinearLayout.LayoutParams((int) ((float) linearLayout.getWidth() / k), linearLayout.getWidth());
                viewGroup.setLayoutParams(params);
                int padding = TestView.dip2px(MainActivity.this, 10);
                viewGroup.setPadding(padding, padding, padding, padding);
                viewGroup.setBackgroundResource(R.drawable.shape_rect);
                linearLayout.addView(viewGroup);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearLayout.getChildCount() == childCount) return;
                TestView viewTest = new TestView(MainActivity.this);
                viewTest.width = viewGroup.getWidth() - TestView.dip2px(MainActivity.this, 20);
                viewTest.height = viewGroup.getHeight() - TestView.dip2px(MainActivity.this, 20);
                viewTest.wt = wt + 1 - thick * 2;
                viewTest.ht = ht;
                viewTest.heightText =300  + 100 * viewGroup.getChildCount();
                viewTest.thickness = thick;
                viewGroup.addView(viewTest);
            }
        });
    }

    private void initView() {

    }

    private void findId() {
        linearLayout = findViewById(R.id.ll_parent);
        width = findViewById(R.id.width);
        height = findViewById(R.id.height);
        btn = findViewById(R.id.btn);
        btn1 = findViewById(R.id.btn1);
        thickness = findViewById(R.id.thickness);
    }


    /**
     * 将像素转换成dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


}
