package com.maifeng.fashiongo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.maifeng.fashiongo.adapter.GetMyCollectionAdapter;
import com.maifeng.fashiongo.base.GetMyCollectionData;
import com.maifeng.fashiongo.base.GetMyCollectionType;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

public class GetMyCollectionActivity extends Activity {
	//初始化顶部导航栏控件
	private View topbar;
	private LinearLayout ll_returnbtn;
	private TextView tv_title;
	private TextView tv_name_function;
	
	private List<GetMyCollectionData> data;
	private GetMyCollectionAdapter adapter;
	private ListView list_mycollection;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getmycollection);

		topbar();
		collectionPost();
		
	}
	private void topbar(){
		topbar=this.findViewById(R.id.topbar);
		// 顶部导航栏控件id
		ll_returnbtn = (LinearLayout)topbar.findViewById(R.id.ll_returnbtn);
		tv_title = (TextView)topbar.findViewById(R.id.tv_title);
		tv_name_function = (TextView)topbar.findViewById(R.id.tv_name_function);
		//顶部导航栏控件相关设置
		tv_title.setText("我的收藏");
		tv_name_function.setVisibility(View.INVISIBLE);
		ll_returnbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		list_mycollection = (ListView)findViewById(R.id.list_mycollection);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Volleyhandle.getInstance(this.getApplicationContext()).getRequestQueue().cancelAll("GET_MY_COLLECTION");
	}
	private void collectionPost(){
		//组装请求数据
		Map<String,String> map = new HashMap<String, String>();
		//登录标识
		SharedPreferences pref = getSharedPreferences("myPref", MODE_PRIVATE);
		final String accessToken = pref.getString("accessToken", "");
		map.put("accessToken", accessToken);
		map.put("page","1");
		VolleyRequest.RequestPost(this,Urls.GET_MY_COLLECTION,"GET_MY_COLLECTION", map,
				new VolleyAbstract(this, VolleyAbstract.listener,VolleyAbstract.errorListener,true) {
					
					@Override
					public void onMySuccess(String result) {
						data = JsonUtil.parseJsonToBean(result, GetMyCollectionType.class).getData();
						
						adapter = new GetMyCollectionAdapter(getApplicationContext(), data, accessToken);
						list_mycollection.setAdapter(adapter);
						
						list_mycollection.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,View arg1, int arg2, long arg3) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(getApplicationContext(),GoodDetailActivity.class);
								intent.putExtra("Code","mycollection");
								intent.putExtra("goodsCode", data.get(arg2).goodsCode);
								startActivity(intent);
								/*overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
								
								finish();
								
							}
						});

						
					}
					@Override
					public void onMyError(VolleyError error) {
						
					}
				});
	}
}
