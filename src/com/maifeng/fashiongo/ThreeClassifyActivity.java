package com.maifeng.fashiongo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.maifeng.fashiongo.adapter.ClassifyThreeAdapter;
import com.maifeng.fashiongo.base.ClassifyThreeData;
import com.maifeng.fashiongo.base.ClassifyThreeType;
import com.maifeng.fashiongo.base.ClassifyTwoData;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

/**
 * 三级分类界面
 * 
 * @author Administrator
 * 
 */
public class ThreeClassifyActivity extends Activity {
	public static ThreeClassifyActivity threeClassifyActivity;
	private List<ClassifyThreeData> threelist;
	private List<ClassifyTwoData> twolist;
	private ListView mListView;
	private View topbar;
	private LinearLayout ll_returnbtn;
	private TextView tv_title;
	private RequestQueue queue = Volleyhandle
			.getInstance(this.getApplication()).getRequestQueue();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classify_three);
		threeClassifyActivity=this;
		initView();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// 接收FirstClassifyActivity传递过来的数据
		Intent intent = getIntent();
		ThreeData(intent.getStringExtra("ClassifyTwoId"));
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
					View view, int position, long id) {
				// 向GoodListActivity传递数据
				Intent intent = new Intent(ThreeClassifyActivity.this,GoodListActivity.class);
				intent.putExtra("ClassifyThreeId",threelist.get(position).getClassifyThreeId());
				intent.putExtra("Code","classify");
				startActivity(intent);
				/*overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/

			}
		});

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Volleyhandle.getInstance(this.getApplicationContext()).getRequestQueue().cancelAll("GET_CLASSIFY_THREE");
	}

	/**
	 * 获取三级分类数据
	 */
	private void ThreeData(String ClassifyTwoId) {
		// 组装请求数据ClassifyTwoId
		Map<String, String> map = new HashMap<String, String>();
		map.put("ClassifyTwoId", ClassifyTwoId);
		
		VolleyRequest.RequestPost(this, Urls.GET_CLASSIFY_THREE, "GET_CLASSIFY_THREE", map,
				new VolleyAbstract(this, VolleyAbstract.listener,
						VolleyAbstract.errorListener,true) {

					@Override
					public void onMySuccess(String result) {
							threelist = JsonUtil.parseJsonToBean(result,
									ClassifyThreeType.class).getData();
							// 绑定适配器
							ClassifyThreeAdapter threeAdapter = new ClassifyThreeAdapter(
									getApplicationContext(), threelist);
							mListView.setAdapter(threeAdapter);

					}

					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub
						if (queue.getCache().get(Urls.GET_CLASSIFY_THREE) != null) {
							String json = new String(queue.getCache().get(Urls.GET_CLASSIFY_THREE).data);
							threelist = JsonUtil.parseJsonToBean(json,ClassifyThreeType.class).getData();
						}

						// 绑定适配器
						ClassifyThreeAdapter threeAdapter = new ClassifyThreeAdapter(
								getApplicationContext(), threelist);
						mListView.setAdapter(threeAdapter);
						mListView.setOnItemClickListener(new OnItemClickListener() {
									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										
										// 向GoodListActivity传递数据
										Intent intent = new Intent(ThreeClassifyActivity.this,GoodListActivity.class);
										intent.putExtra("ClassifyThreeId",threelist.get(position).getClassifyThreeId());
										startActivity(intent);
										/*overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
									}
								});
					}
				});
	}

	private void initView() {
		// TODO Auto-generated method stub
		mListView = (ListView) findViewById(R.id.lv_classify_three);
		topbar = findViewById(R.id.topbar);
		tv_title = (TextView) topbar.findViewById(R.id.tv_title);
		topbar.findViewById(R.id.tv_name_function).setVisibility(View.INVISIBLE);
		ll_returnbtn = (LinearLayout) topbar.findViewById(R.id.ll_returnbtn);
		tv_title.setText("分类");
		ll_returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 返回上一个界面
				finish();
			}
		});

	}

}
