package com.laomaizi.bandwagondemo;
/**
 * @author Tony
 * 20160409
 * 注册activity，接受name passwoed和email三个参数
 * email参数进行正则表达式校验
 * 同样，注册的具体业务也在userservice中实现
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity{

	protected static final String TAG = "BandWagon";
	public static final String MSG_LOGIN_ERROR = "登录错误！";
	public static final String MSG_SERVER_ERROR = "请求服务器出错";
	private static ProgressDialog dialog;
	private EditText txtLoginName;
	private EditText txtPassword;
	private EditText txtEmailaddress;
	private EditText txtRepeatPassword;
	private Button regButton;
	private  String strLoginName= null;
	private String strPassword = null;
	private String strRepeatPassword = null;
	private String strEmail =null;
	private UserService userService = new UserServiceImpl();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		this.init();
		regButton.setOnClickListener(new OnClickListener() {
			
	
				public void onClick(View arg0) {
					strLoginName = txtLoginName.getText().toString();
					strPassword = txtPassword.getText().toString();
					strRepeatPassword = txtRepeatPassword.getText().toString();
					strEmail = txtEmailaddress.getText().toString();
					
					if (validateinput(strLoginName,strPassword,strEmail) == true)
					{
					
					
					//先弹出等待窗口
					if (dialog == null) {
						dialog = new ProgressDialog(RegisterActivity.this);
					}
					dialog.setCancelable(false);
					dialog.setTitle("Waiting");
					dialog.setMessage("注册中... ...");
					dialog.show();
					
					//启动登录线程
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							try {
								Log.d(TAG, "=============开始注册线程=============");
								
								userService.userRegister(strLoginName, strPassword,strEmail);
								dialog.dismiss();
								runOnUiThread(new Runnable() {
									public void run() {
										
										Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
									}
								});
								
								Log.d(TAG, "=============注册线程结束=============");
								
							}	catch(ServiceRulesException e){
		//						e.printStackTrace();
								if (e.getMessage().equals(MSG_LOGIN_ERROR)) {
									
									runOnUiThread(new Runnable() {
										public void run() {
											dialog.dismiss();
											
											Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
										}
									});
								
									
								}
							if (e.getMessage().equals(MSG_SERVER_ERROR)) {
									
									runOnUiThread(new Runnable() {
										public void run() {
											dialog.dismiss();
											
											Toast.makeText(RegisterActivity.this, "请求服务器出错", Toast.LENGTH_SHORT).show();
										}
									});
								
									
								}
								
							}						
						 
							
							catch (Exception e) {
								e.printStackTrace();
							}
							
							
						}
					}).start();
				}
				
			}
		});
	}

	protected boolean validateinput(String strLoginName, String strPassword,
			String strEmail) {

		String regEx =  
			    "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"  
			        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"  
			        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."  
			        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"  
			        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"  
			        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";  
		String strEmailTrim = strEmail.trim();
	    Matcher matcherObj = Pattern.compile(regEx).matcher(strEmailTrim);  
		if ((strPassword.equals(strRepeatPassword))&& (matcherObj.matches())){
			Toast.makeText(RegisterActivity.this, "数据校验成功", Toast.LENGTH_SHORT).show();
			return true;
			
		}
		else{
			Toast.makeText(RegisterActivity.this, "数据校验错误，请检查邮箱地址！", Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	private void init() {
		regButton = (Button) this.findViewById(R.id.btn_register);
		txtLoginName = (EditText) this.findViewById(R.id.register_name_edit);
		txtPassword = (EditText) this.findViewById(R.id.register_pwd_edit);
		txtRepeatPassword = (EditText) this.findViewById(R.id.repeat_register_pwd_edit);
		txtEmailaddress = (EditText) this.findViewById(R.id.register_email_edit);
		
			
	}

}
