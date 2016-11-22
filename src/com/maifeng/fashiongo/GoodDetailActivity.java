package com.maifeng.fashiongo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.android.volley.VolleyError;
import com.maifeng.fashiongo.adapter.ColorAdapter;
import com.maifeng.fashiongo.adapter.MyPagerAdapter;
import com.maifeng.fashiongo.adapter.SizeAdapter;
import com.maifeng.fashiongo.base.AddGoodsToCart;
import com.maifeng.fashiongo.base.Add_Collection;
import com.maifeng.fashiongo.base.GoodDetailData;
import com.maifeng.fashiongo.base.GoodDetailGoodsImage;
import com.maifeng.fashiongo.base.GoodDetailType;
import com.maifeng.fashiongo.base.GoodsSpecificationsData;
import com.maifeng.fashiongo.base.GoodsSpecificationsSize;
import com.maifeng.fashiongo.base.GoodsSpecificationsType;
import com.maifeng.fashiongo.base.OrderData;
import com.maifeng.fashiongo.base.ShareGoods;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

/**
 * ��Ʒ����ҳ����
 * 
 * @author liekkas
 * 
 */
public class GoodDetailActivity extends Activity implements OnClickListener {
	private GoodDetailData detail;
	private List<GoodDetailGoodsImage> urllist;
	private List<GoodsSpecificationsData> specifications;
	private List<GoodsSpecificationsSize> sizes = new ArrayList<GoodsSpecificationsSize>();
	private TextView layout_title;
	private LinearLayout layout_left;
	private String goodsCode;
	private TextView tv_goodname, tv_about, tv_goodcode, tv_isPackage,
			tv_originalPrice, tv_price, tv_goodnum;
	private TextView tv_color, tv_size;
	private Button btn_minus, btn_add;
	private Button btn_join_shoppingcar, btn_pay;
	private EditText et_num;
	private int etnum = 0;
	private ViewPager vPager;
	private ImageView btn_last, btn_next;
	private ImageView layout_right_share, layout_right_collect;
	private int currindex;
	private PopupWindow popupWindow;
	private String accessToken;

	private String text = "";
	private String imageurl = "http://172.16.40.47/shop/public/img/ad/2.jpg";
	private String title = "FashionGo";
	private String url = "";

