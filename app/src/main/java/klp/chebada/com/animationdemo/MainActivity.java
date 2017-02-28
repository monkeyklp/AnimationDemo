package klp.chebada.com.animationdemo;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ImageView mTv;
    private ListView mListView;
    private SimpleAdapter mAdapter;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        List<Map<String, String>> actions = new ArrayList<Map<String, String>>();
        String items[] = new String[]{
                "普通view的水波纹效果",
                "button的水波纹效果",
                "揭露效果",
                "Shared Elements",
                "SwipeRefreshActivity",
                "CoordinatorLayout & Toolbar Simple",
                "自定义 Behavior 嵌套滚动",
                "自定义 Behavior2 View之间的依赖",
                "RecyclerView",
                "Notification",
                "Handler(消息机制)",
                "自定义的进度条",
                "可移动view（方案一）",
                "可移动view（方案二）",
                "Bottom navigation",
                "appcompat dialog",
                "checkBox",
                "behavior dependsOn",
                "Rxjava"
        };

        for (int i = 0; i < items.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("text", items[i]);
            actions.add(map);
        }
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new SimpleAdapter(this, actions, R.layout.item_layout, new String[]{"text"}, new int[]{R.id.text});
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    ButtonActivity.startActivity(MainActivity.this);
                }
                if(position == 2) {//揭露效果
                    AnimationViewActivity.startActivity(MainActivity.this);
                }
                if(position == 3) { //share
                    SharedElementsActivity.startActivity(MainActivity.this);
                }
                if(position == 4) { //SwipeRefreshLayout
                    SwipeRefreshActivity.startActivity(MainActivity.this);
                }
                if(position == 5) {//CoordinatorLayout & Toolbar
                    CoordinatorSimpleActivity.startActivity(MainActivity.this);
                }
                if(position == 6) {//自定义Behavior
                    BehaviorActivity.startActivity(MainActivity.this);
                }
                if(position == 7) {//自定义Behavior2
                    Behavior2Activity.startActivity(MainActivity.this);
                }
                if(position == 8) { //RecyclerView
                    RecyclerViewActivity.startActivity(MainActivity.this);
                }
                if(position == 9) {//Notification
                    NotificationActivity.startActivity(MainActivity.this);
                }
                if(position == 10) { //Handler(消息机制)
                    MessageActivity.startActivity(MainActivity.this);
                }
                if(position == 11) { //自定义进度条
                    CustomProgressActivity.startActivity(MainActivity.this);
                }
                if(position == 12) { //可移动view实现方案一
                    MoveAbleActivity.startActivity(MainActivity.this);
                }
                if(position == 13) { //可移动view实现方案二
                    MoveAbleActivity2.startActivity(MainActivity.this);
                }
                if(position == 14) { //bottom_navigation
                    BottomNavigationActivity.startActivity(MainActivity.this);
                }
                if(position == 15) {//appcompat dialog
                    AppcompatDialogActivity.startActivity(MainActivity.this);
                }
                if(position == 16) {// checkbox
                    CheckBoxActivity.startActivity(MainActivity.this);
                }
                if(position == 17) { //behavior layoutDependsOn
                    BehaviorLayoutDependsOnActivity.startActivity(MainActivity.this);
                }
                if(position == 18) { //Rxjava demo
                    RxActivity.startActivity(MainActivity.this);
                }
            }
        });

//        Explode explode = new Explode();
//        explode.setDuration(500);
//        // 进入时的跳转动画
//        getWindow().setEnterTransition(explode);
//        // 返回时的跳转动画
//        getWindow().setReturnTransition(explode);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
