package com.maifeng.fashiongo.volleyhandle;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Volleyhandle {
	private static Volleyhandle instance;//实例
	
	private RequestQueue queues;//请求队列
	
	private  Context context;//上下文
	
	
	private Volleyhandle(Context context){
		this.context = context;
		queues = getRequestQueue();
		
	}
	
	//异步获取单实例
	public static synchronized Volleyhandle getInstance(Context context){
		if (instance == null) {
			instance = new Volleyhandle(context);
		}
		return instance;
	}
	
	public RequestQueue getRequestQueue() {
		if (queues == null) {
			
			queues=Volley.newRequestQueue(context.getApplicationContext());
		}
		return queues;
	}
	
	
}
