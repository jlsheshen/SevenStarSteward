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

public class MyHeTongAdapter extends SGTHolderDefAdapter<JSONObject> {

	public MyHeTongAdapter(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return new MyHeTongViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.my_ping_gu_item_layout;
	}

	class MyHeTongViewHolder extends DefHolderView<JSONObject> {

		@ViewInject(R.id.one_text)
		TextView oneTV;
		@ViewInject(R.id.two_text)
		TextView twoTV;
		@ViewInject(R.id.three_text)
		TextView threeTV;

		public MyHeTongViewHolder(View layout) {
			super(layout);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			ViewUtils.setTextData(oneTV, entity.getString("SerialNumber"));
			ViewUtils.setTextData(twoTV, entity.getString("ExpireDate"));
			ViewUtils.setTextData(threeTV, entity.getString("StatusName"));
		}

	}

}
