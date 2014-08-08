package com.duguang.baseanimation.ui.customview;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.casee.adsdk.CaseeAdView;
import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

/**
 * 多种Dialog大集合主页面
 * @author Administrator
 *
 */
public class DialogsMainActivity extends BaseActivity implements Runnable {
	/** Called when the activity is first created. */

	/** 确定取消信息框 **/
	private static final int DIALOG_0 = 1;
	/** 多个按钮信息框 **/
	private static final int DIALOG_1 = 2;
	/** 列表框 **/
	private static final int DIALOG_2 = 3;
	/** 进度条框 **/
	private static final int DIALOG_3 = 4;
	/** 单项选择列表框 **/
	private static final int DIALOG_4 = 5;
	/** 多项选择列表框 **/
	private static final int DIALOG_5 = 6;
	/** 自定义布局 **/
	private static final int DIALOG_6 = 7;
	/** 读取进度框 **/
	private static final int DIALOG_7 = 8;

	private static final int MAX_PROGRESS = 100;

	private ProgressDialog mProgressDialog = null;

	final String[] mItems = { "item0", "item1", "itme2", "item3", "itme4",
			"item5", "item6" };

	int mSingleChoiceID = -1;

	ArrayList<Integer> MultiChoiceID = new ArrayList<Integer>();

	CaseeAdView cav;

	@Override
	public void setView() {
		setContentView(R.layout.activity_custom_dialogs_main);
		
	}

	@Override
	public void initView() {
		cav = (CaseeAdView) this.findViewById(R.id.caseeAdView);
		
	}

	@Override
	public void setListener() {
		Button button0 = (Button) findViewById(R.id.button0);
		button0.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CreatDialog(DIALOG_0);
			}
		});

		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CreatDialog(DIALOG_1);
			}
		});

		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CreatDialog(DIALOG_2);
			}
		});

		Button button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CreatDialog(DIALOG_3);
				mProgressDialog.setProgress(0);
			}
		});

		Button button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CreatDialog(DIALOG_4);
			}
		});

		Button button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CreatDialog(DIALOG_5);
			}
		});

		Button button6 = (Button) findViewById(R.id.button6);
		button6.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CreatDialog(DIALOG_6);
			}
		});

		Button button7 = (Button) findViewById(R.id.button7);
		button7.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CreatDialog(DIALOG_7);
			}
		});
		
	}

	public void CreatDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				DialogsMainActivity.this);
		switch (id) {
		case DIALOG_0:
			builder.setIcon(R.drawable.ic_launcher);
			builder.setTitle("你确定要离开吗？");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// 这里添加点击确定后的逻辑
							showDialog("你选择了确定");
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// 这里添加点击确定后的逻辑
							showDialog("你选择了取消");
						}
					});
			break;
		case DIALOG_1:
			builder.setIcon(R.drawable.ic_launcher);
			builder.setTitle("投票");
			builder.setMessage("您认为什么样的内容能吸引您？");
			builder.setPositiveButton("有趣味的",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							showDialog("你选择了有趣味的");
						}
					});
			builder.setNeutralButton("有思想的",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							showDialog("你选择了有思想的");
						}
					});
			builder.setNegativeButton("主题强的",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							showDialog("你选择了主题强的");
						}
					});
			break;
		case DIALOG_2:
			builder.setTitle("列表选择框");
			builder.setItems(mItems, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// 点击后弹出窗口选择了第几项
					showDialog("你选择的id为" + which + " , " + mItems[which]);
				}
			});
			break;
		case DIALOG_3:
			mProgressDialog = new ProgressDialog(DialogsMainActivity.this);
			mProgressDialog.setIcon(R.drawable.ic_launcher);
			mProgressDialog.setTitle("进度条窗口");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setMax(MAX_PROGRESS);
			mProgressDialog.setButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// 这里添加点击后的逻辑
						}
					});
			mProgressDialog.setButton2("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// 这里添加点击后的逻辑
						}
					});
			mProgressDialog.show();
			new Thread(this).start();
			return;
		case DIALOG_4:
			mSingleChoiceID = -1;
			builder.setIcon(R.drawable.ic_launcher);
			builder.setTitle("单项选择");
			builder.setSingleChoiceItems(mItems, 0,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							mSingleChoiceID = whichButton;
							showDialog("你选择的id为" + whichButton + " , "
									+ mItems[whichButton]);
						}
					});
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							if (mSingleChoiceID > 0) {
								showDialog("你选择的是" + mSingleChoiceID);
							}
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

						}
					});
			break;
		case DIALOG_5:
			MultiChoiceID.clear();
			builder.setIcon(R.drawable.ic_launcher);
			builder.setTitle("多项选择");
			builder.setMultiChoiceItems(mItems, new boolean[] { false, false,
					false, false, false, false, false },
					new DialogInterface.OnMultiChoiceClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton, boolean isChecked) {
							if (isChecked) {
								MultiChoiceID.add(whichButton);
								showDialog("你选择的id为" + whichButton + " , "
										+ mItems[whichButton]);
							} else {
								MultiChoiceID.remove(whichButton);
							}

						}
					});
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							String str = "";
							int size = MultiChoiceID.size();
							for (int i = 0; i < size; i++) {
								str += mItems[MultiChoiceID.get(i)] + ", ";
							}
							showDialog("你选择的是" + str);
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

						}
					});
			break;
		case DIALOG_6:
			LayoutInflater factory = LayoutInflater.from(this);
			final View textEntryView = factory.inflate(
					R.layout.activity_custom_dialogs_custom, null);
			builder.setIcon(R.drawable.ic_launcher);
			builder.setTitle("自定义输入框");
			builder.setView(textEntryView);
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

							EditText userName = (EditText) textEntryView
									.findViewById(R.id.etUserName);
							EditText password = (EditText) textEntryView
									.findViewById(R.id.etPassWord);
							showDialog("姓名 ：" + userName.getText().toString()
									+ "密码：" + password.getText().toString());
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

						}
					});
			break;
		case DIALOG_7:
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setTitle("读取ing");
			mProgressDialog.setMessage("正在读取中请稍候");
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(true);
			mProgressDialog.show();
			return;
		}
		builder.create().show();
	}

	private void showDialog(String str) {
		new AlertDialog.Builder(DialogsMainActivity.this).setMessage(str)
				.show();
	}

	public void run() {
		int Progress = 0;
		while (Progress < MAX_PROGRESS) {
			try {
				Thread.sleep(100);
				Progress++;
				mProgressDialog.incrementProgressBy(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
