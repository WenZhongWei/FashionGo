package com.maifeng.fashiongo.adapter;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.maifeng.fashiongo.R;
import com.maifeng.fashiongo.base.RecommendationData;
import com.maifeng.fashiongo.volleyhandle.MyImageCache;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView.ScaleType;

public class RecommendationAdapter extends BaseAdapter {
	
	private List<RecommendationData> list;
	private Context context;
	//图片加载器
	private ImageLoader mImageLoader;
	
	public RecommendationAdapter(Context context, List<RecommendationData> list) {
		// TODO Auto-generated constructor stub
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
					R.layout.item_recommendation_imagelist, null);
			holder = new ViewHolder();
			holder.image_recommendation=(NetworkImageView) convertView.findViewById(R.id.image_recommendation);
			holder.image_recommendation.setScaleType(ScaleType.FIT_XY );
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//加载中显示的图片
		holder.image_recommendation.setDefaultImageResId(R.drawable.bg_loading_image);
		//加载失败时显示的图片
		holder.image_recommendation.setErrorImageResId(R.drawable.bg_error_image);
		//第一个参数用于指定图片的URL地址，第二个参数是创建好的ImageLoader对象
		holder.image_recommendation.setImageUrl(list.get(position).getRecommenImage(), mImageLoader);
		
		
		return convertView;
	}

	private static class ViewHolder {
		NetworkImageView image_recommendation;
	}
}
