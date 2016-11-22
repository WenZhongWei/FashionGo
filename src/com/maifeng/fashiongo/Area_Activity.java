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
import com.maifeng.fashiongo.adapter.Goods_Area_Adapter;
import com.maifeng.fashiongo.base.Goods_AreaData;
import com.maifeng.fashiongo.base.Goods_AreaType;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

public class Area_Activity extends Activity {

	// ��ʼ�������������ؼ�
	private View topbar;
	private LinearLayout ll_returnbtn;
	private TextView tv_title;

	private List<Goods_AreaData> a_data;
	private ListView list_area;
	public int typeString = 0;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ����ͷ��
		super.onCreate(savedInstanceState);
		setContentView(R.layout.area_layout);

		intent = this.getIntent();
		initView();
		clickOn();
	}

	private void initView() {
		// TODO Auto-generated method stub
		topbar = findViewById(R.id.topbar);
		// �����������ؼ�id
		ll_returnbtn = (LinearLayout) topbar.findViewById(R.id.ll_returnbtn);
		tv_title = (TextView) topbar.findViewById(R.id.tv_title);
		// �����������ؼ��������
		tv_title.setText("��/��");
		// ���ز�Ҫ������
		topbar.findViewById(R.id.tv_name_function)
				.setVisibility(View.INVISIBLE);

		list_area = (ListView) findViewById(R.id.list_arae);
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
		list_area.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (typeString == intent.getIntExtra("typeString", 0)) {
					Intent intent = new Intent();
					intent.putExtra("aArea", a_data.get(position).getArea());
					intent.putExtra("aCode", a_data.get(position).getaCode());
					setResult(3, intent);
					finish();
				} else {
					Edit_GoodsAddress_Activity.dl_province.setText(intent
							.getStringExtra("pName"));
					Edit_GoodsAddress_Activity.dl_city.setText(intent
							.getStringExtra("cName"));
					Edit_GoodsAddress_Activity.dl_area.setText(a_data.get(
							position).getArea());
					finish();
				}

			}
		});
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		// ʵ�����������
		RequestQueue queue = Volleyhandle.getInstance(getApplicationContext())
				.getRequestQueue();
		// �����ʱȡ�����󣬼����ڴ�����
		queue.cancelAll("GET_AREA_LIST");
	}

	private void volleyPost() {
		// ��װ��������pCode
		Map<String, String> map = new HashMap<String, String>();
		String cCode = intent.getStringExtra("cCode");
		map.put("cCode", cCode);
		VolleyRequest.RequestPost(this, Urls.GET_AREA_LIST, "GET_CITY_LIST",
				map, new VolleyAbstract(this, VolleyAbstract.listener,
						VolleyAbstract.errorListener,true) {

					@Override
					public void onMySuccess(String result) {
						Goods_AreaType cType = JsonUtil.parseJsonToBean(result,
								Goods_AreaType.class);
						a_data = cType.getData();
						// ��������
						Goods_Area_Adapter adapter = new Goods_Area_Adapter(
								getApplication(), a_data);
						list_area.setAdapter(adapter);
					}

					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});
	}

}
