package com.maifeng.fashiongo;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.maifeng.fashiongo.R;
import com.maifeng.fashiongo.base.RegisterType;
import com.maifeng.fashiongo.base.RegisterVerifyType;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {

	private EditText register_phonenumber, register_input_Verify,
			register_input_Invitation, register_input_pwd,
			register_input_ConfirmPwd;
	private Button ok;
	private Button btn_getVerify;
	private TextView tv_name_function, tv_title, tv_user_access_protocol;
	private String phone, Verify, Invitation, pwd, ConfirmPwd;
	private CheckBox checkBox;
	private LinearLayout returnbtn;

	final TimeCount time = new TimeCount(10000, 1000);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		initView();

		btn_getVerify.setOnClickListener(this);
		ok.setOnClickListener(this);
		returnbtn.setOnClickListener(this);
		tv_user_access_protocol.setOnClickListener(this);

	}

	private void initView() {
		// TODO Auto-generated method stub
		register_phonenumber = (EditText) findViewById(R.id.register_phonenumber);// 手机号
		register_input_Verify = (EditText) findViewById(R.id.register_input_Verify);// 验证码
		register_input_Invitation = (EditText) findViewById(R.id.register_input_Invitation);// 邀请码
		register_input_pwd = (EditText) findViewById(R.id.register_input_pwd);// 密码
		register_input_ConfirmPwd = (EditText) findViewById(R.id.register_input_ConfirmPwd);// 确认密码
		tv_name_function = (TextView) findViewById(R.id.tv_name_function);
		tv_name_function.setVisibility(View.GONE);

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("注册");
		tv_user_access_protocol = (TextView) findViewById(R.id.tv_user_access_protocol);
		ok = (Button) findViewById(R.id.btn_ok);
		btn_getVerify = (Button) findViewById(R.id.register_getVerify);
		checkBox = (CheckBox) findViewById(R.id.checkbox);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					ok.setEnabled(true);
					ok.setBackgroundResource(R.drawable.selector_login_btn);
				} else {
					ok.setEnabled(false);
					ok.setBackgroundColor(Color.parseColor("#606060"));
				}
			}
		});

		returnbtn = (LinearLayout) findViewById(R.id.ll_returnbtn);

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Volleyhandle.getInstance(this.getApplicationContext())
				.getRequestQueue().cancelAll("GetVerify");
		Volleyhandle.getInstance(this.getApplicationContext())
				.getRequestQueue().cancelAll("REGISTER");
	}

	/**
	 * 提交手机号码，获取验证码
	 * 
	 * */
	private void volleyPost(String phone) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", phone);
		map.put("type", "0");
		VolleyRequest.RequestPost(this, Urls.GetVerify, "GetVerify", map,
				new VolleyAbstract(this, VolleyAbstract.listener,
						VolleyAbstract.errorListener, true) {

					@Override
					public void onMySuccess(String result) {
						// TODO Auto-generated method stub
						System.out.println(result);
						Gson gson = new Gson();
						RegisterVerifyType RType = gson.fromJson(result,
								RegisterVerifyType.class);
						if (RType.getErrorcode().equals("1")) {
							Toast.makeText(RegisterActivity.this,
									RType.getMessage(), Toast.LENGTH_SHORT)
									.show();
						} else if (RType.getErrorcode().equals("0")) {
							Toast.makeText(RegisterActivity.this,
									"验证码：" + RType.getMessage(),
									Toast.LENGTH_LONG).show();
						}
					}

					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});

	}

	/**
	 * 
	 * 注册提交个人信息
	 * 
	 * 
	 */
	private void volleyPost(String phone, String pwd, String Invitation,
			String Verify) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("userPhone", phone);
		map.put("password", pwd);
		map.put("invitecode", Invitation);
		map.put("verificationCode", Verify);
		map.put("registerSys", "android");
		map.put("SIM", "123456");
		map.put("IMEI", "123456");
		// 发起Post请求
		VolleyRequest.RequestPost(this, Urls.REGISTER, "REGISTER", map,
				new VolleyAbstract(this, VolleyAbstract.listener,
						VolleyAbstract.errorListener, true) {

					@Override
					public void onMySuccess(String result) {
						System.out.println(result);
						try {
							// Gson解析
							Gson gson = new Gson();
							RegisterType RType = gson.fromJson(result,
									RegisterType.class);
							System.out.println(RType.getErrorcode());

							if (RType.getErrorcode().equals("1")) {
								Toast.makeText(RegisterActivity.this,
										RType.getMessage(), Toast.LENGTH_SHORT)
										.show();
							}
							if (RType.getErrorcode().equals("2")) {
								Toast.makeText(RegisterActivity.this,
										RType.getMessage(), Toast.LENGTH_SHORT)
										.show();
							}
							if (RType.getErrorcode().equals("101")) {
								Toast.makeText(RegisterActivity.this,
										RType.getMessage(), Toast.LENGTH_SHORT)
										.show();
							}
							if (RType.getErrorcode().equals("102")) {
								Toast.makeText(RegisterActivity.this,
										RType.getMessage(), Toast.LENGTH_SHORT)
										.show();
							}
							if (RType.getErrorcode().equals("10000")) {
								Toast.makeText(RegisterActivity.this,
										RType.getMessage(), Toast.LENGTH_SHORT)
										.show();
							}
							if (RType.getErrorcode().equals("0")) {
								Toast.makeText(RegisterActivity.this,
										RType.getMessage(), Toast.LENGTH_SHORT)
										.show();
							}

						} catch (Exception e) {
							e.printStackTrace();

						}

					}

					@Override
					public void onMyError(VolleyError error) {
						Toast.makeText(RegisterActivity.this, "注册失败",
								Toast.LENGTH_SHORT).show();

					}
				});

	}

	// toast
	private void showToast(String test) {
		Toast toast = Toast.makeText(RegisterActivity.this, test,
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/*
	 * 监听事件
	 */

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			phone = register_phonenumber.getText().toString().trim();
			Verify = register_input_Verify.getText().toString().trim();
			Invitation = register_input_Invitation.getText().toString().trim();
			pwd = register_input_pwd.getText().toString().trim();
			ConfirmPwd = register_input_ConfirmPwd.getText().toString().trim();

			if (phone.isEmpty() || phone.length() != 11) {
				showToast("请输入正确的手机号码");
			} else if (Verify.isEmpty()) {
				showToast("请输入验证码");
			} else if (pwd.isEmpty()) {
				showToast("请输入密码");
			} else if (ConfirmPwd.isEmpty()) {
				showToast("请输入确认密码");

			} else {
				if (ConfirmPwd.equals(pwd)) {

					volleyPost(phone, pwd, Invitation, Verify);
				} else {
					showToast("两次输入的密码不一致");
				}
			}
			break;

		case R.id.register_getVerify:
			phone = register_phonenumber.getText().toString().trim();
			if (phone.isEmpty() || phone.length() != 11) {
				showToast("请输入正确的手机号码");
			} else {
				time.start();// 开始计时
				volleyPost(register_phonenumber.getText().toString().trim());
			}
			break;
		case R.id.ll_returnbtn:
			finish();
			break;

		case R.id.tv_user_access_protocol:
			Intent intent = new Intent(RegisterActivity.this,
					protocolActivity.class);
			startActivity(intent);

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
			btn_getVerify.setClickable(false);// 防止重复点击
			btn_getVerify.setText(millisUntilFinished / 1000 + "s");
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			btn_getVerify.setText("获取验证码");
			btn_getVerify.setClickable(true);
		}
	}
}
