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
import com.sevenstar.steward2016.adapter.ServiceForTheAgedAdapter;
import com.sevenstar.steward2016.callback.HttpBaseCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.view.LoadDataErrorLayout;

public class ServiceForTheAgedActivity extends BannerBaseActivity {
	@ViewInject(R.id.listview)
	CustomListView listView;
	@ViewInject(R.id.load_data_error_layout)
	LoadDataErrorLayout loadingLayout;
	private ServiceForTheAgedAdapter adapter;
	private String type = "1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_service_for_the_aged_pager);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			setColumnText(extras.getString("title"));
			type = extras.getString("type");
		} else {
			setColumnText("养老服务");
		}
		x.view().inject(this);
		initData();
	}

	private void initData() {
		List<JSONObject> showList = new ArrayList<JSONObject>();
		adapter = new ServiceForTheAgedAdapter(getActivity(), showList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				JSONObject json = adapter.getItem(position);
				Bundle bundle = new Bundle();
				bundle.putString("json", json.toJSONString());
				IntentUtils.goTo(getBaseContext(), HomeServiceContentActivity.class, bundle, Intent.FLAG_ACTIVITY_NEW_TASK);
			}
		});
		new HttpBaseCallBack<JSONObject>(getBaseContext(), showList, adapter, listView, loadingLayout, JSONObject.class,
				GlobalUrl.GET_HOME_SERVICE, "serviceType", type);
	}

	@Override
	public Activity getActivity() {
		return this;
	}

}
