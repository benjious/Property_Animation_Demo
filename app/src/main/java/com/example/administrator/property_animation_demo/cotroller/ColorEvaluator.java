package com.example.administrator.property_animation_demo.cotroller;

import android.animation.TypeEvaluator;
import android.content.Intent;
import android.graphics.Color;

/**
 * Created by Administrator on 2016/8/25.
 *
 * 1.若是开始,那么生成初始值
 * 2.进行过程中,计算current颜色值,并根据属性动画变化的值变化转化成颜色值
 * 3.16进制的转化,返回颜色值
 */
public class ColorEvaluator implements TypeEvaluator {
    private int mCurrentRed = -1;
    private int mCurrentBlue = -1;
    private int mCurrentGreen = -1;

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        //这里先获取颜色,但第一次运行时可以调用
        String startColor = (String) startValue;
        String endColor = (String) endValue;
        int startRed = Integer.parseInt(startColor.substring(1, 3), 16);
        int startGreen = Integer.parseInt(startColor.substring(3, 5), 16);
        int startBlue = Integer.parseInt(startColor.substring(5, 7), 16);
        int endRed = Integer.parseInt(endColor.substring(1, 3), 16);
        int endGreen = Integer.parseInt(endColor.substring(3, 5), 16);
        int endBlue = Integer.parseInt(endColor.substring(5, 7), 16);

        //初始化颜色的值
        if (mCurrentRed == -1) {
            mCurrentRed = startRed;
        }
        if (mCurrentBlue == -1) {
            mCurrentBlue = startBlue;
        }
        if (mCurrentGreen == -1) {
            mCurrentGreen = startGreen;
        }

        //计算初始颜色和结束颜色之间的差值
        /**
         * 1-2 控制透明度 两位数字
         3-4 控制红色 两位数字
         5-6 控制绿色 两位数字
         7-8 控制蓝色 两位数字
         总体颜色有三原色组成
         int colorDiff = redDiff + greenDiff + blueDiff;
         1-2 透明度可以不设置，这里面就是舍弃掉了。
         变为#000000六位
         */
        int redDiff = Math.abs(startRed - endRed);
        int blueDiff = Math.abs(startBlue - endBlue);
        int greenDiff = Math.abs(startGreen - endGreen);
        //colorDiff差值总和
        int colorDiff = redDiff + greenDiff + blueDiff;
        //如果现在的颜色没到endColor
        //这个三个if判断是当红色元素走完了,就走green的,渐变过程
        if (mCurrentRed != endRed) {
            //这个地方第三个参数传的是差值,第四个参数是"补偿"
            mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0, fraction);
        } else if (mCurrentGreen != endGreen) {

            mCurrentGreen = getCurrentColor(startGreen, endGreen, colorDiff, redDiff, fraction);
        } else if (mCurrentBlue != endBlue) {
            mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff, redDiff + greenDiff , fraction);
        }

        //将计算出的当前的值组装返回
        String currentColor = "#" + getHexString(mCurrentRed) + getHexString(mCurrentGreen) + getHexString(mCurrentBlue);
        return currentColor;

    }

    /**
     * 根据fraction值来计算当前的颜色
     * 第三个参数是"补偿,抵消" --- offset
     *
     *
     */

    public int getCurrentColor(int startColor, int endColor, int colorDiff, int offset, float fraction) {

        int currentColor;
        if (startColor > endColor) {
            currentColor = (int) (startColor - (fraction * colorDiff - offset));
            if (currentColor < endColor) {
                currentColor = endColor;
            }
        } else {
            currentColor = (int) (startColor + (fraction * colorDiff - offset));
            if (currentColor > endColor) {
                currentColor = endColor;
            }
        }
        return currentColor;
    }

    //将10进制颜色值转换成16进制
    private String getHexString(int value) {
        String hexString = Integer.toHexString(value);
        //但只有一个数时
        if (hexString.length()==1) {
            hexString="0"+hexString;
        }
        return hexString;
    }




}

