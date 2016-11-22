package com.maifeng.fashiongo.adapter;

import java.util.List;

import com.maifeng.fashiongo.R;
import com.maifeng.fashiongo.base.ClassifyThreeData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClassifyThreeAdapter extends BaseAdapter {
	private Context context;
	private List<ClassifyThreeData> list;

	public ClassifyThreeAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public ClassifyThreeAdapter(Context context, List<ClassifyThreeData> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
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
					R.layout.item_classify_three, null);
			holder = new ViewHolder();
			holder.tv_classify_three = (TextView) convertView
					.findViewById(R.id.tv_classify_three);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_classify_three.setText(list.get(position)
				.getClassifyThreeName());
		return convertView;
	}

	private static class ViewHolder {
		TextView tv_classify_three;

	}

}
