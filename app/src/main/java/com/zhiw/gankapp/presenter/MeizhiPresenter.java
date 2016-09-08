package com.zhiw.gankapp.presenter;

import com.orhanobut.logger.Logger;
import com.zhiw.gankapp.app.BasePresenter;
import com.zhiw.gankapp.ui.view.MeizhiView;
import com.zhiw.gankapp.utils.FileUtil;
import com.zhiw.gankapp.utils.ShareUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;

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

    public void downloadImage(Bitmap bitmap, String name, int action) {
        Observable<File> observable = Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                File file = FileUtil.saveBitmap(bitmap, name);
                if (!file.exists()) {
                    subscriber.onError(new Exception("save image failed"));
                } else {
                    subscriber.onNext(file);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        if (action == 0) {
            observable.subscribe(file -> {
                Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file));
                context.sendBroadcast(i);
                viewImpl.showImageResult("图片保存成功");

            }, throwable -> {
                Logger.e(throwable.getMessage());

            });
        } else if (action == 1) {
            observable.subscribe(file -> {
                ShareUtil.shareImage(context, Uri.fromFile(file), "分享妹纸");
            });
        }
    }

    public void saveImage(Bitmap bitmap, String name) {
        Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                File file = FileUtil.saveBitmap(bitmap, name);
                if (!file.exists()) {
                    subscriber.onError(new Exception("save image failed"));
                } else {
                    subscriber.onNext(file);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        file -> {
                            Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file));
                            context.sendBroadcast(i);
                            viewImpl.showImageResult("图片保存成功");

                        }, throwable -> {
                            Logger.e(throwable.getMessage());


                        });
    }

    public void shareImage(Bitmap bitmap, String name) {
        Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                File file = FileUtil.saveBitmap(bitmap, name);
                if (!file.exists()) {
                    subscriber.onError(new Exception("failed"));
                } else {
                    subscriber.onNext(file);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> {
                    ShareUtil.shareImage(context, Uri.fromFile(file), "分享妹纸");
                });
    }
}
