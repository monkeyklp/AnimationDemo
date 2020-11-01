package klp.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

import klp.com.animationdemo.databinding.ActivityBottomNavigationBinding;


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
        mBinding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.favorites:
                        mBinding.text.setText("favorites");
                        break;
                    case R.id.recents:
                        mBinding.text.setText("recents");
                        break;
                    case R.id.nearby:
                        mBinding.text.setText("nearby");
                        break;
                    case R.id.mine:
                        mBinding.text.setText("mine");
                        break;
                }
                return true;
            }
        });
    }
}
