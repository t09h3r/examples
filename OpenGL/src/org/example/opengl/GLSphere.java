/***
* Excerpted from "Hello, Android!",
* published by The Pragmatic Bookshelf.
* Copyrights apply to this code. It may not be used to create training material, 
* courses, books, articles, and the like. Contact us if you are in doubt.
* We make no guarantees that this code is fit for any purpose. 
* Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/

package org.example.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.util.ArrayList;
import java.util.List;
import java.nio.*;
import android.animation.*;

class GLSphere extends Entity{
	private final FloatBuffer mVertexBuffer;
	private final ShortBuffer mIndexBuffer;
	private final NormalBuffer mNormalBuffer;
	
	private List<Ti> tris=new ArrayList<Ti>();
	public GLSphere() {
		float one = 1;
		float gold = (float)((double)one/1.61803398875);
		float half = one / 2;
		List<V3> vertices = new ArrayList<V3>();
		vertices.add(new V3(-gold,+one,0));
		vertices.add(new V3(+gold,+one,0));
		vertices.add(new V3(0,+gold,-one));
		vertices.add(new V3(0,+gold,+one));
		vertices.add(new V3(-one,0,-gold));
		vertices.add(new V3(-one,0,+gold));
		vertices.add(new V3(+one,0,-gold));
		vertices.add(new V3(+one,0,+gold));
		vertices.add(new V3(0,-gold,-one));
		vertices.add(new V3(0,-gold,+one));
		vertices.add(new V3(-gold,-one,0));
		vertices.add(new V3(+gold,-one,0));
		
		tris.add(new Ti(0,1,2));
		tris.add(new Ti(1,0,3));
		tris.add(new Ti(0,2,4));
		tris.add(new Ti(0,4,5));
		tris.add(new Ti(3,0,5));
		tris.add(new Ti(2,1,6));
		tris.add(new Ti(6,1,7));
		tris.add(new Ti(1,3,7));
		tris.add(new Ti(3,5,9));
		tris.add(new Ti(7,3,9));
		tris.add(new Ti(4,2,8));
		tris.add(new Ti(2,6,8));
		tris.add(new Ti(4,8,10));
		tris.add(new Ti(5,4,10));
		tris.add(new Ti(9,5,10));
		tris.add(new Ti(9,10,11));
		tris.add(new Ti(7,9,11));
		tris.add(new Ti(6,7,11));
		tris.add(new Ti(8,6,11));
		tris.add(new Ti(10,8,11));
		
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.size()*4*3);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer = vbb.asFloatBuffer();
		for(int i=0;i<vertices.size();i++){
			V3 v=vertices.get(i);
			mVertexBuffer.put(new float[]{v.x,v.y,v.z});
		}
		
		mVertexBuffer.position(0);
		
		mIndexBuffer = ByteBuffer.allocateDirect(tris.size() * 2*3).order(ByteOrder.nativeOrder()).asShortBuffer();
		for(int i=0;i<tris.size();i++){
			Ti t=tris.get(i);
			mIndexBuffer.put(t.vs);
		}
		
		mIndexBuffer.position(0);
		
		
		
		// ...
		//ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4);
		//tbb.order(ByteOrder.nativeOrder());
		//mTextureBuffer = tbb.asIntBuffer();
		//mTextureBuffer.put(texCoords);
		//mTextureBuffer.position(0);
		
	}
	
	public void SubDivide(int iDivs){
		
	}
	
	@Override
	public void draw(GL10 gl) {
		gl.glPushMatrix();
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		if (mNormalBuffer != null) {
			// Enabled the normal buffer for writing and to be used during rendering.
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			
			// Specifies the location and data format of an array of normals to use when rendering.
			gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);
		}
		//	gl.glEnableClientState(GL10.GL_INDEN_ARRAY);
		gl.glScalef(1f,1f,1f);
		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		//	gl.glIndexPointer(3,GL10.GL_SHORT,0,mIndexBuffer);
		
		gl.glColor4f(1, 1, 1, 1);
		gl.glNormal3f(0, 0, 1);
		gl.glDrawElements(GL10.GL_TRIANGLES, 60,GL10.GL_UNSIGNED_SHORT,mIndexBuffer);
		
		gl.glPopMatrix();
	}
	
	
	static void loadTexture(GL10 gl, Context context, int resource) {
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), resource);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		bmp.recycle();
	}
	
}

