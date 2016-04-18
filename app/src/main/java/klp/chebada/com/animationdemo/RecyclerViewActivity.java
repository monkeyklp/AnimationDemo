package klp.chebada.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by klp13115 on 2016/4/18.
 */
public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    public static void startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, RecyclerViewActivity.class);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setAdapter();
    }

    class NormalAdapter extends RecyclerView.Adapter<NormalHolder>{

        @Override
        public NormalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(NormalHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    class NormalHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public NormalHolder(View itemView) {
            super(itemView);
            mTextView =  (TextView) itemView.findViewById(R.id.listitem_name);
        }
    }
}
