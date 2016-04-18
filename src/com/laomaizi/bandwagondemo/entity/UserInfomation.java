package com.laomaizi.bandwagondemo.entity;

/**
 * 
 * @author Tony
 * @20160414
 * 增加这个类，用来记录当前登录的用户名和密码，以便在以后调用
 * 增加两个方法，一个用来保存用户名和密码，一个用来读取用户名
 */
import android.util.Log;

public class UserInfomation {
	
	private static final String TAG = "BandWagon";
	static String userNameString = null;
	static String userPasswordString = null;

	public static void setUserInfomation(String userName,String password) {
		
		Log.d(TAG,"在UserInformation中将写入用户名" + userName);
		userNameString = userName;
		userPasswordString = password;
		Log.d(TAG, "在UserInformation中写入用户名" + userNameString);	
		
	}
	public static String getUserName()
	{
		return userNameString;
		
	}
	
}
