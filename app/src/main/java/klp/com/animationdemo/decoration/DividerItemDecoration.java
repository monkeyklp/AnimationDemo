package klp.com.animationdemo.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import klp.com.animationdemo.R;


/**
 * Created by fhf11991 on 2016/1/25.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    public enum Orientation {
        HORIZONTAL,
        VERTICAL,
        GRID
    }

    /*
    * RecyclerView的布局方向，默认先赋值
    * 为纵向布局
    * RecyclerView 布局可横向，也可纵向
    * 横向和纵向对应的分割想画法不一样
    * */
    private Orientation mOrientation = Orientation.VERTICAL;

    /**
     * item之间分割线的size，默认为1
     */
    private int mDividerHeight = -1;

    private int mDividerColor = android.R.color.transparent;

    /**
     * 绘制item分割线的画笔，和设置其属性
     * 来绘制个性分割线
     */
    private Paint mPaint;

    public DividerItemDecoration() {
        this(Orientation.VERTICAL);
    }

    /**
     * 构造方法传入布局方向，不可不传
     *
     * @param orientation
     */
    public DividerItemDecoration(Orientation orientation) {
        setOrientation(orientation);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        setItemColor(mDividerColor);
    }

    public DividerItemDecoration setOrientation(Orientation orientation) {
        mOrientation = orientation;
        return this;
    }

    public DividerItemDecoration setDividerHeight(int dividerHeight) {
        mDividerHeight = dividerHeight;
        return this;
    }

    public DividerItemDecoration setItemColor(int color) {
        mDividerColor = color;
        mPaint.setColor(mDividerColor);
        return this;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == Orientation.VERTICAL) {
            drawVertical(c, parent);
        } else if (mOrientation == Orientation.HORIZONTAL){
            drawHorizontal(c, parent);
        } else if (mOrientation == Orientation.GRID) {
            drawVertical(c, parent);
            drawHorizontal(c, parent);
        }
    }

    /**
     * 绘制纵向 item 分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + getDividerHeight(parent.getContext());
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    /**
     * 绘制横向 item 分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + getDividerHeight(parent.getContext());
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    /**
     * 设置item分割线的size
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int dividerHeight = getDividerHeight(parent.getContext());
        if (mOrientation == Orientation.VERTICAL) {
            outRect.set(0, 0, 0, dividerHeight);
        } else if (mOrientation == Orientation.HORIZONTAL){
            outRect.set(dividerHeight, 0, dividerHeight, 0);
        } else if (mOrientation == Orientation.GRID) {
            outRect.set(dividerHeight, dividerHeight, dividerHeight, dividerHeight);
        }
    }

    private int getDividerHeight(Context context){
        if (mDividerHeight == -1) {
            return context.getResources().getDimensionPixelSize(R.dimen.line_height);
        } else {
            return mDividerHeight;
        }
    }
}
