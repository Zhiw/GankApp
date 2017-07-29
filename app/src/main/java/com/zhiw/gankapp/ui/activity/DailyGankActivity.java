package com.zhiw.gankapp.ui.activity;

import com.zhiw.gankapp.R;
import com.zhiw.gankapp.adapter.DailyGankAdapter;
import com.zhiw.gankapp.app.ToolBarActivity;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.model.GankDaily;
import com.zhiw.gankapp.presenter.DailyGankPresenter;
import com.zhiw.gankapp.ui.view.DailyGankView;
import com.zhiw.gankapp.utils.DateUtil;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;

public class DailyGankActivity extends ToolBarActivity implements DailyGankView {

    @BindView(R.id.daily_gank_recyclerview) RecyclerView mDailyGankRecyclerView;

    private DailyGankPresenter mPresenter;

    private DailyGankAdapter mAdapter;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_daily_gank;
    }

    @Override
    protected void setUpView() {
        mDailyGankRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DailyGankAdapter(this);
        mDailyGankRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setUpData() {
        mPresenter = new DailyGankPresenter(this, this);
        String publishedTime = getIntent().getStringExtra(Constants.DATE);
        setTitle(DateUtil.parseDate(publishedTime));
        int[] date = DateUtil.getDate(publishedTime);
        mPresenter.getDailyGank(date);
    }



    @Override
    public void refreshUI(GankDaily gankDaily) {
        mAdapter.setData(gankDaily);

    }
}
