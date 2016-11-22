package com.maifeng.fashiongo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.maifeng.fashiongo.adapter.Goods_City_Adapter;
import com.maifeng.fashiongo.base.Goods_CityData;
import com.maifeng.fashiongo.base.Goods_CityType;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

public class City_Activity extends Activity {

	// 初始化顶部导航栏控件
	private View topbar;
	private LinearLayout ll_returnbtn;
	private TextView tv_title;

	private List<Goods_CityData> c_data;
	private ListView list_city;
	public int typeString = 0;// 进入类型 0直接选择城市，1跳到城地区表
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏头部
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_layout);

		intent = this.getIntent();
		initView();
		clickOn();
	}

	private void initView() {
		// TODO Auto-generated method stub
		topbar = findViewById(R.id.topbar);
		// 顶部导航栏控件id
		ll_returnbtn = (LinearLayout) topbar.findViewById(R.id.ll_returnbtn);
		tv_title = (TextView) topbar.findViewById(R.id.tv_title);
		// 顶部导航栏控件相关设置
		tv_title.setText("城市");
		// 隐藏不要的内容
		topbar.findViewById(R.id.tv_name_function)
				.setVisibility(View.INVISIBLE);

		list_city = (ListView) findViewById(R.id.list_city);
	}

	private void clickOn() {
		// TODO Auto-generated method stub
		ll_returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		volleyPost();
		list_city.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (typeString == intent.getIntExtra("typeString", 0)) {
					Intent c_intent = new Intent();
					c_intent.putExtra("cName", c_data.get(position).getcName());
					c_intent.putExtra("cCode", c_data.get(position).getcCode());
					setResult(2, c_intent);
					finish();
				} else {
					Intent c_intent = new Intent(City_Activity.this,
							Area_Activity.class);
					c_intent.putExtra("pName", intent.getStringExtra("pName"));
					c_intent.putExtra("cName", c_data.get(position).getcName());
					c_intent.putExtra("cCode", c_data.get(position).getcCode());
					c_intent.putExtra("typeString", 1);
					startActivity(c_intent);
					/*overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
					finish();
				}

			}
		});
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		// 实例化请求队列
		RequestQueue queue = Volleyhandle.getInstance(getApplicationContext())
				.getRequestQueue();
		// 活动销毁时取消请求，减少内存消耗
		queue.cancelAll("GET_CITY_LIST");
	}

	private void volleyPost() {
		// 组装请求数据pCode
		Map<String, String> map = new HashMap<String, String>();
		String pCode = intent.getStringExtra("pCode");
		map.put("pCode", pCode);
		VolleyRequest.RequestPost(this, Urls.GET_CITY_LIST, "GET_CITY_LIST",
				map, new VolleyAbstract(this, VolleyAbstract.listener,
						VolleyAbstract.errorListener,true) {

					@Override
					public void onMySuccess(String result) {
						Goods_CityType cType = JsonUtil.parseJsonToBean(result,
								Goods_CityType.class);
						c_data = cType.getData();
						// 绑定适配器
						Goods_City_Adapter adapter = new Goods_City_Adapter(
								getApplicationContext(), c_data);
						list_city.setAdapter(adapter);
					}

					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});
	}
}
