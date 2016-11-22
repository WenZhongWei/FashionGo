package com.maifeng.fashiongo.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.alipay.sdk.app.PayTask;
import com.maifeng.fashiongo.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

public class PayDemoActivity extends FragmentActivity {

	// �̻�PID
		public static final String PARTNER = "2088512038867633";
		// �̻��տ��˺�
		public static final String SELLER = "";
		// �̻�˽Կ��pkcs8��ʽ
		public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKf5nBX4Y7elAoXR14syPR9WBsgU5JWFHCNQ4sOxEn5zEHcTZjitXNG30KNPoSkNY8PyLWWW2M0J4ikMXlTRR3sMPs8UsgbVw9eviLfQq3RnuJ93F3GH0RHNqhFbjUFk3SMITvXCNgyeFMnMwBmgWJUGJWn/RKr1lQA+zVaDrxKDAgMBAAECgYEAoF7VwLwXKDwofm7r5P+gD4zoiJo56u7dAHK3LbKfP47Hx4gD7lTF64wda+4YpcMc3vUWlolAj/HBtQMUBNKR9MHfEFCManbSfdVyW685hS8oa9tebe2LYYSf8BhNm2BIw5d7kz0PYvFYAqBce3qMuQERFJxXbPdXIA85F+cqkDECQQDdNX5SR+97mOrBqINneDUaT+3NjCK8623ofyVsLP7xOyw/WpfX3yo5xNt+PIqPOXgmsBT9Ixnnc54MyzHGfPxVAkEAwmTEHUxVk95+wXmWXHZ3tDH2ib6XrVwdeclaz74Nc7B7GbTIhM3hDhgPAPgEgsnZdqso+pYeGX75Hj363+WrdwJAEfy0jdo9le5EQIUEUliK8N3I92rKtFPsdvhHwbkS7eii22/xYuNJmrLwKh6gIa9JntO4kTDWfWSAWIf5PyuQlQJABybD3DwRreXyjdWWRYKKNtPbuQeM9vC7mN5ie+AHCKmGbdSrx873J5iU/LkjGVipqmbzk/xv0QpNyeIH17LQUwJBAIkblp1K+6Nu3xCTMqbk0JZ5haQey6f0AyeFUj7HGrlwdaJsi3X+Sx32ZuJLk+d8Ex8qUjX0KdHEb59LAXq0yUE=";
		// ֧������Կ
		public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
		private static final int SDK_PAY_FLAG = 1;

