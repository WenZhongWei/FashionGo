package com.maifeng.fashiongo.adapter;

import java.util.List;

import com.maifeng.fashiongo.R;
import com.maifeng.fashiongo.base.Goods_GetAddressData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Goods_GetAddress_Adapter extends BaseAdapter {
	
	private Context context;
	private List<Goods_GetAddressData> data;
	private String isDeflut = "1";
	public Goods_GetAddress_Adapter(Context context,List<Goods_GetAddressData> data){
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.personal_cneter_goodsaddress_style, null);
			viewHolder = new ViewHolder();
			viewHolder.gtv_name = (TextView) convertView
					.findViewById(R.id.gtv_name);
			viewHolder.gtv_phone = (TextView) convertView
					.findViewById(R.id.gtv_phone);
			viewHolder.gtv_province = (TextView) convertView
					.findViewById(R.id.gtv_province);
			viewHolder.gtv_city = (TextView) convertView
					.findViewById(R.id.gtv_city);
			viewHolder.gtv_area = (TextView) convertView
					.findViewById(R.id.gtv_area);
			viewHolder.gtv_address = (TextView) convertView
					.findViewById(R.id.gtv_address);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (isDeflut.equals(data.get(position).getIsDefault())) {
			viewHolder.gtv_name.setText(data.get(position).getName());
			viewHolder.gtv_phone.setText(data.get(position).getPhone());
			viewHolder.gtv_province.setText("[д╛хо]"
					+ data.get(position).getProvince());
			viewHolder.gtv_city.setText(data.get(position).getCity());
			viewHolder.gtv_area.setText(data.get(position).getArea());
			viewHolder.gtv_address.setText(data.get(position).getAddress());
			notifyDataSetChanged();
		} else {
			viewHolder.gtv_name.setText(data.get(position).getName());
			viewHolder.gtv_phone.setText(data.get(position).getPhone());
			viewHolder.gtv_province.setText(data.get(position).getProvince());
			viewHolder.gtv_city.setText(data.get(position).getCity());
			viewHolder.gtv_area.setText(data.get(position).getArea());
			viewHolder.gtv_address.setText(data.get(position).getAddress());
			notifyDataSetChanged();
		}

		return convertView;
	}

	private class ViewHolder{
		TextView gtv_name;
		TextView gtv_phone;
		TextView gtv_province;
		TextView gtv_city;
		TextView gtv_area;
		TextView gtv_address;
	}
}
