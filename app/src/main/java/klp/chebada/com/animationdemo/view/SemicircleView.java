package klp.chebada.com.animationdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.math.RoundingMode;

import klp.chebada.com.animationdemo.R;

/**
 * Created by klp13115 on 2017/7/25.
 */

public class SemicircleView extends View {
    private static final String TAG = "SemicircleView";

    //背景圆弧画笔
    private Paint mBackgroundArcPaint;
    //顶部提示画笔
    private Paint mTipsPaint;
    //百分比文字画笔
    private Paint mPercentPaint;
    //实心圆画笔
    private Paint mCirclePaint;
    //圆弧半径
    private float mArcRadius;
    //实心圆半径
    private float mCircleRadius;
    //圆弧宽度
    private float mArcStrokeWidth;
    //渐变开始的颜色
    private int  mBgArcStartColor;
    //渐变结束的颜色
    private int mBgArcEndColor;
    //提示文案
    private String mTipsTxt;
    //已使用百分比
    public float mProgress=0;
    public float mProgressBefore = 0;
    //动画展示弧度
    private float mShowProgress;
    private float mPercentTxtHeight;
    private CircleHandler mCircleHandler;


    public SemicircleView(Context context) {
        this(context, null);
    }

    public SemicircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SemicircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context, attrs);
        mCircleHandler = new CircleHandler(this);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SemicircleView, 0, 0);
        mArcStrokeWidth = typeArray.getDimension(R.styleable.SemicircleView_strokeWidth, 12);
        float tipsTextSize = typeArray.getDimension(R.styleable.SemicircleView_tipsTextSize, 14);
        float percentTextSize = typeArray.getDimension(R.styleable.SemicircleView_percentTextSize, 52);
        mArcRadius = typeArray.getDimension(R.styleable.SemicircleView_radius, 200);
        mCircleRadius = typeArray.getDimension(R.styleable.SemicircleView_circleRadius, 20);
        int tipsTxtColor = typeArray.getColor(R.styleable.SemicircleView_tipTxtColor, Color.WHITE);
        int percentTxtColor = typeArray.getColor(R.styleable.SemicircleView_percentTxtColor, Color.WHITE);
        mBgArcStartColor = typeArray.getColor(R.styleable.SemicircleView_bgArcStartColor, Color.BLUE);
        mBgArcEndColor = typeArray.getColor(R.styleable.SemicircleView_bgArcEndColor, Color.WHITE);
        mTipsTxt = typeArray.getString(R.styleable.SemicircleView_tipsText);
        typeArray.recycle();

        //背景圆弧画笔设置
        mBackgroundArcPaint = new Paint();
        mBackgroundArcPaint.setAntiAlias(true);
        mBackgroundArcPaint.setStyle(Paint.Style.STROKE);
        mBackgroundArcPaint.setStrokeWidth(mArcStrokeWidth);
        mBackgroundArcPaint.setStrokeCap(Paint.Cap.ROUND);//开启显示边缘为圆形

        mTipsPaint = new Paint();
        mTipsPaint.setAntiAlias(true);
        mTipsPaint.setStyle(Paint.Style.FILL);
        mTipsPaint.setColor(tipsTxtColor);
        mTipsPaint.setTextSize(tipsTextSize);

        //百分比数字画笔
        mPercentPaint = new Paint();
        mPercentPaint.setAntiAlias(true);
        mPercentPaint.setStyle(Paint.Style.FILL);
        mPercentPaint.setColor(percentTxtColor);
        mPercentPaint.setTextSize(percentTextSize);

        //实心圆画笔
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.WHITE);

        //获取字体高度
        Paint.FontMetrics fm = mPercentPaint.getFontMetrics();
        mPercentTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int xCenter = getWidth() / 2;
        int yCenter = getHeight() / 2;

        RectF oval = new RectF();
        oval.left = (xCenter - mArcRadius);
        oval.top = (yCenter - mArcRadius);
        oval.right = mArcRadius * 2 + (xCenter - mArcRadius);
        oval.bottom = mArcRadius * 2 + (yCenter - mArcRadius);
        SweepGradient sweepGradient = new SweepGradient(xCenter, yCenter,mBgArcStartColor, mBgArcEndColor);
        Matrix gradientMatrix = new Matrix();
        gradientMatrix.preRotate(20, xCenter, yCenter);
        sweepGradient.setLocalMatrix(gradientMatrix);
        mBackgroundArcPaint.setShader(sweepGradient);
        //绘制背景圆弧
        canvas.drawArc(oval, -180, 180, false, mBackgroundArcPaint);
        //绘制实行圆
        float mShowDegree = mShowProgress *180;
        canvas.save();
        canvas.translate(xCenter, yCenter);
        canvas.rotate(mShowDegree);
        canvas.drawCircle(- mArcRadius, 0, mCircleRadius, mCirclePaint);
        canvas.rotate(-mShowDegree);
        canvas.restore();


        Rect usedRect = new Rect();
//        String usedStr = "成功率预估";
        mTipsPaint.getTextBounds(mTipsTxt, 0, mTipsTxt.length(), usedRect);
        int usedX = xCenter - usedRect.width() / 2;
        canvas.drawText(mTipsTxt, usedX, yCenter-mArcRadius*0.4f, mTipsPaint);

        //百分比数字
        String progressStr = format(mShowProgress * 100) +"%";
        float usedPercentWidth = mPercentPaint.measureText(progressStr, 0, progressStr.length());
        float upX = xCenter - usedPercentWidth/2;
        canvas.drawText(progressStr, upX, yCenter, mPercentPaint);
    }

    public String format(double value) {

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.toString();
    }

    public void setProgress(float progress) {
        mProgressBefore = mProgress;
        mProgress = progress / 100.00f;
        if(!mCircleHandler.isRunning) {
            mCircleHandler.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mCircleHandler != null && mCircleHandler.isRunning) {
            mCircleHandler.stop();
        }
    }

    private static class CircleHandler extends Handler{
        private final static int MSG_NEXT = 1;
        private final static int MSG_STOP = 2;
        private static final int DURATION_TIME = 60; // 36ms刷新一次
        private WeakReference<SemicircleView> mWeakReference;
        private boolean isRunning;
        private float mIndex = 0f;

        CircleHandler(SemicircleView semicircleView) {
            mWeakReference = new WeakReference<SemicircleView>(semicircleView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mWeakReference == null|| mWeakReference.get() == null) {
                return;
            }
            SemicircleView semicircleView = mWeakReference.get();

            if(msg.what == MSG_NEXT) {
                if(!isRunning) {
                    return;
                }
                if(mIndex > 10) {
                    stop();
                } else {
                    semicircleView.mShowProgress = mIndex / 10 * (semicircleView.mProgress - semicircleView.mProgressBefore) + semicircleView.mProgressBefore;
                    mIndex ++;
                    semicircleView.invalidate();
                    sendEmptyMessageDelayed(MSG_NEXT, DURATION_TIME);
                }
            }else if(msg.what == MSG_STOP) {
                removeMessages(MSG_NEXT);
                removeMessages(MSG_STOP);
            }
        }
        private void start() {
            isRunning = true;
            sendEmptyMessageDelayed(MSG_NEXT, DURATION_TIME);
        }

        private void stop() {
            mIndex = 0;
            isRunning = false;
            sendEmptyMessage(MSG_STOP);
        }
    };

}
