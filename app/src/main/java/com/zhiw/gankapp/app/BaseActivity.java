package com.zhiw.gankapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        setUpView();
        setUpData();
    }

    protected abstract int getLayoutResId();

    protected abstract void setUpView();

    protected abstract void setUpData();

    protected void init(Bundle savedInstanceState) {

    }



    public void startActivity(Class<?> aClass) {
        startActivity(new Intent(this, aClass));
    }
}