	private Intent intent;
	private String Code;
	/*private OrderNumberType	orderNumberType;*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_good_detail);

		// ��¼��ʶ
		SharedPreferences pref = getSharedPreferences("myPref", MODE_PRIVATE);
		accessToken = pref.getString("accessToken", "");
		
		intent = this.getIntent();
		Code = intent.getStringExtra("Code");
		if (accessToken.equals("")) {
			Intent intent2 = new Intent(GoodDetailActivity.this,LoginActivity.class);
			startActivity(intent2);
			if (Code.equals("GoodList")) {
				FirstClassifyAcitvity.firstClassifyAcitvity.finish();
				ThreeClassifyActivity.threeClassifyActivity.finish();
				GoodListActivity.goodListActivity.finish();
			}
			MainActivity.mainActivity.finish();
			finish();
		}else {
			initView();
			if (Code.equals("GoodList")) {
				goodsCode = intent.getStringExtra("goodsCode");
				detaildata(accessToken, goodsCode);
			}else if (Code.equals("home")) {
				goodsCode = intent.getStringExtra("goodsCode");
				detaildata(accessToken, goodsCode);
				
			}else if (Code.equals("homelist")) {
				goodsCode = intent.getStringExtra("goodsCode");
				detaildata(accessToken, goodsCode);
				
			}else if(Code.equals("mycollection")){
				goodsCode = intent.getStringExtra("goodsCode");
				detaildata(accessToken, goodsCode);
			}else if(Code.equals("myshare")){
				goodsCode = intent.getStringExtra("goodsCode");
				detaildata(accessToken, goodsCode);
			}
		}
		

	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Volleyhandle.getInstance(this.getApplicationContext()).getRequestQueue().cancelAll("GET_GOODS_DETAILS");
		Volleyhandle.getInstance(this.getApplicationContext()).getRequestQueue().cancelAll("GET_GOODS_SPECIFICATIONS");
		Volleyhandle.getInstance(this.getApplicationContext()).getRequestQueue().cancelAll("SHARE_GOODS");
		Volleyhandle.getInstance(this.getApplicationContext()).getRequestQueue().cancelAll("ADD_COLLECTION");
		Volleyhandle.getInstance(this.getApplicationContext()).getRequestQueue().cancelAll("ADD_GOODS_TO_CART");
	}

	private void detaildata(String accessToken, String goodsCode) {
		// ��װ��������
		Map<String, String> map = new HashMap<String, String>();
		map.put("accessToken", accessToken);
		map.put("goodsCode", goodsCode);
		VolleyRequest.RequestPost(getApplicationContext(),
				Urls.GET_GOODS_DETAILS, "GET_GOODS_DETAILS", map,
				new VolleyAbstract(this, VolleyAbstract.listener,
						VolleyAbstract.errorListener,true) {

					@Override
					public void onMySuccess(String result) {
						detail = JsonUtil.parseJsonToBean(result,
								GoodDetailType.class).getData();
						urllist = detail.getGoodsImageList().getGoodsImage();
						tv_goodname.setText(detail.getGoodsName());
//						tv_about.setText(detail.getGoodsInfo());
						tv_about.setText("����:��������\n���ʵ�:��������\n�����:��������\n��Ʒ��:��������\n");
						tv_goodcode.setText(detail.getGoodsCode());
						tv_originalPrice.setText(detail.getOriginalPrice());
						tv_originalPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); 
						tv_price.setText(detail.getPrice());
						if (detail.getIsPackage() == 0) {
							tv_isPackage.setText("��");
						} else if (detail.getIsPackage() == 1) {
							tv_isPackage.setText("��");
						}
						tv_goodnum.setText(detail.getTotalNum() + "");
						etnum = detail.getTotalNum();
						MyPagerAdapter adapter = new MyPagerAdapter(
								getApplicationContext(), urllist);
						vPager.setAdapter(adapter);

						btn_last.setOnClickListener(new OnClickListener() {
							// ����ص���һ��ͼƬ
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								if (currindex == 0) {

								} else {
									currindex--;
									setCurrentItem(currindex);
								}
							}
						});

						btn_next.setOnClickListener(new OnClickListener() {
							// �������һ��ͼƬ
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								if (currindex == urllist.size() - 1) {

								} else {
									currindex++;
									setCurrentItem(currindex);
								}
							}
						});

						specifications(detail.getGoodsCode());
					}

					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void specifications(String goodsCode) {
		// ��װ��������
		Map<String, String> map = new HashMap<String, String>();
		map.put("goodsCode", goodsCode);
		VolleyRequest.RequestPost(getApplicationContext(),
				Urls.GET_GOODS_SPECIFICATIONS, "GET_GOODS_SPECIFICATIONS", map,
				new VolleyAbstract(this, VolleyAbstract.listener,
						VolleyAbstract.errorListener,true) {

					@Override
					public void onMySuccess(String result) {
						specifications = JsonUtil.parseJsonToBean(result,
								GoodsSpecificationsType.class).getData();

					}

					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});

	}

	public void setCurrentItem(int index) {
		vPager.setCurrentItem(index);
	}

	@Override
	public void onClick(View v) {
		int num = 0;
		switch (v.getId()) {
		case R.id.layout_left:
			finish();
			break;
		case R.id.layout_color:
			if (detail == null) {

				return;
			}
			if (popupWindow != null) {
				popupWindow.dismiss();
				popupWindow = null;
			}
			showWindow(v);
			break;
		case R.id.layout_size:
			if (tv_color.getText().equals("��ѡ��")) {
				Toast.makeText(this, "��ѡ����ɫ���ٲ�����", Toast.LENGTH_SHORT).show();
				return;
			}
			if (popupWindow != null) {
				popupWindow.dismiss();
				popupWindow = null;
			}
			showWindow2(v, specifications.get(0).getSizeList()
					.getSpecificationsId());
			break;
		case R.id.btn_minus:
			if (et_num.getText().toString().equals("")) {
				et_num.setText("0");
			}
			num = Integer.valueOf(et_num.getText().toString());
			num--;
			if (num <= 0) {
				num = 1;
			}
			et_num.setText(num + "");
			et_num.setSelection(et_num.getText().length());
			break;
		case R.id.btn_add:
			if (et_num.getText().toString().equals("")) {
				et_num.setText("0");
			}
			num = Integer.valueOf(et_num.getText().toString());
			num++;
			if (num > etnum && etnum != 0) {
				num = etnum;
			}
			et_num.setText(num + "");
			et_num.setSelection(et_num.getText().length());
			break;
		case R.id.btn_join_shoppingcar: // ���빺�ﳵ
			if (accessToken.equals("")) {
				Toast.makeText(this, "���¼���ٲ�����", Toast.LENGTH_LONG).show();
				return;
			}
			if (et_num.getText().toString().equals("")
					|| et_num.getText().toString().equals("0")) {
				Toast.makeText(this, "ѡ����Ʒ������", Toast.LENGTH_LONG).show();
				return;
			}
			if (tv_size.getText().equals("��ѡ��")) {
				Toast.makeText(this, "��ѡ����Ʒ���ͺ��ٲ�����", Toast.LENGTH_LONG).show();
				return;
			}
			carInfo(goodsCode, specifications.get(0).getSizeList()
					.getSpecificationsId());

			break;
		case R.id.btn_pay: // ����֧��
			if (accessToken.equals("")) {
				Toast.makeText(this, "���¼���ٲ�����", Toast.LENGTH_LONG).show();
				return;
			}
			if (et_num.getText().toString().equals("")
					|| et_num.getText().toString().equals("0")) {
				Toast.makeText(this, "ѡ����Ʒ������", Toast.LENGTH_LONG).show();
				return;
			}
			if (tv_size.getText().equals("��ѡ��")) {
				Toast.makeText(this, "��ѡ����Ʒ���ͺ��ٲ�����", Toast.LENGTH_LONG).show();
				return;
			}
			String imageUrl = null;
			String name = null;
			String price = null;
			String number = null;
			List<OrderData> orderDatas = new ArrayList<OrderData>();
			/*List<CreateOrderType> orderlist = new ArrayList<CreateOrderType>();*/
			imageUrl=urllist.get(0).getGoodsImageList();
			name=detail.getGoodsName();
			price=detail.getPrice();
			number=String.valueOf(et_num.getText());
			OrderData orderData=new OrderData(imageUrl, name, price, number);
			/*CreateOrderType createOrderType = new CreateOrderType(
					detail.getGoodsCode(), detail.getGoodsName());
			orderDatas.add(orderData);
			orderlist.add(createOrderType);*/
			
