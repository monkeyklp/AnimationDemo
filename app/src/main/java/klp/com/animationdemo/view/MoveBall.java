package klp.com.animationdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import static android.view.MotionEvent.ACTION_MOVE;

/**
 * Created by monkey on 16/11/1.
 */

public class MoveBall extends View {

    private float currentX;
    private float currentY;
    private float mRadius = 100;
    private int mParentW;
    private int mParentH;


    public MoveBall(Context context) {
        super(context);
    }

    public MoveBall(Context context, AttributeSet attrs) {
        super(context, attrs);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case ACTION_MOVE:
                currentX = event.getX();
                currentY = event.getY();
                if (mParentH > currentY + mRadius && currentY - mRadius > 0 && mParentW > currentX + mRadius && currentX - mRadius > 0) {
                    invalidate();
                }
                break;
            default:
                break;
        }
        return true;
    }
}
