package klp.chebada.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by monkey on 17/3/14.
 */

public class NestedScrollActivity extends AppCompatActivity {

    public static void startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, NestedScrollActivity.class);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested);
    }
}
