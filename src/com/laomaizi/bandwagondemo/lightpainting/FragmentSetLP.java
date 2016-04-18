package com.laomaizi.bandwagondemo.lightpainting;
/**
 * @author Tony
 * @20160412
 * 设置光绘参数页面，该页面为一个fragment，绘制在LpMainActivity上，
 * 输入相应光绘参数后启动光绘
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import com.laomaizi.bandwagondemo.R;

import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class FragmentSetLP extends Fragment {

	protected static final String TAG = "BandWagon";
	private EditText edittextstring;
	private TextView selcolor;
	private TextView camerasetuptext;
	private Button startShowActivity;
	private Button selColorDialog;
	private Button cameraSetup;
	private SeekBar seekbarsize;
	private SeekBar seekbarspeed;
	private SeekBar seekbarstop;
	private String fontsize = "200";
	private int time = 15;
	private int speed;
	private int iWaitToStop = 5;
	private String strcolor = "#FFFFFF";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View contentView = inflater.inflate(R.layout.activity_fragmentsetlp,null);
		startShowActivity = (Button) contentView
				.findViewById(R.id.start_button);
		edittextstring = (EditText) contentView.findViewById(R.id.edit_text);
		seekbarsize = (SeekBar) contentView.findViewById(R.id.seekBarFont);
		seekbarspeed = (SeekBar) contentView.findViewById(R.id.seekBarspeed);
		selColorDialog = (Button) contentView
				.findViewById(R.id.selcolor_button);
		selcolor = (TextView) contentView.findViewById(R.id.selcolor);
		cameraSetup = (Button) contentView.findViewById(R.id.camera_setup);
		camerasetuptext = (TextView) contentView
				.findViewById(R.id.camera_setuptext);
		seekbarstop = (SeekBar) contentView.findViewById(R.id.seekBarStop);

		seekbarspeed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				time = progress;
				// TODO 自动生成的方法存根

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				Toast.makeText(getActivity().getApplicationContext(),
						"移动速度设置为" + String.valueOf(time) + "秒",
						Toast.LENGTH_SHORT).show();

			}

		});

		cameraSetup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int iedit = edittextstring.length();

				if (iedit > 0) {
					speed = time * 23 / iedit;

					// TODO 自动生成的方法存根
					camerasetuptext.setText("相机设置如下：ISO100-200，快门优先，快门速度为"
							+ String.valueOf(time + iWaitToStop) + "秒");
				} else {
					Toast.makeText(getActivity().getApplicationContext(),
							"请输入光绘文字", Toast.LENGTH_SHORT).show();
				}
			}

		});

		seekbarsize.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

				// 此行注释掉，程序里不需要调节文字大小的，使用默认文字大小即可
				// fontsize = String.valueOf(progress);
				// TODO 自动生成的方法存根

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO 自动生成的方法存根
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO 自动生成的方法存根
			}

		});
		seekbarstop.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				iWaitToStop = progress;

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

				Toast.makeText(getActivity().getApplicationContext(),
						"延迟时间为" + String.valueOf(iWaitToStop) + "秒",
						Toast.LENGTH_SHORT).show();

			}

		});

		selColorDialog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LPMainActivity parentActivity = (LPMainActivity) getActivity();// 调用父activity

				// View contentView=
				// inflater.inflate(R.layout.activity_fragment_new,null);
				LinearLayout layout = new LinearLayout(getActivity()
						.getApplicationContext());
				layout.setOrientation(LinearLayout.VERTICAL);

				final TextView colorText = new TextView(getActivity()
						.getApplicationContext());
				ColorPickerView colorPick = new ColorPickerView(getActivity()
						.getApplicationContext(), Color.parseColor("#FFFFFF"),
						1.5, colorText);

				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
				lp.gravity = Gravity.CENTER_HORIZONTAL;
				layout.addView(colorPick, lp);
				layout.addView(colorText, lp);
				Log.d(TAG, "准备打开对话框");

				AlertDialog mAlertDialog = new AlertDialog.Builder(
						parentActivity)
						.setTitle("选择一个颜色")
						.setView(layout)
						.setPositiveButton(getString(R.string.dialog_color_OK),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										Log.d(TAG,
												"the color choose is "
														+ colorText.getText());
										strcolor = (String) colorText.getText();
										selcolor.setTextColor(Color
												.parseColor(strcolor));
									}
								})
						.setNegativeButton(
								getString(R.string.dialog_color_Cancel),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								}).create();
				mAlertDialog.show();
				Log.d("Laomaizi", "对话框建立" + ((String) colorText.getText()));
				// selcolor.setTextColor(Color.parseColor((String)
				// colorText.getText()));

			}

		});

		startShowActivity.setOnClickListener(new OnClickListener()

		{
			// 按钮点击事件
			@Override
			public void onClick(View v) {

				int iedit = edittextstring.length();
				if (iedit > 0) {
					speed = time * 30 / iedit;
				} else {
					Toast.makeText(getActivity().getApplicationContext(),
							"请输入需要光绘的文字！", Toast.LENGTH_SHORT).show();
					return;
				}

				Intent intent = new Intent(getActivity()
						.getApplicationContext(), StartShowActivity.class);
				String data = edittextstring.getText().toString();
				intent.putExtra("showtext", data);
				intent.putExtra("fontsize", fontsize);
				intent.putExtra("speed", speed);
				intent.putExtra("color", strcolor);

				int iWaittoBlank = iedit * iWaitToStop / time;
				intent.putExtra("stop", iWaittoBlank);
				Log.d("Laomaizi",
						"滚动时间为" + String.valueOf(time) + "字符长度为"
								+ String.valueOf(iedit) + "启动延时"
								+ String.valueOf(iWaitToStop) + "预留空格"
								+ String.valueOf(iWaittoBlank));

				startActivity(intent);
				Log.d(TAG, "启动第二个activity" + strcolor);

				// ============以下为讲文字保存到历史记录中的代码===

				try {
					SaveToFavlist(data, fontsize, speed, strcolor);
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

			}

			private void SaveToFavlist(String data, String fontsize, int speed,
					String strcolor) throws IOException {

				String local_file = getActivity().getApplicationContext()
						.getFilesDir().getAbsolutePath()
						+ "/fav/";
				File newXmlFile = new File(local_file);
				if (!newXmlFile.exists()) {
					newXmlFile.mkdirs();
					Log.d(TAG, "不存在目录，创建"
							+ getActivity().getApplicationContext()
									.getFilesDir().getAbsolutePath().toString());
				}

				local_file = newXmlFile.getAbsolutePath() + "/" + "fav.xml";
				newXmlFile = new File(local_file);
				XmlSerializer serializer = Xml.newSerializer();
				FileOutputStream fileos = null;

				try {
					if (!newXmlFile.exists()) {
						newXmlFile.createNewFile();
						Log.d("Laomaizi", "创建文件成功");
						fileos = new FileOutputStream(newXmlFile);
						// //<?xml version="1.0" encoding="UTF-8"
						// standalone="yes"?>创建文件同时写入头xml信息

						serializer.setOutput(fileos, "UTF-8");
						serializer.startDocument(null, Boolean.valueOf(true));
					} else {
						fileos = new FileOutputStream(newXmlFile, true);
						serializer.setOutput(fileos, "UTF-8");
						// serializer.startDocument(null,
						// Boolean.valueOf(true));
					}

				} catch (IOException ex) {
					ex.printStackTrace();

				}

				try {
					
					//写入xml文件

					// serializer.setFeature("http://xmlpull.org/v1/doc/feature.html#indent-output",
					// true);
					// <lightpainting xmlns="http://www.laomaizi.com"
					serializer.startTag(null, "lightpainting");
					serializer.attribute(null, "xmlns",
							"http://www.laomaizi.com");
					serializer.startTag(null, "text");
					serializer.text(data);
					serializer.endTag(null, "text");
					serializer.startTag(null, "fontsize");
					serializer.text(fontsize);
					serializer.endTag(null, "fontsize");
					serializer.startTag(null, "time");
					serializer.text(String.valueOf(time));
					serializer.endTag(null, "time");
					serializer.startTag(null, "delay");
					serializer.text(String.valueOf(iWaitToStop));
					serializer.endTag(null, "delay");
					serializer.startTag(null, "color");
					serializer.text(strcolor);
					serializer.endTag(null, "color");
					serializer.endTag(null, "lightpainting");
					serializer.endDocument();
					serializer.flush();
					Log.d("Laomaizi", "写入文件完成");
					fileos.close();

				} catch (IllegalArgumentException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

			}

		});

		return contentView;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getActivity().getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
