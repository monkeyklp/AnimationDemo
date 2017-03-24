package klp.chebada.com.animationdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import klp.chebada.com.animationdemo.behavior.FooterBehaviorDependAppBar;

/**
 * Created by monkey on 17/3/15.
 */

public class ToolbarActivity extends AppCompatActivity {

    private ToolbarHelper mToolbarHelper;
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initToolbar();
    }


    private void initToolbar() {
        boolean needToolbar = hasWindowToolbar();
        if(!needToolbar) {
            return;
        }

        // !! below job is to replace preview activity's content
        // view with current activity's content view
        FrameLayout contentView = (FrameLayout) findViewById(Window.ID_ANDROID_CONTENT);
        View previewPageView;
        if(contentView.getChildCount() == 0) {
            previewPageView = new View(this);
        } else {
            // (1) first is to remove preview activity's content view from current content view
            previewPageView = contentView.getChildAt(0);
            contentView.removeView(previewPageView);
        }

        // (2) then add current activity's content view at the place
        // that preview activity's content view removed before
        mToolbarHelper = new ToolbarHelper(previewPageView);
        previewPageView = mToolbarHelper.getRootLayout();
        contentView.addView(previewPageView, 0);

        //init the Toolbar
        Toolbar toolbar = mToolbarHelper.getToolBar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });

        // setup debug dialog
    }


    protected boolean hasWindowToolbar() {
        TypedArray array = null;
        try {
            array = getTheme().obtainStyledAttributes(new int[]{
                    R.attr.windowToolbar,
            });
            return array.getBoolean(0, true);
        }finally {
            if (array != null) {
                array.recycle();
            }
        }
    }


    private class ToolbarHelper {
        private ViewGroup mRootLayout;
        private Toolbar mToolbar;
        private CoordinatorLayout mCoordinatorLayout;

        public ViewGroup getRootLayout() {
            return mRootLayout;
        }

        public Toolbar getToolBar() {
            return mToolbar;
        }

        /**
         *
         * @param contentView 子类里的布局
         */
        public ToolbarHelper(View contentView) {
            Context context = ToolbarActivity.this;
            mRootLayout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.layout_toolbar, null);
            mCoordinatorLayout = (CoordinatorLayout)mRootLayout.findViewById(R.id.coordinator_layout);
            mToolbar = (Toolbar) mRootLayout.findViewById(R.id.toolbar);
            mToolbar.setNavigationIcon(R.mipmap.arrow_navi_back);
            int toolbarBgColor = getToolbarBackgroundColor();
            mToolbar.setBackgroundColor(toolbarBgColor);

            if(needNestedScroll()){
                //toolBar设置
                AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
                params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                        | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS| AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
                mToolbar.setLayoutParams(params);
                //

                ViewGroup wrapLayout = (ViewGroup)contentView;
                int childCount = wrapLayout.getChildCount();
                if(childCount > 0) {
                    View nestedContentView = wrapLayout.findViewById(R.id.nested_content);
                    View footerView = wrapLayout.findViewById(R.id.nested_footer);
                    View otherView = wrapLayout.findViewById(R.id.nested_other);
                    View footerPinView = wrapLayout.findViewById(R.id.nested_footer_pin);

                    wrapLayout.removeAllViews();
                    if(null != otherView) {
                        AppBarLayout appBarLayout = (AppBarLayout)mRootLayout.findViewById(R.id.toolbar_layout);
                        appBarLayout.addView(otherView);
                    }
                    if(null != nestedContentView) {
                        CoordinatorLayout.LayoutParams nestedContentParams = new CoordinatorLayout.LayoutParams(nestedContentView.getLayoutParams().width, ViewGroup.LayoutParams.WRAP_CONTENT);
                        nestedContentParams.setBehavior(new AppBarLayout.ScrollingViewBehavior());
                        nestedContentView.setLayoutParams(nestedContentParams);
                        mCoordinatorLayout.addView(nestedContentView);
                    }
                    if(null != footerView) {
                        CoordinatorLayout.LayoutParams footerParams = new CoordinatorLayout.LayoutParams(footerView.getLayoutParams().width, context.getResources().getDimensionPixelSize(R.dimen.abc_action_bar_default_height_material));
                        footerParams.setBehavior(new FooterBehaviorDependAppBar());
                        footerParams.gravity = Gravity.BOTTOM;
                        footerView.setLayoutParams(footerParams);
                        mCoordinatorLayout.addView(footerView);
                    }
                    if(null != footerPinView) {
                        CoordinatorLayout.LayoutParams footerPinParams = new CoordinatorLayout.LayoutParams(footerPinView.getLayoutParams().width, footerPinView.getLayoutParams().height);
                        footerPinView.setLayoutParams(footerPinParams);
                        mRootLayout.addView(footerPinView);
                    }


                }
            } else { //不需要嵌套滑动
                mRootLayout.addView(contentView);
            }
        }

        private int getToolbarBackgroundColor(){
            TypedArray array = null;
            try {
                array = getTheme().obtainStyledAttributes(new int[]{
                        R.attr.toolbarBackgroundColor,
                });
                return array.getColor(0, ContextCompat.getColor(mRootLayout.getContext(), R.color.blue));
            }finally {
                if (array != null) {
                    array.recycle();
                }
            }
        }

        protected boolean needNestedScroll() {
            TypedArray array = null;
            try {
                array = getTheme().obtainStyledAttributes(new int[]{
                        R.attr.nestedScroll,
                });
                return array.getBoolean(0, false);
            }finally {
                if (array != null) {
                    array.recycle();
                }
            }
        }
    }
}
