package com.sevenstar.steward2016.adapter;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.adapter.SGTHolderDefAdapter;
import com.lib.shiguotao.adapter.holder.BaseViewHolder;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.viewholder.DefHolderView;

public class SelectHouseTypeAdapter extends SGTHolderDefAdapter<JSONObject> implements OnItemClickListener {

	public int index = -1;

	public SelectHouseTypeAdapter(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return new FeatureOrderViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.house_type_item_layout;
	}

	class FeatureOrderViewHolder extends DefHolderView<JSONObject> {
		private TextView titleTV;

		public FeatureOrderViewHolder(View layout) {
			super(layout);
			titleTV = (TextView) layout;
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			ViewUtils.setTextData(titleTV, entity.getString("HouseTypeName"));
			if (index == getIndex()) {
				titleTV.setBackgroundResource(R.drawable.shape_house_type_item_background_two);
				titleTV.setTextColor(context.getResources().getColor(R.color._5F264F));
			} else {
				titleTV.setBackgroundResource(R.drawable.shape_house_type_item_background_one);
				titleTV.setTextColor(context.getResources().getColor(R.color._282828));
			}
		}

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		// TODO Auto-generated method stub
		if (index == position) {
			index = -1;
		} else {
			index = position;
		}
		notifyDataSetChanged();
	}

}