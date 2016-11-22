package com.maifeng.fashiongo.fragment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.maifeng.fashiongo.Confirm_Order_Activity;
import com.maifeng.fashiongo.LoginActivity;
import com.maifeng.fashiongo.R;
import com.maifeng.fashiongo.adapter.ShoppingcarAdapter;
import com.maifeng.fashiongo.adapter.ShoppingcarEditAdapter;
import com.maifeng.fashiongo.banner.XListView;
import com.maifeng.fashiongo.base.EditGoodsForCartType;
import com.maifeng.fashiongo.base.OrderData;
import com.maifeng.fashiongo.base.ShoppingcarData;
import com.maifeng.fashiongo.base.ShoppingcarType;
import com.maifeng.fashiongo.constant.LazyFragment;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.util.LogUtil;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;


public class ShoppingcarFragment extends LazyFragment implements OnClickListener,XListView.IXListViewListener{
	private View topbar;
	private LinearLayout ll_functionbtn;
	private TextView tv_title,tv_name_function;
	private XListView listView;
	public 	static CheckBox selectAll;
	public	static TextView tv_commodityQuantity;
	public 	static TextView tv_Total;
	public Button btn_ok;
	private List<ShoppingcarData> listdate;
	private ShoppingcarAdapter myAdapter;
	private String accessToken;
	private Handler mHandler;
	/*private OrderNumberType orderNumberType;*/

    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    //功能键状态
    private int STATE;
    private int EDITING=1;
    private int FINISH=0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		STATE=FINISH;
		View view = inflater.inflate(R.layout.shoppingcar_fragment2, container,false);
		
		initView(view);
		isPrepared = true;
		selectAll.setOnClickListener(this);
        ll_functionbtn.setOnClickListener(this);
        
