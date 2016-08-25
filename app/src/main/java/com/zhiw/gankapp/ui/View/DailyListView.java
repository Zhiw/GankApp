package com.zhiw.gankapp.ui.View;

import com.zhiw.gankapp.app.BaseView;
import com.zhiw.gankapp.model.Gank;

import java.util.List;

/**
 * ClassName: DailyListView
 * Desc:
 * Created by zhiw on 16/7/19.
 */
public interface DailyListView extends BaseView {

    void refreshGanData(List<Gank> list);
}
