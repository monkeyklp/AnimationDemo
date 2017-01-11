package klp.chebada.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import klp.chebada.com.animationdemo.databinding.ActivityDialogBinding;

/**
 * Created by monkey on 17/1/10.
 */

public class AppcompatDialogActivity extends AppCompatActivity {
    private ActivityDialogBinding mBinding;

    public static void startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, AppcompatDialogActivity.class);
        fromActivity.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(AppcompatDialogActivity.this, R.layout.activity_dialog);
    }
}
