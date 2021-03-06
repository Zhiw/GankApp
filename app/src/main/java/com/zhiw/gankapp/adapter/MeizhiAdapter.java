package com.zhiw.gankapp.adapter;

import com.zhiw.gankapp.R;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.model.Gank;
import com.zhiw.gankapp.ui.activity.MeizhiActivity;
import com.zhiw.gankapp.utils.ImageLoader;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ClassName: MeizhiAdapter
 * Desc:
 * Created by zhiw on 16/6/12.
 */

public class MeizhiAdapter extends RecyclerView.Adapter<MeizhiAdapter.MeizhiViewHolder> {

    private List<Gank> mList;
    private Context mContext;

    public MeizhiAdapter(Context context) {
        mList = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public MeizhiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meizhi, parent, false);
        return new MeizhiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MeizhiViewHolder holder, int position) {
        Gank gank = mList.get(position);
        holder.view.setTag(gank);
        ImageLoader.load(mContext,gank.getUrl(),holder.imageView);


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


    class MeizhiViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView) ImageView imageView;

        View view;

        public MeizhiViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.imageView)
        public void onClick() {
            Intent intent = new Intent(mContext, MeizhiActivity.class);
            intent.putExtra(Constants.URL, ((Gank) view.getTag()).getUrl());
            intent.putExtra(Constants.DATE, ((Gank) view.getTag()).getPublishedAt());

            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation((Activity) mContext, imageView, mContext.getString(R.string.transition));
            ActivityCompat.startActivity(mContext, intent, optionsCompat.toBundle());
        }

    }
}
