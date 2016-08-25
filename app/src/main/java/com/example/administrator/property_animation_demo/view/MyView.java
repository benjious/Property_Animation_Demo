package com.example.administrator.property_animation_demo.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;

import com.example.administrator.property_animation_demo.cotroller.ColorEvaluator;
import com.example.administrator.property_animation_demo.cotroller.DecelerateAccelerateInterpolator;
import com.example.administrator.property_animation_demo.cotroller.PointEvaluator;
import com.example.administrator.property_animation_demo.model.Point;

/**
 * Created by Administrator on 2016/8/24.
 */
public class MyView extends View {
    private static final float RADIUS = 50f;
    private String  color;
    private Point currentPoint;
    private Paint mPaint;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //如果现在的店不存在
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            //这个方法就是获取现在的坐标绘制圆
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }


    private void startAnimation() {
        //1.开始是定开始和结束点
        //2.然后根据我们自定义好的类型计数器(用于返回值:动画的不同的时刻)
        //3.获取当前前坐标,并使他"无效",就可以让他重绘了
//        Point startPoint = new Point(RADIUS, RADIUS);
//        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);

        Point startPoint = new Point(getWidth() / 2,RADIUS);
        Point endPoint = new Point(getWidth() / 2,getHeight()-RADIUS);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                //设置为"无效"这样onDraw()再一次被调用
                invalidate();

            }
        });
        valueAnimator.setInterpolator(new DecelerateAccelerateInterpolator());
        valueAnimator.setDuration(3000);
        valueAnimator.start();
//        ObjectAnimator animator = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(), "#000000", "#00ff00");
//        animator.setRepeatCount(ObjectAnimator.INFINITE);
//        animator.setRepeatMode(ObjectAnimator.RESTART);
//        AnimatorSet set = new AnimatorSet();
//        set.play(valueAnimator).with(animator);
//        set.setDuration(5000);
//        set.start();
    }


}
