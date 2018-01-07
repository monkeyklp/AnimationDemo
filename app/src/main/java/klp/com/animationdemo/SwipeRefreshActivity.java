package klp.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import klp.com.animationdemo.dao.Cheeses;
import klp.com.animationdemo.view.CustomSwipeRefresh;


/**
 * Created by klp13115 on 2016/3/31.
 */
public class SwipeRefreshActivity extends ToolbarActivity {

    private RecyclerView mRecyclerView;
    private CustomSwipeRefresh mSwipeLayout;
    private TextView mEmptyView;


    private static final int LIST_ITEM_COUNT =5;
    private Adapter mListAdapter;

    public static void  startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, SwipeRefreshActivity.class);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mSwipeLayout = (CustomSwipeRefresh)findViewById(R.id.nested_content);
        mEmptyView = (TextView)findViewById(R.id.empty_view);
        initView();
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initiateRefresh();
            }
        });
    }

    private void initView(){
        mSwipeLayout.setColorSchemeColors(ContextCompat.getColor(SwipeRefreshActivity.this ,R.color.colorAccent),
                ContextCompat.getColor(SwipeRefreshActivity.this, R.color.colorPrimary),
                ContextCompat.getColor(SwipeRefreshActivity.this, R.color.colorPrimaryDark));
            mListAdapter = new Adapter();
//        mListAdapter = new ArrayAdapter<String>(
//                this,
//                android.R.layout.simple_list_item_1,
//                android.R.id.text1,
//                Cheeses.randomList(LIST_ITEM_COUNT));

        // Set the adapter between the ListView and its backing data.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SwipeRefreshActivity.this));
        mRecyclerView.setAdapter(mListAdapter);
    }

    private class Adapter extends RecyclerView.Adapter{

        private List<String> dataList = new ArrayList<>();

        public void clear() {
            dataList.clear();
        }

        public void add(String cheese) {
            dataList.add(cheese);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RefreshViewHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((RefreshViewHolder) holder).textView.setText(dataList.get(position));
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

    private class RefreshViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public RefreshViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }



    private void initiateRefresh() {

        /**
         * Execute the background task, which uses {@link android.os.AsyncTask} to load the data.
         */
        new DummyBackgroundTask().execute();
    }

    private void onRefreshComplete(List<String> result) {

//         Remove all items from the ListAdapter, and then replace them with the new items
        mListAdapter.clear();
        for (String cheese : result) {
            mListAdapter.add(cheese);
        }

        // Stop the refreshing indicator
        mListAdapter.notifyDataSetChanged();
        mSwipeLayout.setRefreshing(false);

        mEmptyView.setVisibility(View.VISIBLE);
        mSwipeLayout.setVisibility(View.GONE);

    }


    /**
     * Dummy {@link AsyncTask} which simulates a long running task to fetch new cheeses.
     */
    private class DummyBackgroundTask extends AsyncTask<Void, Void, List<String>> {

        static final int TASK_DURATION = 3 * 1000; // 3 seconds

        @Override
        protected List<String> doInBackground(Void... params) {
            // Sleep for a small amount of time to simulate a background-task
            try {
                Thread.sleep(TASK_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Return a new random list of cheeses
            return Cheeses.randomList(LIST_ITEM_COUNT);
        }

        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);

            // Tell the Fragment that the refresh has completed
            onRefreshComplete(result);
        }

    }
}
