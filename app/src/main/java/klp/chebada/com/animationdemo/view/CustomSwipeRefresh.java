package klp.chebada.com.animationdemo.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import klp.chebada.com.animationdemo.R;

/**
 * Created by monkey on 17/3/22.
 */

public class CustomSwipeRefresh extends SwipeRefreshLayout {


    public CustomSwipeRefresh(Context context) {
        super(context);
    }

    public CustomSwipeRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modelHeight = MeasureSpec.getMode(heightMeasureSpec);

        int height = 0;
        //临界值，小于等于这个高度的时候为临界值，此时，下拉刷新是整个界面都可触发，但嵌套滑动不会，大于临界值，变可实现嵌套滑动
        int limitHeight = sizeHeight - getContext().getResources().getDimensionPixelSize(R.dimen.abc_action_bar_default_height_material) -1;

        int mCount = getChildCount();
        for(int i = 0; i <mCount; i++) {
            View childView = getChildAt(i);
            if(childView instanceof RecyclerView || childView instanceof NestedScrollView) {
                childView.measure(MeasureSpec.makeMeasureSpec(
                        getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                        MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
                        getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), modelHeight));
                int childHeight = childView.getMeasuredHeight();
                height += childHeight;
            }
        }
        if(height  <= limitHeight) {
            height = limitHeight;
        }

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                modelHeight == MeasureSpec.EXACTLY ? sizeHeight : height );

    }
}
