package klp.chebada.com.animationdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import static klp.chebada.com.animationdemo.R.id.toolbar;

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

        public ViewGroup getRootLayout() {
            return mRootLayout;
        }

        public Toolbar getToolBar() {
            return mToolbar;
        }

        public ToolbarHelper(View contentView) {
            Context context = ToolbarActivity.this;
            mRootLayout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.layout_toolbar, null);
            mToolbar = (Toolbar) mRootLayout.findViewById(toolbar);
            mToolbar.setNavigationIcon(R.mipmap.arrow_navi_back);
            int toolbarBgColor = getToolbarBackgroundColor();
            mToolbar.setBackgroundColor(toolbarBgColor);

            if(needNestedScroll()){
                AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
                params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                        | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
                mRootLayout.addView(contentView);
            } else { //不需要嵌套滑动
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) contentView.getLayoutParams();
                params.topMargin = context.getResources().getDimensionPixelSize(R.dimen.abc_action_bar_default_height_material);
                mRootLayout.addView(contentView, params);
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
