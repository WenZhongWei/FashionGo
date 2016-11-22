package com.maifeng.fashiongo.constant;

import android.support.v4.app.Fragment;

public abstract class LazyFragment extends Fragment {
	/*
	 * �ο���վ 
	 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/1021/1813.html
	 */
	
	protected boolean isVisible;//�Ƿ���ʾ
	
	/**
	 * ����ʵ��Fragment���ݵĻ�����.
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
	 * �ɼ�ʱ����
	 */
	protected void onVisible() {
		// TODO Auto-generated method stub
		lazyLoad();
	}
	
	/**
	 * �ӳټ������ݳ��󷽷�
	 */
	protected abstract void lazyLoad();

	/**
	 * ���ɼ�ʱ����
	 */
	protected void onInvisible() {
		// TODO Auto-generated method stub
		
	}


}
