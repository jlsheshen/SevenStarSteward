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
import com.lib.shiguotao.view.listview.CustomListView;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.adapter.MyActivityOrderAdapter;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.callback.HttpBaseCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.view.LoadDataErrorLayout;
/**
 *	我的活动 订单列表
 */
public class MyActivityOrderActivity extends BannerBaseActivity {
	@ViewInject(R.id.listview)
	CustomListView listView;
	@ViewInject(R.id.load_data_error_layout)
	LoadDataErrorLayout loadingLayout;
	private MyActivityOrderAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_my_activity_order_page);
		setColumnText("我的活动");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		List<JSONObject> showList = new ArrayList<JSONObject>();
		adapter = new MyActivityOrderAdapter(getActivity(), showList);
		listView.setAdapter(adapter);
		listView.initFooterView();
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				position -= listView.getHeaderViewsCount();
				if (position >= 0) {
					int childType=adapter.getItem(position).getIntValue("TypeID");
					String activityID="";
					switch(childType){
						case 1:
							activityID = "ActivityID";
							break;
						case 2:
							activityID = "SociallyID";
							break;
						case 3:
							activityID = "CommunityID";
							break;
					}
					HttpSendMananger.sendPost(GlobalUrl.getLiveServiceData, new BaseSimpleHttpCallback(getActivity(), true, false) {
						@Override
						public void onSuccess(JSONObject data) {
							if (data != null) {
								Bundle extras = new Bundle();
								extras.putInt("type",1);
								extras.putString("data",data.toJSONString());
								IntentUtils.goTo(getActivity(), ActivityContentActivity.class, extras);
							}
						}
					},activityID, adapter.getItem(position).getString("MyID"),"childType",String.valueOf(childType),"serviceType","3");

				}
			}
		});
		listView.setGetDataCallBack(new HttpBaseCallBack<JSONObject>(getBaseContext(), showList, adapter, listView, loadingLayout,
				JSONObject.class, GlobalUrl.MY_ACTIVITY_ORDER_URL, "UserID", StarApplication.userInfo.getString("UserID")));
	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}
}