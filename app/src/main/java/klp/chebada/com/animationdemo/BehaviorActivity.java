package klp.chebada.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import klp.chebada.com.animationdemo.adapter.ListAdapter;

/**
 * Created by klp13115 on 2016/4/11.
 */
public class BehaviorActivity extends AppCompatActivity{
    private String mItemData = "Lorem Ipsum ";

    private RecyclerView mRecyclerView;

    public static void startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, BehaviorActivity.class);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_behavior);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        String[] listItems = mItemData.split(" ");

        List<String> list = new ArrayList<String>();
        Collections.addAll(list, listItems);
        mRecyclerView.setAdapter(new ListAdapter(list));

    }
}
