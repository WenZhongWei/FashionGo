package com.maifeng.fashiongo.adapter;

import java.util.List;


import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.maifeng.fashiongo.R;
import com.maifeng.fashiongo.base.ShoppingcarData;
import com.maifeng.fashiongo.volleyhandle.MyImageCache;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingcarEditAdapter extends BaseAdapter{
	private Context context;
	private List<ShoppingcarData> listdate;
	public static List<ShoppingcarData> editlist = null;
	private ImageLoader mImageLoader;
	public ShoppingcarEditAdapter(Context context, List<ShoppingcarData> listdate) {
		super();
		this.context = context;
		this.listdate = listdate;
		mImageLoader = new ImageLoader(Volleyhandle.getInstance(context).getRequestQueue(), MyImageCache.getImageCache(context));
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_shoppingcar_edit, null);
			holder = new Viewholder();
			holder.iv_shopimg = (NetworkImageView) convertView.findViewById(R.id.iv_shopimg);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_model = (TextView) convertView.findViewById(R.id.tv_model);
			holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
			holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
			holder.ed_number = (EditText) convertView.findViewById(R.id.ed_number);
			convertView.setTag(holder);
		}else {
			holder = (Viewholder) convertView.getTag();
			
		}
		holder.iv_shopimg.setDefaultImageResId(R.drawable.bg_loading_image);
		holder.iv_shopimg.setErrorImageResId(R.drawable.bg_error_image);
		holder.iv_shopimg.setImageUrl(listdate.get(position).getGoodsImage(), mImageLoader);
		
		holder.tv_name.setText(listdate.get(position).getGoodsName());
		holder.tv_model.setText(listdate.get(position).getModel());
		holder.tv_size.setText(listdate.get(position).getSize());
		holder.tv_price.setText("£¤"+String.valueOf(listdate.get(position).getPrice()));
		holder.ed_number.setText(listdate.get(position).getNumber());
		
		holder.ed_number.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {

				if (String.valueOf(arg0).equals("")) {
					listdate.get(position).setNumber("1");
					Toast.makeText(context, "²»ÄÜÎª¿Õ", Toast.LENGTH_SHORT).show();
				}else {
					listdate.get(position).setNumber(String.valueOf(arg0));
				}
				editlist =listdate;
				
				
			}
		});
		
		
		return convertView;
	}
	
	
	public static class Viewholder{
		public NetworkImageView iv_shopimg;
		public TextView tv_name;
		public TextView tv_model;
		public TextView tv_size;
		public TextView tv_price;
		public EditText ed_number;
	}

	

}
