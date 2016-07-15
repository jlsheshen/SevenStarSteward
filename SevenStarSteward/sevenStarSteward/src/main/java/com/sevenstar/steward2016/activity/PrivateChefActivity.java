package com.sevenstar.steward2016.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.lib.shiguotao.view.listview.CustomListView;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.PrivateChefAdapter;
import com.sevenstar.steward2016.callback.HttpBaseCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.view.LoadDataErrorLayout;

public class PrivateChefActivity extends BannerBaseActivity implements OnClickListener {
	@ViewInject(R.id.listview)
	CustomListView listView;
	@ViewInject(R.id.load_data_error_layout)
	LoadDataErrorLayout loadingLayout;
	// @ViewInject(R.id.private_chef_button)
	// Button button;
	@ViewInject(R.id.banner_Base_activity_head_right_text_view)
	TextView rightTV;
	private PrivateChefAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_private_chef_pager);
		setColumnText("私人厨师");
		x.view().inject(this);
		// button.setOnClickListener(this);
		initData();
		ViewUtils.setVisibility(View.VISIBLE, rightTV);
		ViewUtils.setOnClickListener(rightTV, this);
		ViewUtils.setTextData(rightTV, "直约技师");
	}

	private void initData() {
		List<JSONObject> showList = new ArrayList<JSONObject>();
		adapter = new PrivateChefAdapter(getActivity(), showList);
		listView.setAdapter(adapter);
		new HttpBaseCallBack<JSONObject>(getBaseContext(), showList, adapter, listView, loadingLayout, JSONObject.class,
				GlobalUrl.GET_MAIN_LIVE_SERVICE, "serviceType", "6", "childType", "3");
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		bundle.putString("classKey", "厨师");
		IntentUtils.goTo(this, TechnicianStraightTrystActivity.class, bundle);
	}

}
