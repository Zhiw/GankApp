package com.zhiw.gankapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ClassName: FileUtil
 * Desc:
 * Created by zhiw on 16/6/17.
 */
public class FileUtil {

    public static File saveBitmap(Bitmap bitmap, String name) {
        File dir = new File(Environment.getExternalStorageDirectory(), "Gank");
        if (!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(dir, name + ".jpg");
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File saveCacheFile(Context context,Bitmap bitmap, String name) {
        File file = new File(context.getCacheDir(), name + ".jpg");
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;

    }
}
