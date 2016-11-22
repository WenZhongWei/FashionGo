package com.maifeng.fashiongo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/** 
 * ��Ҫ���Ϸ�������״̬��Ȩ�� 
 * 
 */  
public class NetUtil {  
      
    /** 
     * �Ƿ������� 
     *  
     * @param context 
     * @return 
     */  
    public static boolean hasNetwork(Context context) {  
        ConnectivityManager con = (ConnectivityManager) context  
                .getSystemService(Context.CONNECTIVITY_SERVICE);  
        NetworkInfo workinfo = con.getActiveNetworkInfo();  
        if (workinfo == null || !workinfo.isAvailable()) {  
            return false;  
        }  
        return true;  
    }  
      
      
    /** 
     * �ж��Ƿ���WiFi���� 
     * @param context 
     * @return 
     */  
    public static boolean isWifiNet(Context context){  
        ConnectivityManager con = (ConnectivityManager) context  
                .getSystemService(Context.CONNECTIVITY_SERVICE);  
        NetworkInfo workinfo = con.getActiveNetworkInfo();  
        return workinfo!=null && workinfo.getType() == ConnectivityManager.TYPE_WIFI;  
    }  
}  
