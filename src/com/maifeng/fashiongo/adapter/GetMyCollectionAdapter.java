package com.maifeng.fashiongo.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.maifeng.fashiongo.R;
import com.maifeng.fashiongo.base.Delete_MyCollectionType;
import com.maifeng.fashiongo.base.GetMyCollectionData;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.volleyhandle.MyImageCache;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

public class GetMyCollectionAdapter extends BaseAdapter {
	private Activity activity;
	private Context context;
	private List<GetMyCollectionData> data;
	private String accessToken;
	//Õº∆¨º”‘ÿ∆˜
	private ImageLoader mImageLoader;
	public GetMyCollectionAdapter(Context context,List<GetMyCollectionData> data,String accessToken){
		
		this.context=context;
		this.data=data;
		this.accessToken=accessToken;
		mImageLoader = new ImageLoader(Volleyhandle.getInstance(context).getRequestQueue(),MyImageCache.getImageCache(context));
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data == null?0:data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertvView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertvView==null) {
			convertvView =LayoutInflater.from(context).inflate(R.layout.getmycollection_style, null);
			holder = new ViewHolder();
			holder.imageView=(NetworkImageView)convertvView.findViewById(R.id.imageView);
			holder.text_goodstwo=(TextView)convertvView.findViewById(R.id.text_goodstwo);
			holder.text_serialnumber=(TextView)convertvView.findViewById(R.id.text_serialnumber);
			holder.text_price=(TextView)convertvView.findViewById(R.id.text_price);
			holder.button_delete=(Button)convertvView.findViewById(R.id.button_delete);
			convertvView.setTag(holder);
		}else {
			holder=(ViewHolder)convertvView.getTag();
		}
		//º”‘ÿ÷–œ‘ æµƒÕº∆¨
		holder.imageView.setDefaultImageResId(R.drawable.bg_loading_image);
		//º”‘ÿ ß∞‹ ±œ‘ æµƒÕº∆¨
		holder.imageView.setErrorImageResId(R.drawable.bg_error_image);
		holder.imageView.setImageUrl(data.get(position).getGoodsImage(), mImageLoader);
		holder.text_goodstwo.setText(data.get(position).getGoodsName());
		holder.text_serialnumber.setText(data.get(position).getGoodsCode());
		holder.text_price.setText(data.get(position).getPrice());
		//…æ≥˝
		holder.button_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Map<String,String> map = new HashMap<String, String>();
				map.put("id",data.get(position).getId());
				map.put("accessToken", accessToken);
				System.out.println(accessToken);
				VolleyRequest.RequestPost(context,Urls.DELETE_COLLECTION,"DELETE_COLLECTION", map,
						new VolleyAbstract(context,VolleyAbstract.listener,VolleyAbstract.errorListener,true) {
							
							@Override
							public void onMySuccess(String result) {
								// TODO Auto-generated method stub
								JsonUtil.parseJsonToBean(result,Delete_MyCollectionType.class);
								data.remove(position);
								notifyDataSetChanged();
							}
							
							@Override 
							public void onMyError(VolleyError error) {
								// TODO Auto-generated method stub
							}
						});
			}
		});
		
		
		return convertvView;
	}
	private class ViewHolder{
		NetworkImageView imageView;
		TextView text_goodstwo;
		TextView text_serialnumber;
		TextView text_price;
		Button button_delete;
	}
}
