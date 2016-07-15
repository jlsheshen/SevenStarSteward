package com.sevenstar.steward2016.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.view.listview.CustomListView;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.adapter.MyPingGuAdapter;
import com.sevenstar.steward2016.callback.HttpBaseCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.view.LoadDataErrorLayout;
/**
 *	我的评估 界面
 */
public class MyPingGuActivity extends BannerBaseActivity {
	@ViewInject(R.id.listview)
	CustomListView listView;
	@ViewInject(R.id.load_data_error_layout)
	LoadDataErrorLayout loadingLayout;
	private MyPingGuAdapter adapter;
	private HttpBaseCallBack<JSONObject> callBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_my_ping_gu_page);
		setColumnText("我的评估");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		List<JSONObject> showList = new ArrayList<JSONObject>();
		adapter = new MyPingGuAdapter(getActivity(), showList);
		listView.setAdapter(adapter);
		listView.initFooterView();
		callBack = new HttpBaseCallBack<JSONObject>(getBaseContext(), showList, adapter, listView, loadingLayout, JSONObject.class,
				GlobalUrl.MY_PING_GU_LIST_URL, "UserID", StarApplication.userInfo.getString("UserID"));
		listView.setGetDataCallBack(callBack);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				position -= listView.getHeaderViewsCount();
				if (position >= 0) {
					JSONObject entity = adapter.getItem(position);
					Bundle extras = new Bundle();
					extras.putString("title", "评估详情");
					extras.putString("id", entity.getString("AssessmentID"));
					int statusID = entity.getIntValue("StatusID");
					if (statusID != 2) {
						extras.putBoolean("isShow", true);
					} else {
						extras.putBoolean("isShow", false);
					}
					String url = entity.getString("FileFolder");
					if (OtherUtils.isNotEmpty(url)) {
						extras.putString("url", GlobalUrl.ADDR + url);
					}
					extras.putInt("type", 0);
					IntentUtils.goTo(getActivity(), ShowPdfAcitivty.class, 0x1000, -1, extras);
				}
			}
		});

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == arg1) {
			callBack.onRefreshData();
		}
	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}
}
