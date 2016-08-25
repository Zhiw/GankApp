package com.zhiw.gankapp.ui.Fragment;


import com.zhiw.gankapp.R;
import com.zhiw.gankapp.adapter.DailyGankAdapter;
import com.zhiw.gankapp.app.BaseFragment;
import com.zhiw.gankapp.model.Gank;
import com.zhiw.gankapp.presenter.DailyListPresenter;
import com.zhiw.gankapp.ui.View.DailyListView;
import com.zhiw.gankapp.ui.widget.MyRecyclerView;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyFragment extends BaseFragment implements DailyListView {

    @Bind(R.id.recycler_view)
    MyRecyclerView mRecyclerView;

    private DailyListPresenter mPresenter;
    private DailyGankAdapter mAdapter;


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
        mAdapter = new DailyGankAdapter(fragmentActivity);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(fragmentActivity));
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.getData();

    }


    @Override
    public void refreshGanData(List<Gank> list) {
        mAdapter.refreshData(list);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
