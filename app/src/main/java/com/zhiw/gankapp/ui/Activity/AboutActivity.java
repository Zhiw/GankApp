package com.zhiw.gankapp.ui.activity;

import com.zhiw.gankapp.R;
import com.zhiw.gankapp.app.ToolBarActivity;
import com.zhiw.gankapp.config.Constants;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends ToolBarActivity {


    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @Bind(R.id.tv_about_content)
    TextView mAboutContentText;
    @Bind(R.id.app_bar)
    AppBarLayout mAppBar;
    @Bind(R.id.recycler_view_lib)
    RecyclerView mLibRecyclerView;

    private ArrayMap<String, String> mLibArrayMap;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_about;
    }

    @Override
    protected void setUpView() {
        mAboutContentText.setAutoLinkMask(Linkify.ALL);
        mAboutContentText.setMovementMethod(LinkMovementMethod.getInstance());
        mAppBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset <= -appBarLayout.getHeight() / 3) {
                mToolbarLayout.setTitle(getString(R.string.app_name));
            } else {
                mToolbarLayout.setTitle("");
            }
        });

    }

    @Override
    protected void setUpData() {
        mLibArrayMap = new ArrayMap<>();
        mLibArrayMap.put("CardView", "http://developer.android.com/reference/android/support/v7/widget/CardView.html");
        mLibArrayMap.put("Gson", "https://github.com/google/gson");
        mLibArrayMap.put("RecyclerView", "http://developer.android.com/reference/android/support/v7/widget/RecyclerView.html");
        mLibArrayMap.put("NavigationView", "http://developer.android.com/reference/android/support/design/widget/NavigationView.html");
        mLibArrayMap.put("ButterKnife", "http://jakewharton.github.io/butterknife/");
        mLibArrayMap.put("Retrofit", "https://github.com/square/retrofit");
        mLibArrayMap.put("OkHttp", "https://github.com/square/okhttp");
        mLibArrayMap.put("RxJava", "https://github.com/ReactiveX/RxJava");
        mLibArrayMap.put("Glide", "https://github.com/bumptech/glide");
        mLibArrayMap.put("PhotoView", "https://github.com/chrisbanes/PhotoView");

        AboutAdapter aboutAdapter = new AboutAdapter();
        mLibRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mLibRecyclerView.setAdapter(aboutAdapter);

    }

    /**
     * 自定义设置了AutoLink的TextView点击事件
     *
     * @param textView android:autoLink="web"
     */
    private void setAutoLinkTextView(TextView textView) {
        CharSequence text = textView.getText();

        if (text instanceof Spannable) {
            int end = text.length();
            Spannable sp = (Spannable) text;
            URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.clearSpans();
            for (URLSpan urlSpan : urls) {
                MyURLSpan myURLSpan = new MyURLSpan(urlSpan.getURL());
                style.setSpan(myURLSpan, sp.getSpanStart(urlSpan),
                        sp.getSpanEnd(urlSpan),
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            textView.setText(style);
        }
    }

    /**
     * 设置点击事件
     */
    private class MyURLSpan extends ClickableSpan {

        private String url;

        public MyURLSpan(String url) {
            this.url = url;
        }

        @Override
        public void onClick(View view) {
            openUrl(url);

        }
    }

    private void openUrl(String url) {
        Intent intent = new Intent(AboutActivity.this, WebViewActivity.class);
        intent.putExtra(Constants.URL, url);
        startActivity(intent);

    }

    class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.AboutViewHolder> {
        @Override
        public AboutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AboutViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lib, parent, false));
        }

        @Override
        public void onBindViewHolder(AboutViewHolder holder, int position) {
            holder.mTextView.setText(" · "+ mLibArrayMap.keyAt(position));
            holder.mTextView.setOnClickListener(v -> openUrl(mLibArrayMap.valueAt(position)));
        }

        @Override
        public int getItemCount() {
            return mLibArrayMap.size();
        }

        class AboutViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.text_lib)
            TextView mTextView;

            public AboutViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
