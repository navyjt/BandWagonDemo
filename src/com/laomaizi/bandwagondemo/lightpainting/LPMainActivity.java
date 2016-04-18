package com.laomaizi.bandwagondemo.lightpainting;
/**
 * @author Tony
 * @20160412
 * 光绘中启动光绘的主界面，该界面中使用fragment实现tab页面效果
 * 现在有两个tab页，一个为设置光绘参数，一个记录光绘历史列表，默认打第一个tab页
 */
import com.laomaizi.bandwagondemo.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class LPMainActivity extends FragmentActivity {


	protected static final String TAG = "BandWagon";
	private View currentButton;
	public  LPMainActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//使应用程序无标题栏
		setContentView(R.layout.activity_lpmain);
		initComponents();
	}

	private void initComponents() {
		ImageButton iButton_one = (ImageButton) findViewById(R.id.buttom_one);
		ImageButton iButton_two = (ImageButton) findViewById(R.id.buttom_two);
		
		iButton_one.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
	        	Log.d(TAG, "第一个按钮被点击");

				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				FragmentSetLP fragmentlp = new FragmentSetLP();
				ft.replace(R.id.fl_content, fragmentlp);
				ft.commit();
				setButton(v);
				
				
			}
		});
		
		iButton_two.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
	        	Log.d(TAG, "第二个按钮被点击");

				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				FragmentFavList fragmentfavlist = new FragmentFavList();
				Log.d(TAG, "初始化fragmentfavlist完成");
				ft.replace(R.id.fl_content, fragmentfavlist);
				ft.commit();
				Log.d(TAG, "ft.commit完成");
				setButton(v);
				Log.d(TAG, "setbutton完成");
			}
		});
	    iButton_one.performClick();
	    

		
	}

	private void setButton(View v) {
		 if (currentButton != null && currentButton.getId() != v.getId()) {
	            currentButton.setEnabled(true);
	        }
	        v.setEnabled(false);
	        currentButton = v;
	    		
	}
	

}
