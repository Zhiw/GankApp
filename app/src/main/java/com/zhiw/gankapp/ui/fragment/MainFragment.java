package com.zhiw.gankapp.ui.fragment;


import com.zhiw.gankapp.R;
import com.zhiw.gankapp.adapter.GankAdapter;
import com.zhiw.gankapp.adapter.MeizhiAdapter;
import com.zhiw.gankapp.app.BaseTabFragment;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.model.Gank;
import com.zhiw.gankapp.presenter.MainFragmentPresenter;
import com.zhiw.gankapp.ui.view.MainFragmentView;
import com.zhiw.gankapp.ui.widget.MyRecyclerView;
import com.zhiw.gankapp.ui.widget.RecyclerViewDivider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends BaseTabFragment implements MainFragmentView, SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_PARAM = "title";

    @BindView(R.id.recycler_view) MyRecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;

    private String type;
    private MainFragmentPresenter mPresenter;

    private MeizhiAdapter meizhiAdapter;
    private GankAdapter mGankAdapter;

    private int mPage = 1;
    private boolean mIsRefresh;


    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String type) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_PARAM);
        }
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void setUpView() {
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(fragmentActivity,R.color.colorAccent));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        if (type.equals(Constants.TYPE_MEIZHI)) {
//            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//            mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragmentActivity);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            meizhiAdapter = new MeizhiAdapter(fragmentActivity);
            mRecyclerView.setAdapter(meizhiAdapter);
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragmentActivity, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.addItemDecoration(new RecyclerViewDivider(fragmentActivity));
            mGankAdapter = new GankAdapter(fragmentActivity, null);
            mRecyclerView.setAdapter(mGankAdapter);
        }

        mRecyclerView.setLoadMoreListener(this::getData);
    }

    @Override
    protected void setUpData() {
        mPresenter = new MainFragmentPresenter(fragmentActivity, this);
    }

    @Override
    protected void loadData() {
        mProgressBar.setVisibility(View.VISIBLE);
        getData();

    }




    @Override
    public void showProgress(boolean show) {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(show);
        }
    }

    @Override
    public void showListView(List<Gank> list) {
        mProgressBar.setVisibility(View.GONE);
        mPage++;
        if (mIsRefresh) {
            meizhiAdapter.refreshData(list);
            mIsRefresh = false;
        } else {
            meizhiAdapter.addData(list);
        }

    }

    @Override
    public void refreshGankList(List<Gank> list) {
        mProgressBar.setVisibility(View.GONE);
        mPage++;
        if (mIsRefresh) {
            mGankAdapter.refreshData(list);
            mIsRefresh = false;
        } else {
            mGankAdapter.addData(list);
        }
    }

    @Override
    public void onRefresh() {
        mIsRefresh = true;
        mPage = 1;
        getData();
    }

    private void getData() {
        if (type.equals(Constants.TYPE_MEIZHI)) {
            mPresenter.getMeizhi(mPage);
        } else {
            mPresenter.getGank(type, mPage);
        }
    }
}
