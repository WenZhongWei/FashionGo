package com.maifeng.fashiongo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.maifeng.fashiongo.adapter.GoodListAdapter;
import com.maifeng.fashiongo.base.GoodListData;
import com.maifeng.fashiongo.base.GoodListType;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.util.KeyboradUtil;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

/**
 * 商品列表界面
 * 
 * @author liekkas
 * 
 */
public class GoodListActivity extends Activity implements OnClickListener {
	public static GoodListActivity goodListActivity;
	private LinearLayout layout_left;
	private RadioButton tab_relevance, tab_sales, tab_price, tab_new;
	private ListView lv_goodlist;
	private TextView empty_view;
	private GoodListAdapter goodlistadapter;
	private List<GoodListData> goodList;
	private EditText et_search;

	private String ClassifyThreeId;
	private String Keyword=null;
	private String Code = null;
	private String type = "0";
	private String keyTag=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goodlist);
		goodListActivity=this;
		initView();
		setLight(0);
		search();

		Intent intent = getIntent();
		Code=intent.getStringExtra("Code");
		if (Code.equals("classify")) {
			ClassifyThreeId = intent.getStringExtra("ClassifyThreeId");
			volleyPost("",ClassifyThreeId, type);
		}else if (Code.equals("FirstA")) {
			Keyword = intent.getStringExtra("first_keyword");
			et_search.setText(Keyword);
			volleyPost(Keyword,"0", type);
		}else if(Code.equals("home")){
			Keyword=intent.getStringExtra("home_keyword");
			et_search.setText(Keyword);
			volleyPost(Keyword, "0", "0");
		}
	}

	private void search() {
		// TODO Auto-generated method stub
		et_search.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// 关闭软件盘
				KeyboradUtil.closeKeyborad(GoodListActivity.this);

				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					if (TextUtils.isEmpty(et_search.getText())
							|| et_search.getText().toString().trim().length() == 0) {
						Toast.makeText(getApplicationContext(), "搜索内容不能为空",
								Toast.LENGTH_SHORT).show();
					} else {
						keyTag = et_search.getText().toString().trim();
						volleyPost(keyTag, "0", "0");
					}

				}
				return false;
			}

		});
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
		Volleyhandle.getInstance(getApplicationContext()).getRequestQueue().cancelAll("GET_GOODS_LIST");
	}

	private void volleyPost(String keyword, String ClassifyThreeId, String type){
		Map<String, String> map = new HashMap<String, String>();
		map.put("keyword", keyword);
		map.put("ClassifyThreeId", ClassifyThreeId);
		map.put("difference", "0");
		map.put("type", type);
		map.put("page", "1");
		VolleyRequest.RequestPost(getApplicationContext(), Urls.GET_GOODS_LIST,
				"GET_GOODS_LIST", map, new VolleyAbstract(this,
						VolleyAbstract.listener, VolleyAbstract.errorListener,true) {

					@Override
					public void onMySuccess(String result) {
						if (JsonUtil.parseJsonToBean(result, GoodListType.class)
								.getErrorcode().equals("0")) {
							// 解析
							goodList = JsonUtil.parseJsonToBean(result,GoodListType.class).getData();
							lv_goodlist.setVisibility(View.VISIBLE);
							empty_view.setVisibility(View.INVISIBLE);
							
							goodlistadapter = new GoodListAdapter(
									getApplicationContext(), goodList);
							// 绑定适配器
							lv_goodlist.setAdapter(goodlistadapter);

							lv_goodlist.setOnItemClickListener(new OnItemClickListener() {
										@Override
										public void onItemClick(AdapterView<?> parent,
												View view, int position, long id) {
											// 向GoodDetailData传递数据
											Intent intent = new Intent(GoodListActivity.this,
													GoodDetailActivity.class);
											intent.putExtra("Code", "GoodList");
											intent.putExtra("goodsCode",goodList.get(position)
															.getGoodsCode());
											startActivity(intent);
											/*overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);*/
										}
									});
						}else if (JsonUtil.parseJsonToBean(result, GoodListType.class)
								.getErrorcode().equals("1")) {
							lv_goodlist.setVisibility(View.GONE);
							empty_view.setVisibility(View.VISIBLE);
						}

					}

					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});

	}

	/**
	 * 设置开关
	 * 
	 * @param i
	 */
	private void setLight(int i) {
		Lightoff();
		switch (i) {
		case 0:
			tab_relevance.setChecked(true);
			break;
		case 1:
			tab_sales.setChecked(true);
			break;
		case 2:
			tab_price.setChecked(true);
			break;
		case 3:
			tab_new.setChecked(true);
			break;
		}
	}

	/**
	 * 开关全暗
	 */
	private void Lightoff() {
		tab_relevance.setChecked(false);
		tab_sales.setChecked(false);
		tab_price.setChecked(false);
		tab_new.setChecked(false);
	}

	private void initView() {
		layout_left = (LinearLayout) findViewById(R.id.layout_left);
		tab_relevance = (RadioButton) findViewById(R.id.tab_relevance);
		tab_sales = (RadioButton) findViewById(R.id.tab_sales);
		tab_price = (RadioButton) findViewById(R.id.tab_price);
		tab_new = (RadioButton) findViewById(R.id.tab_new);
		lv_goodlist = (ListView) findViewById(R.id.lv_goodlist);
		et_search = (EditText) findViewById(R.id.et_search);
		empty_view = (TextView) findViewById(R.id.empty_view);
		tab_relevance.setOnClickListener(this);
		tab_sales.setOnClickListener(this);
		tab_price.setOnClickListener(this);
		tab_new.setOnClickListener(this);
		layout_left.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_left:
			finish();
			break;
		case R.id.tab_relevance:
			type = "0";
			keybord(type);
			setLight(0);
			break;
		case R.id.tab_sales:
			type = "1";
			keybord(type);
			setLight(1);
			break;
		case R.id.tab_price:
			type = "2";
			keybord(type);
			setLight(2);
			break;
		case R.id.tab_new:
			type = "3";
			keybord(type);
			setLight(3);
			break;

		default:
			break;
		}

	}
	
	public void keybord(String type) {
		if (keyTag!=null) {
			volleyPost(keyTag, "0", type);
			return;
		}
		if (Code.equals("classify")) {
			volleyPost("", ClassifyThreeId, type);
		} else if (Code.equals("FirstA")) {
			volleyPost(Keyword, "0", type);
		} else if (Code.equals("home")) {
			volleyPost(Keyword, "0", type);
		}
	}

}
