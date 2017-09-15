package com.fule.mesurekeyheight.test;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements Renderer {
	
	private Square square;
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// ��ɫ����
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		// ������Ӱƽ�������Ǳ���ģ�
		gl.glShadeModel(GL10.GL_SMOOTH);
		// ������Ȼ���
		gl.glClearDepthf(1.0f);
		// ������Ȳ���
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// ������Ȳ��Ե�����
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// ��͸�ӽ�������
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		square = new Square();
	}

	public void onDrawFrame(GL10 gl) {
		// �����Ļ����Ȼ���
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// ����Ļ���ƶ��ĸ���λ
		gl.glTranslatef(0, 0, -4); 
		// ����������
		square.draw(gl);
		// ���õ�ǰ��ģ�͹۲����
		gl.glLoadIdentity();
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// ���û���Ĵ�С
		gl.glViewport(0, 0, width, height);
		// ����ͶӰ����
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// ����ͶӰ����
		gl.glLoadIdentity();
		// ���û������
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
				100.0f);
		// ѡ��ģ�͹۲����
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// ����ģ�͹۲����
		gl.glLoadIdentity();
	}
}