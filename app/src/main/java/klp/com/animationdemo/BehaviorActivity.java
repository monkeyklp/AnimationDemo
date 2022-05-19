package klp.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import klp.com.animationdemo.adapter.ListAdapter;


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

        ArrayList<String> list = new ArrayList<String>();
        Collections.addAll(list, listItems);
        mRecyclerView.setAdapter(new ListAdapter(list, null));

    }
}
