package klp.chebada.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import klp.chebada.com.animationdemo.databinding.ActivityBottomNavigationBinding;

/**
 * Created by monkey on 17/1/10.
 */

public class BottomNavigationActivity extends AppCompatActivity {

    private ActivityBottomNavigationBinding mBinding;



    public static void startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, BottomNavigationActivity.class);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(BottomNavigationActivity.this, R.layout.activity_bottom_navigation);
    }
}
