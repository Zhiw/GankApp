package com.zhiw.gankapp.adapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhiw.gankapp.R;
import com.zhiw.gankapp.model.Gank;
import com.zhiw.gankapp.utils.DateUtil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ClassName: DailyGankAdapter
 * Desc:
 * Created by zhiw on 16/7/22.
 */
public class DailyGankAdapter extends RecyclerView.Adapter<DailyGankAdapter.DailyGankViewHolder> {


    private List<Gank> mList;
    private Context context;

    public DailyGankAdapter(Context context) {
        mList = new ArrayList<>();
        this.context = context;
    }

    @Override
    public DailyGankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily, parent, false);
        return new DailyGankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyGankViewHolder holder, int position) {
        Gank gank = mList.get(position);
        Glide.with(context)
                .load(gank.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.mMeizhi);
        holder.mTvDate.setText(gank.getDesc());
        holder.mTvDate.setText(DateUtil.parseDate(gank.getPublishedAt()));
        holder.mTvDesc.setText(gank.getDesc());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void refreshData(List<Gank> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<Gank> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class DailyGankViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.meizhi)
        ImageView mMeizhi;
        @Bind(R.id.tv_date)
        TextView mTvDate;
        @Bind(R.id.tv_desc)
        TextView mTvDesc;

        DailyGankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
