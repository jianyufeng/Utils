package com.fule.mesurekeyheight.test.camerafilter.filter.BlurFilter;

import android.content.Context;

import com.fule.mesurekeyheight.test.camerafilter.filter.CameraFilter;
import com.fule.mesurekeyheight.test.camerafilter.filter.FilterGroup;

public class CameraFilterGaussianBlur extends FilterGroup<CameraFilter> {

    public CameraFilterGaussianBlur(Context context, float blur) {
        super();
        addFilter(new CameraFilterGaussianSingleBlur(context, blur, false));
        addFilter(new CameraFilterGaussianSingleBlur(context, blur, true));
    }
}
