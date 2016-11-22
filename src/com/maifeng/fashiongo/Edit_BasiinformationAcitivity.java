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

	//��ʼ�������������ؼ�
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
	
	private String pCodeString;//���ʡ��id
	private String cCodeString;//��ų���id
	private String aCodeString;//��ŵ���id
	/*private String pName;
	private String cName;
	private String area;*/
	public GetPersonalDetailsData data;
	private Intent intent;
	
	/**
     * Բ��ͼƬ��Imageview
     */
    private CiecleImageView image_head;
    /**
     * ָ������ͼƬ�ļ�λ�ñ����ȡ������ͼ
     */
    private File outFile;
    /**
     * ��������ջ������ 0 ������ 1 �����
     */
    private int cameraorpic;
    /**
     * ѡ��ͷ�����ѡȡ
     */
    private static final int REQUESTCODE_PICK = 4;
    /**
     * �ü���ͷ��-����ͷ��
     */
    private static final int REQUESTCODE_CUTTING = 5;
    /**
     * ѡ��ͷ������ѡȡ
     */
    private static final int PHOTO_REQUEST_TAKEPHOTO = 6;
    /**
     * �ü��õ�ͷ���Bitmap
     */
    private Bitmap currentBitmap;
    
    /**
     * ͷ��Ķ������ַ���
     */
    private String ImgByte = null;
    
    /**
     * ���ڴ����
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
		// �����������ؼ�id
		ll_returnbtn = (LinearLayout)topbar.findViewById(R.id.ll_returnbtn);
		ll_functionbtn = (LinearLayout)topbar.findViewById(R.id.ll_functionbtn);
		tv_title = (TextView)topbar.findViewById(R.id.tv_title);
		tv_name_function = (TextView)topbar.findViewById(R.id.tv_name_function);
		//�����������ؼ��������
		tv_title.setText("�������� ");
		tv_name_function.setText("����");
		
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
		// ��¼��ʶ
		SharedPreferences pref = getSharedPreferences("myPref", MODE_PRIVATE);
		String accessToken = pref.getString("accessToken", "");
		// ��װ��������
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
						editname.setSelection(editname.getText().length());// �����׷�ٵ����ݵ����
						editage.setText(data.getAge());
						editage.setSelection(editage.getText().length());
						if (data.getSex().equals("0")) {
							textsex.setText("��");
						} else {
							textsex.setText("Ů");
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
			// ��¼��ʶ
			SharedPreferences pref = getSharedPreferences("myPref",
					MODE_PRIVATE);
			String accessToken = pref.getString("accessToken", "");
			String name = editname.getText().toString().trim();// trim()�ǽ�ת������ַ�������ȥ��ǰ��ո�
			String age = editage.getText().toString().trim();
			String sex;
			if (textsex.getText().toString().equals("��")) {
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
				Toast.makeText(getApplicationContext(), "��������Ϊ��",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (TextUtils.isEmpty(age)) {
				Toast.makeText(getApplicationContext(), "���䲻��Ϊ��",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (Integer.valueOf(age) < 0 || Integer.valueOf(age) > 200) {
				Toast.makeText(getApplicationContext(), "������ȷ������������ʽ����",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (textsex.getText().toString().equals("")) {
				Toast.makeText(getApplicationContext(), "�Ա���Ϊ��",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (TextUtils.isEmpty(qq)) {
				Toast.makeText(getApplicationContext(), "QQ����Ϊ��",
						Toast.LENGTH_SHORT).show();
				return;
			}
			String stremail = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
			Pattern p = Pattern.compile(stremail);
			Matcher m = p.matcher(email);
			if (TextUtils.isEmpty(email)) {
				Toast.makeText(getApplicationContext(), "���䲻��Ϊ��",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (!m.matches()) {
				Toast.makeText(getApplicationContext(), "�����ʽ����",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (pCode.equals("��ѡ��ʡ��")) {
				Toast.makeText(getApplicationContext(), "ʡ�ݲ���Ϊ��",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (cCode.equals("��ѡ�����")) {
				Toast.makeText(getApplicationContext(), "���в���Ϊ��",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (aCode.equals("��ѡ�����")) {
				Toast.makeText(getApplicationContext(), "��������Ϊ��",
						Toast.LENGTH_SHORT).show();
				return;
			} else if (TextUtils.isEmpty(address)) {
				Toast.makeText(getApplicationContext(), "��ϸ��ַ����Ϊ��",
						Toast.LENGTH_SHORT).show();
				return;
			} else {

				// ��װ��������
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
		//����
		case R.id.ll_functionbtn:
			vollePost();
			break;
		case R.id.relative_imageview:

			image_head.setBorderWidth(5);
            new ActionSheetDialog(Edit_BasiinformationAcitivity.this).Builder().addSheetItem("����", ActionSheetDialog.SheetItemColor.BULE, new ActionSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int witch) {
                    cameraorpic=1;
                    openCamera();
                }
            }).addSheetItem("�����", ActionSheetDialog.SheetItemColor.BULE, new ActionSheetDialog.OnSheetItemClickListener() {
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
				Toast.makeText(getApplicationContext(),"��ѡ��ʡ�ݺ��ٲ���",Toast.LENGTH_SHORT).show();
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
				Toast.makeText(getApplicationContext(),"��ѡ����к��ٲ���",Toast.LENGTH_SHORT).show();
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
			textprovince.setText(pNameString);//��ʾʡ��
			textcity.setText("��ѡ�����");
			textarea.setText("��ѡ�����");
			cCodeString=null;
			aCodeString=null;
			pCodeString=data.getStringExtra("pCode");//�õ�ʡ��id
		}
		if (resultCode==2) {
			String cNameString = data.getStringExtra("cName");
			textcity.setText(cNameString);//��ʾ����
			textarea.setText("��ѡ�����");
			aCodeString=null;
			cCodeString=data.getStringExtra("cCode");//�õ�����id
		}
		if (resultCode==3) {
			String cNameString = data.getStringExtra("aArea");
			textarea.setText(cNameString);//��ʾ����
			aCodeString = data.getStringExtra("aCode");
		}
		
        switch (requestCode) {
            //���
            case REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            //�ü�
            case REQUESTCODE_CUTTING:
                if (data != null) {
                    setPicToView(data);
                }
                break;
            //����
            case PHOTO_REQUEST_TAKEPHOTO:
                startPhotoZoom(Uri.fromFile(outFile));
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    
	}
	//����ѡ���Ա�
	private void sex(){
        //    ͨ��AlertDialog.Builder�������ʵ�������ǵ�һ��AlertDialog�Ķ���
        AlertDialog.Builder builder = new AlertDialog.Builder(Edit_BasiinformationAcitivity.this);
        //    ����Title��ͼ��
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("ѡ��һ���Ա�");
        //    ָ�������б����ʾ����
        final String[] cities = {"��", "Ů"};
        //    ����һ���������б�ѡ����
        builder.setItems(cities, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(Edit_BasiinformationAcitivity.this, "ѡ����Ա�Ϊ��" + cities[which], Toast.LENGTH_SHORT).show();
				textsex.setText(cities[which]);
                
            }
        });
        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                
            }
        });
        builder.show();
	}
	
	
	/**
     * �����
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
     * �����
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
            Log.e("CAMERA", "��ȷ���Ѿ�����SD��");
        }
    }

    /**
     * �Ѳü��õ�ͼƬ���õ�View�ϻ����ϴ�������
     *
     * @param data
     */
    private void setPicToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            /** ������ͼƬ�ϴ� */
            currentBitmap = extras.getParcelable("data");

            image_head.setImageBitmap(currentBitmap);
            ImgByte=sendImage(currentBitmap);
            
        }
    }
    
    //��Bitmapת���ɶ�����String
    private String sendImage(Bitmap bm){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 60, stream);
		byte[] bytes = stream.toByteArray();
		String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
		return img;
    }

    /**
     * ����ϵͳ��ͼƬ�ü�
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
        intent.putExtra("scale", true); //�ڱ�
        intent.putExtra("scaleUpIfNeeded", true); //�ڱ�
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
