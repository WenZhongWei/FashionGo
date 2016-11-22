package com.maifeng.fashiongo.adapter;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.maifeng.fashiongo.R;
import com.maifeng.fashiongo.base.GoodDetailGoodsImage;
import com.maifeng.fashiongo.volleyhandle.MyImageCache;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyPagerAdapter extends PagerAdapter {
	private Context mContext;
	private List<GoodDetailGoodsImage> urllist;
	private ImageLoader imageLoader;
	private ImageView btn_last;
	private ImageView btn_next;
	
	public MyPagerAdapter (Context context,List<GoodDetailGoodsImage> urllist){
		mContext = context;
		this.urllist = urllist;
		imageLoader = new ImageLoader(Volleyhandle.getInstance(context).getRequestQueue(), MyImageCache.getImageCache(context));
	}
	
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return urllist == null?0:urllist.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		NetworkImageView imageView= null;
//		if (urllist.isEmpty()) {
			imageView= new NetworkImageView(mContext);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			
			imageView.setDefaultImageResId(R.drawable.bg_loading_image);
			imageView.setErrorImageResId(R.drawable.bg_error_image);
			imageView.setImageUrl(urllist.get(position).getGoodsImageList(), imageLoader);
//		}else {
//			imageView = (NetworkImageView) mImageViewCacheList.remove(0);
//		}
		container.addView(imageView);
		
		return imageView;
	}

	/**
	 * Ïú»Ù
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		NetworkImageView view = (NetworkImageView) object;
		container.removeView(view);
	}
	
	
}
