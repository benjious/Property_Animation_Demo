package com.example.administrator.property_animation_demo.cotroller;

import android.animation.TypeEvaluator;

import com.example.administrator.property_animation_demo.model.Point;

/**
 * Created by Administrator on 2016/8/24.
 */
public class PointEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;

        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());

        Point point = new Point(x, y);
        return point;
    }
}
