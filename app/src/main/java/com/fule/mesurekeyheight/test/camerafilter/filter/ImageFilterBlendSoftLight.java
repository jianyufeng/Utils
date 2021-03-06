package com.fule.mesurekeyheight.test.camerafilter.filter;

import android.content.Context;
import android.opengl.GLES10;
import android.support.annotation.DrawableRes;

import com.fule.mesurekeyheight.R;
import com.fule.mesurekeyheight.test.camerafilter.gles.GlUtil;


public class ImageFilterBlendSoftLight extends CameraFilterBlendSoftLight {

    public ImageFilterBlendSoftLight(Context context, @DrawableRes int drawableId) {
        super(context, drawableId);
    }

    @Override public int getTextureTarget() {
        return GLES10.GL_TEXTURE_2D;
    }

    @Override protected int createProgram(Context applicationContext) {
        return GlUtil.createProgram(applicationContext, R.raw.vertex_shader_2d_two_input,
                R.raw.fragment_shader_2d_blend_soft_light);
    }
}