package klp.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import klp.com.animationdemo.view.CustomProgressView;


/**
 * Created by klp13115 on 2016/9/21.
 */

public class CustomProgressActivity extends AppCompatActivity {

    private CustomProgressView mProgressView;
    private SeekBar mSeekBar;

    public static void startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, CustomProgressActivity.class);
        fromActivity.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progress);
        mProgressView = (CustomProgressView)findViewById(R.id.progress);
//        mProgressView.setProgress(10);
        mSeekBar = (SeekBar)findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mProgressView.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBar.setProgress(10);
    }
}
