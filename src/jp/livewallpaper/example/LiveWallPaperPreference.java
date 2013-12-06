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

import jp.library.weatherforecast.WeatherForecast;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

@SuppressWarnings("deprecation")
public class LiveWallPaperPreference extends PreferenceActivity implements
		SharedPreferences.OnSharedPreferenceChangeListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);

		ListPreference LocationId = (ListPreference) getPreferenceScreen()
				.findPreference("LocationId");
		WeatherForecast weatherForecast = new WeatherForecast();
		String[] entriesResId = new String[weatherForecast.getLocationIDs().length];
		String[] entryValues = new String[weatherForecast.getLocationIDs().length];
		for (int i = 0; i < weatherForecast.getLocationIDs().length; i++) {
			int id = weatherForecast.getLocationIDs()[i];
			entriesResId[i] = weatherForecast.getLocationName(id);
			entryValues[i] = String.valueOf(id);
		}
		LocationId.setEntries(entriesResId);
		LocationId.setEntryValues(entryValues);

		// 設定が変更された時に呼び出されるListenerを登録
		SharedPreferences setting = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		setting.registerOnSharedPreferenceChangeListener(this);
		onSharedPreferenceChanged(setting, null);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 設定が変更された時に呼び出されるListenerを削除
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		ListPreference color = (ListPreference) getPreferenceScreen()
				.findPreference("color");
		// 設定が変更された時、選択された項目を表示
		color.setSummary(color.getEntry());
		ListPreference LocationId = (ListPreference) getPreferenceScreen()
				.findPreference("LocationId");
		// 設定が変更された時、選択された項目を表示
		LocationId.setSummary(LocationId.getEntry());
	}
}
