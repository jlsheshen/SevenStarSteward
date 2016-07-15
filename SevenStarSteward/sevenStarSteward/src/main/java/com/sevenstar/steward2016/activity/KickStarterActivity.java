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
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.view.listview.CustomListView;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.KickStarterAdapter;
import com.sevenstar.steward2016.adapter.RentInfoAdapter;
import com.sevenstar.steward2016.callback.HttpBaseCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.view.LoadDataErrorLayout;

public class KickStarterActivity extends BannerBaseActivity {
	@ViewInject(R.id.listview)
	CustomListView listView;
	@ViewInject(R.id.load_data_error_layout)
	LoadDataErrorLayout loadingLayout;
	private KickStarterAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_kick_starter_pager);
		setColumnText("ÖÚ³ïÆ½Ì¨");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		List<JSONObject> showList = new ArrayList<JSONObject>();
		adapter = new KickStarterAdapter(getActivity(), showList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				position -= listView.getHeaderViewsCount();
				if(position>=0){
					JSONObject json = adapter.getItem(position);
					Bundle extras = new Bundle();
					extras.putString("json", json.toJSONString());
					extras.putInt("serviceType", 3);
					IntentUtils.goTo(getActivity(), ShopContentActivity.class, extras);
				}

			}
		});
		new HttpBaseCallBack<JSONObject>(getBaseContext(), showList, adapter, listView, loadingLayout, JSONObject.class,
				GlobalUrl.GET_HAPPY_SHOP_KICK_STARTER_URL);
	}

	@Override
	public Activity getActivity() {
		return this;
	}

}
