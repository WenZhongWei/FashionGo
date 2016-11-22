package com.maifeng.fashiongo.banner;

import com.maifeng.fashiongo.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class LoadListView extends ListView implements OnScrollListener {
	private View footer;//底部布局
	private int totalItemCount;//总数量
	private int lastVisibieItem;//最后一个可见的item;
	private boolean isLoading;//正在加载；
	private ILoadListener iLoadListener;
	
	public LoadListView(Context context) {
		super(context);
		initView(context);
		// TODO Auto-generated constructor stub
	}

	public LoadListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
		// TODO Auto-generated constructor stub
	}

	public LoadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 添加底部加载提示布局到listView
	 * @param context
	 */
	private void initView(Context context){
		LayoutInflater inflater = LayoutInflater.from(context);
		footer = inflate(context, R.layout.footer_layout, null);
		footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
		this.addFooterView(footer);
		this.setOnScrollListener(this);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.lastVisibieItem = firstVisibleItem+visibleItemCount;
		this.totalItemCount = totalItemCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (totalItemCount == lastVisibieItem
				&&scrollState==SCROLL_STATE_IDLE) {
			if (!isLoading) {
				isLoading=true;
				footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
				//加载更多数据
				iLoadListener.onLoad();
			}
			
		}
	}
	
	public void setInterface(ILoadListener iLoadListener){
		this.iLoadListener=iLoadListener;
	}
	
	/**
	 * 加载更多数据的回调接口
	 */
	public interface ILoadListener{
		public void onLoad();
	}

}
