package com.fule.mesurekeyheight.test.camerafilter.filter;

import android.content.Context;
import android.support.annotation.DrawableRes;

import com.fule.mesurekeyheight.R;
import com.fule.mesurekeyheight.test.camerafilter.gles.GlUtil;


public class CameraFilterBlendSoftLight extends CameraFilterBlend {

    public CameraFilterBlendSoftLight(Context context, @DrawableRes int drawableId) {
        super(context, drawableId);
    }

    @Override protected int createProgram(Context applicationContext) {

        return GlUtil.createProgram(applicationContext, R.raw.vertex_shader_two_input,
                R.raw.fragment_shader_ext_blend_soft_light);
    }
}