package com.maifeng.fashiongo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.maifeng.fashiongo.AboutUsActivity;
import com.maifeng.fashiongo.FeedBackAcitvtty;
import com.maifeng.fashiongo.R;


public class MoreFragment extends Fragment implements OnClickListener{
	private TableRow tab_about; //关于我们
	private TableRow tab_clear;	//清除缓存
	private TableRow tab_opinion;//意见反馈
	private TableRow tab_update;//检查更新
	private View topbar;
	private TextView tv_title;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view =inflater.inflate(R.layout.more_fragment, container,false);
		initView(view);
		
		return view;
	}

	private void initView(View view) {
		topbar = view.findViewById(R.id.topbar);
		topbar.findViewById(R.id.ll_returnbtn).setVisibility(View.INVISIBLE);
		topbar.findViewById(R.id.ll_functionbtn).setVisibility(View.INVISIBLE);
		tv_title=(TextView) topbar.findViewById(R.id.tv_title);
		tv_title.setText("更多");
		tab_about = (TableRow) view.findViewById(R.id.tab_about);
		tab_clear = (TableRow) view.findViewById(R.id.tab_clear);
		tab_opinion = (TableRow) view.findViewById(R.id.tab_opinion);
		tab_update = (TableRow) view.findViewById(R.id.tab_update);
		tab_about.setOnClickListener(this);
		tab_clear.setOnClickListener(this);
		tab_opinion.setOnClickListener(this);
		tab_update.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tab_about:
			Intent intent = new Intent(getActivity(),AboutUsActivity.class);
			startActivity(intent);
			break;
		case R.id.tab_clear:
			Toast.makeText(getActivity(), "清除缓存", Toast.LENGTH_SHORT).show();
			break;
		case R.id.tab_opinion:
			Intent intent1 = new Intent(getActivity(),FeedBackAcitvtty.class);
			startActivity(intent1);
			break;
		case R.id.tab_update:
			Toast.makeText(getActivity(), "检查更新", Toast.LENGTH_SHORT).show();
			break;
			
		default:
			break;
		}
		
	}
	
}
