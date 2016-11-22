package com.maifeng.fashiongo.adapter;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.maifeng.fashiongo.R;
import com.maifeng.fashiongo.base.OrderData;
import com.maifeng.fashiongo.volleyhandle.MyImageCache;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ConfirmOrderAdapter extends BaseAdapter{
	private Context context;
	private List<OrderData> list;
	private ImageLoader mImageLoader;
	
	public ConfirmOrderAdapter(Context context,List<OrderData> list){
		this.context = context;
		this.list = list;
		mImageLoader = new ImageLoader(Volleyhandle.getInstance(context).getRequestQueue(), MyImageCache.getImageCache(context));
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null?0:list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_confirm, null);
			holder = new ViewHolder();
			holder.iv_shopimg = (NetworkImageView) convertView.findViewById(R.id.iv_shopimg);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
			holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.iv_shopimg.setDefaultImageResId(R.drawable.bg_loading_image);
		//加载失败时显示的图片
		holder.iv_shopimg.setErrorImageResId(R.drawable.bg_error_image);
		//第一个参数用于指定图片的URL地址，第二个参数是创建好的ImageLoader对象
		holder.iv_shopimg.setImageUrl(list.get(position).getImageUrl(), mImageLoader);
		holder.tv_name.setText(list.get(position).getName());
		holder.tv_price.setText(list.get(position).getPrice());
		holder.tv_number.setText(list.get(position).getNumber());
		
		return convertView;
	}

	private static class ViewHolder {
		NetworkImageView iv_shopimg;
		TextView tv_name;
		TextView tv_price;
		TextView tv_number;
	}

}
