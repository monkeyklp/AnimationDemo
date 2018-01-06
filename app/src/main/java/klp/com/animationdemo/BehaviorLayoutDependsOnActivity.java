package klp.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by monkey on 17/1/13.
 */

public class BehaviorLayoutDependsOnActivity extends AppCompatActivity  {
    private int topTitleHeight;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, BehaviorLayoutDependsOnActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depends);
        findViewById(R.id.first).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        int[] locations = new int[2];
                        view.getLocationInWindow(locations);
                        topTitleHeight = locations[1];
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveViewByLayout(view, (int) motionEvent.getRawX(),
                                (int) motionEvent.getRawY());
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
    }
    private void moveViewByLayout(View view, int rawX, int rawY) {
        int left = 0 ;
        int top = rawY - topTitleHeight;
        int width = left + (int) (view.getWidth());
        int height = top + (int) (view.getHeight());
        view.layout(left, top, width, height);
    }

    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

}
