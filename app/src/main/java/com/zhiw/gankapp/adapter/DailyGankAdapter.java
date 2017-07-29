package com.zhiw.gankapp.adapter;

import com.zhiw.gankapp.R;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.model.Gank;
import com.zhiw.gankapp.model.GankDaily;
import com.zhiw.gankapp.ui.activity.MeizhiActivity;
import com.zhiw.gankapp.ui.activity.WebViewActivity;
import com.zhiw.gankapp.utils.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
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
 * ClassName: DailyGankAdapter
 * Desc:
 * Created by zhiw on 16/8/26.
 */

public class DailyGankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Gank> mGankList;
    private Context mContext;

    private static final int TYPE_MEIZHI = 1;
    private static final int TYPE_GANK = 2;

    public DailyGankAdapter(Context context) {
        this.mContext = context;
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
            MeizhiViewHolder meizhiViewHolder = (MeizhiViewHolder) holder;
            meizhiViewHolder.titleText.setText(gank.getType());

            ImageLoader.load(mContext, gank.getUrl(), ((MeizhiViewHolder) holder).imageView);

            meizhiViewHolder.imageView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, MeizhiActivity.class);
                intent.putExtra(Constants.URL, gank.getUrl());
                intent.putExtra(Constants.DATE, gank.getPublishedAt());

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation((Activity) mContext, v, mContext.getString(R.string.transition));
                ActivityCompat.startActivity(mContext, intent, optionsCompat.toBundle());
            });

        } else {
            GankViewHolder gankViewHolder = (GankViewHolder) holder;
            boolean isTheSameCategory = mGankList.get(position - 1).getType().equals(mGankList.get(position).getType());
            if (isTheSameCategory) {
                gankViewHolder.categoryText.setVisibility(View.GONE);
            } else {
                gankViewHolder.categoryText.setVisibility(View.VISIBLE);

            }
            gankViewHolder.categoryText.setText(gank.getType());

            if (gank.getImages().size() > 0) {
                gankViewHolder.image.setVisibility(View.VISIBLE);
                ImageLoader.load(mContext, gank.getImages().get(0), gankViewHolder.image);
            } else {
                gankViewHolder.image.setVisibility(View.GONE);
            }

            String who = "(" + gank.getWho() + ")";
            SpannableString spannableString = new SpannableString(who);
            spannableString.setSpan(new TextAppearanceSpan(mContext, R.style.ViaTextAppearance), 0, who.length(), 0);
            SpannableStringBuilder builder = new SpannableStringBuilder(gank.getDesc()).append(spannableString);
            gankViewHolder.titleText.setText(builder.subSequence(0, builder.length()));

            gankViewHolder.titleText.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(Constants.URL, gank.getUrl());
                intent.putExtra(Constants.DES, gank.getDesc());
                mContext.startActivity(intent);
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
        @BindView(R.id.meizhi)
        ImageView imageView;
        @BindView(R.id.gank_title)
        TextView titleText;

        public MeizhiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class GankViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_category) TextView categoryText;
        @BindView(R.id.tv_title) TextView titleText;
        @BindView(R.id.image) ImageView image;

        public GankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
