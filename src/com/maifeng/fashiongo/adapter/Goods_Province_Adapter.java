package com.maifeng.fashiongo.adapter;

import java.util.List;

import com.maifeng.fashiongo.R;
import com.maifeng.fashiongo.base.Goods_ProvinceData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Goods_Province_Adapter extends BaseAdapter {
	
	private Context context;
	private List<Goods_ProvinceData> data;
	public Goods_Province_Adapter(Context context,List<Goods_ProvinceData> data){
		this.context=context;
		this.data=data;
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
	public View getView(int position, View convertvView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertvView==null) {
			convertvView=LayoutInflater.from(context).inflate(R.layout.province_layout_style,null);
			holder = new ViewHolder();
			holder.text_province=(TextView)convertvView.findViewById(R.id.text_province);
			convertvView.setTag(holder);
		}else {
			holder=(ViewHolder) convertvView.getTag();
		}
		holder.text_province.setText(data.get(position).getpName());
		return convertvView;
	}
	private class ViewHolder{
		TextView text_province;
	}
}
