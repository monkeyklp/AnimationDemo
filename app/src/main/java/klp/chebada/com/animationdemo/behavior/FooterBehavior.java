package klp.chebada.com.animationdemo.behavior;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by klp13115 on 2016/4/11.
 */
public class FooterBehavior extends CoordinatorLayout.Behavior {

    private static final String TAG = "FooterBehavior";

    private static final Interpolator INTERPOLATOR = new LinearInterpolator();

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
    private void animator(final View view, boolean up) {

        if(valueAnimator == null) {
            valueAnimator = new ValueAnimator();
            valueAnimator.setDuration(200);
            valueAnimator.setInterpolator(INTERPOLATOR);
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
            valueAnimator.setFloatValues(view.getTranslationY(), 0);
        } else {
            valueAnimator.setFloatValues(view.getTranslationY(), view.getHeight());
        }
        valueAnimator.start();
    }
}