		@SuppressLint("HandlerLeak")
		private Handler mHandler = new Handler() {
			@SuppressWarnings("unused")
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case SDK_PAY_FLAG: {
					PayResult payResult = new PayResult((String) msg.obj);
					/**
					 * ͬ�����صĽ��������õ�����˽�����֤����֤�Ĺ����뿴https://doc.open.alipay.com/doc2/
					 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
					 * docType=1) �����̻������첽֪ͨ
					 */
					String resultInfo = payResult.getResult();// ͬ��������Ҫ��֤����Ϣ

					String resultStatus = payResult.getResultStatus();
					// �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
					if (TextUtils.equals(resultStatus, "9000")) {
						Toast.makeText(PayDemoActivity.this, "֧���ɹ�", Toast.LENGTH_SHORT).show();
					} else {
						// �ж�resultStatus Ϊ��"9000"��������֧��ʧ��
						// "8000"����֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
						if (TextUtils.equals(resultStatus, "8000")) {
							Toast.makeText(PayDemoActivity.this, "֧�����ȷ����", Toast.LENGTH_SHORT).show();

						} else {
							// ����ֵ�Ϳ����ж�Ϊ֧��ʧ�ܣ������û�����ȡ��֧��������ϵͳ���صĴ���
							Toast.makeText(PayDemoActivity.this, "֧��ʧ��", Toast.LENGTH_SHORT).show();

						}
					}
					break;
				}
				default:
					break;
				}
			};
		};

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.pay_main);
		}

		/**
		 * call alipay sdk pay. ����SDK֧��
		 * 
		 */
		public void pay(View v) {
			if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
				new AlertDialog.Builder(this).setTitle("����").setMessage("��Ҫ����PARTNER | RSA_PRIVATE| SELLER")
						.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialoginterface, int i) {
								//
								finish();
							}
						}).show();
				return;
			}
			String orderInfo = getOrderInfo("���Ե���Ʒ", "�ò�����Ʒ����ϸ����", "0.1");

			/**
			 * �ر�ע�⣬�����ǩ���߼���Ҫ���ڷ���ˣ�����˽Կй¶�ڴ����У�
			 */
			String sign = sign(orderInfo);
			try {
				/**
				 * �����sign ��URL����
				 */
				sign = URLEncoder.encode(sign, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			/**
			 * �����ķ���֧���������淶�Ķ�����Ϣ
			 */
			final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

			Runnable payRunnable = new Runnable() {

				@Override
				public void run() {
					// ����PayTask ����
					PayTask alipay = new PayTask(PayDemoActivity.this);
					// ����֧���ӿڣ���ȡ֧�����
					String result = alipay.pay(payInfo, true);

					Message msg = new Message();
					msg.what = SDK_PAY_FLAG;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			};

			// �����첽����
			Thread payThread = new Thread(payRunnable);
			payThread.start();
		}

		/**
		 * get the sdk version. ��ȡSDK�汾��
		 * 
		 */
		public void getSDKVersion() {
			PayTask payTask = new PayTask(this);
			String version = payTask.getVersion();
			Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
		}

		/**
		 * ԭ����H5���ֻ���ҳ��֧����natvie֧���� ����Ӧҳ����ҳ֧����ť��
		 * 
		 * @param v
		 */
		public void h5Pay(View v) {
			Intent intent = new Intent(this, H5PayDemoActivity.class);
			Bundle extras = new Bundle();
			/**
			 * url�ǲ��Ե���վ����app�ڲ���ҳ���ǻ���webview�򿪵ģ�demo�е�webview��H5PayDemoActivity��
			 * demo������url����֧�����߼�����H5PayDemoActivity��shouldOverrideUrlLoading����ʵ�֣�
			 * �̻����Ը����Լ���������ʵ��
			 */
			String url = "http://m.taobao.com";
			// url������һ�ŵ�����Ա��ȵ������Ĺ���wapվ�㣬�ڸ���վ��֧�������У�֧����sdk�������֧��
			extras.putString("url", url);
			intent.putExtras(extras);
			startActivity(intent);
		}

		/**
		 * create the order info. ����������Ϣ
		 * 
		 */
		private String getOrderInfo(String subject, String body, String price) {

			// ǩԼ���������ID
			String orderInfo = "partner=" + "\"" + PARTNER + "\"";

			// ǩԼ����֧�����˺�
			orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

			// �̻���վΨһ������
			orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

			// ��Ʒ����
			orderInfo += "&subject=" + "\"" + subject + "\"";

			// ��Ʒ����
			orderInfo += "&body=" + "\"" + body + "\"";

			// ��Ʒ���
			orderInfo += "&total_fee=" + "\"" + price + "\"";

			// �������첽֪ͨҳ��·��
			orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

			// ����ӿ����ƣ� �̶�ֵ
			orderInfo += "&service=\"mobile.securitypay.pay\"";

			// ֧�����ͣ� �̶�ֵ
			orderInfo += "&payment_type=\"1\"";

			// �������룬 �̶�ֵ
			orderInfo += "&_input_charset=\"utf-8\"";

			// ����δ����׵ĳ�ʱʱ��
			// Ĭ��30���ӣ�һ����ʱ���ñʽ��׾ͻ��Զ����رա�
			// ȡֵ��Χ��1m��15d��
			// m-���ӣ�h-Сʱ��d-�죬1c-���죨���۽��׺�ʱ����������0��رգ���
			// �ò�����ֵ������С���㣬��1.5h����ת��Ϊ90m��
			orderInfo += "&it_b_pay=\"30m\"";

			// extern_tokenΪ���������Ȩ��ȡ����alipay_open_id,���ϴ˲����û���ʹ����Ȩ���˻�����֧��
			// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

			// ֧��������������󣬵�ǰҳ����ת���̻�ָ��ҳ���·�����ɿ�
			orderInfo += "&return_url=\"m.alipay.com\"";

			return orderInfo;
		}

		/**
		 * get the out_trade_no for an order. �����̻������ţ���ֵ���̻���Ӧ����Ψһ�����Զ����ʽ�淶��
		 * 
		 */
		private String getOutTradeNo() {
			SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
			Date date = new Date();
			String key = format.format(date);

			Random r = new Random();
			key = key + r.nextInt();
			key = key.substring(0, 15);
			return key;
		}

		/**
		 * sign the order info. �Զ�����Ϣ����ǩ��
		 * 
		 * @param content
		 *            ��ǩ��������Ϣ
		 */
		private String sign(String content) {
			return SignUtils.sign(content, RSA_PRIVATE);
		}

		/**
		 * get the sign type we use. ��ȡǩ����ʽ
		 * 
		 */
		private String getSignType() {
			return "sign_type=\"RSA\"";
		}
	
}
