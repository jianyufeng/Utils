package com.fule.mesurekeyheight.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.Surface;


import com.fule.mesurekeyheight.util.ScreenUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 */

public class CameraInterface {
    private static CameraInterface instance;
    private boolean previewing;

    public static CameraInterface getInstance() {
        if (instance == null) {
            synchronized (CameraInterface.class) {
                if (instance == null) {
                    instance = new CameraInterface();
                }
            }
        }
        return instance;
    }

    private CameraInterface() {

    }
    private Activity activity ;
    public void doOpenCamera(Context c, SurfaceTexture mSurface) throws IOException {
        this.activity = (Activity) c;
        this.mSurface = mSurface;
        initCamera();
    }
    private Camera mCamera;
    private int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private int mWidth;// 视频分辨率宽度
    private int mHeight;// 视频分辨率高度
    private boolean isOpenCamera;// 是否一开始就打开摄像头


    private void initCamera() throws IOException {
        if (mCamera != null) {
            freeCameraResource();
        }
        try {

            mCamera = Camera.open(cameraId);
            isOpenCamera = true;
        } catch (Exception e) {
            e.printStackTrace();
            freeCameraResource();

        }

        Camera.Parameters mParams = mCamera.getParameters();
        mParams.setPreviewFrameRate(30);
        mParams.setPreviewFpsRange(15000,30000);
        List<int[]> fpsRange = mParams.getSupportedPreviewFpsRange();
        if (fpsRange.size() == 1) {
            //fpsRange.get(0)[0] < CAMERA_PREVIEW_FPS < fpsRange.get(0)[1]
            int CAMERA_PREVIEW_FPS = (int)(fpsRange.get(0)[0] + fpsRange.get(0)[1])/2;
            mParams.setPreviewFpsRange(CAMERA_PREVIEW_FPS,CAMERA_PREVIEW_FPS);
        } else {
            //pick first from list to limit framerate or last to maximize framerate
            mParams.setPreviewFpsRange(fpsRange.get(0)[0], fpsRange.get(0)[1]);
        }
        List<String> focusModes = mParams.getSupportedFocusModes();
        if (focusModes.contains("continuous-video")) {
            mParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            mCamera.cancelAutoFocus();
        }
//        mCamera.setDisplayOrientation(90);
        setCameraDisplayOrientation(cameraId);
        Camera.Size size = getCloselyPreSize(ScreenUtil.getScreenWidth(activity), ScreenUtil.getScreenHeight(activity)
                , mParams.getSupportedPreviewSizes());
        if (size!=null){
            mWidth = size.width;
            mHeight = size.height;
        }
        mParams.setPreviewSize(mWidth, mHeight);
        mCamera.setParameters(mParams);
        mCamera.setPreviewTexture(mSurface);
        mCamera.startPreview();
        mCamera.unlock();
    }
    public void switchCamera(boolean reset) throws IOException {
        if (!reset) {
            int numberOfCameras = Camera.getNumberOfCameras();
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            for (int i = 0; i < numberOfCameras; i++) {
                Camera.getCameraInfo(i, cameraInfo);
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    if (cameraId != Camera.CameraInfo.CAMERA_FACING_FRONT) {
                        cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
                        break;
                    }
                } else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    if (cameraId != Camera.CameraInfo.CAMERA_FACING_BACK) {
                        cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
                        break;
                    }
                }
            }
        }
        initCamera();
    }
    public void setCameraDisplayOrientation(int cameraId) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        mCamera.setDisplayOrientation(result);
    }

    /**
     * 通过对比得到与宽高比最接近的尺寸（如果有相同尺寸，优先选择）
     *
     * @param surfaceWidth  需要被进行对比的原宽
     * @param surfaceHeight 需要被进行对比的原高
     * @param preSizeList   需要对比的预览尺寸列表
     * @return 得到与原宽高比例最接近的尺寸
     */
    private Camera.Size getCloselyPreSize(int surfaceWidth, int surfaceHeight,
                                          List<Camera.Size> preSizeList) {

        int ReqTmpWidth;
        int ReqTmpHeight;
        // 当屏幕为垂直的时候需要把宽高值进行调换，保证宽大于高
        ReqTmpWidth = surfaceHeight;
        ReqTmpHeight = surfaceWidth;

        //先查找preview中是否存在与surfaceview相同宽高的尺寸
        for (Camera.Size size : preSizeList) {
            if ((size.width == ReqTmpWidth) && (size.height == ReqTmpHeight)) {
                return size;
            }
        }

        // 得到与传入的宽高比最接近的size
        float reqRatio = ((float) ReqTmpWidth) / ReqTmpHeight;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        Camera.Size retSize = null;
        for (Camera.Size size : preSizeList) {
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio;
                retSize = size;
            }
        }
        return retSize;
    }

    /**
     * 释放摄像头资源
     *
     * @author liuyinjun
     * @date 2015-2-5
     */
    private void freeCameraResource() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.lock();
            mCamera.release();
            mCamera = null;
        }
        isOpenCamera = false;
    }

    public boolean isPreviewing() {
        return isOpenCamera;
    }
    private SurfaceTexture mSurface;

    public void doStopCamera(Context c) {
        freeCameraResource();
    }
}
