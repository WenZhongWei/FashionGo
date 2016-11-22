package com.maifeng.fashiongo;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.maifeng.fashiongo.alipay.PayDemoActivity;
import com.maifeng.fashiongo.banner.CiecleImageView;
import com.maifeng.fashiongo.base.GetPersonalDetailsData;
import com.maifeng.fashiongo.base.GetPersonalDetailsType;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Basic_Info_Activity extends Activity implements OnClickListener {

	// 初始化顶部导航栏控件
	private View topbar;
	private LinearLayout ll_returnbtn;
	private TextView tv_title;
	private RelativeLayout relativemessage;
	private RelativeLayout replace_accuont;
	private RelativeLayout relative_alipay;
	private RelativeLayout relative_unionpay;

	private TextView tv_view_account;
	private TextView tv_view_username;
	private GetPersonalDetailsData data;

	private CiecleImageView head_img_go;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_center_basic_info);

		Click();
	}

	private void Click() {
		topbar = findViewById(R.id.topbar);
		// 顶部导航栏控件id
		ll_returnbtn = (LinearLayout) topbar.findViewById(R.id.ll_returnbtn);
		tv_title = (TextView) topbar.findViewById(R.id.tv_title);
		// 顶部导航栏控件相关设置
		tv_title.setText("基本信息 ");
		topbar.findViewById(R.id.tv_name_function)
				.setVisibility(View.INVISIBLE);

		tv_view_account = (TextView) findViewById(R.id.tv_view_account);
		tv_view_username = (TextView) findViewById(R.id.tv_view_username);
		head_img_go = (CiecleImageView) findViewById(R.id.head_img_go);

		relativemessage = (RelativeLayout) findViewById(R.id.relativemessage);
		replace_accuont = (RelativeLayout) findViewById(R.id.replace_accuont);
		relative_alipay = (RelativeLayout) findViewById(R.id.relative_alipay);
		relative_unionpay = (RelativeLayout) findViewById(R.id.relative_unionpay);

		ll_returnbtn.setOnClickListener(this);
		relativemessage.setOnClickListener(this);
		replace_accuont.setOnClickListener(this);
		relative_alipay.setOnClickListener(this);
		relative_alipay.setOnClickListener(this);

		// 取出用户账号
		SharedPreferences pref = getSharedPreferences("myPref", MODE_PRIVATE);
		String userName = pref.getString("account_number", "");
		tv_view_account.setText(userName);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_returnbtn:
			finish();
			break;
		case R.id.relativemessage:
			Intent intent = new Intent(getApplicationContext(),
					Edit_BasiinformationAcitivity.class);
			startActivity(intent);
			/*
			 * overridePendingTransition(R.anim.push_left_in,
			 * R.anim.push_left_out);
			 */
			break;
		case R.id.replace_accuont:
			Intent intent1 = new Intent(getApplicationContext(),
					LoginActivity.class);
			SharedPreferences pref = getSharedPreferences("myPref",
					MODE_PRIVATE);
			Editor editor = pref.edit();
			editor.clear();
			editor.putString("account_number", "");
			editor.putString("accessToken", "");
			editor.commit();
			pref = getSharedPreferences("headimgurl", MODE_PRIVATE);
			Editor editor2 = pref.edit();
			editor2.clear();
			editor2.putString("headImageUrl", "");
			editor.commit();

			startActivity(intent1);
			/*
			 * overridePendingTransition(R.anim.push_right_in,
			 * R.anim.push_right_out);
			 */
			MainActivity.mainActivity.finish();
			finish();
			break;

		case R.id.relative_alipay:
//			Log.d("====relative_alipay====", "点击我的支付宝");
//			Toast.makeText(getApplication(), "点击我的支付宝", 1).show();
			Intent in = new Intent(getApplicationContext(), PayDemoActivity.class);
			startActivity(in);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		vollePost();
		SharedPreferences pref = getSharedPreferences("headimgurl",
				MODE_PRIVATE);
		String ImageUrl = pref.getString("headImageUrl", "");
		volleygetImage(ImageUrl);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	private void vollePost() {
		// 登录标识
		SharedPreferences pref = getSharedPreferences("myPref", MODE_PRIVATE);
		String accessToken = pref.getString("accessToken", "");
		// 组装请求数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("accessToken", accessToken);
		VolleyRequest.RequestPost(this, Urls.PERSONAL_DETAILS,
				"PERSONAL_DETAILS", map, new VolleyAbstract(this,
						VolleyAbstract.listener, VolleyAbstract.errorListener,
						true) {
					@Override
					public void onMySuccess(String result) {
						data = JsonUtil.parseJsonToBean(result,
								GetPersonalDetailsType.class).getData();
						// 设置显示的昵称
						SharedPreferences pref = getSharedPreferences(
								"headimgurl", MODE_PRIVATE);
						Editor editor = pref.edit();
						editor.clear();
						editor.putString("headImageUrl", data.getImage());
						editor.commit();

						tv_view_username.setText(data.getName());

					}

					@Override
					public void onMyError(VolleyError error) {
					}
				});
	}

	private void volleygetImage(String imgUrl) {
		ImageRequest imageRequest = new ImageRequest(imgUrl,
				new Response.Listener<Bitmap>() {

					@Override
					public void onResponse(Bitmap response) {
						// TODO Auto-generated method stub
						head_img_go.setImageBitmap(response);

					}
				}, 0, 0, Config.RGB_565, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						head_img_go.setImageResource(R.drawable.img_png6);
					}
				});

		Volleyhandle.getInstance(getApplicationContext()).getRequestQueue()
				.add(imageRequest);
	}
}
