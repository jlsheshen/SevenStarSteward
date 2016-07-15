package com.sevenstar.steward2016.adapter;

import java.util.List;

import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.adapter.SGTHolderDefAdapter;
import com.lib.shiguotao.adapter.holder.BaseViewHolder;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.activity.HourPointContentActivity;
import com.sevenstar.steward2016.adapter.viewholder.DefHolderView;

public class PrivateChefAdapter extends SGTHolderDefAdapter<JSONObject> {

	public PrivateChefAdapter(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return new PrivateChefViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.private_chef_item_layout;
	}

	class PrivateChefViewHolder extends DefHolderView<JSONObject> implements OnClickListener {
		@ViewInject(R.id.private_chef_title_tv)
		TextView titleTV;
		@ViewInject(R.id.private_chef_yuyue_tv)
		TextView yuYueTV;

		public PrivateChefViewHolder(View layout) {
			super(layout);
			yuYueTV.setOnClickListener(this);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			ViewUtils.setTextData(titleTV, entity.getString("Dishes"));
			yuYueTV.setTag(entity);
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			JSONObject json = (JSONObject) v.getTag();
			Bundle ext = new Bundle();
			ext.putInt("type", 2);
			ext.putString("title", json.getString("Dishes"));
			ext.putString("json", json.toJSONString());
			IntentUtils.goTo(context, HourPointContentActivity.class, ext);
		}

	}

}
