package com.fule.mesurekeyheight.test.camerafilter.filter.BlurFilter;

import android.content.Context;

import com.fule.mesurekeyheight.test.camerafilter.filter.CameraFilter;
import com.fule.mesurekeyheight.test.camerafilter.filter.FilterGroup;


public class ImageFilterGaussianBlur extends FilterGroup<CameraFilter> {

    public ImageFilterGaussianBlur(Context context, float blur) {
        super();
        addFilter(new ImageFilterGaussianSingleBlur(context, blur, false));
        addFilter(new ImageFilterGaussianSingleBlur(context, blur, true));
    }
}
