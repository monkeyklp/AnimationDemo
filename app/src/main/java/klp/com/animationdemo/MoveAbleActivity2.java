package klp.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import klp.com.animationdemo.view.MoveBall2;


/**
 * Created by monkey on 16/11/1.
 */

public class MoveAbleActivity2 extends AppCompatActivity {
    private MoveBall2 moveBall2;

    public static void startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, MoveAbleActivity2.class);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_able_2);

        moveBall2 = new MoveBall2(MoveAbleActivity2.this);
        moveBall2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MoveAbleActivity2.this, "moveBall2 click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(moveBall2 != null) {
            moveBall2.destroy();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
