package com.fule.mesurekeyheight.test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Square {
	// ������������
	private float vertices[] = { -1.0f, 1.0f, 0.0f, // 0, ����
			-1.0f, -1.0f, 0.0f, // 1, ����
			1.0f, -1.0f, 0.0f, // 2, ����
			1.0f, 1.0f, 0.0f, // 3, ����
	};
	// ���ӹ���
	private short[] indices = { 0, 1, 2, 0, 2, 3 };
	// ���㻺��
	private FloatBuffer vertexBuffer;
	// ��������
	private ShortBuffer indexBuffer;

	public Square() {
		// һ��floatΪ4 bytes, ���Ҫ����4
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		// short����ͬ��
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);
	}

	/**
	 * ���������ε���Ļ
	 * 
	 * @param gl
	 */
	public void draw(GL10 gl) {
		// ��ʱ�뻷��
		gl.glFrontFace(GL10.GL_CCW);
		// �����޳�����
		gl.glEnable(GL10.GL_CULL_FACE);
		// �޳�����
		gl.glCullFace(GL10.GL_BACK);
		// �������㻺��д�빦��
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// ���ö���
		// size:ÿ�������м�����ָ������
		// type:������ÿ��������������͡�
		// stride:������ÿ�������ļ�����������ֽ�λ�ƣ���
		// pointer:�洢��ÿ�����������ֵ����ʼֵΪ0
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
				GL10.GL_UNSIGNED_SHORT, indexBuffer);
		// �رո�������
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}
}
