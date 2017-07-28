package com.fule.mesurekeyheight.util.face;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 作者:Created by 简玉锋 on 2017/7/28 11:57
 * 邮箱: jianyufeng@38.hn
 */

public class EmojiconEditText extends EditText {
    public EmojiconEditText(Context context) {
        super(context);
    }

    public EmojiconEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }
    public EmojiconEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

    }
}
