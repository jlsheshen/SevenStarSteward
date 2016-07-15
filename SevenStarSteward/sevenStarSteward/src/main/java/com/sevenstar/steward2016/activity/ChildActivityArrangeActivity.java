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
import com.sevenstar.steward2016.adapter.ChildActivityArrangeAdapter;
import com.sevenstar.steward2016.callback.HttpBaseCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.view.LoadDataErrorLayout;
/**
 * 活动安排子列表
 */
public class ChildActivityArrangeActivity extends BannerBaseActivity {
	@ViewInject(R.id.listview)
	CustomListView listView;
	@ViewInject(R.id.load_data_error_layout)
	LoadDataErrorLayout loadingLayout;
	private ChildActivityArrangeAdapter adapter;
	private int type;
	private String activityID = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_child_activity_arrange_pager);
		setColumnText("活动安排");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		List<JSONObject> showList = new ArrayList<JSONObject>();
		 type = 1;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			type = extras.getInt("type", 1);
		}
		switch (type) {
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
		adapter = new ChildActivityArrangeAdapter(getActivity(), showList, type);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				position-=listView.getHeaderViewsCount();
				if(position>=0){
					String[] object = new String[3];
					object[0] = adapter.getItem(position).toJSONString();
					object[1] = activityID;
					object[2] = String.valueOf(type);
					Bundle bundle = new Bundle();
					bundle.putStringArray("data", object);
					IntentUtils.goTo(getActivity(), ActivityContentActivity.class, bundle);
				}
			}
		});
		new HttpBaseCallBack<JSONObject>(getBaseContext(), showList, adapter, listView, loadingLayout, JSONObject.class,
				GlobalUrl.GET_MAIN_LIVE_SERVICE, "serviceType", "3", "childType", String.valueOf(type));
	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}

}
