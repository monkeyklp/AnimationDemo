package klp.com.animationdemo.adapter

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearSmoothScroller

/**
 * @Description: 消息流滑动速度控制
 */
class MessageScroller(context: Context) : LinearSmoothScroller(context) {


    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
        return 260f / displayMetrics.densityDpi.toFloat()
    }

    fun setRunnerStop() {
        stop()
    }
}

