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
import android.graphics.Color;
import android.view.MotionEvent;

public class LiveWallPaperExample3 extends LiveWallPaper {
	private static final int[] images = {
		R.drawable.image0,R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,
		R.drawable.image10,R.drawable.image11,R.drawable.image12,R.drawable.image13,R.drawable.image14,};
	private int touchSelect = 0;
	
	public LiveWallPaperExample3() {
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

	// 描画する画像を変更
	@Override
	public void ChangeImage() {
		BitmapImage = BitmapFactory.decodeResource(getResources(), ImageResources[touchSelect * 5 + Offset]);
	}

	// キャンバスに描画を行う 
	@Override
	public void DrawCanvas(Canvas canvas) {
		super.DrawCanvas(canvas);
	}

	// 再度描画が行われる前に呼び出される
	@Override
	public void DrawDelay() {
		touchSelect = 0;
		// 描画処理が行われた後、再度描画処理が呼び出されないようにする
		DelayMillis = 0;
	}
	
	// ダブルタップした時の処理
	@Override
	public boolean DoubleTap(MotionEvent event) {
		touchSelect = 1;
		// 描画処理が行われた後、設定時間後に再度描画処理が呼び出される
		DelayMillis = 3000;	// millisecond
		// 描画処理を呼び出す
		return true;
	}
}
