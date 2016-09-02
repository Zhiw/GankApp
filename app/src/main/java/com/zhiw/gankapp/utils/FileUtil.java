package com.zhiw.gankapp.utils;

import android.graphics.Bitmap;
import android.net.Uri;
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

    // FIXME: 16/9/1 can not save image
    public static Uri saveBitmap(Bitmap bitmap, String name) {
        File dir = new File(Environment.getExternalStorageDirectory().getPath()+"/"+"Gank");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, name);
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
        return Uri.fromFile(file);
    }
}
