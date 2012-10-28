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

class World {
   private final IntBuffer mVertexBuffer;
   private final IntBuffer mTextureBuffer;
   
   private List<Node> nodes = new ArrayList<Node>();
   
   public World() {
      
      int one = 65536;
      int gold = (int)((double)one/1.61803398875);
      int half = one / 2;
      int vertices[] = {
-gold,+one,0,
+gold,+one,0,
0,+gold,-one,
0,+gold,+one,
-one,0,-gold,
-one,0,+gold,
+one,0,-gold,
+one,0,+gold,
0,-gold,-one,
0,-gold,+one,
-gold,-one,0,
+gold,-one,0,
      };

      
      
      int texCoords[] = {
            // FRONT
            0, one, one, one, 0, 0, one, 0,
            // BACK
            one, one, one, 0, 0, one, 0, 0,
            // LEFT
            one, one, one, 0, 0, one, 0, 0,
            // RIGHT
            one, one, one, 0, 0, one, 0, 0,
            // TOP
            one, 0, 0, 0, one, one, 0, one,
            // BOTTOM
            0, 0, 0, one, one, 0, one, one, };
      

      
      // Buffers to be passed to gl*Pointer() functions must be
      // direct, i.e., they must be placed on the native heap
      // where the garbage collector cannot move them.
      //
      // Buffers with multi-byte data types (e.g., short, int,
      // float) must have their byte order set to native order
      ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
      vbb.order(ByteOrder.nativeOrder());
      mVertexBuffer = vbb.asIntBuffer();
      mVertexBuffer.put(vertices);
      mVertexBuffer.position(0);
      

      
      // ...
      ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4);
      tbb.order(ByteOrder.nativeOrder());
      mTextureBuffer = tbb.asIntBuffer();
      mTextureBuffer.put(texCoords);
      mTextureBuffer.position(0);
      
   }
   

   public void draw(GL10 gl) { 
      gl.glVertexPointer(3, GL10.GL_FIXED, 0, mVertexBuffer);
      
      
      gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, mTextureBuffer);
      gl.glColor4f(1, 1, 1, 1);
      gl.glNormal3f(0, 0, 1);
      gl.glDrawArrays(GL10.GL_POINTS, 0, 12);
      
      for (Node temp : nodes) {
	temp.draw(gl);
      }
   }
   
   
   static void loadTexture(GL10 gl, Context context, int resource) {
      Bitmap bmp = BitmapFactory.decodeResource(
            context.getResources(), resource);
      GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
      gl.glTexParameterx(GL10.GL_TEXTURE_2D,
            GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
      gl.glTexParameterx(GL10.GL_TEXTURE_2D,
            GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
      bmp.recycle();
   }
   
}

