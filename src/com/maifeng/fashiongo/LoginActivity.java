package com.maifeng.fashiongo;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.maifeng.fashiongo.base.LoginData;
import com.maifeng.fashiongo.base.LoginType;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;

public class LoginActivity extends Activity implements OnClickListener{

	private Button button;
	private TextView tv_register,tv_forgetpwd;
	private TextView tv_name_function,tv_title;
	private LinearLayout ll_returnbtn;
	private EditText et_phonenumber,et_password;
	private LoginData data;
	@Override

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		initView();
	}

	private void initView() {
		button = (Button) findViewById(R.id.btn_login);
		tv_register = (TextView) findViewById(R.id.tv_register);
		tv_forgetpwd = (TextView) findViewById(R.id.tv_forgetpwd);
		et_phonenumber =(EditText) findViewById(R.id.et_phonenumber);
		et_password = (EditText) findViewById(R.id.et_password);
		ll_returnbtn = (LinearLayout) findViewById(R.id.ll_returnbtn);
		tv_name_function = (TextView) findViewById(R.id.tv_name_function);
		tv_name_function.setVisibility(View.GONE);
		

		tv_title =(TextView) findViewById(R.id.tv_title);
		tv_title.setText("µÇÂ¼");
		ll_returnbtn.setOnClickListener(this);
		button.setOnClickListener(this);
		tv_register.setOnClickListener(this);
		tv_forgetpwd.setOnClickListener(this);
	}



	private void volleyPost(final String userName,final String password){

		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		map.put("password", password);
		map.put("system", "android");
		VolleyRequest.RequestPost(this, Urls.LOGIN, "GetVerify",map,
				new VolleyAbstract(this, VolleyAbstract.listener,VolleyAbstract.errorListener,true) {

			@Override
			public void onMySuccess(String result) {
				
				try {
					Gson gson=new Gson();
					LoginType LType = gson.fromJson(result, LoginType.class);    
					data=LType.getData();					
					
					if(LType.getErrorcode().equals("0")){
						SharedPreferences pref = getSharedPreferences("myPref", MODE_PRIVATE);
						Editor editor = pref.edit();
						editor.clear();
						editor.putString("account_number", userName);
						editor.putString("accessToken", data.getAccessToken());
						editor.commit();

						Toast.makeText(LoginActivity.this, "µÇÂ¼³É¹¦", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(getApplicationContext(),MainActivity.class);
						startActivity(intent);
						finish();
					}
					else if(LType.getErrorcode().equals("2")){
						Toast.makeText(LoginActivity.this,LType.getMessage() , Toast.LENGTH_SHORT).show();
					}
					else if(LType.getErrorcode().equals("101")){
						Toast.makeText(LoginActivity.this, LType.getMessage(), Toast.LENGTH_SHORT).show();
					}
					else if(LType.getErrorcode().equals("102")){
						Toast.makeText(LoginActivity.this,LType.getMessage(), Toast.LENGTH_SHORT).show();
					}
					else if(LType.getErrorcode().equals("10000")){
						Toast.makeText(LoginActivity.this,LType.getMessage(), Toast.LENGTH_SHORT).show();
					}
					else if(LType.getErrorcode().equals("1")){
						Toast.makeText(LoginActivity.this,LType.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			@Override
			public void onMyError(VolleyError error) {
				Toast.makeText(LoginActivity.this, "µÇÂ¼Ê§°ÜÇë¼ì²éÍøÂç", Toast.LENGTH_SHORT).show();
			}
		});

	}	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.btn_login:
			String userName = et_phonenumber.getText().toString().trim();
			String password = et_password.getText().toString().trim();
			/**
			 * Ä¬ÈÏÕË»§ÃÜÂë
			 */
			/*String userName = "13729527645";
			String password = "123456";*/
			
			volleyPost(userName, password);		
			break;
		case R.id.tv_register:
			intent.setClass(getApplicationContext(),RegisterActivity.class);
			startActivity(intent);
			/*overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);*/
			break;
			
		case R.id.tv_forgetpwd:
			intent.setClass(getApplicationContext(),FindPasswordActivity.class);
			startActivity(intent);
			/*overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
			break;
		case R.id.ll_returnbtn:
			intent.setClass(getApplicationContext(),MainActivity.class);
			startActivity(intent);
			/*overridePendingTransition(R.anim.zoomin, R.anim.zoomout);*/
			finish();
			break;
		}

	}
}
