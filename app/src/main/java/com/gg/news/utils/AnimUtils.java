package com.gg.news.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.Property;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class AnimUtils {

    // 移动property
    public static final String TRANSLATE_X = "translationX";
    public static final String TRANSLATE_Y = "translationY";
    public static final String TRANSLATE_Z = "translationZ";
    // 旋转property
    public static final String ROTATION_X = "rotationX";//以X轴做中心线旋转
    public static final String ROTATION_Y = "rotationY";//以Y轴做中心线旋转
    public static final String ROTATION = "rotation";//以自身中心做中心点旋转
    // 缩放property
    public static final String SCALE_X = "scaleX";
    public static final String SCALE_Y = "scaleY";
    // 渐变property
    public static final String alpha = "alpha";

    /**
     * 1.属性动画,真正改变位置，并且除了起始值和终点值之外，还可以有过渡值，参数比较灵活
     * 看清以下的方法，在补间动画和属性动画中的，参数值一样，引用不一样
     * animation.setRepeatMode(Animation.RESTART);//默认
     * animation.setRepeatMode(Animation.REVERSE);//倒退来一遍
     */
    public static ObjectAnimator getObjAnim(View view, String property, long duration, int repeat,
                                            int mode, float... values) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, property, values);
        animator.setDuration(duration);
        animator.setRepeatCount(repeat);
        animator.setRepeatMode(mode);
        //animator.start();
        return animator;
    }

    public static AnimatorSet getObjAnimSet(boolean together, Animator... items) {
        AnimatorSet set = new AnimatorSet();
        if (together) {
            set.playTogether(items);
        } else {
            set.playSequentially(items);
        }
        //set.start();
        return set;
    }

    /**
     * 2.补间动画,没有真正改变位置，只有起始和终点值，有setFillAfter方法
     */
    // 渐变动画
    public static AlphaAnimation getAlpha() {
        return new AlphaAnimation(0, 1);
    }

    public static AlphaAnimation getAlpha(float from, float to) {
        return new AlphaAnimation(from, to);
    }

    public static AlphaAnimation getAlpha(float from, float to, long offset, long duration,
                                          int repeat, boolean fill, int mode) {
        AlphaAnimation animation = new AlphaAnimation(from, to);

        animation.setStartOffset(offset);
        animation.setDuration(duration);
        animation.setRepeatCount(repeat);
        animation.setFillAfter(fill);
        animation.setRepeatMode(mode);
        return animation;
    }

    // 移动动画
    public static TranslateAnimation getTranslate() {
        return new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f);
    }

    public static TranslateAnimation getTranslate(float fromX, float toX, float fromY, float toY) {
        return new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, fromX,
                Animation.RELATIVE_TO_SELF, toX,
                Animation.RELATIVE_TO_SELF, fromY,
                Animation.RELATIVE_TO_SELF, toY);
    }

    public static TranslateAnimation getTranslate(float fromX, float toX, float fromY, float toY,
                                                  long offset, long duration,
                                                  int repeat, boolean fill, int mode) {
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, fromX,
                Animation.RELATIVE_TO_SELF, toX,
                Animation.RELATIVE_TO_SELF, fromY,
                Animation.RELATIVE_TO_SELF, toY);

        animation.setStartOffset(offset);
        animation.setDuration(duration);
        animation.setRepeatCount(repeat);
        animation.setFillAfter(fill);
        animation.setRepeatMode(mode);
        return animation;
    }

    // 缩放动画
    public static ScaleAnimation getScale() {
        return new ScaleAnimation(0.1f, 1, 0.1f, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    }

    public static ScaleAnimation getScale(float fromX, float toX, float fromY, float toY,
                                          float pivotX, float pivotY) {
        return new ScaleAnimation(fromX, toX, fromY, toY,
                Animation.RELATIVE_TO_SELF, pivotX, Animation.RELATIVE_TO_SELF, pivotY);
    }

    public static ScaleAnimation getScale(float fromX, float toX, float fromY, float toY,
                                          float pivotX, float pivotY,
                                          long offset, long duration, int repeat,
                                          boolean fill, int mode) {
        ScaleAnimation animation = new ScaleAnimation(fromX, toX, fromY, toY,
                Animation.RELATIVE_TO_SELF, pivotX, Animation.RELATIVE_TO_SELF, pivotY);

        animation.setStartOffset(offset);
        animation.setDuration(duration);
        animation.setRepeatCount(repeat);
        animation.setFillAfter(fill);
        animation.setRepeatMode(mode);
        return animation;
    }

    // 旋转动画
    public static RotateAnimation getRotate() {
        return new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    }

    public static RotateAnimation getRotate(float from, float to, float pivotX, float pivotY) {
        return new RotateAnimation(from, to,
                Animation.RELATIVE_TO_SELF, pivotX, Animation.RELATIVE_TO_SELF, pivotY);
    }

    public static RotateAnimation getRotate(float from, float to, float pivotX, float pivotY,
                                            long offset, long duration, int repeat,
                                            boolean fill, int mode) {
        RotateAnimation animation = new RotateAnimation(from, to,
                Animation.RELATIVE_TO_SELF, pivotX, Animation.RELATIVE_TO_SELF, pivotY);

        animation.setStartOffset(offset);
        animation.setDuration(duration);
        animation.setRepeatCount(repeat);
        animation.setFillAfter(fill);
        animation.setRepeatMode(mode);
        return animation;
    }

    // AnimationSet相当于animator里的<set/>,多种动画效果叠加
    public static AnimationSet getSet(Animation... items) {
        //false相当于<set/>里的android:shareInterpolator的参数
        AnimationSet Set = new AnimationSet(false);

        for (Animation param : items) {
            Set.addAnimation(param);
        }
        return Set;
    }

    public static AnimationSet getSet(long offset, long duration, int repeat, boolean fill, int mode,
                                      Animation... items) {
        AnimationSet Set = new AnimationSet(true);

        for (Animation param : items) {
            Set.addAnimation(param);
        }
        Set.setStartOffset(offset);
        Set.setDuration(duration);
        Set.setRepeatCount(repeat);
        Set.setFillAfter(fill);
        Set.setRepeatMode(mode);
        Set.setInterpolator(new DecelerateInterpolator());//此处为减速
        return Set;
    }

    // ViewGroup添加动画
    public static LayoutAnimationController getController(AnimationSet as) {
        LayoutAnimationController lac = new LayoutAnimationController(as);
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        lac.setDelay(0.2f);
        return lac;
    }

}
