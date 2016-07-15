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

public class FitnessHeadDataAdapter extends SGTHolderDefAdapter<JSONObject> {

	public FitnessHeadDataAdapter(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		return new FitnessHeadDataViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fitness_item_layout;
	}

	class FitnessHeadDataViewHolder extends DefHolderView<JSONObject> {
		@ViewInject(R.id.title)
		TextView titleTV;
		@ViewInject(R.id.value)
		TextView valueTV;

		public FitnessHeadDataViewHolder(View layout) {
			super(layout);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			ViewUtils.setTextData(titleTV, entity.getString("title"));
			ViewUtils.setTextData(valueTV, entity.getString("value"));
		}
	}

}
