package com.zhiw.gankapp.ui.activity;

import com.zhiw.gankapp.R;
import com.zhiw.gankapp.app.BaseActivity;
import com.zhiw.gankapp.app.BaseFragment;
import com.zhiw.gankapp.ui.fragment.CategoryFragment;
import com.zhiw.gankapp.ui.fragment.DailyFragment;
import com.zhiw.gankapp.ui.fragment.ReadingFragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.nav_view) NavigationView mNavView;
    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;

    private Fragment mCurrentFragment;
    private FragmentManager mFragmentManager;
    private Map<String, BaseFragment> mFragmentList = new HashMap<>();
    private int mLastItemId = -1;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpView() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);

        Fragment fragment = createFragment(DailyFragment.class);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager
                .beginTransaction()
                .add(R.id.frame_content, fragment)
                .commit();

        MenuItem menuItem = mNavView.getMenu().getItem(0);
        mLastItemId = menuItem.getItemId();
        setTitle(menuItem.getTitle());
        mCurrentFragment = fragment;
    }

    @Override
    protected void setUpData() {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)
                MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("请输入搜索关键词");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchResultActivity.openActivity(MainActivity.this, query);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                }
                searchView.clearFocus();
                searchView.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            startActivity(AboutActivity.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (mLastItemId != id) {
            if (id == R.id.nav_home) {
                BaseFragment fragment = createFragment(DailyFragment.class);
                switchFragment(fragment);

            } else if (id == R.id.nav_sort) {
                BaseFragment fragment = createFragment(CategoryFragment.class);
                switchFragment(fragment);

            }else if (id==R.id.nav_reading){
                BaseFragment fragment = createFragment(ReadingFragment.class);
                switchFragment(fragment);

            }
            mLastItemId = id;
        }


        setTitle(item.getTitle());
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private BaseFragment createFragment(Class<?> aClass) {
        BaseFragment fragment = null;
        String className = aClass.getName();
        if (mFragmentList.containsKey(className)) {
            fragment = mFragmentList.get(className);
        } else {
            try {
                fragment = (BaseFragment) Class.forName(className).newInstance();
                mFragmentList.put(className, fragment);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

        return fragment;
    }

    private void switchFragment(BaseFragment fragment) {
        if (fragment.isAdded()) {
            mFragmentManager
                    .beginTransaction()
                    .hide(mCurrentFragment)
                    .show(fragment)
                    .commit();
        } else {
            mFragmentManager
                    .beginTransaction()
                    .hide(mCurrentFragment)
                    .add(R.id.frame_content, fragment)
                    .commit();
        }
        mCurrentFragment = fragment;
    }

}
