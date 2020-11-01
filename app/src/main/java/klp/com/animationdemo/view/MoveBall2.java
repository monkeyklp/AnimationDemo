package klp.com.animationdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;

/**
 * Created by monkey on 16/11/1.
 */

public class MoveBall2 extends View {

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;

    private float currentX;
    private float currentY;
    private float mStartX;
    private float mStartY;
    private float mRadius = 100;
    private int mParentW;
    private int mParentH;


    public MoveBall2(Context context) {
        super(context);
        initView(context);
    }

    public MoveBall2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//悬浮窗不获得焦点
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;

        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        mWindowManager.getDefaultDisplay().getMetrics(dm);

        mLayoutParams.x = dm.widthPixels / 2;
        mLayoutParams.y = dm.heightPixels / 2;

        mWindowManager.addView(this, mLayoutParams);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.RED);
        canvas.drawCircle(currentX, currentY, mRadius, p);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mParentW = MeasureSpec.getSize(widthMeasureSpec);
        mParentH = MeasureSpec.getSize(heightMeasureSpec);

        int resetW = 0;
        int resetH = 0;

        int specModeW = MeasureSpec.getMode(widthMeasureSpec);
        int specModeH = MeasureSpec.getMode(heightMeasureSpec);
        if (specModeW == MeasureSpec.EXACTLY) {
            resetW = mParentW;
        } else {
            resetW = 2 * (int) mRadius;

            if (specModeW == MeasureSpec.AT_MOST) {
                resetW = Math.min(resetW, mParentW);
            }
        }
        if (specModeH == MeasureSpec.EXACTLY) {
            resetH = mParentH;
        } else {
            resetH = 2 * (int) mRadius;
            if (specModeH == MeasureSpec.AT_MOST) {
                resetH = Math.min(resetH, mParentH);
            }
        }
        setMeasuredDimension(resetW, resetH);
        currentX = getMeasuredWidth() / 2;
        currentY = getMeasuredHeight() / 2;
    }


    public void destroy() {
        mWindowManager.removeView(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case ACTION_MOVE:
                float x = event.getRawX();
                float y = event.getRawY();
                int deviationX = (int)(x - mStartX);
                int deviationY = (int)(y - mStartY);

                mStartX = event.getRawX();
                mStartY = event.getRawY();
                mLayoutParams.x = mLayoutParams.x + deviationX;
                mLayoutParams.y = mLayoutParams.y + deviationY;
                mWindowManager.updateViewLayout(this, mLayoutParams);
                break;
            case ACTION_DOWN :
                mStartX = event.getRawX();
                mStartY = event.getRawY();
                break;
            default:
                break;
        }
        return true;
    }
}
