package klp.com.animationdemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import klp.com.animationdemo.databinding.ActivityDialogBinding;


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
        mBinding.buttonNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLocationDialog();
            }
        });
        mBinding.buttonSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmationDialog();
            }
        });
    }


    private void showLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AppcompatDialogActivity.this, R.style.MyDialogTheme);
        builder.setTitle(getString(R.string.dialog_title));
        builder.setMessage(getString(R.string.dialog_message));

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    private void confirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AppcompatDialogActivity.this, R.style.MyDialogTheme2);
        String[] items = getResources().getStringArray(R.array.ringtone_list);
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
//        builder.setMultiChoiceItems()
        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

}
