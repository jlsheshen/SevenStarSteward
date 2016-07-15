package com.sevenstar.steward2016.activity;

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
import com.sevenstar.steward2016.adapter.CommunityActivityAdapter;
import com.sevenstar.steward2016.callback.HttpBaseCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.view.LoadDataErrorLayout;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
/**
 * 社区投诉列表界面
 */
public class CommunityComplaintActivity extends BannerBaseActivity {
	@ViewInject(R.id.listview)
	CustomListView listView;
	@ViewInject(R.id.load_data_error_layout)
	LoadDataErrorLayout loadingLayout;
	private CommunityActivityAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_community_activity_page);
		setColumnText("社区投诉");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		List<JSONObject> showList = new ArrayList<JSONObject>();
		adapter = new CommunityActivityAdapter(getActivity(), showList);
		listView.setAdapter(adapter);
		listView.initFooterView();
		listView.setGetDataCallBack(new HttpBaseCallBack<JSONObject>(getBaseContext(), showList, adapter, listView, loadingLayout,
				JSONObject.class, GlobalUrl.GET_COMMUNITY_LIST_DATA, "communityType", "3"));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				position -= listView.getHeaderViewsCount();
				if (position >= 0) {
					JSONObject jsonObject = adapter.getItem(position);
					Bundle extras = new Bundle();
					extras.putString("json", jsonObject.toJSONString());
					IntentUtils.goTo(getActivity(), CommunityComplaintContentActivity.class, extras);
				}
			}
		});
	}

	@Override
	public Activity getActivity() {
		return this;
	}

}
