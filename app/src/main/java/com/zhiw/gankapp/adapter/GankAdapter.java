package com.zhiw.gankapp.adapter;

import com.zhiw.gankapp.R;
import com.zhiw.gankapp.model.Gank;
import com.zhiw.gankapp.utils.DateUtil;

import android.content.Context;
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
 * ClassName: GankAdapter
 * Desc:
 * Created by zhiw on 16/6/13.
 */
public class GankAdapter extends RecyclerView.Adapter<GankAdapter.GankViewHolder> {

    private List<Gank> mList;
    private Context mContext;

    public GankAdapter(Context context, List<Gank> list) {
        mContext = context;
        if (list == null) {
            list = new ArrayList<>();
        }
        mList = list;
    }

    @Override
    public GankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank, parent, false);
        return new GankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GankViewHolder holder, int position) {
        Gank gank=mList.get(position);
        holder.mTitle.setText(gank.getDesc());
        holder.mWho.setText(gank.getWho());
        holder.mDate.setText(DateUtil.parseDate(gank.getPublishedAt()));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void refreshData(List<Gank> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class GankViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView mTitle;
        @Bind(R.id.who)
        TextView mWho;
        @Bind(R.id.date)
        TextView mDate;

        public GankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
