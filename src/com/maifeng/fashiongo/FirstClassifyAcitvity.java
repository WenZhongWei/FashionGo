package com.maifeng.fashiongo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.maifeng.fashiongo.R;
import com.maifeng.fashiongo.adapter.ClassifyOneAdapter;
import com.maifeng.fashiongo.adapter.ClassifyTwoAdapter;
import com.maifeng.fashiongo.base.ClassifyTwoData;
import com.maifeng.fashiongo.base.ClassifyTwoType;
import com.maifeng.fashiongo.base.ClassifyOneData;
import com.maifeng.fashiongo.base.ClassifyOneType;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.util.KeyboradUtil;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class FirstClassifyAcitvity extends Activity {

	public static FirstClassifyAcitvity firstClassifyAcitvity;
	private List<ClassifyOneData> onelist;
	private List<ClassifyTwoData> twolist;
	private ListView mListView;
	private GridView mGridView;
	private EditText et_search;
	private ClassifyOneAdapter oneAdapter;
	private LinearLayout layout_left;
	private RequestQueue queue = Volleyhandle
			.getInstance(this.getApplication()).getRequestQueue();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_classify);
		firstClassifyAcitvity=this;
		initView();


	}
	
	

	private void initView() {
		// TODO Auto-generated method stub
		et_search = (EditText) findViewById(R.id.et_search);
		mGridView = (GridView) findViewById(R.id.gv_seconed_choice);
		mListView = (ListView) findViewById(R.id.lv_first_choice);
		layout_left = (LinearLayout) findViewById(R.id.layot_left);
		layout_left.setOnClickListener(new OnClickListener() {
			//������һ������
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		//����
				et_search.setOnEditorActionListener(new OnEditorActionListener() {
					
					@Override
					public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
						if (actionId==EditorInfo.IME_ACTION_SEARCH) {
							//�ر������
							KeyboradUtil.closeKeyborad(FirstClassifyAcitvity.this);
							if (TextUtils.isEmpty(et_search.getText())||et_search.getText().toString().trim().length()==0) {
								Toast.makeText(getApplicationContext(), "�������ݲ���Ϊ��", Toast.LENGTH_SHORT).show();
							}else {

								Intent intent = new Intent(FirstClassifyAcitvity.this, GoodListActivity.class);
								
								String keyword=et_search.getText().toString().trim();
								intent.putExtra("first_keyword", keyword);
								intent.putExtra("Code", "FirstA");
								startActivity(intent);
								/*overridePendingTransition(R.anim.zoomin, R.anim.zoomout);*/
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

		volleyGet();

		// �����б�仯
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (ClassifyOneAdapter.selected != position) {
					ClassifyOneAdapter.selected = position;
					oneAdapter.notifyDataSetChanged();
					volleyPost(onelist.get(position).getClassifyId());
				}
			}
		});

		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ��ThreeClassifyActivity��������
				Intent intent = new Intent(FirstClassifyAcitvity.this,
						ThreeClassifyActivity.class);
				intent.putExtra("ClassifyTwoId", twolist.get(position)
						.getClassifyTwoId());
				startActivity(intent);
				/*overridePendingTransition(R.anim.zoomin, R.anim.zoomout);*/
			}
		});

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		// ʵ����������� �����õ���ģʽ��

		// �����ʱ ȡ������ �����ڴ�����
		queue.cancelAll("GET_CLASSIFY_ONE");
		queue.cancelAll("GET_CLASSIFY_TWO");

	}

	/**
	 * ��ȡһ���б�����
	 */
	private void volleyGet() {
		// ����Get����
		VolleyRequest.RequestGet(this, Urls.GET_CLASSIFY_ONE, "GET_CLASSIFY_ONE",
				new VolleyAbstract(this, VolleyAbstract.listener,
						VolleyAbstract.errorListener,true) {

					@Override
					public void onMySuccess(String result) {
						onelist = JsonUtil.parseJsonToBean(result,
								ClassifyOneType.class).getData();
						// ��������
						oneAdapter = new ClassifyOneAdapter(
								getApplicationContext(), onelist);
						mListView.setAdapter(oneAdapter);
						// ����Ĭ��
						volleyPost(onelist.get(0).getClassifyId());

					}

					@Override
					public void onMyError(VolleyError error) {
						if (queue.getCache().get(Urls.GET_CLASSIFY_ONE) != null) {
							String json = new String(queue.getCache().get(
									Urls.GET_CLASSIFY_ONE).data);
							onelist = JsonUtil.parseJsonToBean(json,
									ClassifyOneType.class).getData();
						}

						// ��������
						ClassifyOneAdapter oneAdapter = new ClassifyOneAdapter(
								getApplicationContext(), onelist);
						mListView.setAdapter(oneAdapter);

						// ����Ĭ��
						volleyPost(onelist.get(0).getClassifyId());

					}
				});

	}

	/**
	 * ��������б�����
	 * 
	 * @param classifyId
	 *            ��������
	 */
	private void volleyPost(String classifyId) {
		// ��װ��������classifyId;
		Map<String, String> map = new HashMap<String, String>();
		map.put("classifyId", classifyId);
		// ����Post����
		VolleyRequest.RequestPost(this, Urls.GET_CLASSIFY_TWO, "GET_CLASSIFY_TWO", map,
				new VolleyAbstract(this, VolleyAbstract.listener,
						VolleyAbstract.errorListener,true) {

					@Override
					public void onMySuccess(String result) {

						twolist = JsonUtil.parseJsonToBean(result,
								ClassifyTwoType.class).getData();
						

						// ��������
						ClassifyTwoAdapter twoAdapter = new ClassifyTwoAdapter(
								getApplicationContext(), twolist);
						mGridView.setAdapter(twoAdapter);

					}

					@Override
					public void onMyError(VolleyError error) {
						if (queue.getCache().get(Urls.GET_CLASSIFY_TWO) != null) {
							String json = new String(queue.getCache().get(
									Urls.GET_CLASSIFY_TWO).data);
							twolist = JsonUtil.parseJsonToBean(json,
									ClassifyTwoType.class).getData();
						}
						// ��������
						ClassifyTwoAdapter twoAdapter = new ClassifyTwoAdapter(
								getApplicationContext(), twolist);
						mGridView.setAdapter(twoAdapter);

					}
				});

	}
}
