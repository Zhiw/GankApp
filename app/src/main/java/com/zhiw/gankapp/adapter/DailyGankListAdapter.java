package com.zhiw.gankapp.adapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhiw.gankapp.R;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.model.Gank;
import com.zhiw.gankapp.ui.activity.DailyGankActivity;
import com.zhiw.gankapp.ui.activity.MeizhiActivity;
import com.zhiw.gankapp.utils.DateUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
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
 * ClassName: DailyGankListAdapter
 * Desc:
 * Created by zhiw on 16/7/22.
 */
public class DailyGankListAdapter extends RecyclerView.Adapter<DailyGankListAdapter.DailyGankViewHolder> {


    private List<Gank> mList;
    private Context context;

    public DailyGankListAdapter(Context context) {
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
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mMeizhi);
        holder.mDateText.setText(gank.getDesc());
        holder.mDateText.setText(DateUtil.parseDate(gank.getPublishedAt()));
        holder.mDescText.setText(gank.getDesc());

        holder.mMeizhi.setOnClickListener(v -> {
            Intent intent = new Intent(context, MeizhiActivity.class);
            intent.putExtra(Constants.URL, gank.getUrl());
            intent.putExtra(Constants.DATE, gank.getPublishedAt());

            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation((Activity) context, v, context.getString(R.string.transition));
            ActivityCompat.startActivity(context, intent, optionsCompat.toBundle());
        });

        holder.mCardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DailyGankActivity.class);
            intent.putExtra(Constants.DATE,gank.getPublishedAt());
            context.startActivity(intent);

        });


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
        @Bind(R.id.card_view) CardView mCardView;
        @Bind(R.id.meizhi) ImageView mMeizhi;
        @Bind(R.id.tv_date) TextView mDateText;
        @Bind(R.id.tv_desc) TextView mDescText;

        DailyGankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
