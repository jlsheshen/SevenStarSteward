package com.sevenstar.steward2016.adapter;

import java.util.List;

import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.adapter.SGTHolderDefAdapter;
import com.lib.shiguotao.adapter.holder.BaseViewHolder;
import com.lib.shiguotao.utils.LogUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.viewholder.DefHolderView;

public class ServiceForTheAgedAdapter extends SGTHolderDefAdapter<JSONObject> {

	public ServiceForTheAgedAdapter(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return new ServiceForTheAgedViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.service_for_the_aged_item_layout;
	}

	class ServiceForTheAgedViewHolder extends DefHolderView<JSONObject> {

		@ViewInject(R.id.title_tv)
		TextView titleTV;
		@ViewInject(R.id.content_tv)
		TextView contentTV;
		@ViewInject(R.id.money_tv)
		TextView moneyTV;

		public ServiceForTheAgedViewHolder(View layout) {
			super(layout);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			LogUtils.e("content", entity);
			ViewUtils.setTextData(titleTV, entity.getString("TypeName"));
			ViewUtils.setTextData(moneyTV, entity.getString("Price")+"Ԫ");
			ViewUtils.setTextData(contentTV, entity.getString("Presentation"));
			
		}

	}

}
