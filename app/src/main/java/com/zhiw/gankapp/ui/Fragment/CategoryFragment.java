package com.zhiw.gankapp.ui.Fragment;


import com.zhiw.gankapp.R;
import com.zhiw.gankapp.adapter.ViewPagerAdapter;
import com.zhiw.gankapp.app.BaseFragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseFragment {


    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    private String[] titles = {"福利", "Android", "iOS", "前端", "休息视频"};


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initPresenter() {
        init();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void init(){
        List<Fragment> fragmentList = new ArrayList<>();
        for (String title : titles) {
            fragmentList.add(MainFragment.newInstance(title));
        }

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(fragmentActivity.getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);

        mTabLayout.setupWithViewPager(mViewPager);
    }
}
