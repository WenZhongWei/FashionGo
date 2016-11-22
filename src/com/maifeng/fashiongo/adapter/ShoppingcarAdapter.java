package com.maifeng.fashiongo.adapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.maifeng.fashiongo.R;
import com.maifeng.fashiongo.base.DeleteGoodsForCartType;
import com.maifeng.fashiongo.base.ShoppingcarData;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.fragment.ShoppingcarFragment;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.util.LogUtil;
import com.maifeng.fashiongo.volleyhandle.MyImageCache;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingcarAdapter extends BaseAdapter{
	private Context context;
	public static int commodityQuantity = 0;
	public static int number;
	public static float Total =  0.00f;
	private List<ShoppingcarData> listdate;
	public static Map<Integer, Boolean> isCheckedMap;//用于记录复选框的状态
	public static HashSet<String> idSet;//用于记录选择的复选框	
	private ImageLoader mImageLoader;
	public ShoppingcarAdapter(Context context, List<ShoppingcarData> listdate) {
		super();
		this.context = context;
		this.listdate = listdate;
		mImageLoader = new ImageLoader(Volleyhandle.getInstance(context).getRequestQueue(), MyImageCache.getImageCache(context));
		idSet = new HashSet<String>();
		isCheckedMap=new HashMap<Integer, Boolean>();
		for (int i = 0; i < listdate.size(); i++) {
			isCheckedMap.put(i, false);
		}
	}

	@Override
	public int getCount() {
		return listdate == null?0:listdate.size();
	}

	@Override
	public Object getItem(int position) {
		return listdate.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		 Viewholder holder=null;
		 
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_shoppingcar, null);
			holder = new Viewholder();
			holder.checkBox_list = (CheckBox) convertView.findViewById(R.id.checkBox_list);
			holder.iv_shopimg = (NetworkImageView) convertView.findViewById(R.id.iv_shopimg);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_model = (TextView) convertView.findViewById(R.id.tv_model);
			holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
			holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
			holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
			holder.btn_delete = (Button) convertView.findViewById(R.id.btn_delete);
			convertView.setTag(holder);
		}else {
			holder = (Viewholder) convertView.getTag();
			
		}
		//获取Map集合里的当前item的复选框状态
		holder.checkBox_list.setChecked(isCheckedMap.get(position));
		holder.iv_shopimg.setDefaultImageResId(R.drawable.bg_loading_image);
		holder.iv_shopimg.setErrorImageResId(R.drawable.bg_error_image);
		holder.iv_shopimg.setImageUrl(listdate.get(position).getGoodsImage(), mImageLoader);
		
		holder.tv_name.setText(listdate.get(position).getGoodsName());
		holder.tv_model.setText(listdate.get(position).getModel());
		holder.tv_size.setText(listdate.get(position).getSize());
		holder.tv_price.setText("￥"+String.valueOf(listdate.get(position).getPrice()));
		holder.tv_number.setText("x "+listdate.get(position).getNumber());
		
		if (isCheckedMap.get(position)) {
			idSet.add(listdate.get(position).getId());
		}else {
			idSet.remove(listdate.get(position).getId());
		}
		MyonClick myonClick=new MyonClick(position);
		holder.btn_delete.setOnClickListener(myonClick);
		holder.checkBox_list.setOnClickListener(myonClick);
		
		
		return convertView;
	}
	
	
	public static class Viewholder{
		public CheckBox checkBox_list;
		public NetworkImageView iv_shopimg;
		public TextView tv_name;
		public TextView tv_model;
		public TextView tv_size;
		public TextView tv_price;
		public TextView tv_number;
		public Button btn_delete;
	}
	/**	 
	 *点击事件
	 */
	private class MyonClick implements OnClickListener{
		private int position;
		

		public MyonClick(int position){
			this.position = position;
		}
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.checkBox_list:
				((CheckBox) v).isChecked();
				isCheckedMap.put(position, ((CheckBox) v).isChecked());

				notifyDataSetChanged();
				if (isCheckedMap.get(position).booleanValue()) {
					commodityQuantity =commodityQuantity +Integer.parseInt(listdate.get(position).getNumber());
					Total = Total +( Float.parseFloat(listdate.get(position).getPrice())*commodityQuantity);
					number = number+commodityQuantity ;
					commodityQuantity=0;
					ShoppingcarFragment.tv_commodityQuantity.setText("共"+String.valueOf(number)+"件商品");
					ShoppingcarFragment.tv_Total.setText("￥"+String.valueOf(Total)+"元");
				}else {
					commodityQuantity =Integer.parseInt(listdate.get(position).getNumber());
					Total = Total - (Float.parseFloat(listdate.get(position).getPrice())*commodityQuantity);
					number = number-commodityQuantity ;
					commodityQuantity=0;
					ShoppingcarFragment.tv_commodityQuantity.setText("共"+String.valueOf(number)+"件商品");
					ShoppingcarFragment.tv_Total.setText("￥"+String.valueOf(Total)+"元");
					
				}
				// 判断是否有记录没被选中，以便修改全选CheckBox勾选状态 
				if (isCheckedMap.containsValue(false)) {
					ShoppingcarFragment.selectAll.setChecked(false);
				}else {
					ShoppingcarFragment.selectAll.setChecked(true);
				}
			
				break;

			case R.id.btn_delete:
				LogUtil.i("CC", "点击了普通按钮"+position);
				for (int i = 0; i < isCheckedMap.size(); i++) {
					isCheckedMap.put(i, false);
				}
				
				volleyPost(listdate.get(position).getId(),listdate.get(position).getNumber(),listdate.get(position).getPrice());
				
				
				break;
			}
		}
		private void volleyPost(String id,final String num, final String money) {
			SharedPreferences pref = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
			String accessToken = pref.getString("accessToken", "");
			Map<String, String> map = new HashMap<String, String>();
			map.put("accessToken", accessToken);
			map.put("id", id);
			VolleyRequest.RequestPost(context, Urls.DELETE_GOODS_FOR_CART,"DELETE_GOODS_FOR_CART", map,
					new VolleyAbstract(context,VolleyAbstract.listener,VolleyAbstract.errorListener,true) {
						
						@Override
						public void onMySuccess(String result) {
							System.out.println("aaaaaaaaaaa"+result);
							DeleteGoodsForCartType data=JsonUtil.parseJsonToBean(result, DeleteGoodsForCartType.class);
							switch (data.getErrorcode()) {
							case 0:

								Toast.makeText(context, data.getMessage(), Toast.LENGTH_SHORT).show();
								listdate.remove(position);
								notifyDataSetChanged();
								number=0;
								Total =0.00f;
								
								ShoppingcarFragment.tv_commodityQuantity.setText("共0件商品");
								ShoppingcarFragment.tv_Total.setText("￥0.00元");
								
								break;

							default:
								Toast.makeText(context, data.getMessage(), Toast.LENGTH_SHORT).show();
								break;
							}
						}
						
						@Override
						public void onMyError(VolleyError error) {
							// TODO Auto-generated method stub
							
						}
					});
		}
		
	}
	

}
