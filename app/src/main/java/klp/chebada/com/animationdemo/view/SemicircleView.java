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

import klp.chebada.com.animationdemo.R;

/**
 * Created by klp13115 on 2017/7/25.
 */

public class SemicircleView extends View {

    //背景圆弧画笔
    private Paint mBackgroudArcPaint;
    //已使用圆弧画笔
    private Paint mUsedArcPaint;
    //已使用以及流量文字画笔
    private Paint mUsedTxtPaint;
    //已使用百分比文字画笔
    private Paint mUsedPercentTxtPaint;
    //百分号画笔
    private Paint mPercentTxtPaint;

    //最外层圆弧线颜色
    private int mLineArcColor;
    //背景圆环颜色
    private int mBackgroundArcColor;
    //已使用圆弧颜色
    private int mUsedArcColor;
    //圆弧半径
    private float mArcRadius;
    //外线大半径
    private float mLineRadius;
    //圆弧宽度
    private float mArcStrokeWidth;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    //已使用百分比
    private float mProgress=0;
    //动画展示弧度
    private float mShowProgress;
    private Context mContext;
    //已使用和总流量
    private String mUserdAndAll;
    private float mUsedTextSize;
    private float mUsedPercentTextSize;
    private float mPercentTextSize;
    private float mUsedPercentTxtHeight;
    //private CircleThread

