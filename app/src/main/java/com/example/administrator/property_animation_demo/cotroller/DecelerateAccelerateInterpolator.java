package com.example.administrator.property_animation_demo.cotroller;

import android.animation.TimeInterpolator;

/**
 * Created by Administrator on 2016/8/25.
 */
public class DecelerateAccelerateInterpolator implements TimeInterpolator {

    @Override
    public float getInterpolation(float input) {
        float result;
        if (input<=0.5) {
            //前半段先减速
            result = (float) ((Math.sin(Math.PI*input))/2);
        }else {
            //后半段加速
            result= (float) ((2-Math.sin(Math.PI*input))/2);
        }
        return result;
    }

}
