package klp.chebada.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import klp.chebada.com.animationdemo.view.MoveBall2;

/**
 * Created by monkey on 16/11/1.
 */

public class MoveAbleActivity2 extends AppCompatActivity {

    public static void startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, MoveAbleActivity2.class);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_able_2);

        MoveBall2 moveBall2 = new MoveBall2(MoveAbleActivity2.this);
    }
}