		return view;
	}
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		if (!isPrepared||!isVisible) {
			return;
		}
		SharedPreferences pref = getActivity().getSharedPreferences("myPref", getActivity().MODE_PRIVATE);
		accessToken = pref.getString("accessToken", "");
		if (accessToken.equals("")) {
			Intent intent = new Intent(getActivity(),LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
		}else {
			volleyPost();
		}
		
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lazyLoad();
		
	}
	
	private void initView(View view) {
		mHandler = new Handler();
		//顶部导航栏
        topbar = view.findViewById(R.id.topbar);
		topbar.findViewById(R.id.ll_returnbtn).setVisibility(View.INVISIBLE);
        topbar.findViewById(R.id.ll_returnbtn).setVisibility(View.INVISIBLE);
        ll_functionbtn= (LinearLayout) topbar.findViewById(R.id.ll_functionbtn);
        tv_title = (TextView) topbar.findViewById(R.id.tv_title);
        tv_name_function = (TextView) topbar.findViewById(R.id.tv_name_function);
        tv_title.setText("购物车");
        tv_name_function.setText("编辑");
        btn_ok = (Button) view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        
        listView = (XListView) view.findViewById(R.id.list_shoppingcar);
        listView.setPullRefreshEnable(true);//启用或禁用下拉刷新功能。 
        listView.setPullLoadEnable(true);//启用或禁用加载更多功能。
        listView.setAutoLoadEnable(true);//启用或禁用自动加载功能时，滚动到底部。 
        listView.setXListViewListener(this);//设置监听器
        
		selectAll = (CheckBox) view.findViewById(R.id.selectAll);
		tv_commodityQuantity = (TextView) view.findViewById(R.id.tv_commodity_quantity);
		tv_Total = (TextView) view.findViewById(R.id.tv_total);
        
		
	}

	private void volleyPost() {

		
		Map<String, String> map = new HashMap<String, String>();
		map.put("accessToken", accessToken);
		map.put("page", "1");
		VolleyRequest.RequestPost(getActivity(), Urls.GET_MY_CARTINFO, "GET_MY_CARTINFO", map,
				new VolleyAbstract(getActivity(),VolleyAbstract.listener,VolleyAbstract.errorListener,true) {
					
					@Override
					public void onMySuccess(String result) {
					listdate = JsonUtil.parseJsonToBean(result, ShoppingcarType.class).getData();
					myAdapter =new ShoppingcarAdapter(getActivity(),listdate);
			        listView.setAdapter(myAdapter);
			        selectAll.setChecked(false);
					myAdapter.idSet.clear();
					ShoppingcarAdapter.number =0;
					ShoppingcarAdapter.Total=0;
					tv_commodityQuantity.setText("共0件商品");
					tv_Total.setText("￥0.0元");
					}
					
					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub
					}
				});
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.selectAll:
				if (selectAll.isChecked()) {
					int commodityQuantity =0;
					int number=0;
					float total =0.00f;
					for (int i = 0; i < myAdapter.getCount(); i++) {
						myAdapter.isCheckedMap.put(i, true);
						myAdapter.idSet.add(listdate.get(i).getId());
						commodityQuantity = commodityQuantity+Integer.parseInt(listdate.get(i).getNumber());
						total=total+(Integer.parseInt(listdate.get(i).getPrice())*commodityQuantity);
						number=number+commodityQuantity;
						LogUtil.i("全选", number+"+"+total);
						commodityQuantity =0;
					}
					ShoppingcarAdapter.number =number;
					ShoppingcarAdapter.Total=total;
					tv_commodityQuantity.setText("共"+String.valueOf(number)+"件商品");
					tv_Total.setText("￥"+String.valueOf(total)+"元");
					
				}else {
					for (int i = 0; i < myAdapter.getCount(); i++) {
						myAdapter.isCheckedMap.put(i, false);
					}
					myAdapter.idSet.clear();
					ShoppingcarAdapter.number =0;
					ShoppingcarAdapter.Total=0;
					tv_commodityQuantity.setText("共0件商品");
					tv_Total.setText("￥0.0元");
					
				}
				myAdapter.notifyDataSetChanged();

			
			break;

		case R.id.ll_functionbtn:
			ShoppingcarEditAdapter shoppingcarEditAdapter = new ShoppingcarEditAdapter(getActivity(), listdate);
			if (tv_name_function.getText().equals("编辑")) {
				STATE=EDITING;
				tv_name_function.setText("完成");
				selectAll.setVisibility(View.GONE);
				listView.setAdapter(shoppingcarEditAdapter);
				
			}else {
				tv_name_function.setText("编辑");
				STATE=FINISH;
				if (shoppingcarEditAdapter.editlist != null) {
					editCartPost(shoppingcarEditAdapter.editlist);	
				}else {
					volleyPost();
					selectAll.setVisibility(View.VISIBLE);
				}
			}
			break;
			
			case R.id.btn_ok:
				String imageUrl = null;
				String name = null;
				String price = null;
				String number = null;
				List<OrderData> orderDatas = new ArrayList<OrderData>();
				/*List<CreateOrderType> orderlist = new ArrayList<CreateOrderType>();*/
				for (int i = 0; i < ShoppingcarAdapter.isCheckedMap.size(); i++) {
					if (ShoppingcarAdapter.isCheckedMap.get(i)) {
						imageUrl = listdate.get(i).getGoodsImage();
						name = listdate.get(i).getGoodsName();
						price = listdate.get(i).getPrice();
						number = listdate.get(i).getNumber();
						OrderData orderData = new OrderData(imageUrl, name, price, number);
						orderDatas.add(orderData);
						/*CreateOrderType createOrderType = new CreateOrderType(
								listdate.get(i).getGoodsCode(), listdate.get(i).getNumber());
						orderlist.add(createOrderType);*/
					}
				}
				
				if (orderDatas!=null||orderDatas.isEmpty()) {
//					addOrderPost(orderlist,orderDatas);
					Intent intent = new Intent(getActivity(),Confirm_Order_Activity.class);
					intent.putExtra("Code", "shopcar");
					intent.putExtra("orderdata", (Serializable)orderDatas);
					intent.putExtra("orderNum", 20160609);
					startActivity(intent);

				}

				break;
		}
	}
	
	/*private void addOrderPost(List<CreateOrderType> orderlist,final List<OrderData> orderDatas){
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		map.put("accessToken", accessToken);
		map.put("ordertype", gson.toJson(orderlist));

		VolleyRequest.RequestPost(getActivity(), Urls.GET_ADDORDER, "GET_ADDORDER", map, 
				new VolleyAbstract(getActivity(),VolleyAbstract.listener,VolleyAbstract.errorListener,true) {
					
					@Override
					public void onMySuccess(String result) {
						System.out.println("a"+result);
						// TODO Auto-generated method stub
						orderNumberType=JsonUtil.parseJsonToBean(result, OrderNumberType.class);
						String orderNum =orderNumberType.getData();
						if(orderNum!=null||orderNum.isEmpty()){
//							Intent intent = new Intent(getActivity(),Confirm_Order_Activity.class);
//							intent.putExtra("Code", "shopcar");
//							intent.putExtra("orderdata", (Serializable)orderDatas);
//							intent.putExtra("orderNum", orderNum);
//							startActivity(intent);
						}else {
							Toast.makeText(getActivity(), orderNumberType.getMessage(), Toast.LENGTH_SHORT).show();
						}

					}
					
					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub
					}
				});
	} */

	
	private void editCartPost(List<ShoppingcarData> editlist) {
		List<EditGoodsForCartType> list = new ArrayList<EditGoodsForCartType>();
		for (int i = 0; i < editlist.size(); i++) {
			EditGoodsForCartType eType = new EditGoodsForCartType();
			eType.setId(editlist.get(i).getId());
			eType.setGoodsCode(editlist.get(i).getGoodsCode());
			eType.setSpecificationsId(editlist.get(i).getSpecificationsId());
			eType.setNumber(editlist.get(i).getNumber());
			list.add(eType);
		}
		
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		map.put("accessToken", accessToken);
		map.put("editParam", gson.toJson(list));
		VolleyRequest.RequestPost(getActivity(), Urls.EDIT_GOODS_FOR_CART, "EDIT_GOODS_FOR_CART", map,
				new VolleyAbstract(getActivity(),VolleyAbstract.listener,VolleyAbstract.errorListener,true) {
					
					@Override
					public void onMySuccess(String result) {
						volleyPost();
						selectAll.setVisibility(View.VISIBLE);
					}
					
					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub
						
					}
				});
	}




	@Override
	public void onStop() {
		super.onStop();
		Volleyhandle.getInstance(getActivity()).getRequestQueue().cancelAll("GET_MY_CARTINFO");
		Volleyhandle.getInstance(getActivity()).getRequestQueue().cancelAll("EDIT_GOODS_FOR_CART");
		Volleyhandle.getInstance(getActivity()).getRequestQueue().cancelAll("DELETE_GOODS_FOR_CART");
		Volleyhandle.getInstance(getActivity()).getRequestQueue().cancelAll("GET_ADDORDER");
	}
	//下拉刷新
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		 mHandler.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	            	switch (STATE) {
					case 0:
		            	listdate.clear();
		            	volleyPost();
		                onLoad();
						
						break;

					case 1:
						onLoad();
						break;
					}

	            }
	        }, 2500);
	}
	//加载更多
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		 mHandler.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	            	
	                onLoad();
	            }
	        }, 2500);
	}
	//停止刷新-加载-设置时间
    private void onLoad() {
        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime(getTime());
    }
    //设置最后刷新时间
    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }






}
