package com.sevenstar.steward2016.activity;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BaseActivity;
import com.lib.shiguotao.utils.DES;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.MD5Util;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.SharedPreferencesUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;

/**
 * 登录界面
 */
@ContentView(R.layout.activity_login_pager)
public class LoginActivity extends BaseActivity implements OnClickListener {

	@ViewInject(R.id.login_submit_btn)
	View submitBtn;

	@ViewInject(R.id.login_register_btn)
	View registerBtn;@ViewInject(R.id.find_password_btn)
	View findPasswordBtn;

	@ViewInject(R.id.login_user_name_et)
	EditText loginNameET;
	@ViewInject(R.id.login_user_password_et)
	EditText passwrodET;
	private boolean exitFlag;
	private long lastTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		submitBtn.setOnClickListener(this);
		registerBtn.setOnClickListener(this);
		findPasswordBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_submit_btn:
			String loginName = loginNameET.getText().toString();
			String password = passwrodET.getText().toString();
			if (OtherUtils.isEmpty(loginName)) {
				ToastUtil.showInfo(this, "登录名不能为空");
				return;
			} else if (OtherUtils.isEmpty(password)) {
				ToastUtil.showInfo(this, "密码不能为空");
				return;
			}
			try {
				password = MD5Util.getInstence().encryptDate(password);
				password = DES.Encrypt(password, loginName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			final String logNameFinal = loginName;
			final String passwordFinal = password;
			HttpSendMananger.sendPost(GlobalUrl.USER_LOGIN_URL, new BaseSimpleHttpCallback(this, true, true) {
				@Override
				public void onSuccess(JSONObject data) {
					if (data != null) {
						StarApplication.userInfo = data;
						SharedPreferencesUtils.setStringDate("loginName", logNameFinal);
						SharedPreferencesUtils.setStringDate("password", passwordFinal);
					}
				}

				@Override
				public OnClickListener getDialogMessageListener() {
					// TODO Auto-generated method stub
					return new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (state == 0) {
								LoginActivity.this.finish();
							}
						}
					};
				}
			}, "LoginName", loginName, "Password", password);
			break;
		case R.id.login_register_btn:
			IntentUtils.goTo(this, RegisterUserActivity.class);
			break;
			case R.id.find_password_btn:
				IntentUtils.goTo(this, RetrievePasswordActivity.class);
				break;
		}
	}

//	@Override
//	public void onBackPressed() {
//		long currentTime = System.currentTimeMillis();
//		if (exitFlag) {
//			if (currentTime - lastTime < 2000) {
//				Bundle bundle = new Bundle();
//				bundle.putInt("type", -1);
//				IntentUtils.goTo(this, MainActivity.class, bundle);
//				finish();
//				return;
//			} else {
//				exitFlag = false;
//			}
//		} else {
//			exitFlag = true;
//		}
//		lastTime = currentTime;
//		ToastUtil.showInfo(this, R.string.press_once_finish);
//	}

}
