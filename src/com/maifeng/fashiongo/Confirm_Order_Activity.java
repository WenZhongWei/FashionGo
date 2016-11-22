package com.maifeng.fashiongo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.maifeng.fashiongo.adapter.ConfirmOrderAdapter;
import com.maifeng.fashiongo.base.OrderData;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;

public class Confirm_Order_Activity extends Activity implements OnClickListener{
	private LinearLayout ll_returnbtn,ll_functionbtn;
	private TextView choice_address,tv_title,confirm_order_total;
	private LinearLayout confirm_order_payment;
	private String[] item_list={"银联","支付宝"};
	private TextView paymentMethod;
	private ImageView order_icon;
	private ListView confirm_order_list;
	private List<OrderData> list;
	private Button confirm_order_result;
	private String orderNum;
	private String id_adress;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_order);
		initView();
		String Code= getIntent().getStringExtra("Code");
		if (Code.equals("shopcar")) {
			//从购物车页面跳转
			orderNum = getIntent().getStringExtra("orderNum");
			list=(List<OrderData>) getIntent().getSerializableExtra("orderdata");
			confirm_order_list.setAdapter(new ConfirmOrderAdapter(getApplicationContext(), list));
			float total=0.00f;
			float atotal=0.00f;
			for (int i = 0; i < list.size(); i++) {
				atotal=Integer.parseInt(list.get(i).getNumber())*Integer.parseInt(list.get(i).getPrice());
				total=total+atotal;
			}
			confirm_order_total.setText("￥"+total);
		}else if (Code.equals("detail")) {
			orderNum = getIntent().getStringExtra("orderNum");
			//从详情页面跳转
			list=(List<OrderData>) getIntent().getSerializableExtra("orderdata");
			confirm_order_list.setAdapter(new ConfirmOrderAdapter(getApplicationContext(), list));
			confirm_order_total.setText("￥"+(float)(Integer.parseInt(list.get(0).getNumber())*Integer.parseInt(list.get(0).getPrice())));
		}
		
		
		
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("购物车");
		ll_returnbtn=(LinearLayout) findViewById(R.id.ll_returnbtn);
		ll_returnbtn.setOnClickListener(this);
		ll_functionbtn= (LinearLayout) findViewById(R.id.ll_functionbtn);
		ll_functionbtn.setVisibility(View.INVISIBLE);
		confirm_order_total = (TextView) findViewById(R.id.confirm_order_total);
		choice_address = (TextView) findViewById(R.id.choice_address);
		choice_address.setOnClickListener(this);
		confirm_order_payment = (LinearLayout) findViewById(R.id.confirm_order_payment);
		confirm_order_payment.setOnClickListener(this);
		paymentMethod = (TextView) findViewById(R.id.confirm_order_paymentMethod);
		order_icon = (ImageView) findViewById(R.id.confirm_order_icon);
		order_icon.setVisibility(View.INVISIBLE);
		confirm_order_list=(ListView) findViewById(R.id.confirm_order_list);
		confirm_order_result = (Button) findViewById(R.id.confirm_order_result);
		confirm_order_result.setOnClickListener(this);
	

	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.choice_address:
			Intent intent=new Intent(Confirm_Order_Activity.this,Goods_Address_Activity.class);
			intent.putExtra("Code_address", "shopcar_address");
			startActivityForResult(intent, 1);
			break;
		case R.id.confirm_order_payment:
			showDialog();
			break;
		case R.id.ll_returnbtn:
			finish();
			break;
		case R.id.confirm_order_result:
			if (choice_address.getText().toString().isEmpty()) {
				Toast.makeText(getApplicationContext(), "请选择收货地址", Toast.LENGTH_SHORT).show();
			}else {
//				Volleypost(orderNum,id_adress);	
			}

		
		break;
		}
	}
	
	private void Volleypost(String orderNum,String id_adress) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderNum", orderNum);
		map.put("address", id_adress);
		VolleyRequest.RequestPost(Confirm_Order_Activity.this, Urls.GET_TN, "GET_TN",
				map, new VolleyAbstract(this,VolleyAbstract.listener,VolleyAbstract.errorListener,true) {
					
					@Override
					public void onMySuccess(String result) {
						/*TnType tnType =JsonUtil.parseJsonToBean(result, TnType.class);
						Toast.makeText(getApplicationContext(), tnType.getData(), Toast.LENGTH_SHORT).show();
						UPPayAssistEx.startPay(Confirm_Order_Activity.this,
								null, null, tnType.getData(), "01");*/
					}
					
					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub
						
					}
				});
		
	}

	private void showDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("支付方式");
		builder.setIcon(R.drawable.ic_launcher);
		builder.setItems(item_list, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch (which) {
				case 0:
					paymentMethod.setText("银联支付");
					
					order_icon.setImageResource(R.drawable.ico_unionpay);
					order_icon.setVisibility(View.VISIBLE);
					break;
				case 1:
					paymentMethod.setText("支付宝支付");
					order_icon.setImageResource(R.drawable.ico_alipay);
					order_icon.setVisibility(View.VISIBLE);
				default:
					break;
				}
			}
		});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
			if(requestCode==1){
				if (resultCode==101) {
					choice_address.setText(data.getStringExtra("adress"));
					id_adress = data.getStringExtra("id_adress");
				}
			}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
