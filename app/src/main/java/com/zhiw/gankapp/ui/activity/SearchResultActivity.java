package com.zhiw.gankapp.ui.activity;

import com.orhanobut.logger.Logger;
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

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchResultActivity extends ToolBarActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recycler_view)
    MyRecyclerView mRecyclerView;

    private String mKeyWord;

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

    }

    @Override
    protected void setUpData() {
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

                    }

                    @Override
                    public void onNext(SearchResponse searchResponse) {
                        mAdapter.refreshData(searchResponse.getResults());

                    }
                });

    }

}
