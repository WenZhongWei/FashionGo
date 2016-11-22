package com.maifeng.fashiongo.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.maifeng.fashiongo.FirstClassifyAcitvity;
import com.maifeng.fashiongo.GoodDetailActivity;
import com.maifeng.fashiongo.GoodListActivity;
import com.maifeng.fashiongo.R;

import com.maifeng.fashiongo.adapter.RecommendationAdapter;
import com.maifeng.fashiongo.banner.ImageCycleView;
import com.maifeng.fashiongo.banner.ImageCycleView.ImageCycleViewListener;
import com.maifeng.fashiongo.banner.XScrollView;
import com.maifeng.fashiongo.base.BannerData;
import com.maifeng.fashiongo.base.BannerType;
import com.maifeng.fashiongo.base.RecommendationData;
import com.maifeng.fashiongo.base.RecommendationType;
import com.maifeng.fashiongo.base.ThreeGoodsADInfoData;
import com.maifeng.fashiongo.base.ThreeGoodsADInfoType;
import com.maifeng.fashiongo.constant.LazyFragment;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.util.KeyboradUtil;
import com.maifeng.fashiongo.util.ListViewUtils;
import com.maifeng.fashiongo.volleyhandle.MyImageCache;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class HomeFragment2 extends LazyFragment implements OnClickListener,XScrollView.IXScrollViewListener{
	private ImageView img_menu;
	private ScrollView scrollView;
	private List<BannerData> bannerlist;
	private List<RecommendationData>recommendationlist;
	private RecommendationAdapter mRecommendationAdapter;
	private ListView listView;
	private List<ThreeGoodsADInfoData> adInfolist;
	
	
	
	private ImageCycleView mAdView;

	private ArrayList<String> mImageUrl = null;
	
	private NetworkImageView left_image,right_image_top,right_image_bottom;
	
	private NetworkImageView[] networkImageViews ;
	
	private EditText et_search;
	
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    
    private XScrollView mScrollView;
    
    private Handler mHandler;
	
    private int page=1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_fragment2, container, false);
		
		//关闭焦点键盘
		getActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		et_search = (EditText) view.findViewById(R.id.et_search);
		img_menu = (ImageView) view.findViewById(R.id.img_menu);
		img_menu.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// 跳转到商品分类页面
			Intent intent = new Intent(getActivity()
					.getApplicationContext(), FirstClassifyAcitvity.class);
			startActivity(intent);
		
		}
	});
		mScrollView = (XScrollView) view.findViewById(R.id.scroll_view);
        mScrollView.setPullRefreshEnable(true);
        mScrollView.setPullLoadEnable(true);
        mScrollView.setAutoLoadEnable(true);
        mScrollView.setIXScrollViewListener(this);
        mScrollView.setRefreshTime(getTime());
        View content = LayoutInflater.from(getActivity()).inflate(R.layout.vw_scroll_view_content, null);
		initView(content);
		search();
		isPrepared = true;
		lazyLoad();
		volleyGetBanner();
		volleyGetImage(networkImageViews);
		volleyGetRecommendation(page);
		
		return view;
		
	}
	protected void overridePendingTransition(int pushLeftIn, int pushLeftOut) {
		// TODO Auto-generated method stub
		
	}
	protected void overridePendingTransition() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		if (!isPrepared||!isVisible) {
			return;
		}

		
	}
	@Override
	public void onStop() {
		super.onStop();
		Volleyhandle.getInstance(getActivity()).getRequestQueue().cancelAll("GET_BANNER_LIST");
		Volleyhandle.getInstance(getActivity()).getRequestQueue().cancelAll("GET_THREE_GOODS_AD_INFO");
		Volleyhandle.getInstance(getActivity()).getRequestQueue().cancelAll("GET_RECOMMENDATION");
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mAdView.pushImageCycle();
		
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mAdView.pushImageCycle();
	}
	private void initView(View view) {
		mHandler = new Handler();
		left_image = (NetworkImageView) view.findViewById(R.id.left_image);
		right_image_top = (NetworkImageView) view.findViewById(R.id.right_image_top);
		right_image_bottom = (NetworkImageView) view.findViewById(R.id.right_image_bottom);
		left_image.setOnClickListener(this);
		right_image_top.setOnClickListener(this);
		right_image_bottom.setOnClickListener(this);
		networkImageViews = new NetworkImageView[]{left_image,right_image_top,right_image_bottom};
		listView = (ListView) view.findViewById(R.id.recommendation_list);
		mAdView = (ImageCycleView)view.findViewById(R.id.ad_view);
		scrollView = (ScrollView) view.findViewById(R.id.scrollView);
		mScrollView.setView(view);
	}

	private void volleyGetBanner() {
		VolleyRequest.RequestGet(getActivity(), Urls.GET_BANNER_LIST, "GET_BANNER_LIST",
				new VolleyAbstract(getActivity(),VolleyAbstract.listener,VolleyAbstract.errorListener,true) {
					
					@Override
					public void onMySuccess(String result) {
						bannerlist = JsonUtil.parseJsonToBean(result, BannerType.class).getData();
						mImageUrl = new ArrayList<String>();
						for (int i = 0; i < bannerlist.size(); i++) {
							mImageUrl.add(bannerlist.get(i).getBannerImage());
						}
						mAdView.setImageResources(mImageUrl, mAdCycleViewListener);
					}

					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub
						
					}
				});
		
	}
	private void volleyGetImage(final NetworkImageView[] imageViews) {
		VolleyRequest.RequestGet(getActivity(), Urls.GET_THREE_GOODS_AD_INFO, "GET_THREE_GOODS_AD_INFO",
				new VolleyAbstract(getActivity(),VolleyAbstract.listener,VolleyAbstract.errorListener,true) {
					
					@Override
					public void onMySuccess(String result) {
					ThreeGoodsADInfoType type =JsonUtil.parseJsonToBean(result, ThreeGoodsADInfoType.class);
					adInfolist = type.getData();
					ImageLoader imageLoader =new ImageLoader(Volleyhandle.getInstance(context).getRequestQueue(), MyImageCache.getImageCache(context));
					
					for (int i = 0; i < adInfolist.size(); i++) {
						imageViews[i].setScaleType(ScaleType.CENTER_CROP );
						imageViews[i].setImageUrl(adInfolist.get(i).getADImage(), imageLoader);
					}
					
						
					}
					
					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub
						
					}
				});
	}
	private void volleyGetRecommendation(int page) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("page", String.valueOf(page));
		VolleyRequest.RequestPost(getActivity(), Urls.GET_RECOMMENDATION, 
				"GET_RECOMMENDATION", map, new VolleyAbstract(getActivity(),VolleyAbstract.listener,VolleyAbstract.errorListener,false) {
					
					@Override
					public void onMySuccess(String result) {
						recommendationlist=JsonUtil.parseJsonToBean(result, RecommendationType.class).getData();
						mRecommendationAdapter = new RecommendationAdapter(getActivity(),recommendationlist);
						listView.setAdapter(mRecommendationAdapter);
						ListViewUtils.setListViewHeightBasedOnChildren(listView);
						listView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								Intent intent = new Intent(getActivity(),GoodDetailActivity.class);
								intent.putExtra("Code", "homelist");
								intent.putExtra("goodsCode",recommendationlist.get(position).getGoodsCode());
								startActivity(intent);
							}
						});
					}
					
					@Override
					public void onMyError(VolleyError error) {
						
					}
				});
	}
	
	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		@Override
		public void onImageClick(int position, View imageView) {
			//  单击图片处理事件
			Intent intent = new Intent(getActivity(),GoodDetailActivity.class);
			intent.putExtra("Code", "home");
			intent.putExtra("goodsCode", bannerlist.get(position).getGoodscode());
			startActivity(intent);
			
		}

		@Override
		public void displayImage(String imageURL, NetworkImageView imageView,com.android.volley.toolbox.ImageLoader imageLoader) {

			imageView.setImageUrl(imageURL, imageLoader);
		}
	};

