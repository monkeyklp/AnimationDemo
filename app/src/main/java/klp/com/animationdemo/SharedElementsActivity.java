package klp.com.animationdemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;

/**
 * Created by klp13115 on 2016/3/11.
 */
public class SharedElementsActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Activity fromActivity){
        Intent intent = new Intent(fromActivity, SharedElementsActivity.class);
        Pair<View, String>[] pairs = new Pair[1];
//        pairs[0] = Pair.create(titleTv, fromActivity.getString(R.string.shared_elements));
//        Pair.create(titleTv, fromActivity.getString(R.string.shared_elements);
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(fromActivity);
        fromActivity.startActivity(intent, activityOptions.toBundle());
//        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedelement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpLayout();
    }

   @TargetApi(Build.VERSION_CODES.LOLLIPOP)
   private void setUpLayout(){
       // Transition for fragment1
       Slide slideTransition = new Slide(Gravity.LEFT);
       slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
       // Create fragment and define some of it transitions
       SharedElementFragment1 sharedElementFragment1 = SharedElementFragment1.newInstance();
       sharedElementFragment1.setReenterTransition(slideTransition);
       sharedElementFragment1.setExitTransition(slideTransition);
       sharedElementFragment1.setSharedElementEnterTransition(new ChangeBounds());

       getSupportFragmentManager().beginTransaction()
               .replace(R.id.sample2_content, sharedElementFragment1)
               .commit();
   }

}
