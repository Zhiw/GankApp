package com.zhiw.gankapp.presenter;

import com.zhiw.gankapp.app.BasePresenter;
import com.zhiw.gankapp.ui.view.MeizhiView;
import com.zhiw.gankapp.utils.FileUtil;
import com.zhiw.gankapp.utils.ShareUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * ClassName: MeizhiPresenter
 * Desc:
 * Created by zhiw on 16/6/13.
 */
public class MeizhiPresenter extends BasePresenter<MeizhiView> {

    public MeizhiPresenter(Context context, MeizhiView view) {
        super(context, view);
    }

    public void saveImage(Bitmap bitmap, String name) {
        Observable.create(new Observable.OnSubscribe<Uri>() {
            @Override
            public void call(Subscriber<? super Uri> subscriber) {
                Uri uri = FileUtil.saveBitmap(bitmap, name);
                if (uri == null) {
                    subscriber.onError(new Exception("failed"));
                } else {
                    subscriber.onNext(uri);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> {
                    Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                    context.sendBroadcast(i);
                    viewImpl.showImageResult("图片保存成功");

                });
    }

    public void shareImage(Bitmap bitmap,String name){
        Observable.create(new Observable.OnSubscribe<Uri>() {
            @Override
            public void call(Subscriber<? super Uri> subscriber) {
                Uri uri = FileUtil.saveBitmap(bitmap, name);
                if (uri == null) {
                    subscriber.onError(new Exception("failed"));
                } else {
                    subscriber.onNext(uri);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> {
                    ShareUtil.shareImage(context,uri,"分享妹纸");
                });
    }
}
