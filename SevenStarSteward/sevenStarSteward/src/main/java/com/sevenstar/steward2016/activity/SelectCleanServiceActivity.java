package com.sevenstar.steward2016.activity;

import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.SelectCleanServiceAdapter;
import com.sevenstar.steward2016.adapter.SelectCleanServiceAdapter.FeatureOrderViewHolder;

public class SelectCleanServiceActivity extends BannerBaseActivity implements OnClickListener {
	@ViewInject(R.id.listview)
	ListView listView;
	@ViewInject(R.id.banner_Base_activity_head_right_text_view)
	TextView rightTV;
	private List<JSONObject> listData;
	private SelectCleanServiceAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setColumnText("选择清洁服务");
		setBaseContextView(R.layout.activity_select_clean_service_pager);
		x.view().inject(this);
		Bundle bundle = IntentUtils.getIntentBundle(this);
		if (bundle != null) {
			listData = JSONObject.parseArray(bundle.getString("data"), JSONObject.class);
		}
		adapter = new SelectCleanServiceAdapter(this, listData);
		listView.setAdapter(adapter);
		rightTV.setText("完成");
		rightTV.setVisibility(View.VISIBLE);
		rightTV.setOnClickListener(this);
	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (adapter.index != -1) {
			Intent data = new Intent();
			View view = listView.getChildAt(adapter.index % listView.getChildCount());
			FeatureOrderViewHolder viewHolder = (FeatureOrderViewHolder) view.getTag();
			JSONObject json = adapter.getItem(adapter.index);
			json.put("Price", viewHolder.oneET.getText().toString().replace("元", ""));
			json.put("Target", viewHolder.threeET.getText().toString());
			json.put("Hours", viewHolder.fourET.getText().toString().replace("小时", ""));
			data.putExtra("data", json.toJSONString());
			setResult(0x2000, data);
		}
		finish();
	}

}
