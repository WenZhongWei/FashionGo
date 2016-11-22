package com.maifeng.fashiongo.adapter;

import java.util.List;


import com.maifeng.fashiongo.R;
import com.maifeng.fashiongo.base.GoodsSpecificationsSize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SizeAdapter extends BaseAdapter {

	private Context context;
	private List<GoodsSpecificationsSize> list;

	public SizeAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	public void setData(List<GoodsSpecificationsSize> list){
		this.list=list;
		notifyDataSetChanged();
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
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_size, null);
			holder = new ViewHolder();
			holder.tv_size = (TextView) convertView
					.findViewById(R.id.tv_size);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_size.setText(list.get(position).getSize());
		return convertView;
	}

	private class ViewHolder {
		TextView tv_size;
	}

}
