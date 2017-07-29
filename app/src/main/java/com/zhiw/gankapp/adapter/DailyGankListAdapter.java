package com.zhiw.gankapp.adapter;

import com.zhiw.gankapp.R;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.model.Gank;
import com.zhiw.gankapp.ui.activity.DailyGankActivity;
import com.zhiw.gankapp.ui.activity.MeizhiActivity;
import com.zhiw.gankapp.utils.DateUtil;
import com.zhiw.gankapp.utils.ImageLoader;

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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ClassName: DailyGankListAdapter
 * Desc:
 * Created by zhiw on 16/7/22.
 */
public class DailyGankListAdapter extends RecyclerView.Adapter<DailyGankListAdapter.DailyGankViewHolder> {


    private List<Gank> mList;
    private Context mContext;

    public DailyGankListAdapter(Context context) {
        mList = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public DailyGankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily, parent, false);
        return new DailyGankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyGankViewHolder holder, int position) {
        Gank gank = mList.get(position);
        ImageLoader.load(mContext,gank.getUrl(),holder.meizhi);

        holder.descText.setText(DateUtil.parseDate(gank.getPublishedAt())+"\n"+gank.getDesc());

        holder.meizhi.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MeizhiActivity.class);
            intent.putExtra(Constants.URL, gank.getUrl());
            intent.putExtra(Constants.DATE, gank.getPublishedAt());

            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation((Activity) mContext, v, mContext.getString(R.string.transition));
            ActivityCompat.startActivity(mContext, intent, optionsCompat.toBundle());
        });

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DailyGankActivity.class);
            intent.putExtra(Constants.DATE,gank.getPublishedAt());
            mContext.startActivity(intent);

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
        @BindView(R.id.card_view) CardView cardView;
        @BindView(R.id.meizhi) ImageView meizhi;
        @BindView(R.id.tv_desc) TextView descText;

        DailyGankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
