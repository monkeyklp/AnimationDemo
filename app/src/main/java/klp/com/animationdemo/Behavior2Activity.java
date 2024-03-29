package klp.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import klp.com.animationdemo.adapter.ListAdapter;


/**
 * Created by klp13115 on 2016/4/11.
 */
public class Behavior2Activity extends AppCompatActivity{
    private String mItemData = "Lorem Ipsum is";
//            + "typesetting industry Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

    private RecyclerView mRecyclerView;

    public static void startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, Behavior2Activity.class);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior2);
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
