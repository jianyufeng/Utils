package com.fule.mesurekeyheight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fule.mesurekeyheight.activityUtil.SoftKeyboardStateHelper;
import com.fule.mesurekeyheight.config.ConfigSettings;
import com.fule.mesurekeyheight.config.SPUtil;
import com.fule.mesurekeyheight.util.KeyBordUtil;
import com.fule.mesurekeyheight.util.ScreenUtil;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


public class MainActivity extends AppCompatActivity implements SoftKeyboardStateHelper.SoftKeyboardStateListener {
    private LinearLayout p;
    private EditText ed;
    private ListView lv;
    private static final String TAG = "MainActivity";

    private Button btn;
    private Button send;
    private LinearLayout small;
    private TextView small_show;

    private int keyH;
    private SoftKeyboardStateHelper keyBroadHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keyBroadHelp = new SoftKeyboardStateHelper(findViewById(R.id.contain_id));
        keyBroadHelp.addSoftKeyboardStateListener(this);
        keyH = (int) SPUtil.get(getApplicationContext(), ConfigSettings.SETTING_KEYBOARD_HEIGHT.getId(), 0);
        p = (LinearLayout) findViewById(R.id.contain_id);
        ed = (EditText) findViewById(R.id.edit);
        lv = (ListView) findViewById(R.id.lv);
        btn = (Button) findViewById(R.id.btn);
        send = (Button) findViewById(R.id.send);
        small = (LinearLayout) findViewById(R.id.small);
        small_show = (TextView) findViewById(R.id.small_show);

        p.setMinimumHeight(ScreenUtil.getScreenHeight(this));
        lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeyBordUtil.hideKeybroad(MainActivity.this);
                return false;
            }
        });

        //根据输入框的聚焦状体  决定软键盘的显示和隐藏


        ed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ed.setFocusable(true);
                ed.setFocusableInTouchMode(true);
                ed.requestFocus();

                if (lv.isStackFromBottom()) {
                    lv.setStackFromBottom(false);
                }
                lv.setStackFromBottom(true);

                state = 0;
                if (!isLock) {
                    lockContentHeight();
                }
                return false;
            }

        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lv.isStackFromBottom()) {
                    lv.setStackFromBottom(false);
                }
                lv.setStackFromBottom(true);
                state = 1;
                if (!isLock) {
                    lockContentHeight();
                }

                KeyBordUtil.toggleKeyBroad(MainActivity.this);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.smoothScrollToPosition(lv.getAdapter().getCount());
                state = 2;

                KeyBordUtil.toggleKeyBroad(MainActivity.this);

            }
        });


        final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        //设置 Header 为 Material风格
        refreshLayout.setRefreshHeader(new DeliveryHeader(this));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
            }
        });

    }

    @Override
    protected void onDestroy() {
        keyBroadHelp.onDestroy();
        super.onDestroy();

    }


    private boolean isLock = false;

    private int state = 0;  // 当前点击那个   0 点击输入框  1 点击表情   2 点击加好
    private int keyState = 1; //键盘状态  0 显示  1 隐藏

    /**
     * 锁定内容高度，防止跳闪
     */
    private synchronized void lockContentHeight() {
        ViewGroup.LayoutParams params = p.getLayoutParams();
        params.height = p.getHeight() - keyH;
        p.setLayoutParams(params);
        isLock = true;
    }

    /**
     * 释放内容高度，防止跳闪
     */
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

    }
}
