package klp.com.animationdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;

/**
 * Created by klp13115 on 2016/3/4.
 */
public class AnimationViewActivity extends AppCompatActivity {

    private Button mButton;
    private View mView;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mButton = (Button) findViewById(R.id.btn_show_and_gone);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mView.isShown()) {
                    animationGone();
                } else {
                    animationShow();
                }
            }
        });
        mView = (View) findViewById(R.id.view_animation);
        Fade explode = new Fade ();
        explode.setDuration(500);
        // 进入时的跳转动画
        getWindow().setEnterTransition(explode);
        // 返回时的跳转动画
        getWindow().setReturnTransition(explode);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, AnimationViewActivity.class);
        fromActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(fromActivity).toBundle());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animationShow() {
        int cx = (mView.getLeft() + mView.getRight()) / 2;
        int cy = (mView.getTop() + mView.getBottom()) / 2;

        int finalRadius = Math.max(mView.getWidth(), mView.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(mView, 0, 0, 0, finalRadius);

        mView.setVisibility(View.VISIBLE);
        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animationGone() {
        int cx = (mView.getLeft() + mView.getRight()) / 2;
        int cy = (mView.getTop() + mView.getBottom()) / 2;

        int initialRadius = mView.getWidth();

        Animator anim = ViewAnimationUtils.createCircularReveal(mView, 0, 0, initialRadius, 0);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mView.setVisibility(View.GONE);
            }
        });

        anim.start();
    }
}
