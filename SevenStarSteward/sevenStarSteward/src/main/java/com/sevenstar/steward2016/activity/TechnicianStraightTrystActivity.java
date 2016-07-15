package com.sevenstar.steward2016.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.view.listview.CustomListView;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.TechnicianStraightTrystAdapter;
import com.sevenstar.steward2016.callback.HttpBaseCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.view.LoadDataErrorLayout;

public class TechnicianStraightTrystActivity extends BannerBaseActivity implements OnClickListener {
	@ViewInject(R.id.listview)
	CustomListView listView;
	@ViewInject(R.id.load_data_error_layout)
	LoadDataErrorLayout loadingLayout;
	@ViewInject(R.id.search_bt)
	View serachBT;
	@ViewInject(R.id.search_et)
	EditText serachET;
	private TechnicianStraightTrystAdapter adapter;
	private String classKey = "";
	private String searchKey = "";
	private HttpBaseCallBack<JSONObject> callBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_technician_straight_tryst_pager);
		setColumnText("直约技师");
		x.view().inject(this);
		serachBT.setOnClickListener(this);
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			classKey = extras.getString("classKey");
		}
		List<JSONObject> showList = new ArrayList<JSONObject>();
		adapter = new TechnicianStraightTrystAdapter(getActivity(), showList);
		listView.setAdapter(adapter);
		callBack = new HttpBaseCallBack<JSONObject>(getBaseContext(), showList, adapter, listView, loadingLayout, JSONObject.class,
				GlobalUrl.GET_MAIN_LIVE_SERVICE, "serviceType", "7", "classKey", classKey, "searchKey", searchKey);
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		searchKey = serachET.getText().toString();
		callBack.searchData("serviceType", "7", "classKey", classKey, "searchKey", searchKey);
	}

}
