package com.zhiw.gankapp.app;


import com.zhiw.gankapp.utils.SnackbarUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements BaseView {
    public FragmentActivity fragmentActivity;

    private ViewGroup mRootView;

    private Unbinder unbind;


    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        unbind = ButterKnife.bind(this, view);
        mRootView = (ViewGroup) view.findViewById(android.R.id.content);
        init();
        setUpView();
        setUpData();
        return view;
    }

    protected abstract int getLayoutResId();

    protected abstract void setUpView();

    protected abstract void setUpData();

    protected void init() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fragmentActivity = getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    @Override
    public void error(String msg) {
        SnackbarUtil.showSnackbar(mRootView, msg);
    }

    public void startActivity(Class<?> aClass) {
        fragmentActivity.startActivity(new Intent(fragmentActivity, aClass));
    }

    public void startActivity(Intent intent) {
        fragmentActivity.startActivity(intent);
    }

}
