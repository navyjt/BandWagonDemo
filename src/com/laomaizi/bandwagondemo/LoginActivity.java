package com.laomaizi.bandwagondemo;
/**
 * 
 * @author Tony
 * @20160408
 * app登录activity，使用userservice对象来实现登录处理的线程
 * 使用抛出异常给界面在实现登录不成功的处理
 * 
 */

import com.laomaizi.bandwagondemo.ServiceRulesException;
import com.laomaizi.bandwagondemo.entity.UserInfomation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	protected static final String TAG = "BandWagon";
	public static final String MSG_LOGIN_ERROR = "登录错误！";
	public static final String MSG_SERVER_ERROR = "请求服务器出错";
	private UserService userService = new UserServiceImpl();
	private EditText txtLoginName;
	private EditText txtPassword;
	private Button btnSubmit;
	private Button btnRegister;

	private static ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		this.init();
		// 登录响应部分
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final String strLoginName = txtLoginName.getText().toString();
				final String strPassword = txtPassword.getText().toString();
				//先弹出等待窗口
				if (dialog == null) {
					dialog = new ProgressDialog(LoginActivity.this);
				}
				dialog.setCancelable(false);
				dialog.setTitle("Waiting");
				dialog.setMessage("登录中... ...");
				dialog.show();
				
				//启动登录线程
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							
							userService.userLogin(strLoginName, strPassword);
							dialog.dismiss();
							runOnUiThread(new Runnable() {
								public void run() {
									
									Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
									Intent selFunctionIntent = new Intent(LoginActivity.this, MainFunctionActivity.class);
									LoginActivity.this.startActivity(selFunctionIntent);
									UserInfomation.setUserInfomation(strLoginName, strPassword);
								}
							});
							
						}	catch(ServiceRulesException e){
	//						e.printStackTrace();
							if (e.getMessage().equals(MSG_LOGIN_ERROR)) {
								
								runOnUiThread(new Runnable() {
									public void run() {
										dialog.dismiss();
										
										Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
									}
								});
							
								
							}
						if (e.getMessage().equals(MSG_SERVER_ERROR)) {
								
								runOnUiThread(new Runnable() {
									public void run() {
										dialog.dismiss();
										
										Toast.makeText(LoginActivity.this, "请求服务器出错", Toast.LENGTH_SHORT).show();
									}
								});
							
								
							}
							
						}						
					 
						
						catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
				}).start();
			}
		});

		// 注册用户部分
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);

			}
		});
	}

	private void init() {
		txtLoginName = (EditText) this.findViewById(R.id.login_name_edit);
		txtPassword = (EditText) this.findViewById(R.id.login_pwd_edit);
		btnRegister = (Button) this.findViewById(R.id.btn_register);
		btnSubmit = (Button) this.findViewById(R.id.btn_submit);
	}

}
