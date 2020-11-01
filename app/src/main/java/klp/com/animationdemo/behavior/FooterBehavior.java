package klp.com.animationdemo.behavior;

import android.animation.ValueAnimator;
import android.content.Context;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by klp13115 on 2016/4/11.
 */
public class FooterBehavior extends CoordinatorLayout.Behavior {

    private static final String TAG = "FooterBehavior";

    private static final Interpolator DECELERATION_INTERPOLATOR = new LinearOutSlowInInterpolator();

    private static final Interpolator ACCELERATION_INTERPOLATOR = new FastOutLinearInInterpolator();

    public FooterBehavior() {}

    public FooterBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private ValueAnimator valueAnimator;

    //1.判断滑动的方向 我们需要垂直滑动
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        final boolean started = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0
                //内容超过一屏，可滚动
                && coordinatorLayout.getHeight() - directTargetChild.getHeight() <= child.getHeight();
        if(started && valueAnimator != null) {
            valueAnimator.cancel();
        }
        return started;
    }

    //2.根据滑动的距离显示和隐藏footer view
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        Log.e(TAG, "onNestedPreScroll--------dy " + dy);
        Log.e(TAG, "onNestedPreScroll--------consumed " + consumed[1]);
//        if(dy != 0 ) {
//            int min, max;
//            if (dy < 0) {
//                // We're scrolling down
//                min = -child.getTotalScrollRange();
//                max = min + child.getDownNestedPreScrollRange();
//            } else {
//                // We're scrolling up
//                min = -child.getUpNestedPreScrollRange();
//                max = 0;
//            }
//            consumed[1] = scroll(coordinatorLayout, child, dy, min, max);
//        }

    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.e(TAG, "onNestedScroll--------dyConsumed " + dyConsumed);
        Log.e(TAG, "onNestedScroll--------dyUnconsumed " + dyUnconsumed);
        Log.e(TAG, "onNestedScroll--------target " + target.getClass().getSimpleName());
        Log.e(TAG, "onNestedScroll--------child " + child.getClass().getSimpleName());
        if (dyConsumed > 0 ) {
            // User scrolled down and the FAB is currently visible -> hide the FAB
//            hide(child);
            animator(child, false);
        } else if (dyConsumed < 0) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
//            show(child);
            animator(child, true);
     }
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        Log.e(TAG, "onNestedFling--------velocityY " + velocityY);
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    private void animator(final View view, boolean up) {

        if(valueAnimator == null) {
            valueAnimator = new ValueAnimator();
            valueAnimator.setDuration(150);
            valueAnimator.setTarget(view);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    view.setTranslationY((Float)valueAnimator.getAnimatedValue());
                }
            });
        } else {
            valueAnimator.cancel();
        }
        if(up) {
            valueAnimator.setInterpolator(DECELERATION_INTERPOLATOR);
            valueAnimator.setFloatValues(view.getTranslationY(), 0);
        } else {
            valueAnimator.setInterpolator(ACCELERATION_INTERPOLATOR);
            valueAnimator.setFloatValues(view.getTranslationY(), view.getHeight());
        }
        valueAnimator.start();
    }
}
