package com.zhiw.gankapp.adapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhiw.gankapp.R;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.model.Gank;
import com.zhiw.gankapp.ui.Activity.MeizhiActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ClassName: MeizhiAdapter
 * Desc:
 * Created by zhiw on 16/6/12.
 */

public class MeizhiAdapter extends RecyclerView.Adapter<MeizhiAdapter.MeizhiViewHolder> {

    private List<Gank> mList;
    private Context context;

    public MeizhiAdapter(Context context) {
        mList = new ArrayList<>();
        this.context = context;
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
        Glide.with(context)
                .load(gank.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.mImageView);


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


    class MeizhiViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imageView)
        ImageView mImageView;

        View view;

        public MeizhiViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.imageView)
        public void onClick() {
            Intent intent = new Intent(context, MeizhiActivity.class);
            intent.putExtra(Constants.MEIZHI, ((Gank) view.getTag()).getUrl());
            context.startActivity(intent);
        }

    }
}
