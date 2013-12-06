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

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class LiveWallPaperExample1 extends LiveWallPaper {
	// Offsetが変わると画像が切り替わります
	private static final int[] images = {
		R.drawable.image0,R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,};
	
	public LiveWallPaperExample1() {
		// ChangeImage()で使用する変数を設定
		ImageResources = images;
		//　背景色を白に変更(デフォルトは黒)
		BackgroundColor = Color.WHITE;
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
	
	// 表示状態変更時に呼び出される 
	@Override
	public void VisibilityChanged(boolean visible){
		// スーパークラスの処理は、何もしない
		super.VisibilityChanged(visible);
	}
	
	// サーフェイス変更時に呼び出される
	@Override
	public void SurfaceChanged(int format, int width, int height){
		// スーパークラスの処理は、何もしない
		super.SurfaceChanged(format, width, height);
	}

	// オフセット変更時に呼び出される
	@Override
	public boolean OffsetsChanged(float xOffset, float yOffset, float xStep, float yStep, int xPixels, int yPixels){
		// スーパークラスの処理は、オフセットが変わった場合、値を設定しtrueを返す
		return super.OffsetsChanged(xOffset, yOffset, xStep, yStep, xPixels, yPixels);
	}

	// 描画する画像を変更
	@Override
	public void ChangeImage() {
		// スーパークラスの処理は、ダブルタップした回数に合わせて画像を変更する
		super.ChangeImage();
	}

	// キャンバスに描画を行う 
	@Override
	public void DrawCanvas(Canvas canvas) {
		// スーパークラスの処理は、画像を画面サイズにあわせて描画する
		super.DrawCanvas(canvas);
	}

	// 再度描画が行われる前に呼び出される
	@Override
	public void DrawDelay() {
		// スーパークラスの処理は、何もしない
		super.DrawDelay();
	}
	
	// ダブルタップした時の処理
	@Override
	public boolean DoubleTap(MotionEvent event) {
		// スーパークラスの処理は、何もしない
		return super.DoubleTap(event);
	}
	// フリックした時の処理
	@Override
	public boolean Fling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
		// スーパークラスの処理は、何もしない
		return super.Fling(event1, event2, velocityX, velocityY);
	}
	// 長押しした時の処理
	@Override
	public boolean LongPress(MotionEvent event) {
		// スーパークラスの処理は、何もしない
		return super.LongPress(event);
	}
	// スクロールした時の処理
	@Override
	public boolean Scroll(MotionEvent event1, MotionEvent event2, float distanceX, float distanceY) {
		// スーパークラスの処理は、何もしない
		return super.Scroll(event1, event2, distanceX, distanceY);
	}
	// シングルタップした時の処理
	@Override
	public boolean SingleTapConfirmed(MotionEvent event) {
		// スーパークラスの処理は、何もしない
		return super.SingleTapConfirmed(event);
	}
	// 進行中のジェスチャーに対するスケーリングイベントの処理
	@Override
	public boolean Scale(ScaleGestureDetector detector){
		// スーパークラスの処理は、何もしない
		return super.Scale(detector);
	}
	// スケーリングジェスチャーの開始の処理
	@Override
	public boolean ScaleBegin(ScaleGestureDetector detector){
		// スーパークラスの処理は、何もしない
		return super.ScaleBegin(detector);
	}
	// スケールジェスチャの終了の処理
	@Override
	public boolean ScaleEnd(ScaleGestureDetector detector){
		// スーパークラスの処理は、何もしない
		return super.ScaleEnd(detector);
	}
	// センサが変化した時の処理
	@Override
	public boolean SensorChanged(int type, float [] values){
		// スーパークラスの処理は、何もしない
		return super.SensorChanged(type, values);
	}
}
