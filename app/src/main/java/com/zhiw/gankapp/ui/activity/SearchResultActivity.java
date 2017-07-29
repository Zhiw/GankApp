package com.zhiw.gankapp.ui.activity;

import com.zhiw.gankapp.R;
import com.zhiw.gankapp.adapter.SearchResultAdapter;
import com.zhiw.gankapp.app.ToolBarActivity;
import com.zhiw.gankapp.http.GankDataResource;
import com.zhiw.gankapp.ui.widget.MyRecyclerView;
import com.zhiw.gankapp.ui.widget.RecyclerViewDivider;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchResultActivity extends ToolBarActivity {

    public static final String EXTRA_KEYWORD = "keyword";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    MyRecyclerView mRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.view_stub)
    ViewStub mViewStub;

    private String mKeyWord;
    private int mPage = 1;

    private SearchResultAdapter mAdapter;
    private Disposable mDisposable;

    public static void openActivity(Context context, String keyWord) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(EXTRA_KEYWORD, keyWord);
        context.startActivity(intent);

    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void setUpView() {
        mKeyWord = getIntent().getStringExtra(EXTRA_KEYWORD);
        mAdapter = new SearchResultAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(this));

        mToolbar.setTitle(mKeyWord + "的搜索结果");

        mRecyclerView.setLoadMoreListener(() -> {


        });

    }

    @Override
    protected void setUpData() {
        mProgressBar.setVisibility(View.VISIBLE);
        mDisposable = new GankDataResource()
                .search(mKeyWord, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        searchResponse -> {
                            mProgressBar.setVisibility(View.GONE);
                            if (searchResponse.getResults().size() == 0) {
                                mViewStub.inflate();
                            }
                            mAdapter.refreshData(searchResponse.getResults());
                        },
                        throwable -> mProgressBar.setVisibility(View.GONE));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }
}
