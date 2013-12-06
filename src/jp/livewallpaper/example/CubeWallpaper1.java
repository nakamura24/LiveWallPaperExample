/*
 * Copyright (C) 2012 M.Nakamura
 *
 * This software is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 2.1 Japan License.
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 		http://creativecommons.org/licenses/by-nc-sa/2.1/jp/legalcode
 */
package jp.livewallpaper.example;

import jp.library.livewallpaper.LiveWallPaper;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.view.MotionEvent;

// Android SDK Sample Cube を再現
public class CubeWallpaper1 extends LiveWallPaper {
    private final Paint mPaint = new Paint();
    private float mOffset;
    private float mTouchX = -1;
    private float mTouchY = -1;
    private long mStartTime;
    private float mCenterX;
    private float mCenterY;

	
	public CubeWallpaper1() {
        // Create a Paint to draw the lines for our cube
        final Paint paint = mPaint;
        paint.setColor(0xffffffff);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);

        mStartTime = SystemClock.elapsedRealtime();
        DelayMillis = 1000 / 25;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public Engine onCreateEngine() {
		return new LiveEngine();
	}
	
	// サーフェイス変更時に呼び出される
	@Override
	public void SurfaceChanged(int format, int width, int height){
        mCenterX = WidthPixels/2.0f;
        mCenterY = HeightPixels/2.0f;
	}

	// オフセット変更時に呼び出される
	@Override
	public boolean OffsetsChanged(float xOffset, float yOffset, float xStep, float yStep, int xPixels, int yPixels){
        mOffset = xOffset;
		return true;
	}
	
	@Override
	public void ChangeImage() {
		// dummy
		BitmapImage = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
	}

	// キャンバスに描画を行う 
	@Override
	public void DrawCanvas(Canvas canvas) {
		canvas.save();
		canvas.translate(mCenterX, mCenterY);
		canvas.drawColor(0xff000000);
        drawLine(canvas, -400, -400, -400,  400, -400, -400);
        drawLine(canvas,  400, -400, -400,  400,  400, -400);
        drawLine(canvas,  400,  400, -400, -400,  400, -400);
        drawLine(canvas, -400,  400, -400, -400, -400, -400);

        drawLine(canvas, -400, -400,  400,  400, -400,  400);
        drawLine(canvas,  400, -400,  400,  400,  400,  400);
        drawLine(canvas,  400,  400,  400, -400,  400,  400);
        drawLine(canvas, -400,  400,  400, -400, -400,  400);

        drawLine(canvas, -400, -400,  400, -400, -400, -400);
        drawLine(canvas,  400, -400,  400,  400, -400, -400);
        drawLine(canvas,  400,  400,  400,  400,  400, -400);
        drawLine(canvas, -400,  400,  400, -400,  400, -400);
        canvas.restore();
        
        drawTouchPoint(canvas);
	}

    /*
     * Draw a 3 dimensional line on to the screen
     */
    void drawLine(Canvas c, int x1, int y1, int z1, int x2, int y2, int z2) {
        long now = SystemClock.elapsedRealtime();
        float xrot = ((float)(now - mStartTime)) / 1000;
        float yrot = (0.5f - mOffset) * 2.0f;
        //float zrot = 0;

        // 3D transformations

        // rotation around X-axis
        float newy1 = (float)(Math.sin(xrot) * z1 + Math.cos(xrot) * y1);
        float newy2 = (float)(Math.sin(xrot) * z2 + Math.cos(xrot) * y2);
        float newz1 = (float)(Math.cos(xrot) * z1 - Math.sin(xrot) * y1);
        float newz2 = (float)(Math.cos(xrot) * z2 - Math.sin(xrot) * y2);

        // rotation around Y-axis
        float newx1 = (float)(Math.sin(yrot) * newz1 + Math.cos(yrot) * x1);
        float newx2 = (float)(Math.sin(yrot) * newz2 + Math.cos(yrot) * x2);
        newz1 = (float)(Math.cos(yrot) * newz1 - Math.sin(yrot) * x1);
        newz2 = (float)(Math.cos(yrot) * newz2 - Math.sin(yrot) * x2);

        // 3D-to-2D projection
        float startX = newx1 / (4 - newz1 / 400);
        float startY = newy1 / (4 - newz1 / 400);
        float stopX =  newx2 / (4 - newz2 / 400);
        float stopY =  newy2 / (4 - newz2 / 400);

        c.drawLine(startX, startY, stopX, stopY, mPaint);
    }

    /*
     * Draw a circle around the current touch point, if any.
     */
    void drawTouchPoint(Canvas c) {
        if (mTouchX >=0 && mTouchY >= 0) {
            c.drawCircle(mTouchX, mTouchY, 80, mPaint);
        }
    }
	// シングルタップした時の処理
	@Override
	public boolean SingleTapUp(MotionEvent event) {
        mTouchX = event.getX();
        mTouchY = event.getY();
		return true;
	}
}
