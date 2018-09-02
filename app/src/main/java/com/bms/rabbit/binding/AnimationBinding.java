package com.bms.rabbit.binding;
// Created by Konstantin on 25.07.2018.


import android.databinding.BindingAdapter;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AnimationBinding {

    @BindingAdapter({"visibilityAnim", "duration"})
    public static void setVisibilityAnim(final View view, boolean visible,int duration) {
        if (visible) {
            fadeInAnimate(view, duration);
        } else {
            fadeOutAnimate(view, duration);
        }
    }

    @BindingAdapter({"visibilityAnim"})
    public static void setVisibilityAnim(final View view, boolean visible) {
        setVisibilityAnim(view, visible,1000);
    }

    @BindingAdapter({"visibility", "animatedView"})
    public static void setVisibilityAnimWithCondition(final View view, boolean visible, int duration) {
        if (visible) {
            setVisibilityAnim(view, duration);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("animatedView")
    public static void setVisibilityAnim(final View view,final int duration) {
        fadeInAnimate(view, duration);
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

        exec.schedule(new Runnable() {
            public void run() {
                fadeOutAnimate(view, duration);
            }
        }, 6, TimeUnit.SECONDS);
    }

    private static void fadeInAnimate(final View view,final int duration) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(duration);
        view.startAnimation(fadeIn);
        view.setVisibility(View.VISIBLE);
    }

    private static void fadeOutAnimate(final View view,final int duration) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
//        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(duration);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(fadeOut);
    }
}
