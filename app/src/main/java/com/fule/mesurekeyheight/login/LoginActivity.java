package com.fule.mesurekeyheight.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.fule.mesurekeyheight.MainActivity;
import com.fule.mesurekeyheight.R;
import com.fule.mesurekeyheight.config.ConfigSettings;
import com.fule.mesurekeyheight.config.SPUtil;
import com.fule.mesurekeyheight.util.ScreenUtil;


/**
 * 作者:Created by 简玉锋 on 2017/7/24 16:36
 * 邮箱: jianyufeng@38.hn
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "LoginActivity";

    private ViewTreeObserver.OnGlobalLayoutListener mLayoutChangeListener;
    private boolean mIsSoftKeyboardShowing;

    private EditText account;
    private ScrollView scrollView;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mLayoutChangeListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //判断窗口可见区域大小
                Rect r = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //如果屏幕高度和Window可见区域高度差值大于整个屏幕高度的1/3，则表示软键盘显示中，否则软键盘为隐藏状态。
                int heightDifference = ScreenUtil.getScreenHeight(getApplicationContext()) - (r.bottom - r.top);

                Log.d(TAG, "onGlobalLayout: " + heightDifference);
                boolean isKeyboardShowing = heightDifference > ScreenUtil.getScreenHeight(getApplicationContext()) / 3;
                //如果之前软键盘状态为显示，现在为关闭，或者之前为关闭，现在为显示，则表示软键盘的状态发生了改变
                if ((mIsSoftKeyboardShowing && !isKeyboardShowing) || (!mIsSoftKeyboardShowing && isKeyboardShowing)) {
                    mIsSoftKeyboardShowing = isKeyboardShowing;
                    if (isKeyboardShowing){
                        //键盘高度
                        int  keyH = heightDifference - ScreenUtil.getStatusBarHeight_1(getApplicationContext());
                        SPUtil.put(getApplicationContext(), ConfigSettings.SETTING_KEYBOARD_HEIGHT.getId(),keyH);
                        scrollView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //滑动到底部
                                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        },500);

                    }
                }

            }
        };
        //注册布局变化监听
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);
    scrollView = (ScrollView) findViewById(R.id.scrollView_id);
    login = (Button) findViewById(R.id.login_id);
        login.setOnClickListener(this);
    }
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    @Override
    protected void onDestroy() {
        //移除布局变化监听
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(mLayoutChangeListener);
        } else {
            getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(mLayoutChangeListener);
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.login_id:
                int h = (int) SPUtil.get(getApplicationContext(),ConfigSettings.SETTING_KEYBOARD_HEIGHT.getId(),0);
                Toast.makeText(getApplicationContext(),"H:"+h,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
