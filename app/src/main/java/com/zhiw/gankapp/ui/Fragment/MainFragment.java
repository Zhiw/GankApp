package com.zhiw.gankapp.ui.Fragment;


import com.orhanobut.logger.Logger;
import com.zhiw.gankapp.R;
import com.zhiw.gankapp.adapter.GankAdapter;
import com.zhiw.gankapp.adapter.MeizhiAdapter;
import com.zhiw.gankapp.app.BaseFragment;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.model.Gank;
import com.zhiw.gankapp.presenter.MainFragmentPresenter;
import com.zhiw.gankapp.ui.View.MainFragmentView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends BaseFragment implements MainFragmentView {
    private static final String ARG_PARAM = "title";
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private String type;
    private MainFragmentPresenter mPresenter;

    private MeizhiAdapter meizhiAdapter;
    private GankAdapter gankAdapter;


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
            Logger.d(type + "+++++++++onCreate");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.d(type + "+++++++++++++onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MainFragmentPresenter(fragmentActivity, this);
        mPresenter.init();

    }

    @Override
    public void initView() {
        if (type.equals(Constants.TYPE_MEIZHI)) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
            meizhiAdapter = new MeizhiAdapter(fragmentActivity);
            mRecyclerView.setAdapter(meizhiAdapter);
            mPresenter.getMeizhi();
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragmentActivity, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            gankAdapter = new GankAdapter(fragmentActivity, null);
            mRecyclerView.setAdapter(gankAdapter);
            mPresenter.getGank(type);
        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showListView(List<Gank> list) {
        if (meizhiAdapter != null) {
            meizhiAdapter.refreshData(list);
        }

    }

    @Override
    public void refreshGankList(List<Gank> list) {
        gankAdapter.refreshData(list);
    }
}
