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
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.view.listview.CustomListView;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.adapter.MyHourServiceOrderAdapter;
import com.sevenstar.steward2016.callback.HttpBaseCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.view.LoadDataErrorLayout;
/**
 *	钟点服务订单列表界面
 */
public class MyHourServiceOrderActivity extends BannerBaseActivity {
	@ViewInject(R.id.listview)
	CustomListView listView;
	@ViewInject(R.id.load_data_error_layout)
	LoadDataErrorLayout loadingLayout;
	private MyHourServiceOrderAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_my_hour_service_order_page);
		setColumnText("钟点服务订单");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		List<JSONObject> showList = new ArrayList<JSONObject>();
		adapter = new MyHourServiceOrderAdapter(getActivity(), showList);
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
					IntentUtils.goTo(getActivity(), MyHourServiceOrderContentActivity.class, extras);
				}
			}
		});
		listView.setGetDataCallBack(new HttpBaseCallBack<JSONObject>(getBaseContext(), showList, adapter, listView, loadingLayout, JSONObject.class,
				GlobalUrl.MY_HOUR_SERVICE_ORDER_URL, "UserID", StarApplication.userInfo.getString("UserID")));
	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}
}
