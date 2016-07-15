package com.sevenstar.steward2016.adapter;

import java.util.List;

import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.adapter.SGTHolderDefAdapter;
import com.lib.shiguotao.adapter.holder.BaseViewHolder;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.viewholder.DefHolderView;

public class MyCollectionListAdapter extends SGTHolderDefAdapter<JSONObject> {

	public MyCollectionListAdapter(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return new MyCollectionListViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.my_collection_list_item_layout;
	}

	class MyCollectionListViewHolder extends DefHolderView<JSONObject> {

		@ViewInject(R.id.date)
		TextView dateTV;
		@ViewInject(R.id.content)
		TextView contentTV;

		public MyCollectionListViewHolder(View layout) {
			super(layout);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			ViewUtils.setTextData(dateTV, entity.getString("Date"));
			ViewUtils.setTextData(contentTV, entity.getString("Contents"));
		}

	}

}
