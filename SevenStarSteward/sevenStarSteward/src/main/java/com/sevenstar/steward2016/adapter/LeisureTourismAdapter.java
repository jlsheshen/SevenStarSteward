package com.sevenstar.steward2016.adapter;

import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.adapter.SGTHolderDefAdapter;
import com.lib.shiguotao.adapter.holder.BaseViewHolder;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.viewholder.DefHolderView;
import com.sevenstar.steward2016.constant.GlobalUrl;

public class LeisureTourismAdapter extends SGTHolderDefAdapter<JSONObject> {

	public LeisureTourismAdapter(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return new ChildActivityArrangeViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.activity_leisure_tourism_item_layout;
	}

	class ChildActivityArrangeViewHolder extends DefHolderView<JSONObject> {

		@ViewInject(R.id.leisure_title_tv)
		TextView titleTV;
		@ViewInject(R.id.leisure_tourism_item_one_tv)
		TextView agentTV;
		@ViewInject(R.id.leisure_tourism_item_two_tv)
		TextView timeTV;
		@ViewInject(R.id.leisure_tourism_item_three_tv)
		TextView moneyTV;
		@ViewInject(R.id.leisure_head_iv)
		ImageView headIV;

		public ChildActivityArrangeViewHolder(View layout) {
			super(layout);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			ViewUtils.setTextData(titleTV, entity.getString("Title"));
			ViewUtils.setTextData(agentTV, entity.getString("AgentName"));
			ViewUtils.setTextData(timeTV, entity.getString("Date"));
			ViewUtils.setTextData(moneyTV, entity.getString("Price")+"Ԫ");
			x.image().bind(headIV, GlobalUrl.ADDR+entity.getString("Picture"));
		}

	}

}
