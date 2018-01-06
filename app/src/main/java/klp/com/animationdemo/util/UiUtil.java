package klp.com.animationdemo.util;

import android.content.Context;

/**
 * Created by klp13115 on 2016/9/21.
 */

public class UiUtil {

    public static int dipToPx(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }

}
