package klp.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by monkey on 17/3/14.
 */

public class NestedScrollActivity extends ToolbarActivity {
    private static String TAG = "NestedScrollActivity";
    private NestedScrollView mNestedScrollView;
    private TextView mEmptyView;


    public static void startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, NestedScrollActivity.class);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested);
        mNestedScrollView = (NestedScrollView)findViewById(R.id.nested_content);
        mEmptyView = (TextView)findViewById(R.id.empty_view);
        mNestedScrollView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

}
