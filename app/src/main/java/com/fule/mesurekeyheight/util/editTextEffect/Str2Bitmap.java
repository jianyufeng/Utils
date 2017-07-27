package com.fule.mesurekeyheight.util.editTextEffect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.fule.mesurekeyheight.util.DensityUtil;


/**
 * 作者:Created by 简玉锋 on 2017/7/27 10:11
 * 邮箱: jianyufeng@38.hn
 */

public class Str2Bitmap {
    /**
     * 字符串转换成图片
     * @param cxt
     * @param str
     * @param fontSize   sp
     *
     * @return
     */
    public static Bitmap createBitmap(Context cxt,String str,int fontSize) {
        //获取高度
        int h_ = fontSize + fontSize/2;
        int h = DensityUtil.sp2px(cxt, h_);
        //获取字体大小
        int fs = DensityUtil.sp2px(cxt, fontSize);
        //设置画笔
        Paint paint = new Paint();
        paint.setTextSize(fs);//字体大小
        paint.setColor(Color.BLACK);//字体颜色
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setTextSkewX(0);//斜度

        //测量字符串
        Rect bounds = new Rect();
        paint.getTextBounds(str, 0, str.length(), bounds);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        //获取基线
        int baseline = (h - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;

        //宽度 = 字符串长度   +  字体的一般
        int w = bounds.width() +  fs/2;

        //创建 图
        Bitmap bp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888); //画布大小
        Canvas c = new Canvas(bp);
        c.drawColor(Color.WHITE);//画布颜色

//        Paint paint2 = new Paint();//画姓名前边的间隔
//        paint2.setColor(Color.BLUE);
//        paint2.setStrokeWidth(1f);
//        c.drawLine(0, 0, 0,i, paint2);
        paint.setTextAlign(Paint.Align.LEFT);
//        c.drawText(str, i, 20, paint);//文字位置
        //参数  由paint.setTextAlign(Paint.Align.CENTER);  决定 x:中心点  y:基线位置
        c.drawText(str,0, baseline, paint);
        c.save(Canvas.ALL_SAVE_FLAG );//保存
        c.restore();//
        return bp;
    }
}
