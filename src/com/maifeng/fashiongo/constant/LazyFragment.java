package com.maifeng.fashiongo.constant;

import android.support.v4.app.Fragment;

public abstract class LazyFragment extends Fragment {
	/*
	 * 参考网站 
	 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/1021/1813.html
	 */
	
	protected boolean isVisible;//是否显示
	
	/**
	 * 这里实现Fragment数据的缓加载.
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		}else {
			isVisible = false;
			onInvisible();
		}
	}
	/**
	 * 可见时调用
	 */
	protected void onVisible() {
		// TODO Auto-generated method stub
		lazyLoad();
	}
	
	/**
	 * 延迟加载数据抽象方法
	 */
	protected abstract void lazyLoad();

	/**
	 * 不可见时调用
	 */
	protected void onInvisible() {
		// TODO Auto-generated method stub
		
	}


}
