package com.maifeng.fashiongo.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * ����̴�����
 * 
 * @author liekkas
 * @time 2016/06/03
 */
public class KeyboradUtil {

	/**
	 * �������
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
	 * �ر������
	 */
	public static void closeKeyborad(Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// �õ�InputMethodManager��ʵ��
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(activity.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
}
