package com.fule.mesurekeyheight.activityUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fule.mesurekeyheight.R;


public class SoftMainActivity extends AppCompatActivity{

    private static final String TAG = "SoftMainActivity";

    private SoftKeyboardStateHelper keyBroadHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keyBroadHelp = new SoftKeyboardStateHelper(findViewById(R.id.contain_id));
        keyBroadHelp.addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {

            }

            @Override
            public void onSoftKeyboardClosed() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        keyBroadHelp.onDestroy();
        super.onDestroy();

    }


  /*  private boolean isLock = false;

    private int state = 0;  // 当前点击那个   0 点击输入框  1 点击表情   2 点击加好
    private int keyState = 1; //键盘状态  0 显示  1 隐藏

    *//**
     * 锁定内容高度，防止跳闪
     *//*
    private synchronized void lockContentHeight() {
        ViewGroup.LayoutParams params = p.getLayoutParams();
        params.height = p.getHeight() - keyH;
        p.setLayoutParams(params);
        isLock = true;
    }

    *//**
     * 释放内容高度，防止跳闪
     *//*
    private synchronized void releaseContentHeight() {
        ViewGroup.LayoutParams params = p.getLayoutParams();
        params.height = p.getHeight() + keyH;
        p.setLayoutParams(params);
        isLock = false;
    }


    private void setHeight(View v, int px) {
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.height = px;
        v.setLayoutParams(layoutParams);
    }


    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        if (!isLock) {
            lockContentHeight();
        }
    }

    @Override
    public void onSoftKeyboardClosed() {
        if (state == 0) {
            releaseContentHeight();
        }

    }*/
}
