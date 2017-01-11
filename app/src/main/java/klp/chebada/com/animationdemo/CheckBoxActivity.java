package klp.chebada.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import klp.chebada.com.animationdemo.databinding.ActivityCheckboxBinding;

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
    }
}
