package klp.com.animationdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by monkey on 16/11/1.
 */

public class MoveBall3 extends View {

    private float currentX;
    private float currentY;
    private float mRadius = 100;
    private int mParentW;
    private int mParentH;


    // 记录手指刚按下时的坐标
    private float firstX;
    private float firstY;

    // 记录总偏移量
    private int sumX;
    private int sumY;

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    private Callback mCallback;


    public MoveBall3(Context context) {
        super(context);
    }

    public MoveBall3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    interface Callback {
        void move(int x, int y);
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
        mRadius = resetW / 2;
        setMeasuredDimension(resetW, resetH);
        currentX = getMeasuredWidth() / 2;
        currentY = getMeasuredHeight() / 2;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                firstX = event.getRawX();
                firstY = event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                // 记录总偏移量
                sumX = getScrollX();
                sumY = getScrollY();
                break;

            case MotionEvent.ACTION_MOVE:
                float x = event.getRawX();
                float y = event.getRawY();
                int deviationX = (int)(x - firstX);
                int deviationY = (int)(y - firstY);

                firstX = event.getRawX();
                firstY = event.getRawY();
//                // 在上次移动的基础上再次移动
//                if (mCallback != null) {
//                    mCallback.move(sumX + (int) firstX - x, sumY + (int) firstY - y);
//                }
//                scrollTo(sumX + (int) firstX - x, sumY + (int) firstY - y);
                setTranslationX(getTranslationX() + deviationX);
                setTranslationY(getTranslationY() + deviationY);
                Log.e("MoveBall3", getTop() +"");
//                invalidate();
//                reFreshPosition(getTranslationX() + deviationX, getTranslationY() + deviationY);
                break;

        }

        return true;
    }

    private void reFreshPosition(float x, float y) {
        float currentX = x + getLeft();
        float currentY = y + getTop();
//        int minX = 0;
//        int minY = 0;
//        int maxX = getParent()
        setTranslationX(Math.min(currentX, 0));
        setTranslationY(Math.min(currentY, 0));
//        Math.min(currentX, 0);
//        Math.min(currentY, 0);
    }
}
