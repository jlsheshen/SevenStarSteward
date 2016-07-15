package com.sevenstar.steward2016.adapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.view.wheel.adapters.AbstractWheelTextAdapter;

import android.content.Context;

public class SelectAreaAdapter extends AbstractWheelTextAdapter {

	private JSONArray items = null;
	private String key = "";

	public SelectAreaAdapter(Context context, JSONArray items, String key) {
		super(context);
		this.items = items;
		this.key = key;
	}

	@Override
	public CharSequence getItemText(int index) {
		if (index >= 0 && index < items.size()) {
			return items.getJSONObject(index).getString(key);
		}
		return "";

	}

	public JSONObject getJSONObject(int index) {
		if (items != null) {
			return items.getJSONObject(index);
		}
		return new JSONObject();
	}

	@Override
	public int getItemsCount() {
		// TODO Auto-generated method stub
		return items != null ? items.size() : 0;
	}
}
