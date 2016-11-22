package com.maifeng.fashiongo;

import com.maifeng.fashiongo.constant.Urls;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FeedBackAcitvtty extends Activity{
	 private ProgressDialog dialog;
	 private WebView webView;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.webview);
	init();
	
}
private void init() {
	// TODO Auto-generated method stub
	webView = (WebView) findViewById(R.id.user_access_protocol);
	// WebView���ر�����Դ
	// webView.loadUrl("file:///android_asset/example.html");
	// WebView����web��Դ
	webView.loadUrl(Urls.TIPS);
	// ����WebViewĬ��ͨ��������������ϵͳ���������ҳ����Ϊ��ʹ����ҳ������WebVIew�д�
	webView.setWebViewClient(new WebViewClient(){
		
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			//����ֵ��true��ʱ�������ҳ��WebView��ȥ�򿪣����Ϊfalse����ϵͳ�����������������ȥ��
			view.loadUrl(url);
			return true;
		}
		//WebViewClient����WebViewȥ����һЩҳ����ƺ�����֪ͨ
		
	});
	//����֧��JavaScript
	WebSettings settings = webView.getSettings();
	settings.setJavaScriptEnabled(true);
	//WebView����ҳ������ʹ�û������
	settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
	webView.setWebChromeClient(new WebChromeClient(){
		
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			// TODO Auto-generated method stub
            //newProgress 1-100֮�������
			if(newProgress==100)
			{
				//��ҳ������ϣ��ر�ProgressDialog
				closeDialog();
			}
			else
			{
				//��ҳ���ڼ���,��ProgressDialog
				openDialog(newProgress);
			}
		}

		private void closeDialog() {
			// TODO Auto-generated method stub
              if(dialog!=null&&dialog.isShowing())
              {
            	     dialog.dismiss();
            	     dialog=null;
              }
		}

		private void openDialog(int newProgress) {
			// TODO Auto-generated method stub
			if(dialog==null)
			{
				dialog=new ProgressDialog(FeedBackAcitvtty.this);
				dialog.setTitle("���ڼ���");
				dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				dialog.setProgress(newProgress);
				dialog.show();
				
			}
			else
			{
				dialog.setProgress(newProgress);
			}
		
			
		}
	});
	
	
	
}

//��д�������������ص��߼�
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	// TODO Auto-generated method stub
	if(keyCode==KeyEvent.KEYCODE_BACK)
	{
		//Toast.makeText(this, webView.getUrl(), Toast.LENGTH_SHORT).show();
		if(webView.canGoBack())
		{
			webView.goBack();//������һҳ��
			return true;
		}
		else
		{
			System.exit(0);//�˳�����
		}
	}
	return super.onKeyDown(keyCode, event);
}

}
