package com.zhiw.gankapp.model;

import java.util.List;

/**
 * ClassName: GankData
 * Desc:
 * Created by zhiw on 16/6/12.
 */

public class GankData {
    private boolean error;
    private List<Gank> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Gank> getResults() {
        return results;
    }

    public void setResults(List<Gank> results) {
        this.results = results;
    }
}
