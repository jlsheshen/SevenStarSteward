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
import com.sevenstar.steward2016.adapter.SelectHouseTypeAdapterTwo;
import com.sevenstar.steward2016.adapter.SelectHouseTypeAdapterTwo.SelectHouseTypeTwoViewHolder;

public class SelectHouseTypeActivity extends BannerBaseActivity implements OnClickListener {
	@ViewInject(R.id.listview)
	ListView listview;
	@ViewInject(R.id.banner_Base_activity_head_right_text_view)
	TextView rightTV;
	private List<JSONObject> listData;
	private SelectHouseTypeAdapterTwo adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setColumnText("选择户型");
		setBaseContextView(R.layout.activity_select_house_type_layout);
		x.view().inject(this);
		Bundle bundle = IntentUtils.getIntentBundle(this);
		if (bundle != null) {
			listData = JSONObject.parseArray(bundle.getString("data"), JSONObject.class);
		}
		adapter = new SelectHouseTypeAdapterTwo(this, listData);
		listview.setAdapter(adapter);
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
			View view = listview.getChildAt(adapter.index % listview.getChildCount());
			SelectHouseTypeTwoViewHolder viewHolder = (SelectHouseTypeTwoViewHolder) view.getTag();
			JSONObject json = adapter.getItem(adapter.index);
			json.put("HouseTypeName", viewHolder.oneET.getText().toString());
			json.put("Area", viewHolder.twoET.getText().toString());
			data.putExtra("data", json.toJSONString());
			setResult(0x1000, data);
		}
		finish();
	}

}
