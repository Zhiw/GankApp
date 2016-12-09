package com.zhiw.gankapp.adapter;

import com.zhiw.gankapp.R;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.model.SearchResult;
import com.zhiw.gankapp.ui.activity.WebViewActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ClassName: SearchResultAdapter
 * Desc:
 * Created by zhiw on 16/12/8.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ResultViewHolder> {

    private List<SearchResult> mSearchResultList;
    private Context mContext;

    public SearchResultAdapter(Context context) {
        mSearchResultList = new ArrayList<>();
        mContext = context;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        SearchResult searchResult = mSearchResultList.get(position);
        holder.mTitleText.setText(searchResult.getDesc());
        holder.mTitleText.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(Constants.URL, searchResult.getUrl());
            intent.putExtra(Constants.DES, searchResult.getDesc());
            mContext.startActivity(intent);

        });


    }

    @Override
    public int getItemCount() {
        return mSearchResultList.size();
    }

    public void refreshData(List<SearchResult> list) {
        mSearchResultList.clear();
        mSearchResultList.addAll(list);
        notifyDataSetChanged();
    }

    class ResultViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView mTitleText;

        public ResultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
