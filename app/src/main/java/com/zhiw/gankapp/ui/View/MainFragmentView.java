package com.zhiw.gankapp.ui.View;

import com.zhiw.gankapp.app.BaseView;
import com.zhiw.gankapp.model.Gank;

import java.util.List;

/**
 * ClassName: MainFragmentView
 * Desc:
 * Created by zhiw on 16/6/12.
 */

public interface MainFragmentView extends BaseView {

    void showListView(List<Gank> list);

    void refreshGankList(List<Gank> list);
}
