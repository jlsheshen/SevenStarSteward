package com.sevenstar.steward2016.adapter;

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

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public class CommunityActivityAdapter extends SGTHolderDefAdapter<JSONObject> {

	public CommunityActivityAdapter(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return new MyShopOrderViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.community_activity_item_layout;
	}

	class MyShopOrderViewHolder extends DefHolderView<JSONObject> {

		@ViewInject(R.id.title)
		TextView titleTV;
		@ViewInject(R.id.comminity_name)
		TextView ComminityNameTV;

		public MyShopOrderViewHolder(View layout) {
			super(layout);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			ViewUtils.setTextData(titleTV, entity.getString("Name"));
			ViewUtils.setTextData(ComminityNameTV, entity.getString("Date"));
			//x.image().bind(imageIV, GlobalUrl.ADDR + entity.getString("FileFolder"));
		}

	}

}
