package com.sevenstar.steward2016.adapter;

import java.util.List;

import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.adapter.SGTHolderDefAdapter;
import com.lib.shiguotao.adapter.holder.BaseViewHolder;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.viewholder.DefHolderView;

public class SelectHouseTypeAdapterTwo extends SGTHolderDefAdapter<JSONObject> implements OnCheckedChangeListener {

	public int index = -1;

	public SelectHouseTypeAdapterTwo(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return new SelectHouseTypeTwoViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.select_house_type_item_two;
	}

	// implements TextWatche
	public class SelectHouseTypeTwoViewHolder extends DefHolderView<JSONObject> {
		@ViewInject(R.id.service_et_one)
		public EditText oneET;
		@ViewInject(R.id.service_et_two)
		public EditText twoET;
		@ViewInject(R.id.checkbox)
		public CheckBox checkbox;

		public SelectHouseTypeTwoViewHolder(View layout) {
			super(layout);
			checkbox.setOnCheckedChangeListener(SelectHouseTypeAdapterTwo.this);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			if (entity != null) {
				ViewUtils.setTextData(oneET, entity.getString("HouseTypeName"));
				ViewUtils.setTextData(twoET, entity.getString("Area"));
				checkbox.setTag(getIndex());
				if (index != getIndex()) {
					checkbox.setChecked(false);
				}
			}
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked) {
			Object obj = buttonView.getTag();
			if (obj != null) {
				index = (Integer) obj;
			}
			notifyDataSetChanged();
		}
	}

}