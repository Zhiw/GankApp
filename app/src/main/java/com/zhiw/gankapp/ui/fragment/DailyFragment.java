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

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyFragment extends BaseFragment implements DailyListView {

    @BindView(R.id.recycler_view) MyRecyclerView mRecyclerView;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;

    private DailyListPresenter mPresenter;
    private DailyGankListAdapter mAdapter;

    private int mPage = 1;


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
    protected void setUpView() {
        mAdapter = new DailyGankListAdapter(fragmentActivity);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(fragmentActivity));
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLoadMoreListener(() -> mPresenter.getData(mPage));
    }

    @Override
    protected void setUpData() {
        mPresenter = new DailyListPresenter(fragmentActivity, this);
        mPresenter.getData(mPage);
    }


    @Override
    public void refreshGanData(List<Gank> list) {
        if (mPage == 1) {
            mAdapter.refreshData(list);
        } else {
            mAdapter.addData(list);
        }

        mPage++;

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
