package klp.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import klp.com.animationdemo.databinding.ActivityCheckboxBinding;


/**
 * Created by monkey on 17/1/11.
 */

public class CheckBoxActivity extends AppCompatActivity {

    private ActivityCheckboxBinding mBinding;

    public static void startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, CheckBoxActivity.class);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(CheckBoxActivity.this, R.layout.activity_checkbox);
        mBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.checkbox.setEnabled(mBinding.checkbox.isEnabled() ? false : true);
            }
        });

        mBinding.switchCompat.setTextOff("off");
        mBinding.switchCompat.setTextOn("on");
    }
}
