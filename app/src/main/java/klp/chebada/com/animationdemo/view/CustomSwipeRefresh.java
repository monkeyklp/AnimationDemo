package klp.chebada.com.animationdemo.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

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

        int mCount = getChildCount();
        for(int i = 0; i <mCount; i++) {
            View childView = getChildAt(i);
            if(childView instanceof RecyclerView) {
                childView.measure(widthMeasureSpec, heightMeasureSpec);
            }
            int childHeight = childView.getMeasuredHeight();
            height += childHeight;

        }

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                modelHeight == MeasureSpec.EXACTLY ? sizeHeight : height );

    }
}
