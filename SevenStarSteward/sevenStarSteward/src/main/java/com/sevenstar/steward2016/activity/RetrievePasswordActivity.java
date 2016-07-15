package com.sevenstar.steward2016.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.manager.AppManager;
import com.lib.shiguotao.utils.ConvertUtils;
import com.lib.shiguotao.utils.DES;
import com.lib.shiguotao.utils.HandlerUtils;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.MD5Util;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.SharedPreferencesUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class RetrievePasswordActivity extends BannerBaseActivity implements OnClickListener, OnCheckedChangeListener{

	@ViewInject(R.id.register_phone_et)
	EditText phoneET;
	@ViewInject(R.id.register_get_phone_code_tv)
	TextView getCodeBT;
	@ViewInject(R.id.register_user_yzm_et)
	EditText yzmET;
	@ViewInject(R.id.register_user_password_et)
	EditText passwrodET;
	@ViewInject(R.id.register_user_password_two_et)
	EditText passwrodTwoET;
	@ViewInject(R.id.register_submit_btn)
	View submitBT;
	@ViewInject(R.id.register_show_password)
	CheckBox showPassword;
	@ViewInject(R.id.register_show_password_two)
	CheckBox showPasswordTwo;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				getCodeBT.setPadding(ConvertUtils.dp2px(getBaseContext(), 10), 0, ConvertUtils.dp2px(getBaseContext(), 10), 0);
				break;
			case 1:
				getCodeBT.setPadding(0, 0, 0, 0);
				break;
			case 2:
				ViewUtils.setTextData(getCodeBT, msg.obj);
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setColumnText("找回密码");
		setBaseContextView(R.layout.activity_retrieve_password_pager);
		x.view().inject(this);
		init();
	}

	private void init() {
		submitBT.setOnClickListener(this);
		getCodeBT.setOnClickListener(this);
		showPassword.setOnCheckedChangeListener(this);
		showPasswordTwo.setOnCheckedChangeListener(this);
	}

	private void submitPost() {
		// TODO Auto-generated method stub
		String phone = phoneET.getText().toString();
		if (OtherUtils.isEmpty(phone)) {
			ToastUtil.showInfo(this, "请输入手机号码");
			return;
		}
		String yzm = yzmET.getText().toString();
		if (OtherUtils.isEmpty(yzm)) {
			ToastUtil.showInfo(this, "请输入验证码");
			return;
		}
		String password = passwrodET.getText().toString();
		if (OtherUtils.isEmpty(password)) {
			ToastUtil.showInfo(this, "请输入新密码");
			return;
		}
		if (passwrodET.getText().equals(passwrodTwoET.getText())) {
			ToastUtil.showInfo(this, "两次输入的密码不一致");
			return;
		}
		try {
			password = MD5Util.getInstence().encryptDate(password);
			password = DES.Encrypt(password, phone);
		} catch (Exception e) {
			e.printStackTrace();
		}

		final String logNameFinal = phone;
		final String passwordFinal = password;
		HttpSendMananger.sendPost(GlobalUrl.Retrieve_PASSWROD_URL, new BaseSimpleHttpCallback(this, true, true) {
			@Override
			public OnClickListener getDialogMessageListener() {
				return new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(state==0)
						{
							finish();
						}
					}
				};
			}
		}, "Tel", phone, "Code", yzm, "LoginName", phone, "Password", password);
	}

	private boolean isRunningGetCode;
	private int sexIndex;

	private void getCodePost() {
		// TODO Auto-generated method stub
		String phone = phoneET.getText().toString();
		if (OtherUtils.isEmpty(phone)) {
			ToastUtil.showInfo(this, "请输入手机号码");
			return;
		}
		if (!isRunningGetCode) {
			isRunningGetCode = true;
			AppManager.getInstance().getLoadThreadPool().execute(new Runnable() {

				@Override
				public void run() {
					int i = 60;
					HandlerUtils.sendEmpty(handler, 0);
					while (i > 0) {
						HandlerUtils.send(handler, 2, String.valueOf(i));
						i--;
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					HandlerUtils.send(handler, 2, "重新获取");
					HandlerUtils.sendEmpty(handler, 1);
					isRunningGetCode = false;
				}
			});
			HttpSendMananger.sendPost(GlobalUrl.GET_PHONE_CODE_URL, new BaseSimpleHttpCallback(this, false, true) {
				@Override
				public void onSuccess(JSONObject data) {
					super.onSuccess(data);
					ToastUtil.showInfo(getBaseContext(), data.toJSONString());
				}
			}, "Tel", phone, "CodeType", "2");
		} else {
			ToastUtil.showInfo(getActivity(), "验证码已发送，请稍后...");
		}

	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.register_submit_btn:
			submitPost();
			break;
		case R.id.register_get_phone_code_tv:
			getCodePost();
			break;

		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (showPassword == buttonView) {
			if (isChecked) {
				// 如果选中，显示密码
				passwrodET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
			} else {
				// 否则隐藏密码
				passwrodET.setTransformationMethod(PasswordTransformationMethod.getInstance());
			}

		} else {
			if (isChecked) {
				// 如果选中，显示密码
				passwrodTwoET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
			} else {
				// 否则隐藏密码
				passwrodTwoET.setTransformationMethod(PasswordTransformationMethod.getInstance());
			}
		}
	}


}
