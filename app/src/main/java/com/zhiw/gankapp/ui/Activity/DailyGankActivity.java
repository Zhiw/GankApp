package com.zhiw.gankapp.ui.Activity;

import com.zhiw.gankapp.R;
import com.zhiw.gankapp.adapter.DailyGankAdapter;
import com.zhiw.gankapp.app.ToolBarActivity;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.model.GankDaily;
import com.zhiw.gankapp.presenter.DailyGankPresenter;
import com.zhiw.gankapp.ui.View.DailyGankView;
import com.zhiw.gankapp.utils.DateUtil;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;

public class DailyGankActivity extends ToolBarActivity implements DailyGankView {

    @Bind(R.id.daily_gank_recyclerview)
    RecyclerView mDailyGankRecyclerview;

    private DailyGankPresenter mPresenter;

    private DailyGankAdapter mAdapter;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_daily_gank;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new DailyGankPresenter(this, this);
        mPresenter.init();

    }

    @Override
    public void initView() {
        String publishedTime = getIntent().getStringExtra(Constants.DATE);
        setTitle(DateUtil.parseDate(publishedTime));
        int[] date = DateUtil.getDate(publishedTime);
        mPresenter.getDailyGank(date);

        mDailyGankRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DailyGankAdapter(this);
        mDailyGankRecyclerview.setAdapter(mAdapter);


    }


    @Override
    public void refreshUI(GankDaily gankDaily) {
        mAdapter.setData(gankDaily);

    }
}
