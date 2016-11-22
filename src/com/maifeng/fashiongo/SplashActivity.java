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
 * 1.��ӭ���������
 *   1����ӭ���棺
 * ������ϸ�Ĺ��ܵ㣺
 *      1��Ӧ�õĵ�һ������
 *      2��3��֮���Զ��ر�
 *            ��һ��ʹ�õ�ʱ�����ת����������
 *            ���ǵ�һ��ʹ�þ���ת����ҳ��
 *      3�����ܷ���
 *      4��û�б�����
 *             a�� 2.X�汾��û�б����� ��noTitleBar��
 *              b�� 4.X�汾��û�б����� ��noActionBar�� 
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
	 * ���Back�����ܷ���
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
