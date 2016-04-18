package com.laomaizi.bandwagondemo;
/**
 * @author Tony
 * @20160412
 * 登录app后主页面部分
 * 目前提供两个按钮，第一个按钮启动光绘activity，第二个按钮待开发
 */

import com.laomaizi.bandwagondemo.lightpainting.LPMainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainFunctionActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_mainfunction);
		
		Button lpButton = (Button) findViewById(R.id.btn_lightpainting);
		lpButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				Intent startLPIntent = new Intent(MainFunctionActivity.this,LPMainActivity.class);
				MainFunctionActivity.this.startActivity(startLPIntent);
			}
		});
		
		Button userSettingButton = (Button)findViewById(R.id.btn_usersetting);
		userSettingButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent userSettingIntent = new Intent(MainFunctionActivity.this,UserSettingActivity.class);
				MainFunctionActivity.this.startActivity(userSettingIntent);				
			}
		});
	}
	

}
