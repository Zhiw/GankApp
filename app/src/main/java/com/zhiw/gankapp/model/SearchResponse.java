package com.zhiw.gankapp.model;

import java.util.List;

/**
 * ClassName: SearchResponse
 * Desc:
 * Created by zhiw on 16/12/8.
 */

public class SearchResponse {
    private boolean error;

    private List<SearchResult> results;

    public List<SearchResult> getResults() {
        return results;
    }

    public void setResults(List<SearchResult> results) {
        this.results = results;
    }
}