    private Handler circleHandler = new Handler(){

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                float temp = (Float)msg.obj;
                setShowProgress(temp);
            }
        };
    };

    public SemicircleView(Context context) {
        this(context, null);
    }

    public SemicircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SemicircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        // 获取自定义的属性
        initAttrs(context, attrs);
        initVariable();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SemicircleView, 0, 0);
        mLineRadius = typeArray.getDimension(R.styleable.SemicircleView_radius, 120);
        mArcStrokeWidth = typeArray.getDimension(R.styleable.SemicircleView_strokeWidth, 12);
        mBackgroundArcColor = typeArray.getColor(R.styleable.SemicircleView_bgArcColor, 0xFFFFFFFF);
        mUsedArcColor = typeArray.getColor(R.styleable.SemicircleView_usedArcColor, 0xFFFF3D3B);
        mUsedTextSize = typeArray.getDimension(R.styleable.SemicircleView_usedTextSize, 14);
        mUsedPercentTextSize = typeArray.getDimension(R.styleable.SemicircleView_usedPercentTextSize, 52);
        mPercentTextSize = typeArray.getDimension(R.styleable.SemicircleView_percentTextSize, 16);
        typeArray.recycle();
    }


    private void initVariable() {
        //初始化一些值
        mLineRadius = mLineRadius + mArcStrokeWidth / 2;
        mArcRadius = mLineRadius-1.8f*mArcStrokeWidth;
        mLineArcColor = 0x33FFFFFF;
        mUserdAndAll = "0M/0M";

        //背景圆弧画笔设置
        mBackgroudArcPaint = new Paint();
        mBackgroudArcPaint.setAntiAlias(true);
//        mBackgroudArcPaint.setColor(mBackgroundArcColor);
        mBackgroudArcPaint.setStyle(Paint.Style.STROKE);
        mBackgroudArcPaint.setStrokeWidth(mArcStrokeWidth);
        mBackgroudArcPaint.setStrokeCap(Paint.Cap.ROUND);//开启显示边缘为圆形

        //已使用多少圆环画笔设置
        mUsedArcPaint = new Paint();
        mUsedArcPaint.setAntiAlias(true);
        mUsedArcPaint.setColor(mUsedArcColor);
        mUsedArcPaint.setStyle(Paint.Style.STROKE);
        mUsedArcPaint.setStrokeWidth(mArcStrokeWidth);
        mUsedArcPaint.setStrokeCap(Paint.Cap.ROUND);//开启显示边缘为圆形

        mUsedTxtPaint  = new Paint();
        mUsedTxtPaint.setAntiAlias(true);
        mUsedTxtPaint.setStyle(Paint.Style.FILL);
        mUsedTxtPaint.setColor(0x80FFFFFF);
        mUsedTxtPaint.setTextSize(mUsedTextSize);

        //百分比数字画笔
        mUsedPercentTxtPaint  = new Paint();
        mUsedPercentTxtPaint.setAntiAlias(true);
        mUsedPercentTxtPaint.setStyle(Paint.Style.FILL);
        mUsedPercentTxtPaint.setColor(0xFFFFFFFF);
        mUsedPercentTxtPaint.setTextSize(mUsedPercentTextSize);
        //百分号画笔
        mPercentTxtPaint  = new Paint();
        mPercentTxtPaint.setAntiAlias(true);
        mPercentTxtPaint.setStyle(Paint.Style.FILL);
        mPercentTxtPaint.setColor(0xFFFFFFFF);
        mPercentTxtPaint.setTextSize(mPercentTextSize);

        //获取字体高度
        Paint.FontMetrics fm = mUsedPercentTxtPaint.getFontMetrics();
        mUsedPercentTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;

        RectF oval = new RectF();
        oval.left = (mXCenter - mArcRadius);
        oval.top = (mYCenter - mArcRadius);
        oval.right = mArcRadius * 2 + (mXCenter - mArcRadius);
        oval.bottom = mArcRadius * 2 + (mYCenter - mArcRadius);
        SweepGradient sweepGradient = new SweepGradient(mXCenter, mYCenter, Color.RED, Color.TRANSPARENT);
        Matrix gradientMatrix = new Matrix();
        gradientMatrix.preRotate(20, mXCenter, mYCenter);
        sweepGradient.setLocalMatrix(gradientMatrix);
        mBackgroudArcPaint.setShader(sweepGradient);
        //绘制背景圆弧
        canvas.drawArc(oval, -180, 180, false, mBackgroudArcPaint);
        //绘制已使用圆弧
        float mShowDegree = mShowProgress/100*180;
//        canvas.drawArc(oval, -180, mShowDegree, false, mUsedArcPaint);

        //已使用文字
        Rect usedRect = new Rect();
        String usedStr = "已使用";
        mUsedTxtPaint.getTextBounds(usedStr, 0, usedStr.length(), usedRect);
        int usedX = mXCenter - usedRect.width() / 2;
        canvas.drawText(usedStr, usedX, mYCenter-mArcRadius*0.6f, mUsedTxtPaint);
        //已使用和总流量
        Rect ua_rect = new Rect();
        mUsedTxtPaint.getTextBounds(mUserdAndAll, 0, mUserdAndAll.length(), ua_rect);
        int uaX = mXCenter - ua_rect.width() / 2;
        canvas.drawText(mUserdAndAll, uaX, mYCenter+mArcRadius*0.6f, mUsedTxtPaint);

        //百分比数字
        String progressStr = (int)mShowProgress+"";
        String percentStr = "%";
        float usedPercentWidth = mUsedPercentTxtPaint.measureText(progressStr, 0, progressStr.length());
        float percentWidth = mPercentTxtPaint.measureText(percentStr, 0, percentStr.length());
        float upX = mXCenter-(usedPercentWidth + percentWidth)/2;
        canvas.drawText(progressStr, upX, mYCenter+mUsedPercentTxtHeight/3, mUsedPercentTxtPaint);
        float pX = upX + usedPercentWidth;
        canvas.drawText(percentStr, pX, mYCenter+mUsedPercentTxtHeight/3, mPercentTxtPaint);
    }

    private void setShowProgress(float progress){
        this.mShowProgress = progress;
        postInvalidate();
    }

    public void setProgress(float progress) {
        mProgress = progress;
        new Thread(new CircleThread()).start();
    }


    public void setUsedArcColor(int usedArcColor) {
        this.mUsedArcColor = usedArcColor;
        if(mUsedArcPaint!=null){
            mUsedArcPaint.setColor(mUsedArcColor);
        }
    }

    public void setUsedAndAll(String usedAndAll) {
        this.mUserdAndAll = usedAndAll;
    }



    private class CircleThread implements Runnable{

        int m=0;
        float i=0;

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while(!Thread.currentThread().isInterrupted()){
                try {
                    Thread.sleep(70);
                    m++;
                    Message msg = new Message();
                    msg.what = 1;
                    if(i < mProgress){
                        i += m;
                        msg.obj = i;
                        circleHandler.sendMessage(msg);
                    }else{
                        i = mProgress;
                        msg.obj = i;
                        circleHandler.sendMessage(msg);
                        return;
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

}
