package com.sevenstar.steward2016.adapter;

import java.util.List;
import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.adapter.SGTHolderDefAdapter;
import com.lib.shiguotao.adapter.holder.BaseViewHolder;
import android.content.Context;
import android.view.View;

public class SelectCleanEquipmentAdapter extends SGTHolderDefAdapter<JSONObject> {

	public SelectCleanEquipmentAdapter(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return 0;
	}

}
