package klp.com.animationdemo.behavior;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by klp13115 on 2016/4/11.
 */
public class ToolBarBehavior extends CoordinatorLayout.Behavior {

    private static final String TAG = "ToolBarBehavior";

    private static final Interpolator DECELERATION_INTERPOLATOR = new LinearOutSlowInInterpolator();

    private static final Interpolator ACCELERATION_INTERPOLATOR = new FastOutLinearInInterpolator();

    public ToolBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private ValueAnimator valueAnimator;

    //1.判断滑动的方向 我们需要垂直滑动
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        final boolean started = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0
                //内容超过一屏，可滚动
                && coordinatorLayout.getHeight() - directTargetChild.getHeight() <= child.getHeight();
        if(started && valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.end();
            Log.e(TAG, "Animator cancel");
        }
        return started;
    }



        @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.e(TAG, "onNestedScroll--------dyConsumed " + dyConsumed);
        Log.e(TAG, "onNestedScroll--------dyUnconsumed " + dyUnconsumed);
        if (dyConsumed > 2) {
            // User scrolled down and the FAB is currently visible -> hide the FAB
//            hide(child);
            animator(child, false);

        } else if (dyConsumed < -2) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
//            show(child);
            animator(child, true);
     }
    }
    private void animator(final View view, boolean up) {

        if(valueAnimator == null) {
            valueAnimator = new ValueAnimator();
            valueAnimator.setDuration(200);
            valueAnimator.setTarget(view);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    view.setTranslationY((Float)valueAnimator.getAnimatedValue());
                }
            });
        }
        if(up) {
            valueAnimator.setInterpolator(ACCELERATION_INTERPOLATOR);
            valueAnimator.setFloatValues(view.getTranslationY(), 0);
        } else {
            valueAnimator.setInterpolator(DECELERATION_INTERPOLATOR);
            valueAnimator.setFloatValues(view.getTranslationY(), -view.getHeight());
        }
        valueAnimator.start();
    }
}
