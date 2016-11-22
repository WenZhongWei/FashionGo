package com.maifeng.fashiongo.adapter;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.maifeng.fashiongo.R;
import com.maifeng.fashiongo.base.ClassifyTwoData;
import com.maifeng.fashiongo.volleyhandle.MyImageCache;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClassifyTwoAdapter extends BaseAdapter {
	private List<ClassifyTwoData> list;
	private Context context;
	//图片加载器
	private ImageLoader mImageLoader;


	public ClassifyTwoAdapter(Context context, List<ClassifyTwoData> list) {
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
					R.layout.item_classify_two, null);
			holder = new ViewHolder();
			holder.tv_calssify_two = (TextView) convertView.findViewById(R.id.tv_classify_two);
			holder.img_classify_two=(NetworkImageView) convertView.findViewById(R.id.img_classify_two);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_calssify_two.setText(list.get(position).getClassifyName());
		//加载中显示的图片
		holder.img_classify_two.setDefaultImageResId(R.drawable.bg_loading_image);
		//加载失败时显示的图片
		holder.img_classify_two.setErrorImageResId(R.drawable.bg_error_image);
		//第一个参数用于指定图片的URL地址，第二个参数是创建好的ImageLoader对象
		holder.img_classify_two.setImageUrl(list.get(position).getClassifyImage(), mImageLoader);
		
		
		return convertView;
	}

	private static class ViewHolder {
		TextView tv_calssify_two;
		NetworkImageView img_classify_two;
	}

}
