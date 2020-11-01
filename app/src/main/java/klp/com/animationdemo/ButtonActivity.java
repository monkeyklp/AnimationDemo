package klp.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;

/**
 * Created by klp13115 on 2016/3/4.
 */
public class ButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        Button btn = (Button)findViewById(R.id.btn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public static void startActivity(Activity fromActivity){
        Intent intent = new Intent(fromActivity, ButtonActivity.class);
        fromActivity.startActivity(intent);
    }
}
