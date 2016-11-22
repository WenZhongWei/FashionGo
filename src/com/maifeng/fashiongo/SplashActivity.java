package com.maifeng.fashiongo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;

/**
 * 1.欢迎界面的运用
 *   1、欢迎界面：
 * 分析详细的功能点：
 *      1）应用的第一个界面
 *      2）3秒之后自动关闭
 *            第一次使用的时候就跳转到新手引导
 *            不是第一次使用就跳转到主页面
 *      3）不能返回
 *      4）没有标题栏
 *             a） 2.X版本，没有标题栏 （noTitleBar）
 *              b） 4.X版本，没有标题栏 （noActionBar） 
 * 
 *
 */

public class SplashActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
				boolean isFirst = sp.getBoolean("isFirst", true);
				if (isFirst) {
					Editor editor = sp.edit();
					editor.putBoolean("isFirst", false);
					editor.commit();
					Intent intent = new Intent();
					intent.setClass(SplashActivity.this,UserGuideActivity.class);
					startActivity(intent);
					
				} else {
					Intent intent = new Intent();
					intent.setClass(SplashActivity.this,MainActivity.class);
					startActivity(intent);
				}
				finish();
			}
		}, 3000);
	}
	
	/**
	 * 点击Back健不能返回
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
