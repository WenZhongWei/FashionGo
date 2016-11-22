package com.maifeng.fashiongo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * 引导界面 
 * a.viewpager 
 * b.4张图片，最后一张图片有按钮，点击进入主界面 
 * c.设置viewpager的预加载数量
 * 
 * 
 */

public class UserGuideActivity extends Activity implements OnClickListener{
	
	private ArrayList<View> mList = new ArrayList<View>();
	private ViewPager mPager;
	private int[] imgRes = new int[] { 
			R.drawable.img_guide_background_1,
			R.drawable.img_guide_background_2,
			R.drawable.img_guide_background_3,
			R.drawable.img_guide_background_4 };
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  //没有标题栏
		setContentView(R.layout.activity_user_guild);
		
		LayoutInflater mInflater=getLayoutInflater();
		for (int i = 0; i < imgRes.length; i++) {
			View inflate=mInflater.inflate(R.layout.pager_item, null);
			ImageView iv_guide=(ImageView) inflate.findViewById(R.id.iv_guide);
			iv_guide.setBackgroundResource(imgRes[i]);
			if (i==imgRes.length-1) {
				ImageButton ibtn_guide=(ImageButton) inflate.findViewById(R.id.ibtn_guide);
				ibtn_guide.setVisibility(View.VISIBLE);
				ibtn_guide.setOnClickListener(this);
			}
			mList.add(inflate);
		}
		mPager=(ViewPager) findViewById(R.id.viewpager_guide);
		//设置ViwePager的预加载
		mPager.setOffscreenPageLimit(2);
		mPager.setAdapter(new MyViewPager());
		
	}
	
	class MyViewPager extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			
			return mList.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			
			View view = mList.get(position);
			mPager.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub

			container.removeView(mList.get(position));
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			
			return arg0 == arg1;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.ibtn_guide:
			startActivity(new Intent(getApplicationContext(), MainActivity.class));
			finish();
			break;

		default:
			break;
		}
	}
}
