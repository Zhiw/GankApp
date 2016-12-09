package com.zhiw.gankapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * ClassName: ViewPagerAdapter
 * Desc:
 * Created by zhiw on 16/7/12.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private String[] titles = {"福利", "Android", "iOS", "前端", "休息视频"};


    public ViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mFragmentList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
