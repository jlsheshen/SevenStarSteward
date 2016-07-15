package com.sevenstar.steward2016.activity;
/**
 * 我的合同列表界面
 */
import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.DialogUtils;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.view.listview.CustomListView;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.adapter.MyHeTongAdapter;
import com.sevenstar.steward2016.callback.HttpBaseCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.view.LoadDataErrorLayout;

public class MyHeTongActivity extends BannerBaseActivity {
	@ViewInject(R.id.listview)
	CustomListView listView;
	@ViewInject(R.id.load_data_error_layout)
	LoadDataErrorLayout loadingLayout;
	private MyHeTongAdapter adapter;
	private HttpBaseCallBack<JSONObject> callBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_my_he_tong_page);
		setColumnText("我的合同");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		List<JSONObject> showList = new ArrayList<JSONObject>();
		adapter = new MyHeTongAdapter(getActivity(), showList);
		listView.setAdapter(adapter);
		listView.initFooterView();
		callBack = new HttpBaseCallBack<JSONObject>(getBaseContext(), showList, adapter, listView, loadingLayout, JSONObject.class,
				GlobalUrl.MY_HE_TONG_LIST_URL, "UserID", StarApplication.userInfo.getString("UserID"));
		listView.setGetDataCallBack(callBack);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				position -= listView.getHeaderViewsCount();
				if (position >= 0) {
					Bundle extras = new Bundle();
					JSONObject entity = adapter.getItem(position);
					extras.putString("title", "合同详情");
					extras.putString("id", entity.getString("ContractID"));
					int statusID = entity.getIntValue("StatusID");
					if (statusID == 4) {
						extras.putBoolean("isShow", true);
					} else {
						extras.putBoolean("isShow", false);
					}
					String url = entity.getString("FileFolder");
					if (OtherUtils.isNotEmpty(url)) {
						extras.putString("url", GlobalUrl.ADDR + url);
					}
					extras.putInt("type", 1);
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
