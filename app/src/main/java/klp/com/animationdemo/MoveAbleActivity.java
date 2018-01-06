package klp.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by monkey on 16/11/1.
 */

public class MoveAbleActivity extends AppCompatActivity {


    public static void startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, MoveAbleActivity.class);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_able_1);
    }
}
