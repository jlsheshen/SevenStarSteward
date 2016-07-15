package com.sevenstar.steward2016.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.view.listview.CustomListView;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.NursingInstitutionForTheAgedAdapter;
import com.sevenstar.steward2016.callback.HttpBaseCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.view.LoadDataErrorLayout;

public class NursingInstitutionForTheAgedActivity extends BannerBaseActivity {
	@ViewInject(R.id.listview)
	CustomListView listView;
	@ViewInject(R.id.load_data_error_layout)
	LoadDataErrorLayout loadingLayout;
	private NursingInstitutionForTheAgedAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_nursing_institution_for_the_aged_pager);
		setColumnText("养老机构");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		List<JSONObject> showList = new ArrayList<JSONObject>();
		// TODO Auto-generated method stub
		adapter = new NursingInstitutionForTheAgedAdapter(getActivity(), showList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				position -= listView.getHeaderViewsCount();
				if(position>=0){
					JSONObject json = adapter.getItem(position);
					Bundle extras = new Bundle();
					extras.putString("json", json.toJSONString());
					IntentUtils.goTo(getActivity(), NursingInstitutionForTheAgedContentActivity.class, extras);
				}

			}
		});
		new HttpBaseCallBack<JSONObject>(getBaseContext(), showList, adapter, listView, loadingLayout, JSONObject.class,
				GlobalUrl.GET_MAIN_LIVE_SERVICE, "serviceType", "2");
	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}

}
