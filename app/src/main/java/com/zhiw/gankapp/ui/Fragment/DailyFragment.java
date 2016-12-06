package com.zhiw.gankapp.ui.fragment;


import com.zhiw.gankapp.R;
import com.zhiw.gankapp.adapter.DailyGankListAdapter;
import com.zhiw.gankapp.app.BaseFragment;
import com.zhiw.gankapp.model.Gank;
import com.zhiw.gankapp.presenter.DailyListPresenter;
import com.zhiw.gankapp.ui.view.DailyListView;
import com.zhiw.gankapp.ui.widget.MyRecyclerView;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyFragment extends BaseFragment implements DailyListView {

    @Bind(R.id.recycler_view) MyRecyclerView mRecyclerView;
    @Bind(R.id.progress_bar) ProgressBar mProgressBar;

    private DailyListPresenter mPresenter;
    private DailyGankListAdapter mAdapter;


    public DailyFragment() {
        // Required empty public constructor
    }


    public static DailyFragment newInstance() {
        return new DailyFragment();
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_daily;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new DailyListPresenter(fragmentActivity, this);
        mPresenter.init();

    }

    @Override
    public void initView() {
        mAdapter = new DailyGankListAdapter(fragmentActivity);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(fragmentActivity));
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.getData();

    }


    @Override
    public void refreshGanData(List<Gank> list) {
        mAdapter.refreshData(list);

    }

    @Override
    public void showProgress(boolean isShow) {
        if (isShow) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }



}
