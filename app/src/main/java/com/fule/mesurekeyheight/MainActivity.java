package com.fule.mesurekeyheight;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fule.mesurekeyheight.config.ConfigSettings;
import com.fule.mesurekeyheight.config.SPUtil;
import com.fule.mesurekeyheight.util.KeyBordUtil;
import com.fule.mesurekeyheight.util.ScreenUtil;

public class MainActivity extends AppCompatActivity {
    private LinearLayout p;
    private EditText ed;
    private ListView lv;
    private static final String TAG = "MainActivity";

    private Button btn;
    private Button send;
    private LinearLayout small;
    private TextView small_show;

    private int keyH = (int) SPUtil.get(getApplicationContext(), ConfigSettings.SETTING_KEYBOARD_HEIGHT.getId(),0);
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            setHeight(small, keyH);
            small_show.setVisibility(View.VISIBLE);
            super.handleMessage(msg);
        }
    };


    private ViewTreeObserver.OnGlobalLayoutListener mLayoutChangeListener;
    private boolean mIsSoftKeyboardShowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIsSoftKeyboardShowing = false;
        //注册布局变化监听
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);
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
//                setHeight(small,0);
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
                if (!isLock) {
                    lockContentHeight();
                }
                return false;
            }

        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: " + KeyBordUtil.keyBoardShow(MainActivity.this, ed));
                if (lv.isStackFromBottom()) {
                    lv.setStackFromBottom(false);
                }
                lv.setStackFromBottom(true);

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
//                ed.setFocusable(false);
                KeyBordUtil.toggleKeyBroad(MainActivity.this);
            }
        });
    }

    private boolean isLock = false;

    /**
     * 锁定内容高度，防止跳闪
     */
    private void lockContentHeight() {
        ViewGroup.LayoutParams params = p.getLayoutParams();
        params.height = p.getHeight() - keyH;
        p.setLayoutParams(params);
        isLock = true;
    }

    private void setHeight(View v, int px) {
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.height = px;
        v.setLayoutParams(layoutParams);
    }
}
