package com.zhiw.gankapp.ui.view;

import com.zhiw.gankapp.app.BaseView;
import com.zhiw.gankapp.model.GankDaily;

/**
 * ClassName: DailyGankView
 * Desc:
 * Created by zhiw on 16/8/26.
 */

public interface DailyGankView extends BaseView {

    void refreshUI(GankDaily gankDaily);

}
