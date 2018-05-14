package com.jdy.supa.module.search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jdy.base.BaseActivity;
import com.jdy.supa.R;

public class SearchActivity extends BaseActivity {
    private RecyclerView mRecyclerView;

    @Override
    public int getRootLayoutID() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        mRecyclerView = getView(R.id.search_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        mRecyclerView.setAdapter(new SearchListAdapter());
    }

    @Override
    public void setListener() {
        setOnClickListener(R.id.search_head_image);
        setOnClickListener(R.id.search_head_edit);
        setOnClickListener(R.id.search_head_map);
        setOnClickListener(R.id.search_condition_first);
        setOnClickListener(R.id.search_condition_second);
        setOnClickListener(R.id.search_condition_third);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_head_image:
                finish();
                break;
            case R.id.search_head_edit:
                finish();
                break;
            case R.id.search_head_map:
                finish();
                break;
            case R.id.search_condition_first:
                finish();
                break;
            case R.id.search_condition_second:
                finish();
                break;
            case R.id.search_condition_third:
                finish();
                break;
        }
    }

    private class SearchListAdapter extends Adapter {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
