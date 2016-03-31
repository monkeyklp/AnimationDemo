package klp.chebada.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

/**
 * Created by klp13115 on 2016/3/31.
 */
public class SwipeRefreshActivity extends AppCompatActivity {

    private ListView mListView;
    private SwipeRefreshLayout mSwipeLayout;

    public static void  startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, SwipeRefreshActivity.class);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh);
        mListView = (ListView) findViewById(R.id.listview);
        mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipe);
    }

    private void initView(){
        mSwipeLayout.setColorSchemeColors();
    }
}
