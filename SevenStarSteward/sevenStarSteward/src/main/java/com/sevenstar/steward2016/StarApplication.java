package com.sevenstar.steward2016;

import org.xutils.x;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.SGTApplication;
import com.lib.shiguotao.manager.AppManager;
import com.videogo.openapi.EZOpenSDK;

public class StarApplication extends SGTApplication {

	public static JSONObject userInfo;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		x.Ext.init(this);
		AppManager.getInstance().init(this);
		AppManager.getInstance().initMainBannerLayoutRes(R.layout.banner_activity_base);
		EZOpenSDK.initLib(this, "dc7a22a0c1884476abbad754bf88b0b1","");

	}
}
