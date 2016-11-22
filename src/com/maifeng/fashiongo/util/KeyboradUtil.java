package com.maifeng.fashiongo.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 软键盘处理工具
 * 
 * @author liekkas
 * @time 2016/06/03
 */
public class KeyboradUtil {

	/**
	 * 打开软键盘
	 */
	public static void openKeyborad(final EditText titleInput) {
		titleInput.setFocusable(true);
		titleInput.requestFocus();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) titleInput
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}, 150);
	}

	/**
	 * 关闭软键盘
	 */
	public static void closeKeyborad(Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 得到InputMethodManager的实现
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(activity.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
}
