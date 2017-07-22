package com.fule.mesurekeyheight;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Build;
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

import com.fule.mesurekeyheight.util.KeyBordUtil;
import com.fule.mesurekeyheight.util.ScreenUtil;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    private View p;
    private EditText ed;
    private ListView lv ;
    private static final String TAG = "MainActivity";
    private int keyH = 0;
    private Button btn;
    private Button send;
    private LinearLayout small;
    private TextView small_show;
    private TextView send_show;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            setHeight(small,keyH);
            small_show.setVisibility(View.VISIBLE);
            super.handleMessage(msg);
        }
    };
    public interface OnSoftKeyboardStateChangedListener {
        public void OnSoftKeyboardStateChanged(boolean isKeyBoardShow, int keyboardHeight);
    }

    //注册软键盘状态变化监听
    public void addSoftKeyboardChangedListener(OnSoftKeyboardStateChangedListener listener) {
        if (listener != null) {
            mKeyboardStateListeners.add(listener);
        }
    }

    //取消软键盘状态变化监听
    public void removeSoftKeyboardChangedListener(OnSoftKeyboardStateChangedListener listener) {
        if (listener != null) {
            mKeyboardStateListeners.remove(listener);
        }
    }

    private ArrayList<OnSoftKeyboardStateChangedListener> mKeyboardStateListeners;      //软键盘状态监听列表
    private ViewTreeObserver.OnGlobalLayoutListener mLayoutChangeListener;
    private boolean mIsSoftKeyboardShowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIsSoftKeyboardShowing = false;
        mKeyboardStateListeners = new ArrayList<OnSoftKeyboardStateChangedListener>();
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
                        keyH = heightDifference - ScreenUtil.getStatusBarHeight_1(getApplicationContext());
                    }
                    for (int i = 0; i < mKeyboardStateListeners.size(); i++) {
                        OnSoftKeyboardStateChangedListener listener = mKeyboardStateListeners.get(i);
                        listener.OnSoftKeyboardStateChanged(mIsSoftKeyboardShowing, heightDifference);
                    }
                }
            }
        };
        //注册布局变化监听
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);
        p=findViewById(R.id.contain_id);
        ed= (EditText) findViewById(R.id.edit);
        lv= (ListView) findViewById(R.id.lv);
        btn= (Button) findViewById(R.id.btn);
        send= (Button) findViewById(R.id.send);
        small= (LinearLayout) findViewById(R.id.small);
        small_show= (TextView) findViewById(R.id.small_show);
        send_show= (TextView) findViewById(R.id.send_show);


        lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeyBordUtil.hideKeybroad(MainActivity.this);
//                setHeight(small,0);
                return false;
            }
        });

        //根据输入框的聚焦状体  决定软键盘的显示和隐藏


        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.smoothScrollToPosition(lv.getAdapter().getCount());
                ed.setFocusable(true);
                ed.setFocusableInTouchMode(true);
                ed.requestFocus();
            }
        });
//        ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus){
//                    KeyBordUtil.toggleKeyBroad(MainActivity.this);
//                }else {
//                    KeyBordUtil.hideKeybroad(MainActivity.this);
//                }
//            }
//        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.smoothScrollToPosition(lv.getAdapter().getCount());
                if (small_show.getVisibility() == View.GONE){
                    KeyBordUtil.hideKeybroad(MainActivity.this);
                    handler.sendEmptyMessageDelayed(0,400);

                }else {
                    KeyBordUtil.toggleKeyBroad(MainActivity.this);
                }

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
    private void setHeight(View v ,int px){
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.height = px;
        v.setLayoutParams(layoutParams);
    }
  private boolean isShow = false;
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
}
