package com.maifeng.fashiongo;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.maifeng.fashiongo.base.Goods_AddNew_AddressType;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

public class New_Goods_Address_Activity extends Activity {

	//��ʼ�������������ؼ�
	private View topbar;
	private LinearLayout ll_returnbtn;
	private LinearLayout ll_functionbtn;
	private TextView tv_title;
	private TextView tv_name_function;
	
	private RelativeLayout relayout_province;
	private RelativeLayout relayout_city;
	private RelativeLayout relayout_area;
	private EditText ed_name;
	private EditText ed_phones;
	private TextView tv_province;
	private TextView tv_city;
	private TextView tv_area;
	private EditText ed_address;
	private String pCodeString;//���ʡ��id
	private String cCodeString;//��ų���id
	private String aCodeString;//��ŵ���id
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_center_newadd_goodsaddress);
		initView();
		viewId();
	}
	private void initView(){
		
		topbar =findViewById(R.id.topbar);
		// �����������ؼ�id
		ll_returnbtn = (LinearLayout)topbar.findViewById(R.id.ll_returnbtn);
		tv_title = (TextView)topbar.findViewById(R.id.tv_title);
		ll_functionbtn = (LinearLayout)topbar.findViewById(R.id.ll_functionbtn);
		tv_name_function = (TextView)topbar.findViewById(R.id.tv_name_function);
		//�����������ؼ��������
		tv_title.setText("������ַ");
		tv_name_function.setText("ȷ��");
	}
	private void viewId() {
		// TODO Auto-generated method stub
		relayout_province = (RelativeLayout) findViewById(R.id.relayout_province);
		relayout_city = (RelativeLayout) findViewById(R.id.relayout_city);
		relayout_area = (RelativeLayout) findViewById(R.id.relayout_area);
		ed_name = (EditText) findViewById(R.id.ed_name);
		ed_phones = (EditText) findViewById(R.id.ed_phones);
		tv_province = (TextView) findViewById(R.id.tv_province);
		tv_city = (TextView) findViewById(R.id.tv_city);
		tv_area = (TextView) findViewById(R.id.tv_area);
		ed_address = (EditText) findViewById(R.id.ed_addrss);
		// ����
		ll_returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(New_Goods_Address_Activity.this,
//						Goods_Address_Activity.class);
//				intent.putExtra("Code_address", "Mine");
//				startActivity(intent);
				finish();
			}
		});
		// ȷ��
		ll_functionbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				volleyPost();

			}
		});
		relayout_province.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(getApplicationContext(),
						Provice_Activity.class);
				startActivityForResult(intent1, 1);
			}
		});
		relayout_city.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (pCodeString == null) {
					Toast.makeText(getApplicationContext(), "��ѡ��ʡ�ݺ��ڲ���",
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					Intent intent2 = new Intent(getApplicationContext(),
							City_Activity.class);
					intent2.putExtra("pCode", pCodeString);
					startActivityForResult(intent2, 2);
				}

			}
		});
		relayout_area.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (cCodeString == null) {
					Toast.makeText(getApplicationContext(), "��ѡ����к��ڲ���",
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					Intent intent3 = new Intent(getApplicationContext(),
							Area_Activity.class);
					intent3.putExtra("cCode", cCodeString);
					startActivityForResult(intent3, 3);
				}

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1) {
			String pNameString = data.getStringExtra("pName");
			tv_province.setText(pNameString);// ��ʾʡ��
			tv_city.setText("��ѡ�����");
			tv_area.setText("��ѡ�����");
			cCodeString=null;
			aCodeString=null;
			pCodeString = data.getStringExtra("pCode");// �õ�ʡ��id
		}
		if (resultCode == 2) {
			String cNameString = data.getStringExtra("cName");
			tv_city.setText(cNameString);// ��ʾ����
			tv_area.setText("��ѡ�����");
			aCodeString=null;
			cCodeString = data.getStringExtra("cCode");// �õ�����id
		}
		if (resultCode == 3) {
			String cNameString = data.getStringExtra("aArea");
			tv_area.setText(cNameString);// ��ʾ����
			aCodeString=data.getStringExtra("aCode");
		}
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
		Volleyhandle.getInstance(getApplicationContext()).getRequestQueue().cancelAll("ADD_RECEIVE_ADDRESS");
	}
	private void volleyPost() {
		try {

			// ��¼��ʶ
			SharedPreferences pref = getSharedPreferences("myPref",
					MODE_PRIVATE);

			String accessToken = pref.getString("accessToken", "");
			String name = ed_name.getText().toString().trim();// trim()�ǽ�ת������ַ�������ȥ��ǰ��ո�
			String phone = ed_phones.getText().toString().trim();

			String province = tv_province.getText().toString().trim();
			String city = tv_city.getText().toString().trim();
			String area = tv_area.getText().toString().trim();
			String address = ed_address.getText().toString().trim();
			if (TextUtils.isEmpty(name)) {
				Toast.makeText(getApplicationContext(), "��������Ϊ��",
						Toast.LENGTH_SHORT).show();
				return;
			}
			Pattern phonepattern = Pattern
					.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
			Matcher m = phonepattern.matcher(phone);
			if (TextUtils.isEmpty(phone)) {
				Toast.makeText(getApplicationContext(), "�绰���벻��Ϊ��",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (!m.matches()) {
				Toast.makeText(getApplicationContext(), "�绰���벻��ȷ",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (province.equals("��ѡ��ʡ��")) {
				Toast.makeText(getApplicationContext(), "ʡ�ݲ���Ϊ��",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (city.equals("��ѡ�����")) {
				Toast.makeText(getApplicationContext(), "���в���Ϊ��",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (area.equals("��ѡ�����")) {
				Toast.makeText(getApplicationContext(), "��/������Ϊ��",
						Toast.LENGTH_SHORT).show();
				return;
			} else if (TextUtils.isEmpty(address)) {
				Toast.makeText(getApplicationContext(), "��ϸ��ַ����Ϊ��",
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				// ��װ��������
				Map<String, String> map = new HashMap<String, String>();
				map.put("accessToken", accessToken);
				map.put("name", name);
				map.put("phone", phone);
				map.put("province", province);
				map.put("city", city);
				map.put("area", area);
				map.put("address", address);
				VolleyRequest.RequestPost(this, Urls.ADD_RECEIVE_ADDRESS,
						"ADD_RECEIVE_ADDRESS", map, new VolleyAbstract(this,
								VolleyAbstract.listener,
								VolleyAbstract.errorListener,true) {
							@Override
							public void onMySuccess(String result) {
								Goods_AddNew_AddressType gAddNew_AddressType = JsonUtil.parseJsonToBean(result,
										Goods_AddNew_AddressType.class);
								Toast.makeText(getApplicationContext(), gAddNew_AddressType.getMessage(), Toast.LENGTH_SHORT).show();

								/*if (gAddNew_AddressType.getErrorcode().equals("1")) {
									Toast.makeText(New_Goods_Address_Activity.this,
											gAddNew_AddressType.getMessage(), Toast.LENGTH_SHORT)
											.show();
								}
								if (gAddNew_AddressType.getErrorcode().equals("2")) {
									Toast.makeText(New_Goods_Address_Activity.this,
											gAddNew_AddressType.getMessage(), Toast.LENGTH_SHORT)
											.show();
								}
								if (gAddNew_AddressType.getErrorcode().equals("101")) {
									Toast.makeText(New_Goods_Address_Activity.this,
											gAddNew_AddressType.getMessage(), Toast.LENGTH_SHORT)
											.show();
								}
								if (gAddNew_AddressType.getErrorcode().equals("102")) {
									Toast.makeText(New_Goods_Address_Activity.this,
											gAddNew_AddressType.getMessage(), Toast.LENGTH_SHORT)
											.show();
								}
								if (gAddNew_AddressType.getErrorcode().equals("10000")) {
									Toast.makeText(New_Goods_Address_Activity.this,
											gAddNew_AddressType.getMessage(), Toast.LENGTH_SHORT)
											.show();
								}
								if (gAddNew_AddressType.getErrorcode().equals("0")) {
									Toast.makeText(New_Goods_Address_Activity.this,
											gAddNew_AddressType.getMessage(), Toast.LENGTH_SHORT)
											.show();
								}*/
							}

							@Override
							public void onMyError(VolleyError error) {
								// TODO Auto-generated method stub

							}
						});
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
