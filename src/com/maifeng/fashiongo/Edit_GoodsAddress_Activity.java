package com.maifeng.fashiongo;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.VolleyError;
import com.maifeng.fashiongo.base.Detail_AddressData;
import com.maifeng.fashiongo.base.Detail_AddressType;
import com.maifeng.fashiongo.base.Edit_GetAddressType;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Edit_GoodsAddress_Activity extends Activity {

	private View topbar;
	private LinearLayout ll_returnbtn;
	private TextView tv_title;
	private LinearLayout ll_functionbtn;
	private RelativeLayout relative_pca;
	private TextView tv_name_function;
	private EditText dl_name;
	private EditText dl_phone;
	public static TextView dl_province;
	public static TextView dl_city;
	public static TextView dl_area;
	private EditText dl_detailaddress;
	private Detail_AddressData detail_AddressData;
	private Intent intent;
	private Button edit_save;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_goodsaddress);
		intent=this.getIntent();
		initView();
		getDetailAddress();
		idGet();
	}
	private void getDetailAddress() {
		// 组装请求数据
		Map<String, String> map = new HashMap<String, String>();
		// 登录标识
		SharedPreferences pref = getSharedPreferences("myPref", MODE_PRIVATE);
		String accessToken = pref.getString("accessToken", "");
		String idString = intent.getStringExtra("id");
		map.put("accessToken", accessToken);
		map.put("id", idString);
		VolleyRequest.RequestPost(this, Urls.GET_ADDRESS_DETAIL,
				"GET_ADDRESS_DETAIL", map, new VolleyAbstract(this,
						VolleyAbstract.listener, VolleyAbstract.errorListener,true) {
					@Override
					public void onMySuccess(String result) {
						// TODO Auto-generated method stub
						Detail_AddressType dType = JsonUtil.parseJsonToBean(
								result, Detail_AddressType.class);
						detail_AddressData = dType.getData();
						dl_province.setText(detail_AddressData.getProvince());
						dl_city.setText(detail_AddressData.getCity());
						dl_area.setText(detail_AddressData.getArea());
						dl_detailaddress.setText(detail_AddressData
								.getAddress());
						dl_name.setText(detail_AddressData.getName());
						dl_phone.setText(detail_AddressData.getPhone());
						// 设置将光标追踪至内容的后面
						dl_detailaddress.setSelection(dl_detailaddress
								.getText().length());
						dl_name.setSelection(dl_name.getText().length());
						dl_phone.setSelection(dl_phone.getText().length());
					}

					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});

	}
	private void initView() {
		// TODO Auto-generated method stub
		//头部相关设置
		topbar =findViewById(R.id.topbar);
		ll_returnbtn = (LinearLayout)topbar.findViewById(R.id.ll_returnbtn);
		ll_functionbtn = (LinearLayout)topbar.findViewById(R.id.ll_functionbtn);
		tv_title = (TextView)topbar.findViewById(R.id.tv_title);
		tv_name_function = (TextView)topbar.findViewById(R.id.tv_name_function);
		tv_title.setText("收货地址");
		tv_name_function.setText("删除");
	}

	private  void idGet(){
		edit_save=(Button)findViewById(R.id.edit_save);
		dl_name=(EditText)findViewById(R.id.dl_name);
		dl_phone=(EditText)findViewById(R.id.dl_phone);
		relative_pca=(RelativeLayout)findViewById(R.id.relative_pca);
		dl_province=(TextView)findViewById(R.id.dl_province);
		dl_city=(TextView)findViewById(R.id.dl_city);
		dl_area=(TextView)findViewById(R.id.dl_area);
		dl_detailaddress=(EditText)findViewById(R.id.dl_detailaddress);
		dl_province.setText(intent.getStringExtra("province"));
		dl_city.setText(intent.getStringExtra("city"));
		dl_area.setText(intent.getStringExtra("area"));
		dl_detailaddress.setText(intent.getStringExtra("address"));
		dl_name.setText(intent.getStringExtra("name"));
		dl_phone.setText(intent.getStringExtra("phone"));
		
		//返回
		ll_returnbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		//删除
		ll_functionbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deltetPost();
			
			}
		});
		//保存
		edit_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				editPost();
			}
		});
		// 选择省市县
		relative_pca.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent pcaIntent = new Intent(Edit_GoodsAddress_Activity.this,
						Provice_Activity.class);
				pcaIntent.putExtra("typeString", 1);
				startActivity(pcaIntent);
				/*overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
			}
		});
		
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Volleyhandle.getInstance(this.getApplicationContext()).getRequestQueue().cancelAll("DELETE_ADDRESS");
		Volleyhandle.getInstance(this.getApplicationContext()).getRequestQueue().cancelAll("EDIT_ADDRESS");
	}
	//删除
	private void deltetPost(){
		//组装请求数据
		Map<String,String> map = new HashMap<String, String>();
		//登录标识
		SharedPreferences pref = getSharedPreferences("myPref", MODE_PRIVATE);
		String accessToken = pref.getString("accessToken", "");
		String id = intent.getStringExtra("id");
		map.put("accessToken", accessToken);
		map.put("id", id);
		VolleyRequest.RequestPost(this,Urls.DELETE_ADDRESS, "DELETE_ADDRESS", map,
				new VolleyAbstract(this, VolleyAbstract.listener,VolleyAbstract.errorListener,true) {
					
					@Override
					public void onMySuccess(String result) {
						// TODO Auto-generated method stub
						Edit_GetAddressType eType = JsonUtil.parseJsonToBean(result,Edit_GetAddressType.class);
						Toast.makeText(getApplicationContext(), eType.getMessage(), Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onMyError(VolleyError error) {
					}
				});
	}
	//保存
	private void editPost(){
		//组装请求数据
		Map<String,String> map = new HashMap<String, String>();
		//登录标识
		SharedPreferences pref = getSharedPreferences("myPref", MODE_PRIVATE);
		String accessToken = pref.getString("accessToken", "");
		String id = intent.getStringExtra("id");
 		String name = dl_name.getText().toString();
		String phone =  dl_phone.getText().toString();
		String province = dl_province.getText().toString().trim();
		String city = dl_city.getText().toString().trim();
		String area = dl_area.getText().toString().trim();
		String address =dl_detailaddress.getText().toString().trim();
		map.put("accessToken", accessToken);
		map.put("id", id);
		map.put("name",name);
		map.put("phone",phone);
		map.put("province",province);
		map.put("city",city);
		map.put("area",area);
		map.put("address",address);
		VolleyRequest.RequestPost(this,Urls.EDIT_ADDRESS, "EDIT_ADDRESS", map,
				new VolleyAbstract(this, VolleyAbstract.listener,VolleyAbstract.errorListener,true) {
					
					@Override
					public void onMySuccess(String result) {
						// TODO Auto-generated method stub
						Edit_GetAddressType eType = JsonUtil.parseJsonToBean(result,Edit_GetAddressType.class);
						Toast.makeText(getApplicationContext(), eType.getMessage(), Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onMyError(VolleyError error) {
					}
				});
	}
	
}
