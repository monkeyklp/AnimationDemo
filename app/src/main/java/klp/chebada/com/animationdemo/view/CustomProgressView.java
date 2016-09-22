package klp.chebada.com.animationdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import klp.chebada.com.animationdemo.util.UiUtil;

/**
 * Created by klp13115 on 2016/9/21.
 */

public class CustomProgressView extends View {

    private static final String TAG = "CustomProgressView";

    private int mWidth;
    private int mHeight;
    private Paint mGreenPaint;
    private Paint mBluePaint;
    private int mUpSpace = UiUtil.dipToPx(getContext(), 10);
    private int mRectWidth = UiUtil.dipToPx(getContext(), 10);
    private int count = 0;
    private int i = 0;

    public int mProgress;

    public CustomProgressView(Context context) {
        super(context);
        initView();
    }

    public CustomProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
    }

    private void initView() {
        mGreenPaint = new Paint();
        mGreenPaint.setStyle(Paint.Style.FILL);
        mGreenPaint.setARGB(255,145,225,61);
        mGreenPaint.setAntiAlias(true);

        mBluePaint = new Paint();
        mBluePaint.setStyle(Paint.Style.FILL);
        mBluePaint.setARGB(255,167,231,100);
        mBluePaint.setAntiAlias(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    i ++;
                    postInvalidate();
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);




    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int j = 0; j < count; j ++ ) {
            if( i % 2 == 0) {
                canvas.drawRect((j + 1) * mUpSpace + j * mRectWidth, 0,(j + 1) * (mUpSpace + mRectWidth), mHeight, mGreenPaint);
                canvas.drawRect(j * mUpSpace + j * mRectWidth, 0,(j + 1) *  mRectWidth + j * mRectWidth, mHeight, mBluePaint);
            } else {
                canvas.drawRect((j + 1) * mUpSpace + j * mRectWidth, 0,(j + 1) * (mUpSpace + mRectWidth), mHeight, mBluePaint);
                canvas.drawRect(j * mUpSpace + j * mRectWidth, 0,(j + 1) *  mRectWidth + j * mRectWidth, mHeight, mGreenPaint);
            }
        }
        count = (mWidth * mProgress / 100 ) / mUpSpace / 2 ;
    }
}
