package klp.com.animationdemo.behavior;

import android.content.Context;
import com.google.android.material.appbar.AppBarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by klp13115 on 2016/4/12.
 */
public class FooterBehaviorDependAppBar extends CoordinatorLayout.Behavior {
    public FooterBehaviorDependAppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FooterBehaviorDependAppBar() {
        super();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float translationY =   Math.abs(dependency.getY());
        Log.i("klp", dependency.getClass().getSimpleName());
        Log.i("klp", translationY+"---------------------");
        child.setTranslationY(translationY);
        return true;
    }
}
