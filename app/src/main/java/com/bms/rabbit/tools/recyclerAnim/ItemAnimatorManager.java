package com.bms.rabbit.tools.recyclerAnim;
// Created by Konstantin on 04.10.2017.


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

public class ItemAnimatorManager {
    private static final int DEFAULT_DURATION = 150;

    protected ItemAnimatorManager() {

    }

    public static ItemAnimatorFactory scaleInRight() {
        return new ItemAnimatorFactory() {
            @Override
            public SimpleItemAnimator create(RecyclerView recyclerView) {
                SimpleItemAnimator animator = new ScaleInRightAnimator();
                animator.setAddDuration(DEFAULT_DURATION);
                animator.setRemoveDuration(DEFAULT_DURATION);
                animator.setMoveDuration(DEFAULT_DURATION);
                animator.setChangeDuration(DEFAULT_DURATION);
                return animator;
            }
        };
    }

    public static ItemAnimatorFactory scaleInRight(final int duration) {
        return new ItemAnimatorFactory() {
            @Override
            public SimpleItemAnimator create(RecyclerView recyclerView) {
                SimpleItemAnimator animator = new ScaleInRightAnimator();
                animator.setAddDuration(duration);
                animator.setRemoveDuration(duration);
                animator.setMoveDuration(duration);
                animator.setChangeDuration(duration);
                return animator;
            }
        };
    }

    public static ItemAnimatorFactory landing() {
        return new ItemAnimatorFactory() {
            @Override
            public SimpleItemAnimator create(RecyclerView recyclerView) {
                SimpleItemAnimator animator = new LandingAnimator();
                animator.setAddDuration(DEFAULT_DURATION);
                animator.setRemoveDuration(DEFAULT_DURATION);
                animator.setMoveDuration(DEFAULT_DURATION);
                animator.setChangeDuration(DEFAULT_DURATION);
                return animator;
            }
        };
    }

    public static ItemAnimatorFactory landing(final int duration) {
        return new ItemAnimatorFactory() {
            @Override
            public SimpleItemAnimator create(RecyclerView recyclerView) {
                SimpleItemAnimator animator = new LandingAnimator();
                animator.setAddDuration(duration);
                animator.setRemoveDuration(duration);
                animator.setMoveDuration(duration);
                animator.setChangeDuration(duration);
                return animator;
            }
        };
    }

    public static ItemAnimatorFactory fadeIn() {
        return new ItemAnimatorFactory() {
            @Override
            public SimpleItemAnimator create(RecyclerView recyclerView) {
                SimpleItemAnimator animator = new FadeInAnimator();
                animator.setAddDuration(DEFAULT_DURATION);
                animator.setRemoveDuration(DEFAULT_DURATION);
                animator.setMoveDuration(DEFAULT_DURATION);
                animator.setChangeDuration(DEFAULT_DURATION);
                return animator;
            }
        };
    }

    public static ItemAnimatorFactory fadeIn(final int duration) {
        return new ItemAnimatorFactory() {
            @Override
            public SimpleItemAnimator create(RecyclerView recyclerView) {
                SimpleItemAnimator animator = new FadeInAnimator();
                animator.setAddDuration(duration);
                animator.setRemoveDuration(duration);
                animator.setMoveDuration(duration);
                animator.setChangeDuration(duration);
                return animator;
            }
        };
    }

    public interface ItemAnimatorFactory {
        SimpleItemAnimator create(RecyclerView recyclerView);
    }
}
