package com.zhiw.gankapp.ui.fragment;


import com.zhiw.gankapp.R;
import com.zhiw.gankapp.adapter.ViewPagerAdapter;
import com.zhiw.gankapp.app.BaseFragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseFragment {


    @Bind(R.id.tab_layout) TabLayout mTabLayout;
    @Bind(R.id.view_pager) ViewPager mViewPager;


    private String[] titles = {"福利", "Android", "iOS", "前端", "休息视频"};


    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void setUpView() {
        List<Fragment> fragmentList = new ArrayList<>();
        for (String title : titles) {
            fragmentList.add(MainFragment.newInstance(title));
        }

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragmentList);
        mViewPager.setAdapter(viewPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void setUpData() {

    }

}