// 搜索
public void search() {
	et_search.setOnEditorActionListener(new OnEditorActionListener() {

		@Override
		public boolean onEditorAction(TextView v, int actionId,
				KeyEvent event) {
			// 关闭软件盘
			KeyboradUtil.closeKeyborad(getActivity());
			
			if (actionId == EditorInfo.IME_ACTION_SEARCH) {
				
				if (TextUtils.isEmpty(et_search.getText())||et_search.getText().toString().trim().length()==0) {
					Toast.makeText(getActivity(), "搜索内容不能为空", Toast.LENGTH_SHORT).show();
				}else {
					Intent intent = new Intent(getActivity(),GoodListActivity.class);

					String keyword = et_search.getText().toString().trim();
					intent.putExtra("home_keyword", keyword);
					intent.putExtra("Code", "home");
					startActivity(intent);
				}
				
			}
			return false;
		}

	});
}
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.left_image:
		Toast.makeText(getActivity(), "正在开发中...", Toast.LENGTH_SHORT).show();
		break;
	case R.id.right_image_top:
		Toast.makeText(getActivity(), "正在开发中...", Toast.LENGTH_SHORT).show();
		break;
	case R.id.right_image_bottom:
		Toast.makeText(getActivity(), "正在开发中...", Toast.LENGTH_SHORT).show();
		break;
	}
	
}
private String getTime() {
    return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
}
private void onLoad() {
    mScrollView.stopRefresh();
    mScrollView.stopLoadMore();
    mScrollView.setRefreshTime(getTime());
}
@Override
public void onRefresh() {
	// TODO Auto-generated method stub
	 mHandler.postDelayed(new Runnable() {
         @Override
         public void run() {
     		volleyGetBanner();
    		volleyGetImage(networkImageViews);
    		volleyGetRecommendation(page);
             onLoad();
         }
     }, 2500);
	
	
}

@Override
public void onLoadMore() {
	// TODO Auto-generated method stub
    mHandler.postDelayed(new Runnable() {
        @Override
        public void run() {
        	page=page++;
        	volleyGetRecommendation(page);
            onLoad();
        }
    }, 2500);
}



}
