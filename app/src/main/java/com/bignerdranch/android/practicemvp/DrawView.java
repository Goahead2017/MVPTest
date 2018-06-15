package com.bignerdranch.android.practicemvp;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 绘制登录界面
 */

public class DrawView extends View{

    private int mColor = Color.RED;

    private Paint paint1;
    Path path1;
    RectF oval;
    ValueAnimator anim1;
    private float currentValue1;

    private float X;
    private float Y;

    private Paint paint2;
    private PointF start1,start2,end1,end2,control1,control2;
    Path path2;
    Path path3;
    Path path4;
    Path path5;
    ValueAnimator anim2;
    private float currentValue2;

    private float Q = 100;
    private float w = (float) 0.01;
    private float A = 30;
    Path path;
    private Paint paint;

    public DrawView(Context context) {
        this(context,null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //解析自定义属性
        @SuppressLint("CustomViewStyleable") TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.PracticeMVP);
        mColor = a.getColor(R.styleable.PracticeMVP_Wave_color,Color.BLUE);
        a.recycle();

        init();
        anim1.setDuration(5000);
        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentValue1 = (Float)animation.getAnimatedValue();
                invalidate();
            }
        });
        anim1.start();

        anim2.setDuration(800);
        anim2.setRepeatCount(5);
        anim2.setRepeatMode(ValueAnimator.REVERSE);
        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentValue2 = (Float)valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        anim2.start();

    }

    @SuppressLint("ResourceAsColor")
    private void init() {

        paint1 = new Paint();
        paint1.setColor(Color.YELLOW);
        paint1.setDither(true);
        paint1.setAntiAlias(true);
        paint1.setStyle(Paint.Style.STROKE);

        paint2 = new Paint();
        paint2.setColor(Color.BLUE);
        paint2.setDither(true);
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.STROKE);

        path1 = new Path();
        anim1 = ValueAnimator.ofFloat(0,360);

        start1 = new PointF(0,0);
        end1 = new PointF(0,0);
        control1 = new PointF(0,0);
        path2 = new Path();
        path3 = new Path();

        start2 = new PointF(0,0);
        end2 = new PointF(0,0);
        control2 = new PointF(0,0);
        path4 = new Path();
        path5 = new Path();
        anim2 = ValueAnimator.ofFloat(0,360);

        path = new Path();
        paint = new Paint();
        paint.setColor(mColor);
        paint.setAlpha(100);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);

    }

    //绘制水波纹
    private void drawSin(Canvas canvas){
        Q -= 0.03;
        float y;

        path.reset();
        path.moveTo(0,getHeight());

        for(float x = 0;x <= getWidth();x += 20){
            y = (float)(A * Math.sin(w * x + Q) + 200);
            path.lineTo(x,getHeight() - y);
        }

        //填充矩形
        path.lineTo(getWidth(),getHeight());
        path.lineTo(0,getHeight());
        path.close();

        canvas.drawPath(path,paint);

        path.reset();
        path.moveTo(0,getHeight());

        for(float x = 0;x <= getWidth();x += 20){
            y = (float)(A * Math.sin(w * x + Q - 90) + 200);
            path.lineTo(x,getHeight() - y);
        }

        //填充矩形
        path.lineTo(getWidth(),getHeight());
        path.lineTo(0,getHeight());
        path.close();

        canvas.drawPath(path,paint);

        path.reset();
        path.moveTo(0,getHeight());

        for(float x = 0;x <= getWidth();x += 20){
            y = (float)(A * Math.sin(w * x + Q - 180) + 200);
            path.lineTo(x,getHeight() - y);
        }

        //填充矩形
        path.lineTo(getWidth(),getHeight());
        path.lineTo(0,getHeight());
        path.close();

        canvas.drawPath(path,paint);
    }

    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //获取屏幕长宽
        X = getWidth();
        Y = getHeight();

        drawSin(canvas);

        //绘制小海鸥
        //初始化数据点和控制点的位置
        start1.x = X / 2;
        start1.y = Y / 2 - 150;
        end1.x = X / 2 -20;
        end1.y = Y / 2 - 155;
        control1.x = X / 2 - 10;
        control1.y = Y / 2 - 170;
        //绘制贝塞尔曲线
        path2.reset();
        path2.moveTo(start1.x,start1.y + 10);
        path2.quadTo(control1.x - (int) currentValue2 / 180,control1.y + (int) currentValue2 /20,end1.x - (int) currentValue2 /180,end1.y + (int) currentValue2 /20);
        canvas.drawPath(path2,paint2);

        path3.reset();
        path3.moveTo(start1.x,start1.y + 10);
        path3.quadTo(control1.x + 25 + (int) currentValue2 / 180,control1.y + 5 + (int) currentValue2 / 20,start1.x + 25 + (int) currentValue2 / 180,start1.y + 5 + (int) currentValue2 / 20);
        canvas.drawPath(path3,paint2);

        //初始化数据点和控制点的位置
        start2.x = X - 70;
        start2.y = Y / 2 - 210;
        end2.x = X - 95;
        end2.y = Y / 2 - 215;
        control2.x = X - (float) 82.5;
        control2.y = Y / 2 - 230;
        //绘制贝塞尔曲线
        path4.reset();
        path4.moveTo(start2.x, start2.y + 10);
        path4.quadTo(control2.x - (int) currentValue2 / 180, control2.y + (int) currentValue2 / 20, end2.x - (int) currentValue2 / 180, end2.y + (int) currentValue2 / 20);
        canvas.drawPath(path4,paint2);

        path5.reset();
        path5.moveTo(start2.x,start2.y + 10);
        path5.quadTo(control2.x + 25 + (int) currentValue2 / 180,control2.y + 5 + (int) currentValue2 / 20,start2.x + 25 + (int) currentValue2 / 180,start2.y + 5 + (int) currentValue2 / 20);
        canvas.drawPath(path5,paint2);

        //绘制小太阳
        oval = new RectF(100 - currentValue1 / 10,100 - currentValue1 /10,100 + currentValue1 / 10,100 + currentValue1 / 10);
        currentValue1 += currentValue1;
        path1.addArc(oval,-90 + currentValue1,180);
        canvas.drawPath(path1,paint1);

        canvas.translate(100,100);
        for(int i = 0;i <= (int)currentValue1 / 40 * 20;i += 20){
            canvas.drawLine(0,40,0,80,paint1);
            canvas.rotate(20);
        }


    }

}
