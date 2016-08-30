package com.zhiw.gankapp.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * ClassName: SnackbarUtil
 * Desc:
 * Created by zhiw on 16/6/17.
 */
public class SnackbarUtil {

    public static void showSnackbar(View view, String text, String actionText, View.OnClickListener listener) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).setAction(actionText, listener).show();
    }

    public static void showSnackbar(View view, String tipText) {
        Snackbar.make(view, tipText, Snackbar.LENGTH_SHORT).show();
    }

}
