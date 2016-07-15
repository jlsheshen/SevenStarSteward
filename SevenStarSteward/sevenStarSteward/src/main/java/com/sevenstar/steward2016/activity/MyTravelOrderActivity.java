package com.sevenstar.steward2016.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.SharedPreferencesUtils;
import com.lib.shiguotao.view.listview.CustomListView;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.adapter.MyTravelOrderAdapter;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.callback.HttpBaseCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.view.LoadDataErrorLayout;

public class MyTravelOrderActivity extends BannerBaseActivity {
	@ViewInject(R.id.listview)
	CustomListView listView;
	@ViewInject(R.id.load_data_error_layout)
	LoadDataErrorLayout loadingLayout;
	private MyTravelOrderAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_my_travel_order_page);
		setColumnText("我的行程");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		List<JSONObject> showList = new ArrayList<JSONObject>();
		adapter = new MyTravelOrderAdapter(getActivity(), showList);
		listView.setAdapter(adapter);
		listView.initFooterView();
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				position -= listView.getHeaderViewsCount();
				if (position >= 0) {
					HttpSendMananger.sendPost(GlobalUrl.getLiveServiceData, new BaseSimpleHttpCallback(getActivity(), true, false) {
						@Override
						public void onSuccess(JSONObject data) {
							if (data != null) {
								Bundle extras = new Bundle();
								extras.putInt("type",1);
								extras.putString("data",data.toJSONString());
								IntentUtils.goTo(getActivity(), LeisureTourismContentActivity.class, extras);
							}
						}
					}, "TripID", adapter.getItem(position).getString("MyID"),"serviceType","4");
				}
			}
		});
		listView.setGetDataCallBack(new HttpBaseCallBack<JSONObject>(getBaseContext(), showList, adapter, listView, loadingLayout,
				JSONObject.class, GlobalUrl.MY_TRAVEL_ORDER_URL, "UserID", StarApplication.userInfo.getString("UserID")));
	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}
}