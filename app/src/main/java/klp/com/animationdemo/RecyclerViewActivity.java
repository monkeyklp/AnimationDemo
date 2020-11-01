package klp.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import klp.com.animationdemo.decoration.DividerItemDecoration;


/**
 * Created by klp13115 on 2016/4/18.
 */
public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private NormalAdapter mAdapter;

    private String mItemData = "Lorem Ipsum is simply dummy text of the printing and "
            + "typesetting industry Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

    public static void startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, RecyclerViewActivity.class);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        String[] listItems = mItemData.split(" ");

        List<String> list = new ArrayList<String>();
        Collections.addAll(list, listItems);

        mAdapter = new NormalAdapter(list);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration());
        mRecyclerView.setAdapter(mAdapter);
    }

    class NormalAdapter extends RecyclerView.Adapter<NormalHolder>{

        List<String> mListData;

        public NormalAdapter(List<String> mListData) {
            this.mListData = mListData;
        }
        @Override
        public NormalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NormalHolder(LayoutInflater.from(RecyclerViewActivity.this).inflate(R.layout.item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(NormalHolder holder, int position) {
            holder.mTextView.setText(mListData.get(position));
        }

        @Override
        public int getItemCount() {
            return mListData == null ? 0 : mListData.size();
        }
    }

    class NormalHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public NormalHolder(View itemView) {
            super(itemView);
            mTextView =  (TextView) itemView.findViewById(R.id.text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(RecyclerViewActivity.this, "click", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
