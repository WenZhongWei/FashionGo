package com.maifeng.fashiongo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.maifeng.fashiongo.banner.ActionSheetDialog;
import com.maifeng.fashiongo.banner.CiecleImageView;
import com.maifeng.fashiongo.base.GetPersonalDetailsData;
import com.maifeng.fashiongo.base.GetPersonalDetailsType;
import com.maifeng.fashiongo.base.Goods_AddNew_AddressType;
import com.maifeng.fashiongo.constant.Urls;
import com.maifeng.fashiongo.util.JsonUtil;
import com.maifeng.fashiongo.volleyhandle.VolleyAbstract;
import com.maifeng.fashiongo.volleyhandle.VolleyRequest;
import com.maifeng.fashiongo.volleyhandle.Volleyhandle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Edit_BasiinformationAcitivity extends Activity implements OnClickListener{

	//初始化顶部导航栏控件
	private View topbar;
	private LinearLayout ll_returnbtn;
	private LinearLayout ll_functionbtn;
	private TextView tv_title;
	private TextView tv_name_function;
	
	private RelativeLayout relative_imageview;
	private RelativeLayout replacesex_relayout;
	private RelativeLayout province_relayout;
	private RelativeLayout city_relayout;
	private RelativeLayout area_relayout;
	
//	private ImageView image_head;
	private EditText editname=null;
	private EditText editage=null;
	private TextView textsex=null;
	private EditText editqq=null;
	private EditText editemaill=null;
	private TextView textprovince=null;
	private TextView textcity=null;
	private TextView textarea=null;
	private EditText editaddress=null;
	
	private String pCodeString;//存放省份id
	private String cCodeString;//存放城市id
	private String aCodeString;//存放地区id
	/*private String pName;
	private String cName;
	private String area;*/
	public GetPersonalDetailsData data;
	private Intent intent;
	
	/**
     * 圆形图片的Imageview
     */
    private CiecleImageView image_head;
    /**
     * 指定拍摄图片文件位置避免获取到缩略图
     */
    private File outFile;
    /**
     * 标记是拍照还是相册 0 是拍照 1 是相册
     */
    private int cameraorpic;
    /**
     * 选择头像相册选取
     */
    private static final int REQUESTCODE_PICK = 4;
    /**
     * 裁剪好头像-设置头像
     */
    private static final int REQUESTCODE_CUTTING = 5;
    /**
     * 选择头像拍照选取
     */
    private static final int PHOTO_REQUEST_TAKEPHOTO = 6;
    /**
     * 裁剪好的头像的Bitmap
     */
    private Bitmap currentBitmap;
    
    /**
     * 头像的二进制字符串
     */
    private String ImgByte = null;
    
    /**
     * 用于打开相册
     */
   /* private final String IMAGE_TYPE = "image/*";
    public static final int IMAGE_REQUEST_CODE = 0x102;*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_basiinformation);
		intent = this.getIntent();
		idGet();
		getvollePost();
		SharedPreferences pref = getSharedPreferences("headimgurl", MODE_PRIVATE);
		String ImageUrl = pref.getString("headImageUrl", "");
		volleygetImage(ImageUrl);
	}
	private void idGet(){
		topbar =findViewById(R.id.topbar);
		// 顶部导航栏控件id
		ll_returnbtn = (LinearLayout)topbar.findViewById(R.id.ll_returnbtn);
		ll_functionbtn = (LinearLayout)topbar.findViewById(R.id.ll_functionbtn);
		tv_title = (TextView)topbar.findViewById(R.id.tv_title);
		tv_name_function = (TextView)topbar.findViewById(R.id.tv_name_function);
		//顶部导航栏控件相关设置
		tv_title.setText("基本资料 ");
		tv_name_function.setText("保存");
		
		relative_imageview=(RelativeLayout)findViewById(R.id.relative_imageview);
		replacesex_relayout=(RelativeLayout)findViewById(R.id.replacesex_relayout);
		province_relayout=(RelativeLayout)findViewById(R.id.province_relayout);
		city_relayout=(RelativeLayout)findViewById(R.id.city_relayout);
		area_relayout=(RelativeLayout)findViewById(R.id.area_relayout);
		
		ll_returnbtn.setOnClickListener(this);
		ll_functionbtn.setOnClickListener(this);
		relative_imageview.setOnClickListener(this);
		replacesex_relayout.setOnClickListener(this);
		province_relayout.setOnClickListener(this);
		city_relayout.setOnClickListener(this);
		area_relayout.setOnClickListener(this);
		
		image_head=(CiecleImageView)findViewById(R.id.image_head);
		editname=(EditText)findViewById(R.id.editname);
		editage=(EditText)findViewById(R.id.editage);
		textsex=(TextView)findViewById(R.id.textsex);
		editqq=(EditText)findViewById(R.id.editqq);
		editemaill=(EditText)findViewById(R.id.editemaill);
		textprovince=(TextView)findViewById(R.id.textprovince);
		textcity=(TextView)findViewById(R.id.textcity);
		textarea=(TextView)findViewById(R.id.textarea);
		editaddress=(EditText)findViewById(R.id.editaddress);

	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Volleyhandle.getInstance(getApplicationContext()).getRequestQueue().cancelAll("CHANGE_PEMAL_INFO");
	}
	public void getvollePost() {
		// 登录标识
		SharedPreferences pref = getSharedPreferences("myPref", MODE_PRIVATE);
		String accessToken = pref.getString("accessToken", "");
		// 组装请求数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("accessToken", accessToken);
		VolleyRequest.RequestPost(this, Urls.PERSONAL_DETAILS,
				"PERSONAL_DETAILS", map, new VolleyAbstract(this,
						VolleyAbstract.listener, VolleyAbstract.errorListener,true) {
					@Override
					public void onMySuccess(String result) {
						data = JsonUtil.parseJsonToBean(result,
								GetPersonalDetailsType.class).getData();

						editname.setText(data.getName());
						editname.setSelection(editname.getText().length());// 将光标追踪到内容的最后
						editage.setText(data.getAge());
						editage.setSelection(editage.getText().length());
						if (data.getSex().equals("0")) {
							textsex.setText("男");
						} else {
							textsex.setText("女");
						}
						
						editqq.setText(data.getQq());
						editemaill.setText(data.getEmail());
						textprovince.setText(data.getpName());
						textcity.setText(data.getcName());
						textarea.setText(data.getArea());
						editaddress.setText(data.getAddress());
						editaddress.setSelection(editaddress.getText().length());
						pCodeString= data.getpCode();
						cCodeString=data.getcCode();
						aCodeString=data.getaCode();
						/*pName = data.getpName();
						cName = data.getcName();
						area = data.getArea();*/
					}

					@Override
					public void onMyError(VolleyError error) {
					}
				});
	}

	private void vollePost() {

		try {
			// 登录标识
			SharedPreferences pref = getSharedPreferences("myPref",
					MODE_PRIVATE);
			String accessToken = pref.getString("accessToken", "");
			String name = editname.getText().toString().trim();// trim()是将转化后的字符串类型去掉前后空格。
			String age = editage.getText().toString().trim();
			String sex;
			if (textsex.getText().toString().equals("男")) {
				sex = "0";
			} else {
				sex = "1";
			}
			String qq = editqq.getText().toString().trim();
			String email = editemaill.getText().toString().trim();
			String pCode = textprovince.getText().toString().trim();
			String cCode = textcity.getText().toString().trim();
			String aCode = textarea.getText().toString().trim();
			String address = editaddress.getText().toString().trim();
			
			if (TextUtils.isEmpty(name)) {
				Toast.makeText(getApplicationContext(), "姓名不能为空",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (TextUtils.isEmpty(age)) {
				Toast.makeText(getApplicationContext(), "年龄不能为空",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (Integer.valueOf(age) < 0 || Integer.valueOf(age) > 200) {
				Toast.makeText(getApplicationContext(), "不是正确的年龄或年龄格式不对",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (textsex.getText().toString().equals("")) {
				Toast.makeText(getApplicationContext(), "性别不能为空",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (TextUtils.isEmpty(qq)) {
				Toast.makeText(getApplicationContext(), "QQ不能为空",
						Toast.LENGTH_SHORT).show();
				return;
			}
			String stremail = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
			Pattern p = Pattern.compile(stremail);
			Matcher m = p.matcher(email);
			if (TextUtils.isEmpty(email)) {
				Toast.makeText(getApplicationContext(), "邮箱不能为空",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (!m.matches()) {
				Toast.makeText(getApplicationContext(), "邮箱格式不对",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (pCode.equals("请选择省份")) {
				Toast.makeText(getApplicationContext(), "省份不能为空",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (cCode.equals("请选择城市")) {
				Toast.makeText(getApplicationContext(), "城市不能为空",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (aCode.equals("请选择地区")) {
				Toast.makeText(getApplicationContext(), "地区不能为空",
						Toast.LENGTH_SHORT).show();
				return;
			} else if (TextUtils.isEmpty(address)) {
				Toast.makeText(getApplicationContext(), "详细地址不能为空",
						Toast.LENGTH_SHORT).show();
				return;
			} else {

				// 组装请求数据
				Map<String, String> map = new HashMap<String, String>();
				map.put("accessToken", accessToken);
				if (ImgByte!=null) {
					map.put("image",ImgByte);
				}
				map.put("name", name);
				map.put("age", age);
				map.put("sex", sex);
				map.put("qq", qq);
				map.put("email", email);
				map.put("pCode", pCodeString);
				map.put("cCode", cCodeString);
				map.put("aCode", aCodeString);
				map.put("address", address);
				//Todo
				/*map.put("pName", pName);
				map.put("cName", cName);
				map.put("area", area);*/
				
				VolleyRequest.RequestPost(this, Urls.CHANGE_PEMAL_INFO,
						"CHANGE_PEMAL_INFO", map, new VolleyAbstract(this,
								VolleyAbstract.listener,
								VolleyAbstract.errorListener,true) {

							@Override
							public void onMySuccess(String result) {
								Goods_AddNew_AddressType gAddNew_AddressType=JsonUtil.parseJsonToBean(result,
										Goods_AddNew_AddressType.class);
//								Toast.makeText(getApplicationContext(), gAddNew_AddressType.getMessage(), Toast.LENGTH_SHORT).show();
								if (gAddNew_AddressType.getErrorcode().equals("1")) {
									Toast.makeText(Edit_BasiinformationAcitivity.this,
											gAddNew_AddressType.getMessage(), Toast.LENGTH_SHORT)
											.show();
								}
								else if(gAddNew_AddressType.getErrorcode().equals("0")){
									Toast.makeText(Edit_BasiinformationAcitivity.this,
											gAddNew_AddressType.getMessage(), Toast.LENGTH_SHORT)
											.show();
									Edit_BasiinformationAcitivity.this.finish();
								}
								
								
							}

							@Override
							public void onMyError(VolleyError error) {
								// TODO Auto-generated method stub
							}
						});
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_returnbtn:
			finish();
			break;
		//保存
		case R.id.ll_functionbtn:
			vollePost();
			break;
		case R.id.relative_imageview:

			image_head.setBorderWidth(5);
            new ActionSheetDialog(Edit_BasiinformationAcitivity.this).Builder().addSheetItem("拍照", ActionSheetDialog.SheetItemColor.BULE, new ActionSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int witch) {
                    cameraorpic=1;
                    openCamera();
                }
            }).addSheetItem("打开相册", ActionSheetDialog.SheetItemColor.BULE, new ActionSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int witch) {
                    cameraorpic=0;
                    openPic();
                }
            }).show();
        
			break;
		case R.id.replacesex_relayout:
			sex();
			break;
		case R.id.province_relayout:
			Intent intent1 = new Intent(getApplicationContext(),Provice_Activity.class);
			startActivityForResult(intent1, 1);
			/*overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
			break;
		case R.id.city_relayout:
			if (pCodeString==null) {
				Toast.makeText(getApplicationContext(),"请选择省份后再操作",Toast.LENGTH_SHORT).show();
				return;
			}else {
				Intent intent2 = new Intent(getApplicationContext(),City_Activity.class);
				intent2.putExtra("pCode", pCodeString);
				startActivityForResult(intent2, 2);
				/*overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
			}
			
			break;
		case R.id.area_relayout:
			if (cCodeString==null) {
				Toast.makeText(getApplicationContext(),"请选择城市后再操作",Toast.LENGTH_SHORT).show();
				return;
			}else {
				Intent intent3 = new Intent(getApplicationContext(),Area_Activity.class);
				intent3.putExtra("cCode", cCodeString);
				startActivityForResult(intent3, 3);
				/*overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
			}
			
			break;
		default:
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode==1) {
			String pNameString = data.getStringExtra("pName");
			textprovince.setText(pNameString);//显示省份
			textcity.setText("请选择城市");
			textarea.setText("请选择地区");
			cCodeString=null;
			aCodeString=null;
			pCodeString=data.getStringExtra("pCode");//得到省份id
		}
		if (resultCode==2) {
			String cNameString = data.getStringExtra("cName");
			textcity.setText(cNameString);//显示城市
			textarea.setText("请选择地区");
			aCodeString=null;
			cCodeString=data.getStringExtra("cCode");//得到城市id
		}
		if (resultCode==3) {
			String cNameString = data.getStringExtra("aArea");
			textarea.setText(cNameString);//显示地区
			aCodeString = data.getStringExtra("aCode");
		}
		
        switch (requestCode) {
            //相册
            case REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            //裁减
            case REQUESTCODE_CUTTING:
                if (data != null) {
                    setPicToView(data);
                }
                break;
            //拍照
            case PHOTO_REQUEST_TAKEPHOTO:
                startPhotoZoom(Uri.fromFile(outFile));
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    
	}
	//设置选择性别
	private void sex(){
        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(Edit_BasiinformationAcitivity.this);
        //    设置Title的图标
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("选择一个性别");
        //    指定下拉列表的显示数据
        final String[] cities = {"男", "女"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(Edit_BasiinformationAcitivity.this, "选择的性别为：" + cities[which], Toast.LENGTH_SHORT).show();
				textsex.setText(cities[which]);
                
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                
            }
        });
        builder.show();
	}
	
	
	/**
     * 打开相册
     */
    private void openPic() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, REQUESTCODE_PICK);
        /*overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
    	/*Intent pickIntent = new Intent();
    	pickIntent.addCategory(Intent.CATEGORY_OPENABLE);
    	pickIntent.setType(IMAGE_TYPE);
    	 if (Build.VERSION.SDK_INT <19) {
    		 pickIntent.setAction(Intent.ACTION_GET_CONTENT);
    	  }else {
    		  pickIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
    	  }
    	 startActivityForResult(pickIntent, IMAGE_REQUEST_CODE);*/
    }

    /**
     * 打开相机
     */
    private void openCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!outDir.exists()) {
                outDir.mkdir();
            }
            outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
            /*overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
        } else {
            Log.e("CAMERA", "请确认已经插入SD卡");
        }
    }

    /**
     * 把裁减好的图片设置到View上或者上传到网络
     *
     * @param data
     */
    private void setPicToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            /** 可用于图片上传 */
            currentBitmap = extras.getParcelable("data");

            image_head.setImageBitmap(currentBitmap);
            ImgByte=sendImage(currentBitmap);
            
        }
    }
    
    //把Bitmap转换成二进制String
    private String sendImage(Bitmap bm){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 60, stream);
		byte[] bytes = stream.toByteArray();
		String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
		return img;
    }

    /**
     * 调用系统的图片裁减
     *
     * @param data
     */
    private void startPhotoZoom(Uri data) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true); //黑边
        intent.putExtra("scaleUpIfNeeded", true); //黑边
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDEtection", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
        /*overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
    }

    
	private void volleygetImage(String imgUrl){
		ImageRequest imageRequest = new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap response) {
				// TODO Auto-generated method stub
				image_head.setImageBitmap(response);
				
			}
		}, 0, 0, Config.RGB_565,new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				image_head.setImageResource(R.drawable.img_png6);
			}
		});
		
		
		Volleyhandle.getInstance(getApplicationContext()).getRequestQueue().add(imageRequest);
	}
}
