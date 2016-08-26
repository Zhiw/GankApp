package com.zhiw.gankapp.model;

import java.util.List;

/**
 * ClassName: GankDaily
 * Desc:
 * Created by zhiw on 16/8/26.
 */

public class GankDaily {


    private List<Gank> Android;


    private List<Gank> iOS;


    private List<Gank> 休息视频;


    private List<Gank> 拓展资源;


    private List<Gank> 瞎推荐;


    private List<Gank> 福利;

    public List<Gank> getAndroid() {
        return Android;
    }

    public void setAndroid(List<Gank> Android) {
        this.Android = Android;
    }

    public List<Gank> getIOS() {
        return iOS;
    }

    public void setIOS(List<Gank> iOS) {
        this.iOS = iOS;
    }

    public List<Gank> get休息视频() {
        return 休息视频;
    }

    public void set休息视频(List<Gank> 休息视频) {
        this.休息视频 = 休息视频;
    }

    public List<Gank> get拓展资源() {
        return 拓展资源;
    }

    public void set拓展资源(List<Gank> 拓展资源) {
        this.拓展资源 = 拓展资源;
    }

    public List<Gank> get瞎推荐() {
        return 瞎推荐;
    }

    public void set瞎推荐(List<Gank> 瞎推荐) {
        this.瞎推荐 = 瞎推荐;
    }

    public List<Gank> get福利() {
        return 福利;
    }

    public void set福利(List<Gank> 福利) {
        this.福利 = 福利;
    }

}
