package com.sevenstar.steward2016.activity;

import java.util.ArrayList;
import java.util.List;
import org.xutils.x;
import org.xutils.view.annotation.ViewInject;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.view.listview.CustomListView;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.adapter.MyShopOrderAdapter;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.callback.HttpBaseCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.view.LoadDataErrorLayout;

public class MyShopOrderActivity extends BannerBaseActivity {
	@ViewInject(R.id.listview)
	CustomListView listView;
	@ViewInject(R.id.load_data_error_layout)
	LoadDataErrorLayout loadingLayout;
	private MyShopOrderAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_my_shop_order_page);
		setColumnText("¹ºÎï¶©µ¥");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		List<JSONObject> showList = new ArrayList<JSONObject>();
		adapter = new MyShopOrderAdapter(getActivity(), showList);
		listView.setAdapter(adapter);
		listView.initFooterView();
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				position -= listView.getHeaderViewsCount();
				if (position >= 0) {
					Bundle extras = new Bundle();
					JSONObject entity = adapter.getItem(position);
					extras.putString("json",entity.toJSONString());
					IntentUtils.goTo(getActivity(), MyShopOrderContentActivity.class, extras);
				}
			}
		});
		// listView.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int position,
		// long arg3) {
		// // TODO Auto-generated method stub
		// position -= listView.getHeaderViewsCount();
		// final JSONObject json = adapter.getItem(position);
		// final int serviceType = json.getIntValue("TypeID");
		// String shopType = "";
		// switch (serviceType) {
		// case 1:
		// shopType = "ProductID";
		// break;
		// case 2:
		// shopType = "EquipmentID";
		// break;
		// case 3:
		// shopType = "MarkID";
		// break;
		// case 4:
		// shopType = "SpecialID";
		// break;
		// }
		// HttpSendMananger.sendPost(GlobalUrl.MY_SHOP_INFO_URL, new
		// BaseSimpleHttpCallback(getActivity(), true, false) {
		// @Override
		// public void onSuccess(JSONObject data) {
		// Bundle extras = new Bundle();
		// extras.putString("json", data.toJSONString());
		// extras.putInt("serviceType", serviceType);
		// extras.putBoolean("noShowOrder", true);
		// IntentUtils.goTo(getActivity(), ShopContentActivity.class, extras);
		// }
		// }, shopType, json.getString("MyID"), "serviceType",
		// json.getString("TypeID"), "UserID",
		// StarApplication.userInfo.getString("UserID"));
		// }
		// });

		listView.setGetDataCallBack(new HttpBaseCallBack<JSONObject>(getBaseContext(), showList, adapter, listView, loadingLayout, JSONObject.class,
				GlobalUrl.MY_SHOP_ORDER_URL, "UserID", StarApplication.userInfo.getString("UserID")));
	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}

}
