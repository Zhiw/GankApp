package com.zhiw.gankapp.ui.activity;

import com.zhiw.gankapp.R;
import com.zhiw.gankapp.adapter.SearchResultAdapter;
import com.zhiw.gankapp.app.ToolBarActivity;
import com.zhiw.gankapp.http.GankRetrofit;
import com.zhiw.gankapp.http.GankService;
import com.zhiw.gankapp.model.SearchResponse;
import com.zhiw.gankapp.ui.widget.MyRecyclerView;
import com.zhiw.gankapp.ui.widget.RecyclerViewDivider;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchResultActivity extends ToolBarActivity {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.recycler_view) MyRecyclerView mRecyclerView;
    @Bind(R.id.progress_bar) ProgressBar mProgressBar;
    @Bind(R.id.view_stub) ViewStub mViewStub;

    private String mKeyWord;
    private int mPage = 1;

    private SearchResultAdapter mAdapter;

    public static void openActivity(Context context, String keyWord) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra("keyword", keyWord);
        context.startActivity(intent);

    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void setUpView() {
        mKeyWord = getIntent().getStringExtra("keyword");
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
        GankRetrofit.getRetrofit()
                .create(GankService.class)
                .search(mKeyWord, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onNext(SearchResponse searchResponse) {
                        mProgressBar.setVisibility(View.GONE);
                        if (searchResponse.getResults().size() == 0) {
                            mViewStub.inflate();
                        }
                        mAdapter.refreshData(searchResponse.getResults());

                    }
                });

    }

}
