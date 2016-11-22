package com.maifeng.fashiongo.adapter;

import java.util.List;

import com.maifeng.fashiongo.R;
import com.maifeng.fashiongo.base.GoodsAddressType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GoodsAddress_Adapter extends BaseAdapter {

	private Context context;
	private List<GoodsAddressType> list;
	public GoodsAddress_Adapter(Context context,List<GoodsAddressType> list){
		this.context=context;
		this.list=list;
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		//创建一个盒子
		ViewHolder viewHolder = null;
		if (convertView == null ) {
			convertView= LayoutInflater.from(context).inflate(R.layout.personal_cneter_goodsaddress_style,null);
			//把布局保存起来
			viewHolder = new ViewHolder();
			viewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
			viewHolder.tv_phone = (TextView)convertView.findViewById(R.id.tv_phone);
			viewHolder.tv_address = (TextView)convertView.findViewById(R.id.tv_address);
			//布局保存起来。避免二次加载再去FindViewById寻找一处控件
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder)convertView.getTag();
		}
		//操作holder中的textView就是操作布局对象中的TextView对象
		viewHolder.tv_name.setText(list.get(position).getTv_name());
		viewHolder.tv_phone.setText(list.get(position).getTv_phone());
		viewHolder.tv_address.setText(list.get(position).getTv_address());
		
		return convertView;
	}

	//通过ViewHolder减少findViewById方法执行的次数
	private class ViewHolder{
		TextView tv_name;
		TextView tv_phone;
		TextView tv_address;
	}
}
