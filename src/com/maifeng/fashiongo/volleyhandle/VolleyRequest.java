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
		//����tagֵ����ָ���Ķ���ȫ���ر�
		Volleyhandle.getInstance(context).getRequestQueue().cancelAll(tag);
		
		stringRequest = new StringRequest(Method.GET, url, vif.loadingListener(), vif.errorListener());
		
		//���������ñ�ǩ  ����������
		stringRequest.setTag(tag);
		
		//��������뵽����
		Volleyhandle.getInstance(context).getRequestQueue().add(stringRequest);
		
	}
	
	public static void RequestPost(Context context,String url,String tag,final Map<String, String> map,VolleyAbstract vif){
		
		//����tagֵ����ָ���Ķ���ȫ���ر�
		Volleyhandle.getInstance(context).getRequestQueue().cancelAll(tag);
		
		stringRequest = new StringRequest(Method.POST,url, vif.loadingListener(), vif.errorListener()){
			
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {

				return map;
			}
		};
		//���������ñ�ǩ  ����������
		stringRequest.setTag(tag);
		
		//��������뵽����
		Volleyhandle.getInstance(context).getRequestQueue().add(stringRequest);
	}
}
