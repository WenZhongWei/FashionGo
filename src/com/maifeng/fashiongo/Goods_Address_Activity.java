package com.maifeng.fashiongo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.android.volley.VolleyError;
import com.maifeng.fashiongo.adapter.Goods_GetAddress_Adapter;
import com.maifeng.fashiongo.base.Goods_GetAddressData;
import com.maifeng.fashiongo.base.Goods_GetAddressType;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

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

public class Goods_Address_Activity extends Activity {

	//初始化顶部导航栏控件
	private View topbar;
	private LinearLayout ll_returnbtn;
	private LinearLayout ll_functionbtn;
	private TextView tv_title;
	private TextView tv_name_function;
	private Goods_GetAddress_Adapter adapter;
	private ListView lv_goodsaddress;
	private List<Goods_GetAddressData> listaddress;
	private String codeString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_center_goodsaddress);
		initView();
		//点击事件

		Clicks();
		lv_goodsaddress.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent = new Intent();
				codeString=getIntent().getStringExtra("Code_address");
				if (codeString.equals("shopcar_address")) {
					intent.putExtra("Code", "adress");
					intent.putExtra("adress", listaddress.get(position).getProvince()+listaddress.get(position).getCity()+listaddress.get(position).getArea()+listaddress.get(position).getAddress());
					intent.putExtra("id_adress", listaddress.get(position).getId());
					setResult(101,intent);
					finish();
				}else if (codeString.equals("Mine")) {
					intent.setClass(getApplicationContext(),Edit_GoodsAddress_Activity.class);
					SharedPreferences pref = getSharedPreferences("myPref", MODE_PRIVATE);
					String accessToken = pref.getString("accessToken", "");
					String idString = listaddress.get(position).getId();
					String names = listaddress.get(position).getName();
					String phones = listaddress.get(position).getPhone();
					String provinces = listaddress.get(position).getProvince();
					String citys = listaddress.get(position).getCity();
					String areas = listaddress.get(position).getArea();
					String addresss = listaddress.get(position).getAddress();
					intent.putExtra("accessToken",accessToken);
					intent.putExtra("id", idString);
					intent.putExtra("name",names);
					intent.putExtra("phone", phones);
					intent.putExtra("province",provinces);
					intent.putExtra("city", citys);
					intent.putExtra("area",areas);
					intent.putExtra("address", addresss);
					startActivity(intent);
					/*overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
				}
				
				
			}
		});
		
		
		
	}
	private void initView() {
		topbar =findViewById(R.id.topbar);
		// 顶部导航栏控件id
		ll_returnbtn = (LinearLayout)topbar.findViewById(R.id.ll_returnbtn);
		tv_title = (TextView)topbar.findViewById(R.id.tv_title);
		ll_functionbtn = (LinearLayout)topbar.findViewById(R.id.ll_functionbtn);
		tv_name_function = (TextView)topbar.findViewById(R.id.tv_name_function);
		//顶部导航栏控件相关设置
		tv_title.setText("收货地址");
		tv_name_function.setText("新增");
		lv_goodsaddress = (ListView)findViewById(R.id.lv_goodsaddress);
		
	}
	private void Clicks(){
		ll_returnbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ll_functionbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Goods_Address_Activity.this,New_Goods_Address_Activity.class);
				startActivity(intent);
				/*overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
//				finish();
			}
		});
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		//组装请求数据
		Map<String,String> map = new HashMap<String, String>();
		//登录标识
		SharedPreferences pref = getSharedPreferences("myPref", MODE_PRIVATE);
		String accessToken = pref.getString("accessToken", "");
		map.put("accessToken", accessToken);
		VolleyRequest.RequestPost(this,Urls.GET_RECEIVE_ADDRESS,"GET_RECEIVE_ADDRESS", map,
				new VolleyAbstract(this,VolleyAbstract.listener,VolleyAbstract.errorListener,true) {
					@Override
					public void onMySuccess(String result) {
						// TODO Auto-generated method stub
						listaddress = JsonUtil.parseJsonToBean(result, Goods_GetAddressType.class).getData();
						 adapter = new Goods_GetAddress_Adapter(getApplicationContext(), listaddress);
						lv_goodsaddress.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					}
					
					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub
						
					}
				});
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Volleyhandle.getInstance(this.getApplicationContext()).getRequestQueue().cancelAll("GET_RECEIVE_ADDRESS");
	}
}
