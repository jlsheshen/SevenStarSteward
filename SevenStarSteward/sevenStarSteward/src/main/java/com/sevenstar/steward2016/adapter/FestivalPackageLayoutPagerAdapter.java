package com.sevenstar.steward2016.adapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sevenstar.steward2016.view.FestivalPackageContentLayout;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class FestivalPackageLayoutPagerAdapter extends PagerAdapter {

	private Context context;
	private JSONArray jsonArray;
	private JSONObject jsonObject;

	public FestivalPackageLayoutPagerAdapter(Context context, JSONArray jsonArray, JSONObject jsonObject) {
		this.context = context;
		this.jsonArray = jsonArray;
		this.jsonObject = jsonObject;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public View instantiateItem(ViewGroup container, int position) {
		FestivalPackageContentLayout layout = new FestivalPackageContentLayout(context, jsonObject, jsonArray.getJSONObject(position));
		container.addView(layout, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		return layout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		if (jsonArray != null) {
			return jsonArray.size();
		}
		return 0;
	}

}
