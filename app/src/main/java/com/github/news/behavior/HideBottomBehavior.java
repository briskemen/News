package com.github.news.behavior;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class HideBottomBehavior extends CoordinatorLayout.Behavior {

    private boolean   isBottomHide = false;
    private boolean   isAnimating  = false;
    private final int SCROOL_VALUE = 50;
    private int childHeight;
    private final int animationDuration = 500;


    public HideBottomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        if (target instanceof RecyclerView) {
            if (childHeight == 0) {
                childHeight = child.getHeight();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        if (isAnimating) {
            return;
        }
        if (dy > SCROOL_VALUE && !isBottomHide) {
            hide(child, target);
        } else if (dy < -SCROOL_VALUE && isBottomHide) {
            show(child, target);
        }
    }

    public void hide(final View child, final View target) {
        isBottomHide = true;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(0, childHeight);
        valueAnimator.setDuration(animationDuration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (child.getBottom() > 0) {
                    int value = (int) animation.getAnimatedValue();
                    isAnimating = value != childHeight;
                    // child.layout(child.getLeft(), child.getTop()+value, child.getRight(), child.getBottom()+value);
                    ViewCompat.offsetTopAndBottom(child,value);
                    // target.layout(target.getLeft(), -value + childHeight, target.getRight(), target.getBottom());
                }
            }
        });
        valueAnimator.start();
    }

    public void show(final View child, final View target) {
        isBottomHide = false;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(0, childHeight);
        valueAnimator.setDuration(animationDuration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (child.getBottom() < childHeight) {
                    int value = (int) animation.getAnimatedValue();
                    isAnimating = value != childHeight;
                    ViewCompat.offsetTopAndBottom(child,-value);
                    // child.layout(child.getLeft(), value - childHeight, child.getRight(), value);
                    // target.layout(target.getLeft(), value, target.getRight(), target.getBottom());
                }
            }
        });
        valueAnimator.start();
    }

}