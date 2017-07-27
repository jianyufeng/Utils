package com.fule.mesurekeyheight.util.editTextEffect;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.TextView;

/**
 * 作者:Created by 简玉锋 on 2017/7/27 15:33
 * 邮箱: jianyufeng@38.hn
 * <p>
 * SpannableString
 * <p>
 * BackgroundColorSpan 背景色
 * ClickableSpan 文本可点击，有点击事件
 * UnderlineSpan 下划线
 * ImageSpan 图片
 * StyleSpan 字体样式：粗体、斜体等
 * URLSpan 文本超链接
 * 此外还有删除线,缩放大小等不同样式的拓展
 */

public class EditTextAddImg {
    /**
     * 在TextView 中添加 图片    实际是使用 图片代替字符串 显示  但实际上 获取的内容  还是字符串
     *
     * @param tv
     * @param rep
     * @param b
     */
    public static void insertBitmap(TextView tv, String rep, Bitmap b) {
        //用这个bitmap对象代替字符串easy
        final SpannableString s = new SpannableString(rep);
        //bitmap对象加工，使插入的图片在 TextView 中居中;
        VerticalImageSpan span = new VerticalImageSpan(tv.getContext(), b);
        //包括0但是不包括"easy".length()即：4。[0,4)
        s.setSpan(span, 0, rep.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.append(s);
    }

    /**
     * 在TextView 中添加 图片    实际是使用 图片代替字符串 显示  但实际上 获取的内容  还是字符串
     * drawable 不要太大
     *
     * @param tv
     * @param rep
     * @param id
     */
    public static void insertDrawable(TextView tv, String rep, int id) {
        final SpannableString s = new SpannableString(rep);
        //得到drawable对象，即所有插入的图片
        Drawable d = ContextCompat.getDrawable(tv.getContext(), id);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        //用这个drawable对象代替字符串easy
        VerticalImageSpan span = new VerticalImageSpan(d);
        //包括0但是不包括"easy".length()即：4。[0,4)
        s.setSpan(span, 0, rep.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.append(s);
    }
}
