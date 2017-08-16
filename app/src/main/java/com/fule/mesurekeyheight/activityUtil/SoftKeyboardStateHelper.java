package com.fule.mesurekeyheight.activityUtil;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

import com.fule.mesurekeyheight.util.ScreenUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 *  二版： 使用工具类集成的方法   使用： SoftMainActivity 使用例子
 *  1、获取软件盘高度
 *  2、监听软件盘打开和关闭
 */

public class SoftKeyboardStateHelper implements ViewTreeObserver.OnGlobalLayoutListener {

    public interface SoftKeyboardStateListener {
        void onSoftKeyboardOpened(int keyboardHeightInPx);
        void onSoftKeyboardClosed();
    }

    private final List<SoftKeyboardStateListener> listeners = new LinkedList<SoftKeyboardStateListener>();
    private final View activityRootView;
    private int        lastSoftKeyboardHeightInPx;
    private boolean    isSoftKeyboardOpened;

    public SoftKeyboardStateHelper(View activityRootView) {
        this(activityRootView, false);
    }

    public SoftKeyboardStateHelper(View activityRootView, boolean isSoftKeyboardOpened) {
        this.activityRootView     = activityRootView;
        this.isSoftKeyboardOpened = isSoftKeyboardOpened;
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        final Rect r = new Rect();
        //r will be populated with the coordinates of your view that area still visible.
        activityRootView.getWindowVisibleDisplayFrame(r);

        int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
        //变化的实际高度   =  窗体高度  -  状态栏高度  -  （假如有虚拟键盘高度）
       int changeHeight =  heightDiff - ScreenUtil.getStatusBarHeight_1(activityRootView.getContext()) - ScreenUtil.getNavigationBarHeight(activityRootView.getContext());
        if (!isSoftKeyboardOpened && changeHeight > 100) { // if more than 100 pixels, its probably a keyboard...
            isSoftKeyboardOpened = true;
            notifyOnSoftKeyboardOpened(changeHeight);
        } else if (isSoftKeyboardOpened && changeHeight < 100) {
            isSoftKeyboardOpened = false;
            notifyOnSoftKeyboardClosed();
        }
    }

    public void setIsSoftKeyboardOpened(boolean isSoftKeyboardOpened) {
        this.isSoftKeyboardOpened = isSoftKeyboardOpened;
    }

    public boolean isSoftKeyboardOpened() {
        return isSoftKeyboardOpened;
    }

    /**
     * Default value is zero (0)
     * @return last saved keyboard height in px
     */
    public int getLastSoftKeyboardHeightInPx() {
        return lastSoftKeyboardHeightInPx;
    }

    public void addSoftKeyboardStateListener(SoftKeyboardStateListener listener) {
        listeners.add(listener);
    }

    public void removeSoftKeyboardStateListener(SoftKeyboardStateListener listener) {
        listeners.remove(listener);
    }

    private void notifyOnSoftKeyboardOpened(int keyboardHeightInPx) {
        this.lastSoftKeyboardHeightInPx = keyboardHeightInPx;

        for (SoftKeyboardStateListener listener : listeners) {
            if (listener != null) {
                listener.onSoftKeyboardOpened(keyboardHeightInPx);
            }
        }
    }

    private void notifyOnSoftKeyboardClosed() {
        for (SoftKeyboardStateListener listener : listeners) {
            if (listener != null) {
                listener.onSoftKeyboardClosed();
            }
        }
    }
    public void onDestroy(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activityRootView.getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            activityRootView.getRootView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }

    }
}
