package klp.chebada.com.animationdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import klp.chebada.com.animationdemo.view.SemicircleView;

public class SemicircleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semicircle);
        SemicircleView view =  (SemicircleView)findViewById(R.id.semicircle);
        view.setUsedAndAll("170M/200M");
        view.setProgress(81);
    }

    public static void startActivity(Context fromActivity) {
        Intent intent = new Intent(fromActivity, SemicircleActivity.class);
        fromActivity.startActivity(intent);
    }
}
