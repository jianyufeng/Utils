package com.fule.mesurekeyheight.login;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ReplacementTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fule.mesurekeyheight.R;
import com.fule.mesurekeyheight.config.ConfigSettings;
import com.fule.mesurekeyheight.config.SPUtil;
import com.fule.mesurekeyheight.util.ScreenUtil;
import com.fule.mesurekeyheight.util.face.Emojicon;
import com.fule.mesurekeyheight.util.widget.ClearEditText;


/**
 * 作者:Created by 简玉锋 on 2017/7/24 16:36
 * 邮箱: jianyufeng@38.hn
 * <p>
 * 登录界面获取软键盘的高度 存储到配置文件中
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {


    private static final String TAG = "LoginActivity";

    private ViewTreeObserver.OnGlobalLayoutListener mLayoutChangeListener;
    //记录软件盘当前的显示状态
    private boolean mIsSoftKeyboardShowing;

    private ScrollView scrollView;
    private TextView login;

    private int keyHeight = 0; //键盘高度

    private ClearEditText account;
    private ClearEditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局
        setContentView(R.layout.login_activity);
        //创建监听  布局尺寸变换监听器
        mLayoutChangeListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            //布局变化时回调
            @Override
            public void onGlobalLayout() {
                //判断窗口可见区域大小
                Rect r = new Rect();
                //获取当前可见视图的尺寸大小
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //如果屏幕高度和Window可见区域高度差值大于整个屏幕高度的1/3，则表示软键盘显示中，否则软键盘为隐藏状态。
                int heightDifference = ScreenUtil.getScreenHeight(getApplicationContext()) - (r.bottom - r.top);
                boolean isKeyboardShowing = heightDifference > ScreenUtil.getScreenHeight(getApplicationContext()) / 3;
                //如果之前软键盘状态为显示，现在为关闭，或者之前为关闭，现在为显示，则表示软键盘的状态发生了改变
                if ((mIsSoftKeyboardShowing && !isKeyboardShowing) || (!mIsSoftKeyboardShowing && isKeyboardShowing)) {
                    mIsSoftKeyboardShowing = isKeyboardShowing;
                    //当前软键盘keyHeight显示 并且键盘高度为0  获取软键盘高度
                    if (isKeyboardShowing) {
                        //键盘高度  =  剩余高度   -  状态栏高度   -  虚拟按键的高度(不一定有  大部分 手机没有)
                        int keyH = heightDifference - ScreenUtil.getStatusBarHeight_1(getApplicationContext()) -   ScreenUtil.getNavigationBarHeight(getApplicationContext());;
                        if (keyH > keyHeight) {
                            //存储最高的键盘高度
                            keyHeight = keyH;
                            //保存高度到配置文件中
                            SPUtil.put(getApplicationContext(), ConfigSettings.SETTING_KEYBOARD_HEIGHT.getId(), keyH);
                            //滑动显示位置到底部
                            scrollBottom(scrollView);
                        }
                    }
                }

            }
        };
        //注册布局变化监听
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);

        //获取组件
        scrollView = (ScrollView) findViewById(R.id.scrollView_id);
        login = (TextView) findViewById(R.id.login_id);
        //设置点击监听
        login.setOnClickListener(this);

        account = (ClearEditText) findViewById(R.id.account);
        pass = (ClearEditText) findViewById(R.id.password);
        pass.setOnEditorActionListener(this);

        //替换密码默认显示形式
        pass.setTransformationMethod(new PasswordReplace());
        Emojicon emojicon = Emojicon.fromCodePoint(0x1f60d);
        int icon = emojicon.getIcon();
        String emoji = emojicon.getEmoji();
        emojicon.getValue();
      account.setText(emoji);


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
        switch (id) {
            case R.id.login_id://登录点击事件
                //登录的逻辑
                login();
                break;
        }
    }

    private void scrollBottom(final View v) {
        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                //滑动到底部
                v.scrollTo(0, v.getHeight());
            }
        }, 300);
    }

    private void login(){
        int h = (int) SPUtil.get(getApplicationContext(), ConfigSettings.SETTING_KEYBOARD_HEIGHT.getId(), 0);
        Toast.makeText(getApplicationContext(), "H:" + h+"  pass::"+ pass.getText().toString(), Toast.LENGTH_SHORT).show();


    }

    //软件键盘 点击的事件回调
    //处理确定按钮
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
                    /*隐藏软键盘*/
            //处理登录逻辑
            login();

            return true;
        }
        return false;
    }

    /**
     * 密码默认显示替换成  * 好
     */


    class PasswordReplace extends ReplacementTransformationMethod {

        String strWord = null;

        @Override
        protected char[] getOriginal() {
            //循环ASCII值 字符串形式累加到String
            for (char i = 0; i < 256; i++) {
                strWord += String.valueOf(i);
            }
            //strWord转换为字符形式的数组
            char[] charOriginal = strWord.toCharArray();
            return charOriginal;
        }

        @Override
        protected char[] getReplacement() {
            char[] charReplacement = new char[255];
            //输入的字符在ASCII范围内，将其转换为*
            for (int i = 0; i < 255; i++) {
                charReplacement[i] = '*';
            }

            return charReplacement;
        }

    }
}