			if (orderData!=null||orderDatas.isEmpty()) {
				Intent intent = new Intent(GoodDetailActivity.this,Confirm_Order_Activity.class);
				intent.putExtra("Code", "detail");
				intent.putExtra("orderdata", (Serializable)orderDatas);
				intent.putExtra("orderNum", "123456");
				startActivity(intent);
//				addOrderPost(orderlist,orderDatas);
			}
			
			break;
		case R.id.layout_right_collect: // �ղ�
			if (accessToken.equals("")) {
				Toast.makeText(this, "���¼���ٲ�����", Toast.LENGTH_LONG).show();
				return;
			}
			collect(goodsCode);
			break;
		case R.id.layout_right_share: // ����
			if (accessToken.equals("")) {
				Toast.makeText(this, "���¼���ٲ�����", Toast.LENGTH_LONG).show();
				return;
			}
			showShare();
			Shared(goodsCode);
			break;
		default:
			break;
		}

	}
	/*private void addOrderPost(List<CreateOrderType> orderlist,final List<OrderData> orderDatas){
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		map.put("accessToken", accessToken);
		map.put("ordertype", gson.toJson(orderlist));
		VolleyRequest.RequestPost(getApplicationContext(), Urls.GET_ADDORDER, "GET_ADDORDER", map, 
				new VolleyAbstract(getApplicationContext(),VolleyAbstract.listener,VolleyAbstract.errorListener,true) {
					
					@Override
					public void onMySuccess(String result) {
						// TODO Auto-generated method stub
						orderNumberType=JsonUtil.parseJsonToBean(result, OrderNumberType.class);
						String orderNum =orderNumberType.getData();
						if(orderNum!=null||orderNum.isEmpty()){
							Intent intent = new Intent(GoodDetailActivity.this,Confirm_Order_Activity.class);
							intent.putExtra("Code", "detail");
							intent.putExtra("orderdata", (Serializable)orderDatas);
							intent.putExtra("orderNum", orderNum);
							startActivity(intent);
						}else {
							Toast.makeText(GoodDetailActivity.this, orderNumberType.getMessage(), Toast.LENGTH_SHORT).show();
						}

					}
					
					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub
						
					}
				});
	} */

	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// �ر�sso��Ȩ
		oks.disableSSOWhenAuthorize();

		// title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
		oks.setTitle(title);
		// titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		oks.setTitleUrl("http://sharesdk.cn");
		// text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		oks.setText("���Ƿ����ı�");
		oks.setImageUrl(imageurl);
		// url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		oks.setUrl("http://sharesdk.cn");
		// ��������GUI
		oks.show(this);

	}

	private void Shared(String goodsCode) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("accessToken", accessToken);
		map.put("goodsCode", goodsCode);
		VolleyRequest.RequestPost(getApplicationContext(), Urls.SHARE_GOODS,
				"SHARE_GOODS", map, new VolleyAbstract(this,
						VolleyAbstract.listener, VolleyAbstract.errorListener,true) {

					@Override
					public void onMySuccess(String result) {
						ShareGoods shareGoods = JsonUtil.parseJsonToBean(
								result, ShareGoods.class);
						switch (shareGoods.getErrorcode()) {
						case 0:
//							Toast.makeText(getApplicationContext(),
//									shareGoods.getMessage(), Toast.LENGTH_SHORT)
//									.show();
							break;

						default:
//							Toast.makeText(getApplicationContext(),
//									shareGoods.getMessage(), Toast.LENGTH_SHORT)
//									.show();
							break;
						}
					}

					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void collect(String goodsCode) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("accessToken", accessToken);
		map.put("goodsCode", goodsCode);
		VolleyRequest.RequestPost(getApplicationContext(), Urls.ADD_COLLECTION,
				"ADD_COLLECTION", map, new VolleyAbstract(this,
						VolleyAbstract.listener, VolleyAbstract.errorListener,true) {

					@Override
					public void onMySuccess(String result) {
						Add_Collection add_Collection = JsonUtil
								.parseJsonToBean(result, Add_Collection.class);
						switch (add_Collection.getErrorcode()) {
						case 0:
							Toast.makeText(getApplicationContext(),
									add_Collection.getMessage(),
									Toast.LENGTH_SHORT).show();
							break;

						default:
							Toast.makeText(getApplicationContext(),
									add_Collection.getMessage(),
									Toast.LENGTH_SHORT).show();
							break;
						}
					}

					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void carInfo(String goodsCode, String specificationsId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("accessToken", accessToken);
		map.put("goodsCode", goodsCode);
		map.put("goodsNum", String.valueOf(et_num.getText()));
		map.put("specificationsId", specificationsId);
		VolleyRequest.RequestPost(getApplicationContext(),
				Urls.ADD_GOODS_TO_CART, "ADD_GOODS_TO_CART", map,
				new VolleyAbstract(this, VolleyAbstract.listener,
						VolleyAbstract.errorListener,true) {

					@Override
					public void onMySuccess(String result) {
						// TODO Auto-generated method stub
						AddGoodsToCart addGoodsToCart = JsonUtil
								.parseJsonToBean(result, AddGoodsToCart.class);
						switch (addGoodsToCart.getErrorcode()) {
						case 0:
							Toast.makeText(getApplicationContext(),
									addGoodsToCart.getMessage(),
									Toast.LENGTH_SHORT).show();
							break;

						default:
							Toast.makeText(getApplicationContext(),
									addGoodsToCart.getMessage(),
									Toast.LENGTH_SHORT).show();
							break;
						}

					}

					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void showWindow(View parent) {
		if (popupWindow == null) {
			View view = LayoutInflater.from(this).inflate(
					R.layout.dialog_choice_color, null);
			view.setFocusableInTouchMode(true);
			popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
		}
		ListView listView = (ListView) popupWindow.getContentView()
				.findViewById(R.id.lv_color);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				sizes.clear();
				tv_color.setText(specifications.get(position).getModel());
				sizes.add(specifications.get(position).getSizeList());
			}
		});
		ColorAdapter colorAdapter = new ColorAdapter(this);
		colorAdapter.setData(specifications);
		listView.setAdapter(colorAdapter);
		// ʹ��ۼ�
		popupWindow.setFocusable(true);
		// ����������������ʧ
		popupWindow.setOutsideTouchable(true);
		// �����Ϊ�˵��������Back��Ҳ��ʹ����ʧ�����Ҳ���Ӱ����ı���
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// ��ʾ��λ��Ϊ:��Ļ�Ŀ�ȵ�һ�롪��PopupWindow�ĸ߶ȵ�һ��
		popupWindow.showAsDropDown(parent, 0, -5);

	}

	private void showWindow2(View parent, String specificationsId) {
		if (popupWindow == null) {
			View view = LayoutInflater.from(this).inflate(
					R.layout.dialog_choice_color, null);
			view.setFocusableInTouchMode(true);
			popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
		}
		ListView listView = (ListView) popupWindow.getContentView()
				.findViewById(R.id.lv_color);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				tv_size.setText(sizes.get(position).getSize());
				tv_goodnum.setText(sizes.get(position).getNum() + "");
				etnum = sizes.get(position).getNum();

			}
		});
		SizeAdapter sizeAdapter = new SizeAdapter(this);

		sizeAdapter.setData(sizes);
		listView.setAdapter(sizeAdapter);
		// ʹ��ۼ�
		popupWindow.setFocusable(true);
		// ����������������ʧ
		popupWindow.setOutsideTouchable(true);
		// �����Ϊ�˵��������Back��Ҳ��ʹ����ʧ�����Ҳ���Ӱ����ı���
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// ��ʾ��λ��Ϊ:��Ļ�Ŀ�ȵ�һ�롪��PopupWindow�ĸ߶ȵ�һ��
		popupWindow.showAsDropDown(parent, 0, -5);

	}

	private void initView() {
		// TODO Auto-generated method stub
		layout_left = (LinearLayout) findViewById(R.id.layout_left);
		layout_title = (TextView) findViewById(R.id.layout_title);
		tv_goodname = (TextView) findViewById(R.id.tv_goodname); // ��Ʒ����
		tv_about = (TextView) findViewById(R.id.tv_about);// ��Ʒ���
		tv_goodcode = (TextView) findViewById(R.id.tv_goodcode);// ��Ʒ���
		tv_isPackage = (TextView) findViewById(R.id.tv_isPackage);// �Ƿ����
		tv_originalPrice = (TextView) findViewById(R.id.tv_originalPrice);// �г���
		tv_price = (TextView) findViewById(R.id.tv_price);// �Żݼ�
		tv_goodnum = (TextView) findViewById(R.id.tv_goodnum);// �ܿ��

		tv_color = (TextView) findViewById(R.id.tv_color);
		tv_size = (TextView) findViewById(R.id.tv_size);

		LinearLayout layout_color = (LinearLayout) findViewById(R.id.layout_color);
		LinearLayout layout_size = (LinearLayout) findViewById(R.id.layout_size);

		et_num = (EditText) findViewById(R.id.et_num);
		btn_minus = (Button) findViewById(R.id.btn_minus);
		btn_add = (Button) findViewById(R.id.btn_add);
		btn_join_shoppingcar = (Button) findViewById(R.id.btn_join_shoppingcar);// ���빺�ﳵ
		btn_pay = (Button) findViewById(R.id.btn_pay);// ����֧��
		layout_right_collect = (ImageView) findViewById(R.id.layout_right_collect); // �ղ�
		layout_right_share = (ImageView) findViewById(R.id.layout_right_share); // ����

		layout_title.setText("��Ʒ����");
		vPager = (ViewPager) findViewById(R.id.vPager);
		btn_last = (ImageView) findViewById(R.id.btn_last); // ��һ��ͼƬ
		btn_next = (ImageView) findViewById(R.id.btn_next); // ��һ��ͼƬ

		layout_left.setOnClickListener(this);
		layout_color.setOnClickListener(this);
		layout_size.setOnClickListener(this);
		btn_minus.setOnClickListener(this);
		btn_add.setOnClickListener(this);
		btn_join_shoppingcar.setOnClickListener(this);
		btn_pay.setOnClickListener(this);
		layout_right_collect.setOnClickListener(this);
		layout_right_share.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ShareSDK.stopSDK(this);
	}

}
