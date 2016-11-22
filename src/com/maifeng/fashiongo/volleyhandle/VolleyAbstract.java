package com.maifeng.fashiongo.volleyhandle;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.maifeng.fashiongo.util.LogUtil;

import android.app.ProgressDialog;
import android.content.Context;

public abstract class VolleyAbstract {

	public Context context;
	public static Listener<String> listener;
	public static ErrorListener errorListener;
	private ProgressDialog mdialog;// 加载窗口
	
	public VolleyAbstract(Context context,Listener<String>listener,
			ErrorListener errorListener,Boolean isDialog){
		
		this.context = context;
		if (!(mdialog == null || !mdialog.isShowing())) {
			mdialog.dismiss();
		}
		if (isDialog) {
			mdialog = ProgressDialog.show(context, null, "加载中", true, true,null);
		}
//		this.listener=listener;
//		this.errorListener=errorListener;
	}
	public abstract void onMySuccess(String result);
	public abstract void onMyError(VolleyError error);
	
	public Listener<String> loadingListener(){


	
		 listener=new Listener<String>() {

			@Override
			public void onResponse(String response) {
				if (!(mdialog == null || !mdialog.isShowing())) {
					mdialog.dismiss();	
				}
				onMySuccess(response);
//			Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
				LogUtil.i("请求成功", response);
			}
		};
		return listener;
	}
	
	public ErrorListener errorListener(){
		errorListener = new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				if (!(mdialog == null || !mdialog.isShowing())) {
					mdialog.dismiss();	
				}
				onMyError(error);
//				Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
				LogUtil.i("请求失败", error.toString());
				
			}
		};
		return errorListener;
		
	}
	
}
