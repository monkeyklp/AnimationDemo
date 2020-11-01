package klp.com.animationdemo.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import klp.com.animationdemo.R;


/**
 * Created by monkey on 17/1/13.
 */

public class FollowBehavior extends CoordinatorLayout.Behavior {
    private int mTargetId;
    public FollowBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Follow);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            if(a.getIndex(i) == R.styleable.Follow_target){
                mTargetId = a.getResourceId(attr, -1);
            }
        }
        a.recycle();
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
            child.setY(dependency.getY()+dependency.getHeight());
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == mTargetId;
    }
}
