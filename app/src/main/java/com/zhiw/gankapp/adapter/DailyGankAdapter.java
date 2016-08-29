package com.zhiw.gankapp.adapter;

import com.bumptech.glide.Glide;
import com.zhiw.gankapp.R;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.model.Gank;
import com.zhiw.gankapp.model.GankDaily;
import com.zhiw.gankapp.ui.Activity.MeizhiActivity;
import com.zhiw.gankapp.ui.Activity.WebViewActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ClassName: DailyGankAdapter
 * Desc:
 * Created by zhiw on 16/8/26.
 */

public class DailyGankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Gank> mGankList;
    private Context context;

    private static final int TYPE_MEIZHI = 1;
    private static final int TYPE_GANK = 2;

    public DailyGankAdapter(Context context) {
        this.context = context;
        mGankList = new ArrayList<>();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_MEIZHI) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_gank_meizhi, parent, false);
            return new MeizhiViewHolder(view);
        } else if (viewType == TYPE_GANK) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_gank, parent, false);
            return new GankViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Gank gank = mGankList.get(position);
        if (position == 0) {
            ((MeizhiViewHolder) holder).mTitle.setText(gank.getType());
            Glide.with(context)
                    .load(gank.getUrl())
                    .into(((MeizhiViewHolder) holder).mImageView);

            ((MeizhiViewHolder) holder).mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MeizhiActivity.class);
                    intent.putExtra(Constants.URL, gank.getUrl());
                    intent.putExtra(Constants.DATE, gank.getPublishedAt());

                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                            .makeSceneTransitionAnimation((Activity) context, v, context.getString(R.string.transition));
                    ActivityCompat.startActivity((Activity) context, intent, optionsCompat.toBundle());
                }
            });

        } else {
            boolean isTheSameCategory = mGankList.get(position - 1).getType().equals(mGankList.get(position).getType());
            if (isTheSameCategory) {
                ((GankViewHolder) holder).mTvCategory.setVisibility(View.GONE);
            } else {
                ((GankViewHolder) holder).mTvCategory.setVisibility(View.VISIBLE);

            }

            ((GankViewHolder) holder).mTvCategory.setText(gank.getType());
            ((GankViewHolder) holder).mTvTitle.setText(gank.getDesc());

            ((GankViewHolder) holder).mTvTitle.setOnClickListener(v -> {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(Constants.URL, gank.getUrl());
                intent.putExtra(Constants.DES, gank.getDesc());
                context.startActivity(intent);
            });

        }


    }

    @Override
    public int getItemCount() {
        return mGankList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_MEIZHI : TYPE_GANK;
    }

    public void setData(GankDaily gankDaily) {
        mGankList = new ArrayList<>();
        if (gankDaily.get福利() != null) {
            mGankList.addAll(gankDaily.get福利());
        }
        if (gankDaily.getAndroid() != null) {
            mGankList.addAll(gankDaily.getAndroid());
        }
        if (gankDaily.getIOS() != null) {
            mGankList.addAll(gankDaily.getIOS());
        }
        if (gankDaily.get拓展资源() != null) {
            mGankList.addAll(gankDaily.get拓展资源());
        }
        if (gankDaily.get瞎推荐() != null) {
            mGankList.addAll(gankDaily.get瞎推荐());
        }
        if (gankDaily.get休息视频() != null) {
            mGankList.addAll(gankDaily.get休息视频());
        }

        notifyDataSetChanged();

    }

    class MeizhiViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.meizhi)
        ImageView mImageView;
        @Bind(R.id.gank_title)
        TextView mTitle;

        public MeizhiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class GankViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_category)
        TextView mTvCategory;
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.ll_gank_parent)
        LinearLayout mLlGankParent;

        public GankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}