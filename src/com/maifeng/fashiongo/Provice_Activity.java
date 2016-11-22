package com.maifeng.fashiongo;

import java.util.List;

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
import com.maifeng.fashiongo.adapter.Goods_Province_Adapter;
import com.maifeng.fashiongo.base.Goods_ProvinceData;
import com.maifeng.fashiongo.base.Goods_ProvinceType;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

public class Provice_Activity extends Activity {
	// Provice_Activity
	// ��ʼ�������������ؼ�
	private View topbar;
	private LinearLayout ll_returnbtn;
	private TextView tv_title;
	private ListView list_province;
	private List<Goods_ProvinceData> p_data;
	public int typeString = 0;// �������� 0ֱ��ѡ��ʡ�ݣ�1���������б�
	public Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ����ͷ��
		super.onCreate(savedInstanceState);
		setContentView(R.layout.province_layout);
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
		tv_title.setText("ʡ��");
		// ���ز�Ҫ������
		topbar.findViewById(R.id.tv_name_function)
				.setVisibility(View.INVISIBLE);

		list_province = (ListView) findViewById(R.id.list_province);
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
		volleyGet();
		list_province.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (typeString == intent.getIntExtra("typeString", 0)) {
					Intent p_intent = new Intent();
					p_intent.putExtra("pName", p_data.get(position).getpName());
					p_intent.putExtra("pCode", p_data.get(position).getpCode());
					setResult(1, p_intent);
					finish();
				} else {
					Intent p_intent = new Intent(Provice_Activity.this,
							City_Activity.class);
					p_intent.putExtra("pName", p_data.get(position).getpName());
					p_intent.putExtra("pCode", p_data.get(position).getpCode());
					p_intent.putExtra("typeString", 1);
					startActivity(p_intent);
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
		// ʵ�����������
		RequestQueue queue = Volleyhandle.getInstance(
				this.getApplicationContext()).getRequestQueue();
		// �����ʱȡ����������ڴ�����
		queue.cancelAll("GET_PROVINCE_LIST");
	}

	private void volleyGet() {
		VolleyRequest.RequestGet(this, Urls.GET_PROVINCE_LIST,
				"GET_PROVINCE_LIST", new VolleyAbstract(this,
						VolleyAbstract.listener, VolleyAbstract.errorListener,true) {

					@Override
					public void onMySuccess(String result) {
						Goods_ProvinceType pType = JsonUtil.parseJsonToBean(
								result, Goods_ProvinceType.class);
						p_data = pType.getData();
						// ��������
						Goods_Province_Adapter adapter = new Goods_Province_Adapter(
								getApplicationContext(), p_data);
						list_province.setAdapter(adapter);

					}

					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});
	}
}
