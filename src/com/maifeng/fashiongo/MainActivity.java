package com.maifeng.fashiongo;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maifeng.fashiongo.fragment.HomeFragment2;
import com.maifeng.fashiongo.fragment.MineFragment;
import com.maifeng.fashiongo.fragment.MoreFragment;
import com.maifeng.fashiongo.fragment.ShoppingcarFragment;

public  class MainActivity extends FragmentActivity implements OnClickListener {

	public static MainActivity mainActivity;
	
	private ImageView iv_home;
	private ImageView iv_shoppingcar;
	private ImageView iv_mine;
	private ImageView iv_more;
	

	private TextView tv_home;
	private TextView tv_shoppingcar;
	private TextView tv_mine;
	private TextView tv_more;

	private LinearLayout layout_home;
	private LinearLayout layout_shoppingcar;
	private LinearLayout layout_more;
	private LinearLayout layout_mine;

	private ViewPager viewpager;

	private List<Fragment> fragmentList;
	private FragmentPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainActivity=this;

		initView();

		setSelect(0);
		// 适配器
		adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return fragmentList.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return fragmentList.get(arg0);
			}
		};
		//缓存页面
		viewpager.setOffscreenPageLimit(4);
		/*viewpager.setPageTransformer(true, new ZoomOutPageTransformer()); */
		viewpager.setAdapter(adapter);

		// 监听ViewPager动作
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				int current = viewpager.getCurrentItem();// 得到当前viewpager的项
				setTab(current);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	// 初始化控件
	private void initView() {
		viewpager = (ViewPager) findViewById(R.id.viewpager);

		layout_home = (LinearLayout) findViewById(R.id.layout_home);
		layout_shoppingcar = (LinearLayout) findViewById(R.id.layout_shoppingcar);
		layout_mine = (LinearLayout) findViewById(R.id.layout_mine);
		layout_more = (LinearLayout) findViewById(R.id.layout_more);

		iv_home = (ImageView) findViewById(R.id.iv_home);
		iv_shoppingcar = (ImageView) findViewById(R.id.iv_shoppingcar);
		iv_mine = (ImageView) findViewById(R.id.iv_mine);
		iv_more = (ImageView) findViewById(R.id.iv_more);
		

		tv_home = (TextView) findViewById(R.id.tv_home);
		tv_shoppingcar = (TextView) findViewById(R.id.tv_shoppingcar);
		tv_mine = (TextView) findViewById(R.id.tv_mine);
		tv_more = (TextView) findViewById(R.id.tv_more);

		fragmentList = new ArrayList<Fragment>();
//		Fragment mTab1 = new HomeFragment();
		Fragment mTab1 = new HomeFragment2();
//		Fragment mTab2 = new ShoppingcarFragment();
		Fragment mTab2 = new ShoppingcarFragment();
		Fragment mTab3 = new MineFragment();
		Fragment mTab4 = new MoreFragment();

		fragmentList.add(mTab1);
		fragmentList.add(mTab2);
		fragmentList.add(mTab3);
		fragmentList.add(mTab4);

		layout_home.setOnClickListener(this);
		layout_shoppingcar.setOnClickListener(this);
		layout_mine.setOnClickListener(this);
		layout_more.setOnClickListener(this);



	}

	// 将图片和文字设置为亮色
	private void setTab(int current) {
		// TODO Auto-generated method stub
		resetImgs();
		switch (current) {
		case 0: {
			iv_home.setImageResource(R.drawable.ico_home_in);
			tv_home.setTextColor(Color.parseColor("#fc3c4a"));
			break;
		}
		case 1: {
			iv_shoppingcar.setImageResource(R.drawable.ico_shoppingcar_in);
			tv_shoppingcar.setTextColor(Color.parseColor("#fc3c4a"));
			break;
		}
		case 2: {
			iv_mine.setImageResource(R.drawable.ico_mine_in);
			tv_mine.setTextColor(Color.parseColor("#fc3c4a"));
			break;
		}
		case 3: {
			iv_more.setImageResource(R.drawable.ico_more_in);
			tv_more.setTextColor(Color.parseColor("#fc3c4a"));
			break;
		}
		}
	}

	// 重置图标和文字
	private void resetImgs() {
		// TODO Auto-generated method stub
		iv_home.setImageResource(R.drawable.ico_home_unin);
		iv_shoppingcar.setImageResource(R.drawable.ico_shoppingcar_unin);
		iv_mine.setImageResource(R.drawable.ico_mine_unin);
		iv_more.setImageResource(R.drawable.ico_more_unin);

		tv_home.setTextColor(Color.parseColor("#606060"));
		tv_shoppingcar.setTextColor(Color.parseColor("#606060"));
		tv_mine.setTextColor(Color.parseColor("#606060"));
		tv_more.setTextColor(Color.parseColor("#606060"));

	}

	@Override
	// 导航栏按钮响应事件
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.layout_home: {
			setSelect(0);
			break;
		}
		case R.id.layout_shoppingcar: {
			setSelect(1);
			break;
		}
		case R.id.layout_mine: {
			setSelect(2);
			break;
		}
		case R.id.layout_more: {
			setSelect(3);
			break;
		}
		default:
			break;
		}
	}

	// 设置当前显示区域
	private void setSelect(int i) {
		// TODO Auto-generated method stub
		setTab(i);
		viewpager.setCurrentItem(i);
	}

}
