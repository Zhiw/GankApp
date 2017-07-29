package com.zhiw.gankapp.presenter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.orhanobut.logger.Logger;
import com.zhiw.gankapp.app.BasePresenter;
import com.zhiw.gankapp.ui.view.MeizhiView;
import com.zhiw.gankapp.utils.FileUtil;
import com.zhiw.gankapp.utils.ShareUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


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
        Observable<File> observable = Observable.create((ObservableOnSubscribe<File>) e -> {
            File file = FileUtil.saveBitmap(bitmap, name);
            if (!file.exists()) {
                e.onError(new Exception("save image failed"));
            } else {
                e.onNext(file);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


        if (action == 0) {
            observable.subscribe(file -> {
                        Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file));
                        context.sendBroadcast(i);
                        viewImpl.showImageResult("图片保存成功");

                    }
                    , throwable -> Logger.e(throwable.getMessage()));
        } else if (action == 1) {
            observable.subscribe(file -> ShareUtil.shareImage(context, Uri.fromFile(file), "分享妹纸"));
        }
    }

    public void saveImage(Bitmap bitmap, String name) {
        Observable.create((ObservableOnSubscribe<File>) e -> {
            File file = FileUtil.saveBitmap(bitmap, name);
            if (!file.exists()) {
                e.onError(new Exception("save image failed"));
            } else {
                e.onNext(file);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        file -> {
                            try {
                                MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), name, null);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file));
                            context.sendBroadcast(i);
                            viewImpl.showImageResult("图片保存成功");

                        },
                        throwable -> Logger.e(throwable.getMessage()));
    }

    public void shareImage(Bitmap bitmap, String name) {
        Observable.create((ObservableOnSubscribe<File>) e -> {
            File file = FileUtil.saveCacheFile(context, bitmap, name);
            if (!file.exists()) {
                e.onError(new Exception("failed"));
            } else {
                e.onNext(file);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> ShareUtil.shareImage(context, Uri.fromFile(file), "分享妹纸"));
    }

    public void shareImage(String url) {
        Observable.create((ObservableOnSubscribe<File>) e -> {
            FutureTarget<File> target = Glide.with(context)
                    .load(url)
                    .downloadOnly(300, 300);

            try {
                File cacheFile = target.get();
                e.onNext(cacheFile);
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> ShareUtil.shareImage(context, Uri.fromFile(file), "分享妹纸"));
    }
}
