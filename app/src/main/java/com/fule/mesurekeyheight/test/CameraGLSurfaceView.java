package com.fule.mesurekeyheight.test;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.AttributeSet;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2017/9/15.
 */

public class CameraGLSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {
    private Context mContext;
    private int mTextureID = -1;
    private SurfaceTexture mSurface;
    private DirectDrawer mDirectDrawer;

    public CameraGLSurfaceView(Context context) {
        super(context);
        this.mContext = context;

    }

    public CameraGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setEGLContextClientVersion(2);//设置了GLSurfaceView的版本
        setRenderer(this);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
        //当有数据时才rendered或者主动调用了GLSurfaceView的requestRender.默认是连续模式，
        // 很显然Camera适合脏模式，一秒30帧，当有数据来时再渲染。
    }



    private int createTextureID() {
        int[] texture = new int[1];
        GLES20.glGenTextures(1,texture,0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,texture[0]);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        return texture[0];
    }

    @Override
    public void onPause() {
        super.onPause();
        //停止相机 释放相机
        CameraInterface.getInstance().doStopCamera(mContext);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    // 黑色背景
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        // 启用阴影平滑（不是必须的）
        gl.glShadeModel(GL10.GL_SMOOTH);
        // 设置深度缓存
        gl.glClearDepthf(1.0f);
        // 启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // 所作深度测试的类型
        gl.glDepthFunc(GL10.GL_LEQUAL);
        // 对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        mTextureID = createTextureID(); //纹理ID。
        mSurface = new SurfaceTexture(mTextureID);
        mSurface.setOnFrameAvailableListener(this);
        mDirectDrawer = new DirectDrawer(mTextureID);
        //初始化相机   打开Camera
        try {
            CameraInterface.getInstance().doOpenCamera(mContext,mSurface);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 设置画面的大小
        gl.glViewport(0, 0, width, height);
        // 设置投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // 重置投影矩阵
        gl.glLoadIdentity();
        // 设置画面比例
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,100.0f);
        // 选择模型观察矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // 重置模型观察矩阵
        gl.glLoadIdentity();

        CameraInterface.getInstance().doStartPreview(mSurface, 1.33f);
//        GLES20.glViewport(0,0,width,height);
        //开始预览  开启预览
     /*   if(!CameraInterface.getInstance().isPreviewing()){
            try {
                CameraInterface.getInstance().doStartPreview(mSurface, 1.33f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 清除屏幕和深度缓存

        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        mSurface.updateTexImage();
        float[] mtx = new float[16];
        mSurface.getTransformMatrix(mtx);
        mDirectDrawer.draw(mtx);
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.requestRender();
        //当有数据上来后会进到此方法   主动调用渲染器
    }
}
