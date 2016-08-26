package com.zhiw.gankapp.model;

import java.util.List;

/**
 * ClassName: DailyGank
 * Desc: 每日干货数据
 * Created by zhiw on 16/8/26.
 */

public class DailyGank {

    private boolean error;
    private GankDaily results;
    private List<String> category;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public GankDaily getResults() {
        return results;
    }

    public void setResults(GankDaily results) {
        this.results = results;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

}
