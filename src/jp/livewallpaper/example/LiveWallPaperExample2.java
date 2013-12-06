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

import java.text.SimpleDateFormat;
import java.util.*;

import jp.library.livewallpaper.LiveWallPaper;
import jp.library.weatherforecast.StaticHash;
import jp.library.weatherforecast.WeatherForecast;
import jp.library.weatherforecast.WeatherForecast.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.preference.PreferenceManager;

public class LiveWallPaperExample2 extends LiveWallPaper {
	private static final int[] images = {
			// R.drawable.dsc_0015,R.drawable.dsc_0017,R.drawable.dsc_0023,R.drawable.dsc_0025,R.drawable.dsc_0039,
			R.drawable.image0, R.drawable.image1, R.drawable.image2,
			R.drawable.image3, R.drawable.image4, R.drawable.image10,
			R.drawable.image11, R.drawable.image12, R.drawable.image13,
			R.drawable.image14, };
	private static final int[] colors = { Color.BLACK, Color.RED, Color.GREEN,
			Color.BLUE, Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.WHITE };
	private Random randam = new Random();
	private Context context;

	public LiveWallPaperExample2() {
		// ChangeImage()で使用する変数を設定
		ImageResources = images;
		// 背景色を白に変更(デフォルトは黒)
		BackgroundColor = Color.WHITE;
		// ActionTimeTickを有効にする
		UseTimeTick = true;
		// ActionBatteryChangedを有効にする
		UseBatteryChanged = true;
		context = this;
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
		// ランダムに画像を変更
		BitmapImage = BitmapFactory.decodeResource(getResources(),
				ImageResources[randam.nextInt(images.length)]);
	}

	// キャンバスに描画を行う
	@Override
	public void DrawCanvas(Canvas canvas) {
		// 設定された内容を取得
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		int color = Integer.parseInt(sharedPreferences.getString("color", "7"));
		// 背景色を設定
		BackgroundColor = colors[color];
		super.DrawCanvas(canvas);
		// 上書き
		if (sharedPreferences.getBoolean("overlay", false))
			OverLayer(canvas);
	}

	// キャンバスに上書きする
	private void OverLayer(Canvas canvas) {
		// Scaledは画面幅(Pixel)を160で割った値。480の場合3、720の場合4.5
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd(EEE)",
				Locale.JAPANESE);
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.JAPANESE);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		paint.setTextSize(6 * Scaled);
		canvas.drawText(sdf1.format(date), 6 * Scaled, 23 * Scaled, paint);
		paint.setTextSize(17 * Scaled);
		canvas.drawText(sdf2.format(date), 6 * Scaled, 38 * Scaled, paint);
		int battery = (int) ((double) BatteryLevel / BatteryScale * 100.0 + 0.5);
		paint.setTextSize(6 * Scaled);
		Resources resource = getResources();
		canvas.drawText(
				resource.getString(R.string.battery) + String.valueOf(battery)
						+ "%", 6 * Scaled, 45 * Scaled, paint);
		OverLayerForecast(canvas);
	}

	// キャンバスに天気予報を上書きする
	private void OverLayerForecast(Canvas canvas) {
		StaticHash hash = new StaticHash(context);
		int id = Integer.parseInt(hash.get("LocationId", "4410"));
		WeatherForecast weatherForecast = new WeatherForecast();
		Date now = new Date();
		Date LastUpdate = weatherForecast.getLastUpdate(context, id);
		if ((now.getTime() - LastUpdate.getTime()) > 10800000L) {
			weatherForecast.getForecast(context, id);
		}
		ArrayList<WeeklyForecast> getWeeklyForecasts = weatherForecast
				.getWeeklyForecast(context, id);
		if (getWeeklyForecasts == null || getWeeklyForecasts.size() == 0)
			return;
		// Scaledは画面幅(Pixel)を160で割った値。480の場合3、720の場合4.5
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		paint.setTextSize(12 * Scaled);
		canvas.drawText(getWeeklyForecasts.get(0).Forecast, 60 * Scaled,
				28 * Scaled, paint);
		paint.setTextSize(9 * Scaled);
		canvas.drawText(getWeeklyForecasts.get(0).Probability, 60 * Scaled,
				42 * Scaled, paint);
		paint.setTextSize(6 * Scaled);
		ArrayList<OneDayForecast> oneDayForecasts = weatherForecast
				.getOneDayForecast(context, id);
		for (int i = 0; i < oneDayForecasts.size() && i < 4; i++) {
			String text = oneDayForecasts.get(i).Hour + ":"
					+ oneDayForecasts.get(i).Forecast + " "
					+ oneDayForecasts.get(i).Temp;
			canvas.drawText(text, 120 * Scaled, (23 + i * 6) * Scaled, paint);
		}
	}
}
