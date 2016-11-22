package com.maifeng.fashiongo.volleyhandle;

import java.util.Map;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;

public class VolleyRequest {
	public static StringRequest stringRequest;
	//public Context context;
	

	public static void RequestGet(Context context,String url,String tag,
			VolleyAbstract vif){
		//给定tag值，将指定的队列全部关闭
		Volleyhandle.getInstance(context).getRequestQueue().cancelAll(tag);
		
		stringRequest = new StringRequest(Method.GET, url, vif.loadingListener(), vif.errorListener());
		
		//给请求设置标签  用于与活动关联
		stringRequest.setTag(tag);
		
		//将请求加入到队列
		Volleyhandle.getInstance(context).getRequestQueue().add(stringRequest);
		
	}
	
	public static void RequestPost(Context context,String url,String tag,final Map<String, String> map,VolleyAbstract vif){
		
		//给定tag值，将指定的队列全部关闭
		Volleyhandle.getInstance(context).getRequestQueue().cancelAll(tag);
		
		stringRequest = new StringRequest(Method.POST,url, vif.loadingListener(), vif.errorListener()){
			
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {

				return map;
			}
		};
		//给请求设置标签  用于与活动关联
		stringRequest.setTag(tag);
		
		//将请求加入到队列
		Volleyhandle.getInstance(context).getRequestQueue().add(stringRequest);
	}
}
