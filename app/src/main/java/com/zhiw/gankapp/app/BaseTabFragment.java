package com.zhiw.gankapp.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ClassName: BaseTabFragment
 * Desc:
 * Created by zhiw on 16/7/13.
 */
public abstract class BaseTabFragment extends BaseFragment {

    protected boolean isVisible = false;
    private boolean isInitView = false;
    protected boolean isLoadOnce = false;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();

        } else {
            isVisible = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        loadData();
        isInitView = true;
        isLoadOnce = true;

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    private void lazyLoad() {
        if (!isInitView || !isVisible || isLoadOnce) {
            return;
        }
        loadData();
        isLoadOnce = true;

    }

    protected abstract void loadData();

}
