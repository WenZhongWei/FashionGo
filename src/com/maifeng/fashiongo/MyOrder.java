package com.maifeng.fashiongo;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.maifeng.fashiongo.base.GetMyOrderType;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

public class MyOrder extends Activity implements OnClickListener{
	//初始化顶部导航栏控件
	private View topbar;
	private LinearLayout ll_returnbtn;
	private TextView tv_title;
	private RadioButton stay_payment,stay_delivery,stay_goods,have_goods;
	private ListView list_myorder;
	private String type="1";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myorder_layout);
		topbar =findViewById(R.id.topbar);
		// 顶部导航栏控件id
		ll_returnbtn = (LinearLayout)topbar.findViewById(R.id.ll_returnbtn);
		tv_title = (TextView)topbar.findViewById(R.id.tv_title);
		
		//顶部导航栏控件相关设置
		tv_title.setText("我的订单");
		topbar.findViewById(R.id.tv_name_function).setVisibility(View.INVISIBLE);
		setOnclick();
		setLight(0);
		
	}
	private void setOnclick(){
		stay_delivery=(RadioButton)findViewById(R.id.stay_delivery);
		stay_payment=(RadioButton)findViewById(R.id.stay_payment);
		stay_goods=(RadioButton)findViewById(R.id.stay_goods);
		have_goods=(RadioButton)findViewById(R.id.have_goods);
		list_myorder=(ListView)findViewById(R.id.list_myorder);
		
		ll_returnbtn.setOnClickListener(this);
		stay_payment.setOnClickListener(this);
		stay_delivery.setOnClickListener(this);
		stay_goods.setOnClickListener(this);
		have_goods.setOnClickListener(this);
	}

	/**
	 * 设置开关
	 * 
	 * @param i
	 */
	private void setLight(int i) {
		Lightoff();
		switch (i) {
		case 0:
			stay_payment.setChecked(true);
			break;
		case 1:
			stay_delivery.setChecked(true);
			break;
		case 2:
			stay_goods.setChecked(true);
			break;
		case 3:
			have_goods.setChecked(true);
			break;
		}
	}

	/**
	 * 开关全暗
	 */
	private void Lightoff() {
		stay_payment.setChecked(false);
		stay_delivery.setChecked(false);
		stay_goods.setChecked(false);
		have_goods.setChecked(false);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_returnbtn:
			finish();
			break;
		case R.id.stay_payment:
			setLight(0);
			type="1";
			VolleyPost(type);
			
			break;
		case R.id.stay_delivery:
			setLight(1);
			type="2";
			VolleyPost(type);
			
			break;
		case R.id.stay_goods:
			setLight(2);
			type="3";
			VolleyPost(type);
			break;
		case R.id.have_goods:
			setLight(3);
			type="4";
			VolleyPost(type);
			break;
		default:
			break;
		}
	}

	private void VolleyPost(String type){
		//组装请求数据
		Map<String,String> map = new HashMap<String, String>();
		//登录标识
		SharedPreferences pref = getSharedPreferences("myPref", MODE_PRIVATE);
		String accessToken = pref.getString("accessToken", "");
		map.put("accessToken", accessToken);
		map.put("type", type);
		map.put("page","1");
		VolleyRequest.RequestPost(this,Urls.GET_MY_ORDER,"GET_MY_ORDER", map,
				new VolleyAbstract(this, VolleyAbstract.listener,VolleyAbstract.errorListener,true) {
					
					@Override
					public void onMySuccess(String result) {
						// TODO Auto-generated method stub
						GetMyOrderType order = JsonUtil.parseJsonToBean(result,GetMyOrderType.class);
						Toast.makeText(getApplicationContext(),order.getMessage(),Toast.LENGTH_SHORT).show();
						System.out.println("---MyOrder---"+result);
					}
					
					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub
						System.out.println("请求失败了");
					}
				});
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Volleyhandle.getInstance(this.getApplicationContext()).getRequestQueue().cancelAll("GET_MY_ORDER");
	}
	
}
