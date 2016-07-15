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

public class SingleDataAdapter extends SGTHolderDefAdapter<JSONObject> {

	private String key;

	public SingleDataAdapter(Context context, List<JSONObject> list, String key) {
		super(context, list);
		this.key = key;
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		return new FitnessHeadDataViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		return android.R.layout.select_dialog_singlechoice;
	}

	class FitnessHeadDataViewHolder extends DefHolderView<JSONObject> {
		@ViewInject(android.R.id.text1)
		TextView titleTV;

		public FitnessHeadDataViewHolder(View layout) {
			super(layout);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			ViewUtils.setTextData(titleTV, entity.getString(key));
		}
	}

}
