package com.maifeng.fashiongo.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.maifeng.fashiongo.R;
import com.maifeng.fashiongo.base.Delete_MyShareType;
import com.maifeng.fashiongo.base.GetMyShareData;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.volleyhandle.MyImageCache;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class GetMyShareAdapter extends BaseAdapter {
	private Context context;
	private List<GetMyShareData> list;
	private String accessToken;
	//Õº∆¨º”‘ÿ∆˜
	private ImageLoader mImageLoader;
	public GetMyShareAdapter(Context context,List<GetMyShareData> list,String accessToken){
		this.accessToken = accessToken;
		this.context=context;
		this.list=list;
		mImageLoader = new ImageLoader(Volleyhandle.getInstance(context).getRequestQueue(),MyImageCache.getImageCache(context));
	}
	@Override
	public int getCount() {
		return list== null? 0:list.size();
		
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
	public View getView(final int position, View convertvView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		if (convertvView==null) {
			convertvView =LayoutInflater.from(context).inflate(R.layout.getmyshare_style, null);
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
		holder.imageView.setImageUrl(list.get(position).getGoodsImage(), mImageLoader);
		holder.text_goodstwo.setText(list.get(position).getGoodsName());
		holder.text_serialnumber.setText(list.get(position).getGoodsCode());
		holder.text_price.setText(list.get(position).getPrice());
		//…æ≥˝
		holder.button_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("id",list.get(position).getId());
				map.put("accessToken", accessToken);
				VolleyRequest.RequestPost(context,Urls.DELETE_MY_SHARE,"DELETE_MY_SHARE", map,
						new VolleyAbstract(context,VolleyAbstract.listener,VolleyAbstract.errorListener,true) {
							
							@Override
							public void onMySuccess(String result) {
								// TODO Auto-generated method stub
								JsonUtil.parseJsonToBean(result,Delete_MyShareType.class);
								list.remove(position);
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
