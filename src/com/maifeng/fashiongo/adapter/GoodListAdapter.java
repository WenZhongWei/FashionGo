package com.maifeng.fashiongo.adapter;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.maifeng.fashiongo.R;
import com.maifeng.fashiongo.base.GoodListData;
import com.maifeng.fashiongo.volleyhandle.MyImageCache;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GoodListAdapter extends BaseAdapter {

	private Context context;
	private List<GoodListData> list;
	private ImageLoader mImageLoader; // 图片加载器

	public GoodListAdapter(Context context, List<GoodListData> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		mImageLoader = new ImageLoader(Volleyhandle.getInstance(context)
				.getRequestQueue(), MyImageCache.getImageCache(context));
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
					R.layout.item_goodlist, null);
			holder = new ViewHolder();
			holder.img_good = (NetworkImageView) convertView
					.findViewById(R.id.img_good);
			holder.tv_goodname = (TextView) convertView
					.findViewById(R.id.tv_goodname);
			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			holder.tv_originalPrice = (TextView) convertView
					.findViewById(R.id.tv_originalPrice);
			holder.tv_rmb = (TextView) convertView.findViewById(R.id.tv_rmb);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_goodname.setText(list.get(position).getGoodsName());
		holder.tv_price.setText("现价："+list.get(position).getPrice());
		holder.tv_originalPrice.setText("原价："+list.get(position).getOriginalPrice());
		holder.tv_originalPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); 
		// 加载中显示的图片
		holder.img_good.setDefaultImageResId(R.drawable.bg_loading_image);
		// 加载失败时显示的图片
		holder.img_good.setErrorImageResId(R.drawable.bg_error_image);
		// 第一个参数用于指定图片的URL地址，第二个参数是创建好的ImageLoader对象
		holder.img_good.setImageUrl(list.get(position).getGoodsImage(),
				mImageLoader);
		return convertView;
	}

	private class ViewHolder {
		NetworkImageView img_good;
		TextView tv_goodname;
		TextView tv_originalPrice;
		TextView tv_price;
		TextView tv_rmb;
	}

}
