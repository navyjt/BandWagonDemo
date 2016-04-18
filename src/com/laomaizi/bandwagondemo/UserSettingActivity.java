package com.laomaizi.bandwagondemo;

/**
 * @author Tony
 * @20160414
 * 用户设置页，该activity主要完成用户头像的上传和下载功能
 * 
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.laomaizi.bandwagondemo.entity.UserInfomation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserSettingActivity extends Activity {

	private UserService userService = new UserServiceImpl();

	protected static final int FLAG_LOAD_IMAGE = 1;

	public static final String MSG_SERVER_ERROR = "请求服务器出错";

	public static final String MSG_UPLOAD_USER_PORTRAIT_ERROR = "用户头像上传失败";
	private String pathName;
	public String userNameString;

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == FLAG_LOAD_IMAGE) {
			
			if (data == null) {
				Toast.makeText(this, "你没有选择任何图片", Toast.LENGTH_LONG).show();
			
			}
			else {
				Uri uri = data.getData();
				if (uri == null) {
					Toast.makeText(this, "你没有选择任何图片", Toast.LENGTH_LONG).show();

				} else {
					String path = null;
					String[] pojo = { MediaStore.Images.Media.DATA };
					Cursor cursor = getContentResolver().query(uri, pojo, null,
							null, null);
					if (cursor != null) {
						int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);

						cursor.moveToFirst();
						path = cursor.getString(columnIndex);
						cursor.close();

					}

					if (path == null) {
						Toast.makeText(this, "未能获得图片的物理路径", Toast.LENGTH_LONG)
								.show();

					} else {
						pathName = path;
						Toast.makeText(this, "图片的物理路径" + path,	Toast.LENGTH_LONG).show();
						new AlertDialog.Builder(this)
								.setTitle("提示")
								.setMessage("D U want to upload")
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(DialogInterface arg0,int arg1) {
													doupload();
											}
										})
								.setNegativeButton("取消",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(DialogInterface arg0,int arg1) {
											
											}
										}).create().show();

					}

				}
				
			}
			
		}
		
	}


	protected void doupload() {


		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					//二进制文件数据
					InputStream in = new FileInputStream(new File(pathName));
					//普通字符串数据  
					Map<String, String> data = new HashMap<String, String>();
					data.put("userName", userNameString);
					final String result = userService.userPortraitUpload(in, data,pathName);
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(UserSettingActivity.this, result,
									Toast.LENGTH_LONG).show();

						}
					});

				} catch(final ServiceRulesException e){
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(UserSettingActivity.this, e.getMessage(),
									Toast.LENGTH_LONG).show();
						}

					});

				}
				
				
				catch (Exception e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(UserSettingActivity.this, "上传出错",
									Toast.LENGTH_LONG).show();
						}

					});
				}
			}
		}).start();
			
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_usersetting);
		userNameString = UserInfomation.getUserName();
		TextView text = (TextView) findViewById(R.id.text_usersetting_name);
		text.setText(userNameString);
		
		Button  userPortraitUploadButton = (Button)findViewById(R.id.btn_upload_user_portrait);
		userPortraitUploadButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, FLAG_LOAD_IMAGE);
			}
		});
	}
	
	

}
