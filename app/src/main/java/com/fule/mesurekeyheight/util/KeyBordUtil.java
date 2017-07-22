package com.fule.mesurekeyheight.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 作者:Created by 简玉锋 on 2017/7/22 11:10
 * 邮箱: jianyufeng@38.hn
 */

public class KeyBordUtil {


    /**
     * 强制隐藏输入法键盘
     */

    //此方法只是关闭软键盘
    public static void hideKeybroad(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    //此方法，如果显示则隐藏，如果隐藏则显示
    public static void toggleKeyBroad(Context c) {
        InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }

    public static boolean keyBoardShow(Context c, View v) {
        InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例

        if (imm.hideSoftInputFromWindow(v.getWindowToken(), 0)) {
            imm.showSoftInput(v, 0);
        //软键盘已弹出
            return true;
        } else {
            return false;
            //软键盘未弹出
        }

    }
}
