package com.maifeng.fashiongo.volleyhandle;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Volleyhandle {
	private static Volleyhandle instance;//ʵ��
	
	private RequestQueue queues;//�������
	
	private  Context context;//������
	
	
	private Volleyhandle(Context context){
		this.context = context;
		queues = getRequestQueue();
		
	}
	
	//�첽��ȡ��ʵ��
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
