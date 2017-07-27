package com.fule.mesurekeyheight.util.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.fule.mesurekeyheight.R;


/**
 * 作者:Created by 简玉锋 on 2017/7/26 17:42
 * 邮箱: jianyufeng@38.hn
 */

public class ClearEditText extends EditText implements View.OnTouchListener, View.OnFocusChangeListener {

    //EditText右侧的删除按钮
    private Drawable mClearDrawable;


    public ClearEditText(Context context) {
        this(context, null);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片,获取图片的顺序是左上右下（0,1,2,3,）
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = ContextCompat.getDrawable(getContext(), R.drawable.search_clear);
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
                mClearDrawable.getIntrinsicHeight());
        doClearDrawable();
//        setHeight(mClearDrawable.getIntrinsicHeight() + 5 * getResources().getDimensionPixelSize(R.dimen.OneDPPadding));
        setOnTouchListener(this);
        addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                doClearDrawable();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        setOnFocusChangeListener(this);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，
     * 输入长度为零，隐藏删除图标，否则，显示删除图标
     */
    private void doClearDrawable() {
        if("".equals(getText().toString()) || !isFocused()) {
            setClearDrawableNull();
            return;
        }
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], mClearDrawable, getCompoundDrawables()[3]);
    }
    /**
     *
     */
    private void setClearDrawableNull() {
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(getCompoundDrawables()[2] == null) {
            return false;
        }

        if((event.getAction() != MotionEvent.ACTION_UP)
                || event.getX() <= (getWidth() - getPaddingRight() - mClearDrawable.getIntrinsicWidth())) {
            return false;

        }
        getText().clear();

        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        doClearDrawable();
    }




}
