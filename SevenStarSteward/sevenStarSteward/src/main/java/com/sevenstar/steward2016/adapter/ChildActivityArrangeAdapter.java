package com.sevenstar.steward2016.adapter;

import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.adapter.SGTHolderDefAdapter;
import com.lib.shiguotao.adapter.holder.BaseViewHolder;
import com.lib.shiguotao.manager.AppManager;
import com.lib.shiguotao.utils.DialogUtils;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.activity.ActivityContentActivity;
import com.sevenstar.steward2016.adapter.viewholder.DefHolderView;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;

public class ChildActivityArrangeAdapter extends SGTHolderDefAdapter<JSONObject>  {

	private int type;

	public ChildActivityArrangeAdapter(Context context, List<JSONObject> list, int type) {
		super(context, list);
		this.type = type;
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return new ChildActivityArrangeViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.activity_arrange_child_item_layout;
	}

	class ChildActivityArrangeViewHolder extends DefHolderView<JSONObject> {

		@ViewInject(R.id.activity_head_iv)
		ImageView headIV;
		@ViewInject(R.id.activity_title_tv)
		TextView titleTV;
		@ViewInject(R.id.activity_time_tv)
		TextView timeTV;
		@ViewInject(R.id.activity_price_tv)
		TextView priceTV;
		@ViewInject(R.id.activity_place_tv)
		TextView placeTV;
		@ViewInject(R.id.activity_submit_bt)
		View submitBT;

		public ChildActivityArrangeViewHolder(View layout) {
			super(layout);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			ViewUtils.setTextData(titleTV, entity.getString("Title"));
			ViewUtils.setTextData(timeTV, "时间:\t" + entity.getString("Date"));
			ViewUtils.setTextData(priceTV, "价格:\t" + entity.getIntValue("Price") + "元");
			ViewUtils.setTextData(placeTV, "地点:\t" + entity.getString("Address"));
			x.image().bind(headIV, GlobalUrl.ADDR + entity.getString("Picture"));

		}

	}

}
