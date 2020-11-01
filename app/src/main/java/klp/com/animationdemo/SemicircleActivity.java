package klp.com.animationdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import klp.com.animationdemo.view.SemicircleView;

public class SemicircleActivity extends AppCompatActivity {
    private static final String TAG = "SemicircleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semicircle);
        final SemicircleView view =  (SemicircleView)findViewById(R.id.semicircle);
        view.setProgress(0f);
        Button btn = (Button)findViewById(R.id.change);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int progress = r.nextInt(100);
                view.setProgress(progress);
                Log.d(TAG, progress + "");
            }
        });
    }

    public static void startActivity(Context fromActivity) {
        Intent intent = new Intent(fromActivity, SemicircleActivity.class);
        fromActivity.startActivity(intent);
    }
}
