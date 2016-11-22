package com.maifeng.fashiongo;



import java.util.HashMap;
import java.util.Map;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.maifeng.fashiongo.base.FindPasswordType;
import com.maifeng.fashiongo.base.RegisterType;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FindPasswordActivity extends Activity implements OnClickListener{

	
	 private TextView tv_title,tv_name_function;
	 
	 private LinearLayout returnbtn; 
	
	 private EditText et_findpassword_phone,et_findpassword_inputVerify,et_findpassword_inputpwd;
	 
	 private Button btn_findpassword_verify,btn_findpassword_confirm;
	 
	 private String phone,pwd,Verify;
	 
	
	 final TimeCount time = new TimeCount(10000, 1000);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findpassword);
		initView();

		returnbtn.setOnClickListener(this);
		btn_findpassword_verify.setOnClickListener(this);
		btn_findpassword_confirm.setOnClickListener(this);
		
	}
	
	

	
	
	private void initView() {
		// TODO Auto-generated method stub
		et_findpassword_inputpwd = (EditText) findViewById(R.id.et_findpassword_inputpwd);
		et_findpassword_inputVerify = (EditText) findViewById(R.id.et_findpassword_inputVerify);
		et_findpassword_phone = (EditText) findViewById(R.id.et_findpassword_phone);
		btn_findpassword_verify = (Button) findViewById(R.id.btn_findpassword_verify);
		btn_findpassword_confirm = (Button) findViewById(R.id.btn_findpassword_confirm);
		tv_name_function = (TextView) findViewById(R.id.tv_name_function);
		tv_name_function.setVisibility(View.GONE);
		tv_title =(TextView) findViewById(R.id.tv_title);
		tv_title.setText("找回密码");
		returnbtn = (LinearLayout) findViewById(R.id.ll_returnbtn);
	}


@Override
protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
	Volleyhandle.getInstance(getApplicationContext()).getRequestQueue().cancelAll("GetVerify");
	Volleyhandle.getInstance(getApplicationContext()).getRequestQueue().cancelAll("FINDPASSWORD");
}


	private void volleyPost(String phone){
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("userPhone", phone);
		map.put("type", "1");
		VolleyRequest.RequestPost(this, Urls.GetVerify, "GetVerify",map, new VolleyAbstract(this, VolleyAbstract.listener,VolleyAbstract.errorListener,true) {
			
			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub
				Gson gson=new Gson();
				FindPasswordType FType = gson.fromJson(result, FindPasswordType.class);
				if(FType.getErrorcode().equals("1001")){
					Toast.makeText(FindPasswordActivity.this, FType.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});
		
}	

	
	private void volleyPost(String phone,String pwd,String Verify) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("userPhone", phone);
		map.put("verificationCode", Verify);
		map.put("password", pwd);
		
		
		//发起Post请求
		VolleyRequest.RequestPost(this, Urls.FINDPASSWORD, "FINDPASSWORD", map,
				new VolleyAbstract(this, VolleyAbstract.listener,VolleyAbstract.errorListener,true) {

			@Override
			public void onMySuccess(String result) {
				System.out.println(result);
				try {
					//Gson解析
					Gson gson=new Gson();
					RegisterType RType = gson.fromJson(result, RegisterType.class);
					System.out.println(RType.getErrorcode());
					
					 if(RType.getErrorcode().equals("2001")){
						Toast.makeText(FindPasswordActivity.this, RType.getMessage(), Toast.LENGTH_SHORT).show();
					}
					 if(RType.getErrorcode().equals("1112")){
						Toast.makeText(FindPasswordActivity.this, RType.getMessage(), Toast.LENGTH_SHORT).show();
					}
					 if(RType.getErrorcode().equals("0")){
						Toast.makeText(FindPasswordActivity.this, RType.getMessage(), Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) { 
					e.printStackTrace();
					
				}
			}
			@Override
			public void onMyError(VolleyError error) {
				Toast.makeText(FindPasswordActivity.this, "找回密码失败", Toast.LENGTH_SHORT).show();

			}
		});

	}
	
	private void showToast(String test){
		Toast toast=Toast.makeText(FindPasswordActivity.this,test,Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER,0,0);
		toast.show();
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_returnbtn:
			finish();
			break;
		case R.id.btn_findpassword_verify:
			phone=et_findpassword_phone.getText().toString().trim();
			if(phone.isEmpty()||phone.length()!=11)
			{
				showToast("请输入正确的手机号码");
			}else{
				time.start();// 开始计时
				volleyPost(et_findpassword_phone.getText().toString().trim());					
			}
			break;
		case R.id.btn_findpassword_confirm:
			phone=et_findpassword_phone.getText().toString().trim();
			pwd = et_findpassword_inputpwd.getText().toString().trim();
			 Verify= et_findpassword_inputVerify.getText().toString().trim();
			if(phone.isEmpty()||phone.length()!=11)
			{
				showToast("请输入正确的手机号码");
			}else if(Verify.isEmpty()){
				showToast("请输入验证码");
			}
			else if(pwd.isEmpty()){
				showToast("请输入密码");
			}else{
					volleyPost(phone,pwd,Verify);
				}				
			break;
		default:
			break;
		}
	}
	
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			btn_findpassword_verify.setClickable(false);//防止重复点击
			btn_findpassword_verify.setText(millisUntilFinished / 1000 + "s");
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			btn_findpassword_verify.setText("获取验证码");
			btn_findpassword_verify.setClickable(true);
		}
	}
}
